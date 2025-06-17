CREATE TABLE payments (
    id UUID PRIMARY KEY,
    auction_id UUID NOT NULL,
    user_id UUID NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED')),
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    retry_count INT DEFAULT 0
);

CREATE INDEX idx_payments_auction ON payments(auction_id);
CREATE INDEX idx_payments_user ON payments(user_id);