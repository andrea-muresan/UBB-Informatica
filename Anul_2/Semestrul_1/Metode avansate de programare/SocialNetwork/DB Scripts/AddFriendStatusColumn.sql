SELECT * FROM friendships

ALTER TABLE friendships
ADD friend_request_status VARCHAR(15)

UPDATE friendships
SET friend_request_status = 'ACCEPTED'