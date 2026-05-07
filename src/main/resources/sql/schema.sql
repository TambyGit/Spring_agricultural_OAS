create type payment_mode as enum (
    'BANK_TRANSFER',
    'MOBILE_BANKING',
    'CASH');

create type bank_name as enum (
    'BRED',
    'MCB',
    'BMOI',
    'BOA',
    'BGFI',
    'AFG',
    'ACCES_BAQUE',
    'BAOBAB',
    'SIPEM');


create type gender as enum ('MALE', 'FEMALE');

create type frequency as enum (
    'WEEKLY',
    'MONTHLY',
    'ANNUALLY',
    'PUNCTUALLY');

create type activity_status as enum (
    'ACTIVE',
    'INACTIVE');

create type member_occupation as enum ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

create type transaction_type as enum (
    'IN',
    'OUT');

create type mobile_banking_service as enum (
    'ORANGE_MONEY',
    'MVOLA',
    'AIRTEL_MONEY');

create type activity_type as enum ('MEETING', 'TRAINING', 'PUNCTUAL');

create type attendance_status as enum ('PRESENT', 'ABSENT', 'UNDEFINED');

create table if not exists "member"
(
    id                    varchar primary key,
    first_name            varchar,
    last_name             varchar,
    birth_date            date,
    gender                gender,
    address               varchar,
    profession            varchar,
    phone_number          varchar,
    email                 varchar,
    occupation            member_occupation,
    registration_fee_paid boolean,
    membership_dues_paid  boolean
);

create table if not exists "membership_fee"
(
    id              varchar primary key,
    label           varchar,
    amount          numeric(12, 2),
    eligible_from   date,
    status          activity_status,
    frequency       frequency,
    collectivity_id varchar references "collectivity" (id)
);

create table if not exists "member_payment"
(
    id                   varchar primary key,
    amount               numeric(12, 2),
    creation_date        date,
    member_debited_id    varchar references member ("id"),
    membership_fee_id    varchar references membership_fee ("id"),
    payment_mode         payment_mode,
    financial_account_id varchar
);

create table if not exists "collectivity"
(
    id                varchar primary key,
    name              varchar,
    number            integer,
    location          varchar,
    specialization    varchar,
    president_id      varchar references "member" (id),
    vice_president_id varchar references "member" (id),
    treasurer_id      varchar references "member" (id),
    secretary_id      varchar references "member" (id)
);

create table if not exists "collectivity_member"
(
    id              varchar primary key,
    member_id       varchar references "member" (id),
    collectivity_id varchar references "collectivity" (id)
);

create table if not exists "member_referee"
(
    id                 varchar primary key,
    member_refereed_id varchar references "member" (id),
    member_referee_id  varchar references "member" (id)
);

create table if not exists cash_account
(
    id              varchar primary key,
    collectivity_id varchar references collectivity (id)
);

create table if not exists "bank_account"
(
    id              varchar primary key,
    holder_name     varchar,
    bank_name       bank_name,
    bank_code       integer,
    branch_code     integer,
    account_number  integer,
    key             integer,
    collectivity_id varchar references "collectivity" (id)
);
create table if not exists "mobile_banking_account"
(
    id              varchar primary key,
    holder_name     varchar,
    service         mobile_banking_service,
    mobile_number   varchar,
    collectivity_id varchar references "collectivity" (id)
);

create table if not exists "bank_account"
(
    id              varchar primary key,
    holder_name     varchar,
    bank_name       bank_name,
    bank_code       integer,
    branch_code     integer,
    account_number  integer,
    key             integer,
    collectivity_id varchar references "collectivity" (id)
);

create table if not exists "transaction"
(
    id                   varchar primary key,
    amount               numeric(12, 2),
    creation_date        date,
    transaction_type     transaction_type,
    financial_account_id varchar
);
alter table if exists "transaction"
    add column if not exists member_debited_id varchar references member ("id");

-- ilay 6mai
UPDATE membership_fee SET status = 'INACTIVE' WHERE id = 'cot-1';

CREATE TABLE activity (
                          id VARCHAR PRIMARY KEY,
                          label VARCHAR NOT NULL,
                          date DATE NOT NULL,
                          collectivity_id VARCHAR REFERENCES collectivity(id)
);

CREATE TYPE attendance_status AS ENUM ('PRESENT', 'ABSENT');

CREATE TABLE activity_attendance (
                                     id VARCHAR PRIMARY KEY,
                                     activity_id VARCHAR REFERENCES activity(id),
                                     member_id VARCHAR REFERENCES member(id),
                                     status attendance_status NOT NULL,
                                     UNIQUE (activity_id, member_id)  -- on ne peut pas modifier, donc unique
);

create table if not exists "activity"
(
    id              varchar primary key,
    label           varchar,
    type            activity_type,
    date            date,
    recurrence      varchar,
    collectivity_id varchar references "collectivity" (id)
);

create table if not exists "activity_occupation"
(
    id          varchar primary key,
    activity_id varchar references "activity" (id),
    occupation  member_occupation
);

create table if not exists "activity_attendance"
(
    id          varchar primary key,
    activity_id varchar references "activity" (id),
    member_id   varchar references "member" (id),
    status      attendance_status not null,
    date_activity        date
);