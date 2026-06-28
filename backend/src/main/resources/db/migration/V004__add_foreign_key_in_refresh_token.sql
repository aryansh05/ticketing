ALTER TABLE refresh_tokens
ADD CONSTRAINT fk_refresh_token_user_id
FOREIGN KEY (user_id)
REFERENCES users(user_id)
ON DELETE CASCADE;