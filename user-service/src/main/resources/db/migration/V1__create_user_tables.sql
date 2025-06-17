CREATE TABLE user_profiles (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    role VARCHAR(50) NOT NULL CHECK (role IN ('ROLE_BIDDER', 'ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_USER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE balance_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    type VARCHAR(10) NOT NULL CHECK (type IN ('CREDIT', 'DEBIT')),
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_balance_transactions_user_id FOREIGN KEY (user_id) REFERENCES user_profiles(id)
);
--CREATE TABLE user_profiles (
--    id UUID PRIMARY KEY,
--    name VARCHAR(255),
--    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);
--
--CREATE TABLE balance_transactions (
--    id UUID PRIMARY KEY,
--    user_id UUID REFERENCES user_profiles(id),
--    amount DECIMAL(10,2) NOT NULL,
--    type VARCHAR(10) NOT NULL CHECK (type IN ('CREDIT', 'DEBIT')),
--    description TEXT,
--    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);
