CREATE TABLE bids (
    id UUID PRIMARY KEY,
    auction_id UUID NOT NULL,
    user_id UUID NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bid_auction FOREIGN KEY(auction_id) REFERENCES auctions(id),
    CONSTRAINT fk_bid_user FOREIGN KEY(user_id) REFERENCES user_profiles(id)
);

CREATE INDEX idx_bids_auction ON bids(auction_id);
CREATE INDEX idx_bids_user ON bids(user_id);