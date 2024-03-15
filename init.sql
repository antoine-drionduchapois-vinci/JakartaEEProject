DROP SCHEMA IF EXISTS projetae CASCADE ;

CREATE SCHEMA projetae;

CREATE TABLE projetae.entreprises(
    entreprise_id SERIAL PRIMARY KEY ,
    nom VARCHAR(50) NOT NULL,
    appellation VARCHAR(50),
    adresse VARCHAR(80),
    telephone VARCHAR(50) NOT NULL,
    is_blacklist BOOLEAN,
    avis_professeur VARCHAR(200)
);

CREATE TABLE projetae.utilisateurs(
  utilisateur_id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL ,
    prenom VARCHAR(50) NOT NULL ,
    email VARCHAR(50) NOT NULL ,
    telephone VARCHAR(50) NOT NULL ,
    mdp VARCHAR(200) NOT NULL ,
    annee VARCHAR(50) NOT NULL ,
    role VARCHAR(15) NOT NULL
);

CREATE TABLE projetae.responsables(
  responsable_id SERIAL PRIMARY KEY ,
  nom VARCHAR(50) NOT NULL ,
  prenom VARCHAR(50) NOT NULL,
  telephone VARCHAR(50) NOT NULL ,
  email VARCHAR(50) NOT NULL ,
  entreprise INTEGER NOT NULL REFERENCES projetae.entreprises
);

CREATE TABLE projetae.contacts(
    contact_id SERIAL PRIMARY KEY ,
  description VARCHAR(100),
    etat VARCHAR(12) NOT NULL ,
    motif_refus VARCHAR(100) ,
    annee VARCHAR(50) NOT NULL,
    utilisateur INTEGER NOT NULL REFERENCES projetae.utilisateurs,
    entreprise INTEGER NOT NULL REFERENCES projetae.entreprises

);

CREATE TABLE projetae.stages(
  stage_id SERIAL PRIMARY KEY,
    sujet VARCHAR(100),
    annee VARCHAR(10) NOT NULL ,
    utilisateur INTEGER NOT NULL REFERENCES projetae.utilisateurs,
    entreprise INTEGER NOT NULL REFERENCES projetae.entreprises,
    responsable INTEGER NOT NULL REFERENCES projetae.responsables,
    contact INTEGER NOT NULL REFERENCES projetae.contacts
);

INSERT INTO projetae.utilisateurs(nom, prenom, email, telephone, mdp, annee, role) VALUES
               ('Admin','Francis','admin.francis@vinci.be','05050504','$2a$10$QPhIrlyraGQ30NYZupaD8eZ/nuJYvVD5nhradLQzN6dTtxq3COXk.','1995-1996','administratif');
--password : admin

-- Insert an enterprise
INSERT INTO projetae.entreprises(nom, appellation, adresse, telephone, is_blacklist, avis_professeur) VALUES
    ('Tech Innovations', 'TechInnov', '123 Innovation Drive, Tech City', '123456789', FALSE, 'Highly recommended for students.');

-- Insert a responsible (from the enterprise)
INSERT INTO projetae.responsables(nom, prenom, telephone, email, entreprise) VALUES
    ('Responsible', 'Jordan', '111222333', 'jordan.responsible@techinnov.com', 1);

-- Insert a contact (related to the internship opportunity)
INSERT INTO projetae.contacts(description, etat, motif_refus, annee, utilisateur, entreprise) VALUES
    ('Initial Meeting', 'Confirmed', NULL, '2023-2024', 1, 1);

-- Insert a stage (internship)
INSERT INTO projetae.stages(sujet, annee, utilisateur, entreprise, responsable, contact) VALUES
    ('Developing an Innovative Solution', '2023-2024', 1, 1, 1, 1);