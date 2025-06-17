#!/bin/bash
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE authdb;
    CREATE DATABASE userdb;
    CREATE DATABASE auctiondb;
    CREATE DATABASE biddb;
    CREATE DATABASE paymentdb;
    CREATE DATABASE notificationdb;
EOSQL