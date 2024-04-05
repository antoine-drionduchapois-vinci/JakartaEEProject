DROP SCHEMA IF EXISTS projetae CASCADE;

CREATE SCHEMA projetae;

CREATE TABLE projetae.enterprises
(
    enterprise_id      SERIAL PRIMARY KEY,
    name               VARCHAR(50) NOT NULL,
    label              VARCHAR(50),
    address            VARCHAR(80),
    phone              VARCHAR(50) NOT NULL,
    email              VARCHAR(50),
    is_blacklisted     BOOLEAN,
    blacklisted_reason VARCHAR(200),
    version            INTEGER     NOT NULL
);

CREATE TABLE projetae.supervisors
(
    supervisor_id SERIAL PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    surname       VARCHAR(50) NOT NULL,
    phone         VARCHAR(50) NOT NULL,
    email         VARCHAR(50) NOT NULL,
    enterprise    INTEGER     NOT NULL REFERENCES projetae.enterprises,
    version       INTEGER     NOT NULL
);

CREATE TABLE projetae.users
(
    user_id          SERIAL PRIMARY KEY,
    name             VARCHAR(50)  NOT NULL,
    surname          VARCHAR(50)  NOT NULL,
    email            VARCHAR(50)  NOT NULL,
    phone            VARCHAR(50)  NOT NULL,
    password         VARCHAR(200) NOT NULL,
    year             VARCHAR(50)  NOT NULL,
    inscription_date DATE         NOT NULL,
    role             VARCHAR(15)  NOT NULL,
    version          INTEGER      NOT NULL
);

CREATE TABLE projetae.contacts
(
    contact_id     SERIAL PRIMARY KEY,
    meeting_point  VARCHAR(100),
    state          VARCHAR(12) NOT NULL,
    refusal_reason VARCHAR(100),
    year           VARCHAR(50) NOT NULL,
    "user"         INTEGER     NOT NULL REFERENCES projetae.users,
    enterprise     INTEGER     NOT NULL REFERENCES projetae.enterprises,
    version        INTEGER     NOT NULL
);

CREATE TABLE projetae.internships
(
    internship_id SERIAL PRIMARY KEY,
    subject       VARCHAR(100),
    year          VARCHAR(10) NOT NULL,
    "user"        INTEGER     NOT NULL REFERENCES projetae.users,
    enterprise    INTEGER     NOT NULL REFERENCES projetae.enterprises,
    supervisor    INTEGER     NOT NULL REFERENCES projetae.supervisors,
    contact       INTEGER     NOT NULL REFERENCES projetae.contacts,
    version       INTEGER     NOT NULL
);

-------------------------------------------------------------------------------

INSERT INTO projetae.enterprises (name, phone, address, version)
VALUES ('Assyst Europe', '02.609.25.00', 'Avenue du Japon, 1/B9, 1420 Braine-l''Alleud', 1),
       ('LetsBuild', '014 54 67 54', 'Chaussée de Bruxelles, 135A, 1310 La Hulpe', 1),
       ('Niboo', '0487 02 79 13', 'Boulevard du Souverain, 24, 1170 Watermael-Boisfort', 1),
       ('Sopra Steria', '02 566 66 66', 'Avenue Arnaud Fraiteur, 15/23, 1050 Bruxelles', 1);

INSERT INTO projetae.supervisors(name, surname, phone, email, enterprise, version)
VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', 2, 1),
       ('ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', '', 4, 1),
       ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1, 1);

INSERT INTO projetae.users(name, surname, phone, email, password, role, year, inscription_date,
                           version)
VALUES ('Baroni', 'Raphaël', '0481 01 01 01', 'raphael.baroni@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021',
        '2024-03-28', 1),
       ('Lehmann', 'Brigitte', '0482 02 02 02', 'brigitte.lehmann@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021',
        '2024-03-28', 1),
       ('Leleux', 'Laurent', '0483 03 03 03', 'laurent.leleux@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021',
        '2024-03-28', 1),
       ('Lancaster', 'Annouck', '0484 04 04 04', 'annouck.lancaster@vinci.be',
        '$2a$10$tMH2ROuqMhZ1QMB/9uJkVejyEZtKgyR5e0aNa/pRb36rz63uX6W5q', 'ADMIN', '2020-2021',
        '2024-03-28', 1),
       ('Line', 'Caroline', '0486 00 00 01', 'Caroline.line@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2024-03-28', 1),
       ('Ile', 'Achille', '0487 00 00 01', 'Ach.ile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2024-03-28', 1),
       ('Ile', 'Basile', '0488 00 00 01', 'Basile.Ile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2024-03-28', 1),
       ('skile', 'Achille', '0490 00 00 01', 'Achille.skile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2024-03-28', 1),
       ('skile', 'Carole', '0489 00 00 01', 'Carole.skile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2024-03-28', 1);

-- passwords admins: "Admin;10."
-- passwords teachers : "Prof24,z"
-- passwords students : "mdpuser.1"

INSERT INTO projetae.contacts(year, enterprise, state, refusal_reason, meeting_point,
                              "user", version)
VALUES ('2023-2024', 2, 'accepted', NULL, 'A distance', 9, 1),
       ('2023-2024', 4, 'accepted', NULL, 'Dans l''entreprise', 6, 1),
       ('2023-2024', 3, 'refused', NULL, 'A distance', 6, 1),
       ('2023-2024', 1, 'accepted', NULL, 'Dans l''entreprise', 7, 1),
       ('2023-2024', 2, 'suspended', NULL, 'A distance', 7, 1),
       ('2023-2024', 4, 'suspended', NULL, NULL, 7, 1),
       ('2023-2024', 3, 'refused', NULL, 'Dans l''entreprise', 7, 1),
       ('2023-2024', 3, 'meet', NULL, 'A distance', 5, 1),
       ('2023-2024', 4, 'initiated', NULL, NULL, 5, 1),
       ('2023-2024', 2, 'initiated', NULL, NULL, 5, 1),
       ('2023-2024', 4, 'initiated', NULL, NULL, 8, 1);

INSERT INTO projetae.internships(subject, year, "user", supervisor, contact,
                                 enterprise, version)
VALUES ('Un ERP : Odoo', '2023-2024', 9, 1, 1, 2, 1),
       ('sBMS project - a complex environment', '2023-2024', 6, 2, 2, 4, 1),
       ('CRM : Microsoft Dynamics 365 For Sales', '2023-2024', 7, 3, 4, 1, 1);

-------------------------------------------------------------------------------

SELECT count(*)
FROM projetae.users;

SELECT count(*)
FROM projetae.enterprises;

SELECT year, COUNT (*) AS nombre_de_stages
FROM projetae.internships
GROUP BY year;

SELECT year, COUNT (*) AS nombre_de_contacts
FROM projetae.contacts
GROUP BY year;

SELECT
    CASE
        WHEN state = 'accepted' THEN 'Accepté'
        WHEN state = 'refused' THEN 'Refusé'
        WHEN state = 'suspended' THEN 'Suspendu'
        WHEN state = 'meet' THEN 'Contacté'
        WHEN state = 'initiated' THEN 'Initié'
        WHEN state = 'unfollowed' THEN 'Non Suivis'
        ELSE 'Inconnu'
    END AS etat_contact,
    COUNT(*) AS nombre_de_contacts
FROM
    projetae.contacts
GROUP BY
    state;