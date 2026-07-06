CREATE TABLE IF NOT EXISTS app_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    secret VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS chat_rooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS room_users (
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (room_id, user_id),
    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES chat_rooms(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_users(id)
);

CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(255),
    time BIGINT,
    sender_id BIGINT,
    room_id BIGINT,
    CONSTRAINT fk_msg_sender FOREIGN KEY (sender_id) REFERENCES app_users(id),
    CONSTRAINT fk_msg_room FOREIGN KEY (room_id) REFERENCES chat_rooms(id)
);

INSERT INTO app_users (username, secret)
VALUES ('SummaryBot', 'SERVICE_ACCOUNT_NO_LOGIN');