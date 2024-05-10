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
    is_blacklisted     BOOLEAN     NOT NULL,
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

INSERT INTO projetae.enterprises (name, phone, address, is_blacklisted, version)
VALUES ('Assyst Europe', '02.609.25.00', 'Avenue du Japon, 1/B9, 1420 Braine-l''Alleud', false, 1),
       ('AXIS SRL', '02 752 17 60', 'Avenue de l''Hélianthe, 63, 1180 Uccle', false, 1),
       ('Infrabel', '02 525 22 11', 'Rue Bara, 135, 1070 Bruxelles', false, 1),
       ('La route du papier', '02 586 16 65', 'Avenue des Mimosas, 83, 1150 Woluwe-Saint-Pierre', false, 1),
       ('LetsBuild', '014 54 67 54', 'Chaussée de Bruxelles, 135A, 1310 La Hulpe', false, 1),
       ('Niboo', '0487 02 79 13', 'Boulevard du Souverain, 24, 1170 Watermael-Boisfort', false, 1),
       ('Sopra Steria', '02 566 66 66', 'Avenue Arnaud Fraiteur, 15/23, 1050 Bruxelles', false, 1),
       ('The Bayard Partnership', '02 309 52 45', 'Grauwmeer, 1/57 bte 55, 3001 Leuven', false, 1);

INSERT INTO projetae.supervisors(name, surname, phone, email, enterprise, version)
VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', 5, 1),
       ('Alvarez Corchete', 'Roberto', '02.566.60.14', '', 7, 1),
       ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1, 1),
       ('Ile', 'Roberto', '0489 32 16 54', '', 4, 1),
       ('Hibo', 'Owln', '0456 678 567', '', 3, 1),
       ('Barn', 'Henri', '02 752 17 60', '', 2, 1);

INSERT INTO projetae.users(name, surname, phone, email, password, role, year, inscription_date,
                           version)
VALUES ('Baroni', 'Raphaël', '0481 01 01 01', 'raphael.baroni@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021',
        '2020-09-21', 1),

       ('Lehmann', 'Brigitte', '0482 02 02 02', 'brigitte.lehmann@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021',
        '2020-09-21', 1),

       ('Leleux', 'Laurent', '0483 03 03 03', 'laurent.leleux@vinci.be',
        '$2a$10$bGwz1upQx0AvLsRJxIwjpu6iFHsnqjPTgxdra3uUh6x1Ie1dHvuka', 'TEACHER', '2020-2021',
        '2020-09-21', 1),

       ('Lancaster', 'Annouck', '0484 04 04 04', 'annouck.lancaster@vinci.be',
        '$2a$10$tMH2ROuqMhZ1QMB/9uJkVejyEZtKgyR5e0aNa/pRb36rz63uX6W5q', 'ADMIN', '2020-2021',
        '2020-09-21', 1),

       ('Skile', 'Elle', '0491 00 00 01', 'elle.skile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Ilotie', 'Basile', '0491 00 00 11', 'basile.Ilotie@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Frilot', 'Basile', '0491 00 00 21', 'basile.frilot@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Ilot', 'Basile', '0492 00 00 01', 'basile.Ilot@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Dito', 'Arnaud', '0493 00 00 01', 'arnaud.dito@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Dilo', 'Arnaud', '0494 00 00 01', 'arnaud.dilo@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Dilot', 'Cedric', '0495 00 00 01', 'cedric.dilot@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Linot', 'Auristelle', '0496 00 00 01', 'auristelle.linot@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2021-2022',
        '2021-09-21', 1),

       ('Demoulin', 'Basile', '0496 00 00 02', 'basile.demoulin@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-23', 1),

       ('Moulin', 'Arthur', '0497 00 00 02', 'arthur.moulin@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-23', 1),

       ('Moulin', 'Hugo', '0497 00 00 03', 'hugo.moulin@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-23', 1),

       ('Demoulin', 'Jeremy', '0497 00 00 20', 'jeremy.demoulin@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-23', 1),

       ('Mile', 'Aurèle', '0497 00 00 21', 'aurele.mile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-23', 1),

       ('Mile', 'Frank', '0497 00 00 75', 'frank.mile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-27', 1),

       ('Dumoulin', 'Basile', '0497 00 00 58', 'basile.dumoulin@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-27', 1),

       ('Dumoulin', 'Axel', '0497 00 00 97', 'axel.dumoulin@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2022-2023',
        '2022-09-27', 1),

       ('Line', 'Caroline', '0486 00 00 01', 'caroline.line@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2023-09-18', 1),

       ('Ile', 'Achille', '0487 00 00 01', 'ach.ile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2023-09-18', 1),

       ('Ile', 'Basile', '0488 00 00 01', 'basile.ile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2023-09-18', 1),

       ('Skile', 'Achille', '0490 00 00 01', 'achille.skile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2023-09-18', 1),

       ('Skile', 'Carole', '0489 00 00 01', 'carole.skile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2023-09-18', 1),

       ('Ile', 'Théophile', '0488 35 33 89', 'theophile.ile@student.vinci.be',
        '$2a$10$hyBUNX6oO1x14sdE6BpGx.M0jzUluskpq01Drt4ilCOGfUri1.OP2', 'STUDENT', '2023-2024',
        '2024-03-01', 1);

-- passwords admins: "Admin;10."
-- passwords teachers : "Prof24,z"
-- passwords students : "mdpuser.1"

INSERT INTO projetae.contacts(year, enterprise, state, refusal_reason, meeting_point,
                              "user", version)
VALUES ('2023-2024', 5, 'accepted', NULL, 'A distance', 25, 1),
       ('2023-2024', 7, 'accepted', NULL, 'Dans l''entreprise', 22, 1),
       ('2023-2024', 6, 'refused', 'N''ont pas accepté d''avoir un entretien', 'A distance', 22, 1),
       ('2023-2024', 1, 'accepted', NULL, 'Dans l''entreprise', 23, 1),
       ('2023-2024', 5, 'suspended', NULL, 'A distance', 23, 1),
       ('2023-2024', 7, 'suspended', NULL, NULL, 23, 1),
       ('2023-2024', 6, 'refused', 'ne prennent qu''un seul étudiant', 'Dans l''entreprise', 23, 1),
       ('2023-2024', 6, 'refused', 'Pas d’affinité avec le l’ERP Odoo', 'A distance', 21, 1),
       ('2023-2024', 7, 'unfollowed', NULL, NULL, 21, 1),
       ('2023-2024', 5, 'meet', NULL, 'A distance', 21, 1),
       ('2023-2024', 7, 'initiated', NULL, NULL, 26, 1),
       ('2023-2024', 6, 'initiated', NULL, NULL, 26, 1),
       ('2023-2024', 5, 'initiated', NULL, NULL, 26, 1),
       ('2023-2024', 7, 'initiated', NULL, NULL, 24, 1),


       ('2021-2022', 4, 'accepted', NULL, 'A distance', 5, 1),
       ('2021-2022', 7, 'unfollowed', NULL, NULL, 8, 1),
       ('2021-2022', 8, 'refused', 'ne prennent pas de stage', 'A distance', 7, 1),
       ('2021-2022', 7, 'accepted', NULL, 'Dans l''entreprise', 9, 1),
       ('2021-2022', 7, 'accepted', NULL, 'Dans l''entreprise', 10, 1),
       ('2021-2022', 1, 'accepted', NULL, 'Dans l''entreprise', 11, 1),
       ('2021-2022', 7, 'refused', 'Choix autre étudiant', 'Dans l''entreprise', 11, 1),
       ('2021-2022', 3, 'accepted', NULL, 'A distance', 12, 1),
       ('2021-2022', 7, 'suspended', NULL, NULL, 12, 1),
       ('2021-2022', 6, 'refused', 'Choix autre étudiant', 'A distance', 12, 1),


       ('2022-2023', 1, 'accepted', NULL, 'A distance', 16, 1),
       ('2022-2023', 2, 'accepted', NULL, 'Dans l''entreprise', 14, 1),
       ('2022-2023', 2, 'accepted', NULL, 'Dans l''entreprise', 15, 1),
       ('2022-2023', 2, 'accepted', NULL, 'A distance', 17, 1),
       ('2022-2023', 2, 'accepted', NULL, 'A distance', 18, 1),
       ('2022-2023', 2, 'refused', 'Entretien n''a pas eu lieu', 'Dans l''entreprise', 19, 1),
       ('2022-2023', 6, 'refused', 'Entretien n''a pas eu lieu', 'Dans l''entreprise', 19, 1),
       ('2022-2023', 7, 'refused', 'Entretien n''a pas eu lieu', 'A distance', 19, 1),
       ('2022-2023', 7, 'accepted', NULL, 'A distance', 20, 1),
       ('2022-2023', 7, 'refused', 'Choix autre étudiant', 'A distance', 7, 1)
;

INSERT INTO projetae.internships(subject, year, "user", supervisor, contact,
                                 enterprise, version)
VALUES ('Un ERP : Odoo', '2023-2024', 25, 1, 1, 5, 1),
       ('sBMS project - a complex environment', '2023-2024', 22, 2, 2, 7, 1),
       ('CRM : Microsoft Dynamics 365 For Sales', '2023-2024', 23, 3, 4, 1, 1),

       ('Conservation et restauration d’œuvres d’art', '2021-2022', 5, 4, 15, 4, 1),
       ('L''analyste au centre du développement', '2021-2022', 9, 2, 18, 7, 1),
       ('L''analyste au centre du développement', '2021-2022', 10, 2, 19, 7, 1),
       ('ERP : Microsoft Dynamics 366', '2021-2022', 11, 3, 20, 1, 1),
       ('Entretien des rails', '2021-2022', 12, 5, 22, 3, 1),

       ('CRM : Microsoft Dynamics 365 For Sales', '2022-2023', 16, 3, 25, 1, 1),
       ('Un métier : chef de projet', '2022-2023', 14, 6, 26, 2, 1),
       ('Un métier : chef de projet', '2022-2023', 15, 6, 27, 2, 1),
       ('Un métier : chef de projet', '2022-2023', 17, 6, 28, 2, 1),
       ('Un métier : chef de projet', '2022-2023', 18, 6, 29, 2, 1),
       ('sBMS project - Java Development', '2022-2023', 20, 2, 33, 7, 1);

-------------------------------------------------------------------------------

/* 1. Comptage du nombre d’utilisateurs, par rôle et par année académique */
SELECT role, year AS "Année academique", COUNT(user_id) AS "Nbr d'utilisateurs"
FROM projetae.users
GROUP BY role, year
ORDER BY year;

/* 2. Année académique et comptage du nombre de stages par année académique. */
SELECT year, COUNT(*) AS nombre_de_stages
FROM projetae.internships
GROUP BY year;

/* 3. Entreprise, année académique, et comptage du nombre de stages par entreprise et année académique */
SELECT e.name AS enterprise_name, i.year, COUNT(i.internship_id) AS "Nombre de stages"
FROM projetae.internships i
         JOIN projetae.enterprises e ON i.enterprise = e.enterprise_id
GROUP BY e.name, i.year
ORDER BY i.year;

/* 4. Année académique et comptage du nombre de contacts par année académique. */
SELECT year, COUNT(*) AS nombre_de_contacts
FROM projetae.contacts
GROUP BY year
ORDER BY year;

/* 5. Etats (en format lisible par le client) et comptage du nombre de contacts dans chacun des états. */
SELECT CASE
           WHEN state = 'accepted' THEN 'Accepté'
           WHEN state = 'refused' THEN 'Refusé'
           WHEN state = 'suspended' THEN 'Suspendu'
           WHEN state = 'meet' THEN 'Pris'
           WHEN state = 'initiated' THEN 'Initié'
           WHEN state = 'unfollowed' THEN 'Non suivi'
           ELSE 'Inconnu'
           END  AS etat_contact,
       COUNT(*) AS nombre_de_contacts
FROM projetae.contacts
GROUP BY state;

/* 6. Année académique, états (en format lisible par le client) et comptage du nombre de contacts dans chacun des états par année académique. */
SELECT year,
    CASE
    WHEN state = 'accepted' THEN 'Accepté'
    WHEN state = 'refused' THEN 'Refusé'
    WHEN state = 'suspended' THEN 'Suspendu'
    WHEN state = 'meet' THEN 'Pris'
    WHEN state = 'initiated' THEN 'Initié'
    WHEN state = 'unfollowed' THEN 'Non suivi'
    ELSE 'Inconnu'
END  AS etat_contact,
       COUNT(*) AS nombre_de_contacts
FROM projetae.contacts
GROUP BY state, year
ORDER BY year;

/* 7. Entreprise, états (en format lisible par le client) et comptage du nombre de contacts dans chacun des états par entreprise. */
SELECT e.name   AS enterprise_name,
       CASE
           WHEN c.state = 'accepted' THEN 'Accepté'
           WHEN c.state = 'refused' THEN 'Refusé'
           WHEN c.state = 'suspended' THEN 'Suspendu'
           WHEN c.state = 'meet' THEN 'Pris'
           WHEN c.state = 'initiated' THEN 'Initié'
           WHEN c.state = 'unfollowed' THEN 'Non suivi'
           ELSE 'Inconnu'
           END  AS etat_contact,
       COUNT(*) AS nombre_de_contacts
FROM projetae.contacts c
         JOIN projetae.enterprises e ON c.enterprise = e.enterprise_id
GROUP BY e.name, c.state
ORDER BY e.name;

-------------------------------------