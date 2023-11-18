-- User table
CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   	first_name CHARACTER VARYING(50) NOT NULL,
   	last_name CHARACTER VARYING(50) NOT NULL,
	email CHARACTER VARYING(50) NOT NULL
);

-- Friendship table
CREATE TABLE friendships (
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user1_id BIGINT,
    user2_id BIGINT,
	friends_from TIMESTAMP,
    FOREIGN KEY (user1_id) REFERENCES users(id),
    FOREIGN KEY (user2_id) REFERENCES users(id)
);
