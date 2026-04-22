CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');

CREATE TYPE occupation_enum AS ENUM (
    'JUNIOR',
    'SENIOR',
    'SECRETARY',
    'TREASURER',
    'VICE_PRESIDENT',
    'PRESIDENT'
);

CREATE TYPE relationship_enum AS ENUM (
    'FAMILY',
    'FRIEND',
    'COLLEAGUE',
    'OTHER'
);

CREATE TABLE members
(
    id           VARCHAR PRIMARY KEY,
    first_name   VARCHAR(100)        NOT NULL,
    last_name    VARCHAR(100)        NOT NULL,
    birth_date   DATE                NOT NULL,
    gender       gender_enum         NOT NULL,
    address      TEXT,
    profession   VARCHAR(100),
    phone_number VARCHAR(20),
    email        VARCHAR(150) UNIQUE NOT NULL,
    occupation   occupation_enum     NOT NULL,
    join_date    DATE                NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE collectivities
(
    id                  VARCHAR PRIMARY KEY,
    name                VARCHAR(150) UNIQUE NOT NULL,
    location            VARCHAR(150)        NOT NULL,
    specialty           VARCHAR(100)        NOT NULL,
    creation_date       DATE                NOT NULL DEFAULT CURRENT_DATE,
    federation_approval BOOLEAN             NOT NULL DEFAULT FALSE,
    annual_fee          NUMERIC(10, 2)               DEFAULT 0.00
);

CREATE TABLE memberships
(
    id              SERIAL PRIMARY KEY,
    member_id       VARCHAR NOT NULL,
    collectivity_id VARCHAR NOT NULL,
    join_date       DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id)
);

CREATE TABLE member_referees
(
    member_id    VARCHAR,
    referee_id   VARCHAR,
    relationship relationship_enum NOT NULL,
    PRIMARY KEY (member_id, referee_id),
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (referee_id) REFERENCES members (id)
);

CREATE TABLE mandates
(
    id              SERIAL PRIMARY KEY,
    member_id       VARCHAR         NOT NULL,
    collectivity_id VARCHAR         NOT NULL,
    role            occupation_enum NOT NULL,
    year            INT             NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id),
    UNIQUE (collectivity_id, role, year)
);

CREATE TABLE federation_mandates
(
    id         SERIAL PRIMARY KEY,
    member_id  VARCHAR         NOT NULL,
    role       occupation_enum NOT NULL,
    start_year INT             NOT NULL,
    end_year   INT             NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members (id),
    CHECK (end_year = start_year + 2)
);

CREATE TABLE payments
(
    id                    SERIAL PRIMARY KEY,
    member_id             VARCHAR,
    collectivity_id       VARCHAR,
    registration_fee_paid BOOLEAN        NOT NULL,
    membership_dues_paid  BOOLEAN        NOT NULL,
    amount                NUMERIC(10, 2) NOT NULL,
    payment_date          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id)
);

ALTER TABLE collectivities
    ADD COLUMN number VARCHAR(50) UNIQUE;


CREATE TABLE cash_accounts
(
    id              VARCHAR(36) PRIMARY KEY,
    holder_name     VARCHAR(255) NOT NULL,
    balance         DECIMAL(15, 2) DEFAULT 0,
    collectivity_id VARCHAR(36)  NOT NULL,
    created_at      TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id),
    CONSTRAINT unique_cash_per_collectivity UNIQUE (collectivity_id)
);

CREATE TABLE bank_accounts
(
    id              VARCHAR(36) PRIMARY KEY,
    holder_name     VARCHAR(255) NOT NULL,
    balance         DECIMAL(15, 2) DEFAULT 0,
    collectivity_id VARCHAR(36)  NOT NULL,
    created_at      TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    bank_name       VARCHAR(50)  NOT NULL,
    bank_code       VARCHAR(5)   NOT NULL,
    branch_code     VARCHAR(5)   NOT NULL,
    account_number  VARCHAR(11)  NOT NULL,
    rib_key         VARCHAR(2)   NOT NULL,
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id)
);

CREATE TABLE mobile_money_accounts
(
    id              VARCHAR(36) PRIMARY KEY,
    holder_name     VARCHAR(255) NOT NULL,
    balance         DECIMAL(15, 2) DEFAULT 0,
    collectivity_id VARCHAR(36)  NOT NULL,
    created_at      TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    mobile_service  VARCHAR(50)  NOT NULL,
    phone_number    VARCHAR(15)  NOT NULL,
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id)
);

CREATE TABLE membership_fees
(
    id              VARCHAR(36) PRIMARY KEY,
    collectivity_id VARCHAR(36)    NOT NULL,
    eligible_from   DATE           NOT NULL,
    frequency       VARCHAR(20)    NOT NULL,
    amount          DECIMAL(15, 2) NOT NULL,
    label           VARCHAR(255),
    status          VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id)
);

CREATE TABLE member_payments
(
    id                  VARCHAR(36) PRIMARY KEY,
    member_id           VARCHAR(36)    NOT NULL,
    membership_fee_id   VARCHAR(36)    NOT NULL,
    amount              DECIMAL(15, 2) NOT NULL,
    payment_mode        VARCHAR(50)    NOT NULL,
    account_credited_id VARCHAR(36)    NOT NULL,
    payment_date        TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    federation_share    DECIMAL(15, 2) DEFAULT 0,
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (membership_fee_id) REFERENCES membership_fees (id)
);

CREATE TABLE transactions
(
    id                  VARCHAR(36) PRIMARY KEY,
    collectivity_id     VARCHAR(36)    NOT NULL,
    member_id           VARCHAR(36)    NOT NULL,
    amount              DECIMAL(15, 2) NOT NULL,
    payment_mode        VARCHAR(50)    NOT NULL,
    account_credited_id VARCHAR(36)    NOT NULL,
    transaction_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type                VARCHAR(50)    NOT NULL,
    FOREIGN KEY (collectivity_id) REFERENCES collectivities (id),
    FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE INDEX idx_bank_accounts_collectivity ON bank_accounts (collectivity_id);
CREATE INDEX idx_mobile_accounts_collectivity ON mobile_money_accounts (collectivity_id);
CREATE INDEX idx_membership_fees_collectivity ON membership_fees (collectivity_id);
CREATE INDEX idx_member_payments_member ON member_payments (member_id);
CREATE INDEX idx_transactions_collectivity_date ON transactions (collectivity_id, transaction_date);