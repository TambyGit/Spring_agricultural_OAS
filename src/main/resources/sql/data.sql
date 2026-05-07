INSERT INTO "collectivity" (id, number, name, location, specialization)
VALUES ('col-1', 1, 'Mpanorina', 'Ambatondrazaka', 'Riziculture'),
       ('col-2', 2, 'Dobo voalohany', 'Ambatondrazaka', 'Pisciculture'),
       ('col-3', 3, 'Tantely mamy', 'Brickaville', 'Apiculture');

INSERT INTO "member" (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, registration_fee_paid, membership_dues_paid)
VALUES
    ('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE',   'Lot II V M Ambato.', 'Riziculture',  '0341234567', 'member.1@fed-agri.mg', 'PRESIDENT',      true, true),
    ('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE',   'Lot II F Ambato.',   'Agriculteur',  '0321234567', 'member.2@fed-agri.mg', 'VICE_PRESIDENT', true, true),
    ('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE',   'Lot II J Ambato.',   'Collecteur',   '0331234567', 'member.3@fed-agrimg',  'SECRETARY',      true, true),
    ('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'TREASURER',      true, true),
    ('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE',   'Lot UV 80 Ambato.',  'Riziculture',  '0373434567', 'member.5@fed-agri.mg', 'SENIOR',         true, true),
    ('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.',   'Riziculture',  '0372234567', 'member.6@fed-agri.mg', 'SENIOR',         true, true),
    ('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE',   'Lot UV 7 Ambato.',   'Riziculture',  '0374234567', 'member.7@fed-agri.mg', 'SENIOR',         true, true),
    ('C1-M8', 'Prénom membre 6', 'Nom membre 8', '1975-08-20', 'MALE',   'Lot UV 8 Ambato.',   'Riziculture',  '0370234567', 'member.8@fed-agri.mg', 'SENIOR',         true, true);

INSERT INTO "collectivity_member" (id, member_id, collectivity_id)
VALUES ('cm-col1-C1M1', 'C1-M1', 'col-1'),
       ('cm-col1-C1M2', 'C1-M2', 'col-1'),
       ('cm-col1-C1M3', 'C1-M3', 'col-1'),
       ('cm-col1-C1M4', 'C1-M4', 'col-1'),
       ('cm-col1-C1M5', 'C1-M5', 'col-1'),
       ('cm-col1-C1M6', 'C1-M6', 'col-1'),
       ('cm-col1-C1M7', 'C1-M7', 'col-1'),
       ('cm-col1-C1M8', 'C1-M8', 'col-1');

INSERT INTO "member_referee" (id, member_refereed_id, member_referee_id)
VALUES

    ('mr-C1M3-C1M1', 'C1-M3', 'C1-M1'),
    ('mr-C1M3-C1M2', 'C1-M3', 'C1-M2'),

    ('mr-C1M4-C1M1', 'C1-M4', 'C1-M1'),
    ('mr-C1M4-C1M2', 'C1-M4', 'C1-M2'),

    ('mr-C1M5-C1M1', 'C1-M5', 'C1-M1'),
    ('mr-C1M5-C1M2', 'C1-M5', 'C1-M2'),

    ('mr-C1M6-C1M1', 'C1-M6', 'C1-M1'),
    ('mr-C1M6-C1M2', 'C1-M6', 'C1-M2'),

    ('mr-C1M7-C1M1', 'C1-M7', 'C1-M1'),
    ('mr-C1M7-C1M2', 'C1-M7', 'C1-M2'),

    ('mr-C1M8-C1M6', 'C1-M8', 'C1-M6'),
    ('mr-C1M8-C1M7', 'C1-M8', 'C1-M7');

UPDATE "collectivity"
SET president_id      = 'C1-M1',
    vice_president_id = 'C1-M2',
    secretary_id      = 'C1-M3',
    treasurer_id      = 'C1-M4'
WHERE id = 'col-1';

INSERT INTO "collectivity_member" (id, member_id, collectivity_id)
VALUES ('cm-col2-C1M1', 'C1-M1', 'col-2'),
       ('cm-col2-C1M2', 'C1-M2', 'col-2'),
       ('cm-col2-C1M3', 'C1-M3', 'col-2'),
       ('cm-col2-C1M4', 'C1-M4', 'col-2'),
       ('cm-col2-C1M5', 'C1-M5', 'col-2'),
       ('cm-col2-C1M6', 'C1-M6', 'col-2'),
       ('cm-col2-C1M7', 'C1-M7', 'col-2'),
       ('cm-col2-C1M8', 'C1-M8', 'col-2');

-- 2. Membres référents : déjà insérés lors de col-1, aucun doublon nécessaire.

-- 3. Bureau de la collectivité 2
UPDATE "collectivity"
SET president_id      = 'C1-M5',
    vice_president_id = 'C1-M6',
    secretary_id      = 'C1-M7',
    treasurer_id      = 'C1-M8'
WHERE id = 'col-2';

-- 1. Membres (tous nouveaux)
INSERT INTO "member" (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, registration_fee_paid, membership_dues_paid)
VALUES
    ('C3-M1', 'Prénom membre 9',  'Nom membre 9',  '1988-01-02', 'MALE',   'Lot 33 J Antsirabe',  'Apiculteur',   '034034567',  'member.9@fed-agri.mg',  'PRESIDENT',      true, true),
    ('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1982-03-05', 'MALE',   'Lot 2 J Antsirabe',   'Agriculteur',  '0338634567', 'member.10@fed-agri.mg', 'VICE_PRESIDENT', true, true),
    ('C3-M3', 'Prénom membre 11', 'Nom membre 11', '1992-03-12', 'MALE',   'Lot 8 KM Antsirabe',  'Collecteur',   '0338234567', 'member.11@fed-agrimg',  'SECRETARY',      true, true),
    ('C3-M4', 'Prénom membre 12', 'Nom membre 12', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe','Distributeur', '0382334567', 'member.12@fed-agri.mg', 'TREASURER',      true, true),
    ('C3-M5', 'Prénom membre 13', 'Nom membre 13', '1999-08-11', 'MALE',   'Lot UV 80 Antsirabe', 'Apiculteur',   '0373365567', 'member.13@fed-agri.mg', 'SENIOR',         true, true),
    ('C3-M6', 'Prénom membre 14', 'Nom membre 14', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe',  'Apiculteur',   '0378234567', 'member.14@fed-agri.mg', 'SENIOR',         true, true),
    ('C3-M7', 'Prénom membre 15', 'Nom membre 15', '1998-01-13', 'MALE',   'Lot UV 7 Antsirabe',  'Apiculteur',   '0374914567', 'member.15@fed-agri.mg', 'SENIOR',         true, true),
    ('C3-M8', 'Prénom membre 16', 'Nom membre 16', '1975-08-02', 'MALE',   'Lot UV 8 Antsirabe',  'Apiculteur',   '0370634567', 'member.16@fed-agri.mg', 'SENIOR',         true, true);

-- 2. Liaisons collectivity_member
INSERT INTO "collectivity_member" (id, member_id, collectivity_id)
VALUES ('cm-col3-C3M1', 'C3-M1', 'col-3'),
       ('cm-col3-C3M2', 'C3-M2', 'col-3'),
       ('cm-col3-C3M3', 'C3-M3', 'col-3'),
       ('cm-col3-C3M4', 'C3-M4', 'col-3'),
       ('cm-col3-C3M5', 'C3-M5', 'col-3'),
       ('cm-col3-C3M6', 'C3-M6', 'col-3'),
       ('cm-col3-C3M7', 'C3-M7', 'col-3'),
       ('cm-col3-C3M8', 'C3-M8', 'col-3');

-- 3. Membres référents
INSERT INTO "member_referee" (id, member_refereed_id, member_referee_id)
VALUES
    -- C3-M1 et C3-M2 parrainés par les fondateurs fédération (C1-M1, C1-M2)
    ('mr-C3M1-C1M1', 'C3-M1', 'C1-M1'),
    ('mr-C3M1-C1M2', 'C3-M1', 'C1-M2'),
    ('mr-C3M2-C1M1', 'C3-M2', 'C1-M1'),
    ('mr-C3M2-C1M2', 'C3-M2', 'C1-M2'),
    -- Les suivants parrainés par C3-M1 et C3-M2
    ('mr-C3M3-C3M1', 'C3-M3', 'C3-M1'),
    ('mr-C3M3-C3M2', 'C3-M3', 'C3-M2'),
    ('mr-C3M4-C3M1', 'C3-M4', 'C3-M1'),
    ('mr-C3M4-C3M2', 'C3-M4', 'C3-M2'),
    ('mr-C3M5-C3M1', 'C3-M5', 'C3-M1'),
    ('mr-C3M5-C3M2', 'C3-M5', 'C3-M2'),
    ('mr-C3M6-C3M1', 'C3-M6', 'C3-M1'),
    ('mr-C3M6-C3M2', 'C3-M6', 'C3-M2'),
    ('mr-C3M7-C3M1', 'C3-M7', 'C3-M1'),
    ('mr-C3M7-C3M2', 'C3-M7', 'C3-M2'),
    ('mr-C3M8-C3M1', 'C3-M8', 'C3-M1'),
    ('mr-C3M8-C3M2', 'C3-M8', 'C3-M2');

UPDATE "collectivity"
SET president_id      = 'C3-M1',
    vice_president_id = 'C3-M2',
    secretary_id      = 'C3-M3',
    treasurer_id      = 'C3-M4'
WHERE id = 'col-3';

insert into membership_fee (id, label, amount, eligible_from, status, frequency, collectivity_id)
values ('cot-1', 'Costisation annuelle', 100000, '2026/01/01', 'ACTIVE', 'ANNUALLY', 'col-1'),
       ('cot-2', 'Costisation annuelle', 100000, '2026/01/01', 'ACTIVE', 'ANNUALLY', 'col-2'),
       ('cot-3', 'Costisation annuelle', 50000, '2026/01/01', 'ACTIVE', 'ANNUALLY', 'col-3');

-- ============================================================
-- COMPTES CASH  (table cash_account)
-- ============================================================
INSERT INTO "cash_account" (id, collectivity_id)
VALUES ('C1-A-CASH', 'col-1'),
       ('C2-A-CASH', 'col-2'),
       ('C3-A-CASH', 'col-3');

-- ============================================================
-- COMPTES MOBILE MONEY  (table mobile_banking_account)
-- ============================================================
-- col-3 n'a pas de compte mobile dans les données fournies
INSERT INTO "mobile_banking_account" (id, holder_name, service, mobile_number, collectivity_id)
VALUES ('C1-A-MOBILE-1', 'Mpanorina', 'ORANGE_MONEY', '0370489612', 'col-1'),
       ('C2-A-MOBILE-1', 'Dobo voalohany', 'ORANGE_MONEY', '0320489612', 'col-2');

-- ============================================================
-- TRANSACTIONS — COLLECTIVITÉ 1  (col-1)
-- Toutes vers le compte cash C1-A-CASH
-- ============================================================
INSERT INTO "transaction" (id, amount, creation_date, transaction_type, financial_account_id, member_debited_id)
VALUES ('tx-col1-C1M1', 100000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M1'),
       ('tx-col1-C1M2', 100000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M2'),
       ('tx-col1-C1M3', 100000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M3'),
       ('tx-col1-C1M4', 100000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M4'),
       ('tx-col1-C1M5', 100000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M5'),
       ('tx-col1-C1M6', 100000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M6'),
       ('tx-col1-C1M7', 60000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M7'),
       ('tx-col1-C1M8', 90000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M8');

INSERT INTO "transaction" (id, amount, creation_date, transaction_type, financial_account_id, member_debited_id)
VALUES ('tx-col2-C1M1', 60000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M1'),
       ('tx-col2-C1M2', 90000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M2'),
       ('tx-col2-C1M3', 100000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M3'),
       ('tx-col2-C1M4', 100000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M4'),
       ('tx-col2-C1M5', 100000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M5'),
       ('tx-col2-C1M6', 100000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M6'),
       ('tx-col2-C1M7', 40000.00, '2026-01-01', 'IN', 'C2-A-MOBILE-1', 'C1-M7'),
       ('tx-col2-C1M8', 60000.00, '2026-01-01', 'IN', 'C2-A-MOBILE-1', 'C1-M8');


-- Vaovao
INSERT INTO "activity" (id, label, type, date, recurrence, collectivity_id)
VALUES
    ('act-1', 'AG1',              'MEETING'::activity_type,  NULL,         '1er samedi de chaque mois',   'col-1'),
    ('act-2', 'Formation de base','TRAINING'::activity_type, NULL,         '2è dimanche de chaque mois',  'col-1'),
    ('act-3', 'AG2',              'MEETING'::activity_type,  NULL,         '1er dimanche de chaque mois', 'col-2'),
    ('act-4', 'Formation de base','TRAINING'::activity_type, NULL,         '3è dimanche de chaque mois',  'col-2'),
    ('act-5', 'Perfectionnement', 'PUNCTUAL'::activity_type, '2026-04-30', NULL,                          'col-2'),
    ('act-6', 'AG3',              'MEETING'::activity_type,  NULL,         '1er vendredi de chaque mois', 'col-3'),
    ('act-7', 'Formation de base','TRAINING'::activity_type, NULL,         '4è mercredi de chaque mois',  'col-3');

INSERT INTO "activity_occupation" (id, activity_id, occupation)
VALUES
    ('ao-1-1', 'act-1', 'JUNIOR'::member_occupation),
    ('ao-1-2', 'act-1', 'SENIOR'::member_occupation),
    ('ao-1-3', 'act-1', 'SECRETARY'::member_occupation),
    ('ao-1-4', 'act-1', 'TREASURER'::member_occupation),
    ('ao-1-5', 'act-1', 'VICE_PRESIDENT'::member_occupation),
    ('ao-1-6', 'act-1', 'PRESIDENT'::member_occupation),
    ('ao-2-1', 'act-2', 'JUNIOR'::member_occupation),
    ('ao-3-1', 'act-3', 'JUNIOR'::member_occupation),
    ('ao-3-2', 'act-3', 'SENIOR'::member_occupation),
    ('ao-3-3', 'act-3', 'SECRETARY'::member_occupation),
    ('ao-3-4', 'act-3', 'TREASURER'::member_occupation),
    ('ao-3-5', 'act-3', 'VICE_PRESIDENT'::member_occupation),
    ('ao-3-6', 'act-3', 'PRESIDENT'::member_occupation),
    ('ao-4-1', 'act-4', 'JUNIOR'::member_occupation),
    ('ao-5-1', 'act-5', 'SENIOR'::member_occupation),
    ('ao-6-1', 'act-6', 'JUNIOR'::member_occupation),
    ('ao-6-2', 'act-6', 'SENIOR'::member_occupation),
    ('ao-6-3', 'act-6', 'SECRETARY'::member_occupation),
    ('ao-6-4', 'act-6', 'TREASURER'::member_occupation),
    ('ao-6-5', 'act-6', 'VICE_PRESIDENT'::member_occupation),
    ('ao-6-6', 'act-6', 'PRESIDENT'::member_occupation),
    ('ao-7-1', 'act-7', 'JUNIOR'::member_occupation);

INSERT INTO "activity_attendance" (id, activity_id, member_id, status, date_activity)
VALUES
    ('att-act1-C1M1-mar', 'act-1', 'C1-M1', 'PRESENT'::attendance_status, '2026-03-07'),
    ('att-act1-C1M2-mar', 'act-1', 'C1-M2', 'PRESENT'::attendance_status, '2026-03-07'),
    ('att-act1-C1M3-mar', 'act-1', 'C1-M3', 'PRESENT'::attendance_status, '2026-03-07'),
    ('att-act1-C1M4-mar', 'act-1', 'C1-M4', 'PRESENT'::attendance_status, '2026-03-07'),
    ('att-act1-C1M5-mar', 'act-1', 'C1-M5', 'PRESENT'::attendance_status, '2026-03-07'),
    ('att-act1-C1M6-mar', 'act-1', 'C1-M6', 'PRESENT'::attendance_status, '2026-03-07'),
    ('att-act1-C1M7-mar', 'act-1', 'C1-M7', 'ABSENT'::attendance_status,  '2026-03-07'),
    ('att-act1-C1M8-mar', 'act-1', 'C1-M8', 'ABSENT'::attendance_status,  '2026-03-07');

INSERT INTO "activity_attendance" (id, activity_id, member_id, status, date_activity)
VALUES
    ('att-act1-C1M1-avr', 'act-1', 'C1-M1', 'PRESENT'::attendance_status, '2026-04-04'),
    ('att-act1-C1M2-avr', 'act-1', 'C1-M2', 'PRESENT'::attendance_status, '2026-04-04'),
    ('att-act1-C1M3-avr', 'act-1', 'C1-M3', 'ABSENT'::attendance_status,  '2026-04-04'),
    ('att-act1-C1M4-avr', 'act-1', 'C1-M4', 'ABSENT'::attendance_status,  '2026-04-04'),
    ('att-act1-C1M5-avr', 'act-1', 'C1-M5', 'PRESENT'::attendance_status, '2026-04-04'),
    ('att-act1-C1M6-avr', 'act-1', 'C1-M6', 'PRESENT'::attendance_status, '2026-04-04'),
    ('att-act1-C1M7-avr', 'act-1', 'C1-M7', 'PRESENT'::attendance_status, '2026-04-04'),
    ('att-act1-C1M8-avr', 'act-1', 'C1-M8', 'PRESENT'::attendance_status, '2026-04-04');

INSERT INTO "activity_attendance" (id, activity_id, member_id, status, date_activity)
VALUES
    ('att-act3-C1M1-mar', 'act-3', 'C1-M1', 'PRESENT'::attendance_status, '2026-03-08'),
    ('att-act3-C1M2-mar', 'act-3', 'C1-M2', 'PRESENT'::attendance_status, '2026-03-08'),
    ('att-act3-C1M3-mar', 'act-3', 'C1-M3', 'ABSENT'::attendance_status,  '2026-03-08'),
    ('att-act3-C1M4-mar', 'act-3', 'C1-M4', 'ABSENT'::attendance_status,  '2026-03-08'),
    ('att-act3-C1M5-mar', 'act-3', 'C1-M5', 'PRESENT'::attendance_status, '2026-03-08'),
    ('att-act3-C1M6-mar', 'act-3', 'C1-M6', 'PRESENT'::attendance_status, '2026-03-08'),
    ('att-act3-C1M7-mar', 'act-3', 'C1-M7', 'PRESENT'::attendance_status, '2026-03-08'),
    ('att-act3-C1M8-mar', 'act-3', 'C1-M8', 'PRESENT'::attendance_status, '2026-03-08');

INSERT INTO "activity_attendance" (id, activity_id, member_id, status, date_activity)
VALUES
    ('att-act3-C1M1-avr', 'act-3', 'C1-M1', 'PRESENT'::attendance_status, '2026-04-05'),
    ('att-act3-C1M2-avr', 'act-3', 'C1-M2', 'PRESENT'::attendance_status, '2026-04-05'),
    ('att-act3-C1M3-avr', 'act-3', 'C1-M3', 'ABSENT'::attendance_status,  '2026-04-05'),
    ('att-act3-C1M4-avr', 'act-3', 'C1-M4', 'PRESENT'::attendance_status, '2026-04-05'),
    ('att-act3-C1M5-avr', 'act-3', 'C1-M5', 'PRESENT'::attendance_status, '2026-04-05'),
    ('att-act3-C1M6-avr', 'act-3', 'C1-M6', 'PRESENT'::attendance_status, '2026-04-05'),
    ('att-act3-C1M7-avr', 'act-3', 'C1-M7', 'PRESENT'::attendance_status, '2026-04-05'),
    ('att-act3-C1M8-avr', 'act-3', 'C1-M8', 'ABSENT'::attendance_status,  '2026-04-05');

-- Perfectionnement (act-5, 30/04/2026)
INSERT INTO "activity_attendance" (id, activity_id, member_id, status, date_activity)
VALUES
    ('att-act5-C1M1', 'act-5', 'C1-M1', 'PRESENT'::attendance_status,   '2026-04-30'),
    ('att-act5-C1M2', 'act-5', 'C1-M2', 'PRESENT'::attendance_status,   '2026-04-30'),
    ('att-act5-C1M3', 'act-5', 'C1-M3', 'PRESENT'::attendance_status,   '2026-04-30'),
    ('att-act5-C1M4', 'act-5', 'C1-M4', 'ABSENT'::attendance_status,    '2026-04-30'),
    ('att-act5-C1M5', 'act-5', 'C1-M5', 'UNDEFINED'::attendance_status, '2026-04-30'),
    ('att-act5-C1M6', 'act-5', 'C1-M6', 'UNDEFINED'::attendance_status, '2026-04-30'),
    ('att-act5-C1M7', 'act-5', 'C1-M7', 'UNDEFINED'::attendance_status, '2026-04-30'),
    ('att-act5-C1M8', 'act-5', 'C1-M8', 'UNDEFINED'::attendance_status, '2026-04-30');

-- ============================================================
-- PRÉSENCES — COLLECTIVITÉ 3 (act-6 AG3)
-- ============================================================
-- Mars 2026
INSERT INTO "activity_attendance" (id, activity_id, member_id, status, date_activity)
VALUES
    ('att-act6-C3M1-mar', 'act-6', 'C3-M1', 'PRESENT'::attendance_status, '2026-03-06'),
    ('att-act6-C3M2-mar', 'act-6', 'C3-M2', 'PRESENT'::attendance_status, '2026-03-06'),
    ('att-act6-C3M3-mar', 'act-6', 'C3-M3', 'PRESENT'::attendance_status, '2026-03-06'),
    ('att-act6-C3M4-mar', 'act-6', 'C3-M4', 'PRESENT'::attendance_status, '2026-03-06'),
    ('att-act6-C3M5-mar', 'act-6', 'C3-M5', 'PRESENT'::attendance_status, '2026-03-06'),
    ('att-act6-C3M6-mar', 'act-6', 'C3-M6', 'PRESENT'::attendance_status, '2026-03-06'),
    ('att-act6-C3M7-mar', 'act-6', 'C3-M7', 'ABSENT'::attendance_status,  '2026-03-06'),
    ('att-act6-C3M8-mar', 'act-6', 'C3-M8', 'ABSENT'::attendance_status,  '2026-03-06');

-- Avril 2026
INSERT INTO "activity_attendance" (id, activity_id, member_id, status, date_activity)
VALUES
    ('att-act6-C3M1-avr', 'act-6', 'C3-M1', 'PRESENT'::attendance_status, '2026-04-03'),
    ('att-act6-C3M2-avr', 'act-6', 'C3-M2', 'PRESENT'::attendance_status, '2026-04-03'),
    ('att-act6-C3M3-avr', 'act-6', 'C3-M3', 'ABSENT'::attendance_status,  '2026-04-03'),
    ('att-act6-C3M4-avr', 'act-6', 'C3-M4', 'ABSENT'::attendance_status,  '2026-04-03'),
    ('att-act6-C3M5-avr', 'act-6', 'C3-M5', 'PRESENT'::attendance_status, '2026-04-03'),
    ('att-act6-C3M6-avr', 'act-6', 'C3-M6', 'PRESENT'::attendance_status, '2026-04-03'),
    ('att-act6-C3M7-avr', 'act-6', 'C3-M7', 'ABSENT'::attendance_status,  '2026-04-03'),
    ('att-act6-C3M8-avr', 'act-6', 'C3-M8', 'PRESENT'::attendance_status, '2026-04-03'),
    ('att-act6-C1M1-avr', 'act-6', 'C1-M1', 'PRESENT'::attendance_status, '2026-04-03');
