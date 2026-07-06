package ru.foxscribe.simplechat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.foxscribe.simplechat.dto.CreateMessageRequestDto;
import ru.foxscribe.simplechat.dto.MessageDto;
import ru.foxscribe.simplechat.service.MessageService;
import ru.foxscribe.simplechat.service.SummaryService;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Endpoints for retrieving and sending chat messages")
public class MessageController {
    private final MessageService messageService;
    private final SummaryService summaryService;

    @GetMapping("/get")
    @Operation(
            summary = "Get messages in a chat room",
            description = "Returns a list of messages since a given time in the selected chat room."
    )
    public List<MessageDto> get(
            @Parameter(description = "ID of the chat room", required = true)
            @RequestParam("room")
            Long roomId,
            @Parameter(description = "Time of the first message (epoch, seconds)", required = true)
            @RequestParam("since")
            Long time,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return messageService.get(roomId, time, userDetails.getId());
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a new message"
    )
    public void create(
            @RequestBody CreateMessageRequestDto request,
            @AuthenticationPrincipal CustomUserDetails user) {
        messageService.create(request.getRoomId(), request.getText(), user.getId());
    }

    @GetMapping("/page")
    @Operation(
            summary = "Get messages in a chat room",
            description = "Returns  messages from the selected chat room. Paginated!"
    )
    public List<MessageDto> page(
            @Parameter(description = "ID of the chat room", required = true)
            @RequestParam("room")
            Long roomId,
            @Parameter(description = "Size of the page", required = true)
            @RequestParam("size")
            int size,
            @Parameter(description = "Page number; enumeration starts with zero", required = true)
            @RequestParam("page")
            int page,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return messageService.getMessagesPage(roomId, size, page, userDetails.getId());
    }

    @GetMapping("/summary")
    @Operation(
            summary = "Summarize the room"
    )
    public void summary(
            @Parameter(description = "ID of the chat room", required = true)
            @RequestParam("room")
            Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        summaryService.generateSummary(userDetails.getId(), roomId);
    }
}