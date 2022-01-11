DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions (id UUID DEFAULT random_uuid() PRIMARY KEY , reference VARCHAR(6) UNIQUE, account_iban VARCHAR(24), date TIMESTAMP, amount NUMERIC(20, 2), fee NUMERIC(20, 2), description VARCHAR(255));
