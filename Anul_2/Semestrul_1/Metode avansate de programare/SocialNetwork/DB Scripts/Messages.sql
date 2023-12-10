CREATE TABLE messages (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    from_user_id BIGINT,
    message_text VARCHAR(1000) NOT NULL,
    date_time TIMESTAMP,
    FOREIGN KEY (from_user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE message_recipients (
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	to_user_id BIGINT,
	message_id BIGINT,
	reply_to_message_id BIGINT,
	FOREIGN KEY (to_user_id) REFERENCES users(id) ON DELETE CASCADE,
	FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
	FOREIGN KEY (reply_to_message_id) REFERENCES messages(id) ON DELETE CASCADE
);