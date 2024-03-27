DROP SCHEMA IF EXISTS projetae CASCADE ;

CREATE SCHEMA projetae;

CREATE TABLE projetae.enterprises
(
    enterprise_id      SERIAL PRIMARY KEY,
    name               VARCHAR(50) NOT NULL,
    label              VARCHAR(50),
    address            VARCHAR(80),
    phone              VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    is_blacklisted     BOOLEAN,
    blacklisted_reason VARCHAR(200)
);

CREATE TABLE projetae.supervisors
(
    supervisor_id SERIAL PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    surname       VARCHAR(50) NOT NULL,
    phone         VARCHAR(50) NOT NULL,
    email         VARCHAR(50) NOT NULL,
    enterprise    INTEGER     NOT NULL REFERENCES projetae.enterprises
);

CREATE TABLE projetae.users
(
    user_id  SERIAL PRIMARY KEY,
    name     VARCHAR(50)  NOT NULL,
    surname  VARCHAR(50)  NOT NULL,
    email    VARCHAR(50)  NOT NULL,
    phone    VARCHAR(50)  NOT NULL,
    password VARCHAR(200) NOT NULL,
    year     VARCHAR(50)  NOT NULL,
    role     VARCHAR(15)  NOT NULL
);

CREATE TABLE projetae.contacts
(
    contact_id     SERIAL PRIMARY KEY,
    meeting_point  VARCHAR(100),
    state          VARCHAR(12) NOT NULL,
    refusal_reason VARCHAR(100),
    year           VARCHAR(50) NOT NULL,
    "user"         INTEGER     NOT NULL REFERENCES projetae.users,
    enterprise     INTEGER     NOT NULL REFERENCES projetae.enterprises
);

CREATE TABLE projetae.internships
(
    internship_id SERIAL PRIMARY KEY,
    subject       VARCHAR(100),
    year          VARCHAR(10) NOT NULL,
    "user" INTEGER NOT NULL REFERENCES projetae.users,
    enterprise    INTEGER     NOT NULL REFERENCES projetae.enterprises,
    supervisor    INTEGER     NOT NULL REFERENCES projetae.supervisors,
    contact       INTEGER     NOT NULL REFERENCES projetae.contacts
);

INSERT INTO projetae.enterprises (name, phone, address)
VALUES ('Assyst Europe', '02.609.25.00', 'Avenue du Japon, 1/B9, 1420 Braine-l''Alleud'),
       ('LetsBuild', '014 54 67 54', 'Chaussée de Bruxelles, 135A, 1310 La Hulpe'),
       ('Niboo', '0487 02 79 13', 'Boulevard du Souverain, 24, 1170 Watermael-Boisfort'),
       ('Sopra Steria', '02 566 66 66', 'Avenue Arnaud Fraiteur, 15/23, 1050 Bruxelles');

INSERT INTO projetae.supervisors(name, surname, phone, email, enterprise)
VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', 2),
       ('ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', '', 4),
       ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1);

INSERT INTO projetae.users(name, surname, phone, email, password, role, year)
VALUES ('Baroni', 'Raphaël', '0481 01 01 01', 'raphael.baroni@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021'),
       ('Lehmann', 'Brigitte', '0482 02 02 02', 'brigitte.lehmann@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER',
        '2020-2021'),
       ('Leleux', 'Laurent', '0483 03 03 03', 'laurent.leleux@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021'),
       ('Lancaster', 'Annouck', '0484 04 04 04', 'annouck.lancaster@vinci.be',
        '$2a$10$BOq4oWmcSeHcnPw2Xliffe3ApgiHxuTb/PK4zKD.yfvCYUrNjoSjG', 'ADMIN',
        '2020-2021'),
       ('Line', 'Caroline', '0486 00 00 01', 'Caroline.line@student.vinci.be',
        '$2a$10$hNsrr31b9TQZSGqOnss4S.7d6a5PldlQY7PhQPiwojWiK/D5IVbqm', 'STUDENT',
        '2023-2024'),
       ('Ile', 'Achille', '0487 00 00 01', 'Ach.ile@student.vinci.be',
        '$2a$10$hNsrr31b9TQZSGqOnss4S.7d6a5PldlQY7PhQPiwojWiK/D5IVbqm', 'STUDENT', '2023-2024'),
       ('Ile', 'Basile', '0488 00 00 01', 'Basile.Ile@student.vinci.be',
        '$2a$10$hNsrr31b9TQZSGqOnss4S.7d6a5PldlQY7PhQPiwojWiK/D5IVbqm', 'STUDENT', '2023-2024'),
       ('skile', 'Achille', '0490 00 00 01', 'Achille.skile@student.vinci.be',
        '$2a$10$hNsrr31b9TQZSGqOnss4S.7d6a5PldlQY7PhQPiwojWiK/D5IVbqm', 'STUDENT',
        '2023-2024'),
       ('skile', 'Carole', '0489 00 00 01', 'Carole.skile@student.vinci.be',
        '$2a$10$hNsrr31b9TQZSGqOnss4S.7d6a5PldlQY7PhQPiwojWiK/D5IVbqm', 'STUDENT',
        '2023-2024');

-- passwords admins: " Admin;10."
-- passwords teachers : "Prof24,z"
-- passwords students : " mdpuser.1"

INSERT INTO projetae.contacts(year, enterprise, state, refusal_reason, meeting_point,
                              "user")
VALUES ('2023-2024', 2, 'accepted', NULL, 'A distance', 9),
       ('2023-2024', 4, 'accepted', NULL, 'Dans l''entreprise', 6),
       ('2023-2024', 3, 'refused', NULL, 'A distance', 6),
       ('2023-2024', 1, 'accepted', NULL, 'Dans l''entreprise', 7),
       ('2023-2024', 2, 'suspended', NULL, 'A distance', 7),
       ('2023-2024', 4, 'suspended', NULL, NULL, 7),
       ('2023-2024', 3, 'refused', NULL, 'Dans l''entreprise', 7),
       ('2023-2024', 3, 'contacted', NULL, 'A distance', 5),
       ('2023-2024', 4, 'initiated', NULL, NULL, 5),
       ('2023-2024', 2, 'initiated', NULL, NULL, 5),
       ('2023-2024', 4, 'initiated', NULL, NULL, 8);

INSERT INTO projetae.internships(subject, year, "user", supervisor, contact,
                                 enterprise)
VALUES ('Un ERP : Odoo', '2023-2024', 9, 1, 1, 2),
       ('sBMS project - a complex environment', '2023-2024', 6, 2, 2, 4),
       ('CRM : Microsoft Dynamics 365 For Sales', '2023-2024', 7, 3, 4, 1);