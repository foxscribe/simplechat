package ru.foxscribe.simplechat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.foxscribe.simplechat.model.Message;
import ru.foxscribe.simplechat.repository.MessageRepository;
import ru.foxscribe.simplechat.repository.RoomRepository;
import ru.foxscribe.simplechat.repository.UserRepository;
import ru.foxscribe.simplechat.util.OllamaProperties;
import ru.foxscribe.simplechat.util.exceptions.AccessDeniedException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
@EnableConfigurationProperties(OllamaProperties.class)
public class SummaryService {

    private final static String BOT_NAME = "SummaryBot";

    private final static String SYSTEM_PROMPT = """
            Ты — умный ассистент для анализа групповых чатов.
            Твоя задача — читать переписку и создавать структурированное резюме.
            Выдели главные обсуждаемые темы, принятые решения и поставленные задачи.
            Пиши кратко, по делу, строго на русском языке. Используй нумерованные списки.
            """;

    private final MessageRepository messages;
    private final UserRepository users;
    private final RoomRepository rooms;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final OllamaProperties properties;

    @Transactional
    public void generateSummary(Long userId, Long roomId) {
        log.info("received summary request");

        var user = users.findById(userId).orElseThrow();
        var room = rooms.findById(roomId).orElseThrow();
        var bot = users.findByUsername(BOT_NAME);
        if (!user.getRooms().contains(room)) {
            throw new AccessDeniedException("You are not a member of this room");
        }
        log.info("validated user room and bot");

        Pageable pageable = PageRequest.of(0, 100);
        var messages = this.messages.retrieveMessagesPaginated(room.getId(), pageable);
        var requestDto = createChatRequest(messages);
        log.info("fetched {} messages", messages.size());

        try {
            String jsonBody = objectMapper.writeValueAsString(requestDto);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(properties.getBaseUrl() + "/api/chat"))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(properties.getTimeoutSeconds()))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            log.info("sending request to ollama; it can take awhile!");
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (httpResponse.statusCode() != 200) {
                throw new RuntimeException("Ollama API error: " + httpResponse.statusCode() + " - "
                        + httpResponse.body());
            }
            log.info("ollama finished. posting summary to chat!");

            ChatResponse responseDto = objectMapper.readValue(
                    httpResponse.body(),
                    ChatResponse.class
            );
            log.info("mapped response");

            var message = new Message();
            message.setRoom(room);
            message.setText(responseDto.message().content());
            message.setTime(Instant.now().getEpochSecond());
            message.setSender(bot);
            this.messages.save(message);
            log.info("summary posted");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to process JSON for Ollama API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Request to Ollama was interrupted", e);
        } catch (Exception e) {
            log.warn("summary failed :(", e);
            throw new RuntimeException("Failed to communicate with Ollama", e);
        }
    }

    private @NonNull ChatRequest createChatRequest(List<Message> messages) {
        StringBuilder log = new StringBuilder();
        for (var m : messages) {
            if (Objects.equals(m.getSender().getUsername(), BOT_NAME)) {
                continue;
            }
            log.append(String.format("[%s] %s: \"%s\"",
                    Instant.ofEpochSecond(m.getTime()).toString(),
                    m.getSender().getUsername(), m.getText()));
        }
        String userMessage = "Вот переписка из чата:\n\n" + log + "\n\nСделай резюме:";

        return new ChatRequest(
                properties.getModel(),
                false, // stream = false
                Map.of("temperature", 0.3),
                List.of(
                        new ChatRequest.Message("system", SYSTEM_PROMPT),
                        new ChatRequest.Message("user", userMessage)
                )
        );
    }

    private record ChatRequest(
            String model,
            boolean stream,
            Map<String, Object> options,
            List<Message> messages
    ) {
        public record Message(String role, String content) {
        }
    }

    private record ChatResponse(Message message, boolean done) {
        public record Message(String role, String content) {
        }
    }
}
