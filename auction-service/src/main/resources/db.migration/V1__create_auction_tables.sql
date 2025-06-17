CREATE TABLE auctions (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_price DECIMAL(10,2) NOT NULL,
    min_increment DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_by UUID NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    current_bid DECIMAL(10,2) DEFAULT 0.00,
    winner_id UUID
);

CREATE INDEX idx_auctions_status ON auctions(status);