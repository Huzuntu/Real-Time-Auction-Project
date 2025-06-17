CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES user_profiles(id),
    message TEXT NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('EMAIL', 'SMS', 'PUSH')),
    delivered BOOLEAN NOT NULL DEFAULT FALSE,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP
);

CREATE INDEX idx_notifications_user ON notifications(user_id);