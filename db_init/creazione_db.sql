-- -----------------------------------------------------
-- Schema ChessOrg
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ChessOrg` DEFAULT CHARACTER SET utf8 ;
USE `ChessOrg`;

-- -----------------------------------------------------
-- Table `ChessOrg`.`organizzatori`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`organizzatori` (
    `idadmin` INT NOT NULL AUTO_INCREMENT,
    `username` CHAR(30) NOT NULL,
    `password` VARCHAR(512) NOT NULL,
    `cf` CHAR(16) NOT NULL,
    `nome` CHAR(30) NOT NULL,
    `cognome` CHAR(30) NOT NULL,
    PRIMARY KEY (`idadmin`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`giocatori`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`giocatori` (
    `idgiocatore` INT NOT NULL AUTO_INCREMENT,
    `punteggio` INT  DEFAULT 1000,
    `username` CHAR(30) NOT NULL,
    `password` VARCHAR(512) NOT NULL,
    `cf` CHAR(16) NOT NULL,
    `nome` CHAR(30) NOT NULL,
    `cognome` CHAR(30) NOT NULL,
    PRIMARY KEY (`idgiocatore`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`arbitri`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`arbitri` (
    `numtessera` INT NOT NULL AUTO_INCREMENT,
    `username` CHAR(30) NOT NULL,
    `password` VARCHAR(512) NOT NULL,
    `cf` CHAR(16) NOT NULL,
    `nome` CHAR(30) NOT NULL,
    `cognome` CHAR(30) NOT NULL,
    PRIMARY KEY (`numtessera`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`sedi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`sedi` (
    `indirizzo` CHAR(50) NOT NULL,
    `indicazioni` VARCHAR(255),
    PRIMARY KEY (`indirizzo`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`annunci`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`annunci` (
    `idannuncio`  INT NOT NULL AUTO_INCREMENT, -- DA AGGIUNGERE
    `nome` VARCHAR(20),
    `indirizzo` CHAR(50) NOT NULL,
    `scadenza` DATE NOT NULL,
    `maxiscrizioni` INT NOT NULL,
    `miniscrizioni` INT NOT NULL,
    `idadmin` INT NOT NULL,
    PRIMARY KEY (`idannuncio`),
    FOREIGN KEY (`indirizzo`) REFERENCES `ChessOrg`.`sedi` (`indirizzo`),
    FOREIGN KEY (`idadmin`) REFERENCES `ChessOrg`.`organizzatori` (`idadmin`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`tornei`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`tornei` (
    `codtorneo` INT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(20),
    `indirizzo` CHAR(50) NOT NULL,
    `datainizio` DATE NOT NULL,
    `numpartecipanti` INT NOT NULL,
    `idannuncio` INT NOT NULL,
    PRIMARY KEY (`codtorneo`),
    FOREIGN KEY (`indirizzo`) REFERENCES `ChessOrg`.`sedi` (`indirizzo`),
    FOREIGN KEY (`idannuncio`) REFERENCES `ChessOrg`.`annunci` (`idannuncio`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`iscrizioni`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`iscrizioni` (
    `idiscrizione` INT NOT NULL AUTO_INCREMENT,
    `idannuncio`  INT NOT NULL, -- DA AGGIUNGERE
    `idgiocatore` INT NOT NULL,
    `data` DATE NOT NULL,
    PRIMARY KEY (`idiscrizione`),
    FOREIGN KEY (`idannuncio`) REFERENCES `ChessOrg`.`annunci` (`idannuncio`),
    FOREIGN KEY (`idgiocatore`) REFERENCES `ChessOrg`.`giocatori` (`idgiocatore`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`gestione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`gestione` (
    `indirizzo` CHAR(50) NOT NULL,
    `numtessera` INT NOT NULL,
    PRIMARY KEY (`numtessera`, `indirizzo`),
    FOREIGN KEY (`numtessera`) REFERENCES `ChessOrg`.`arbitri` (`numtessera`)
    ON DELETE CASCADE,
    FOREIGN KEY (`indirizzo`) REFERENCES `ChessOrg`.`sedi` (`indirizzo`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`designazioni`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`designazioni` (
    `coddesignazione` INT NOT NULL AUTO_INCREMENT,
    `numtessera` INT NOT NULL,
    `codtorneo` INT NOT NULL,
    PRIMARY KEY (`coddesignazione`, `numtessera`),
    FOREIGN KEY (`numtessera`) REFERENCES `ChessOrg`.`arbitri` (`numtessera`),
    FOREIGN KEY (`codtorneo`) REFERENCES `ChessOrg`.`tornei` (`codtorneo`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`partite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`partite` (
    `codpartita` INT NOT NULL AUTO_INCREMENT,
    `codtorneo` INT NOT NULL, -- da eliminare livello
    `data` DATE NOT NULL,
    `vincitore` VARCHAR(20) CHECK (`vincitore` IN ('Bianco', 'Nero', 'Pari','')),
    PRIMARY KEY (`codpartita`),
    FOREIGN KEY (`codtorneo`) REFERENCES `ChessOrg`.`tornei` (`codtorneo`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`partecipanti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`partecipanti` (
    `codpartita` INT NOT NULL,
    `idiscrizione` INT NOT NULL,
    `fazione` VARCHAR(20) NOT NULL CHECK (`fazione` IN ('Bianco', 'Nero')),
    PRIMARY KEY (`codpartita`,`idiscrizione`),
    FOREIGN KEY (`idiscrizione`) REFERENCES `ChessOrg`.`iscrizioni` (`idiscrizione`),
    FOREIGN KEY (`codpartita`) REFERENCES `ChessOrg`.`partite` (`codpartita`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`caselle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`caselle` (
    `riga` INT NOT NULL CHECK (`riga` BETWEEN 1 AND 8),
    `colonna` CHAR (1) NOT NULL CHECK (`colonna` BETWEEN 'a' AND 'h'),
    `visite` INT NOT NULL,
    PRIMARY KEY (`riga`,`colonna`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`mosse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`mosse` (
    `idmossa` INT NOT NULL AUTO_INCREMENT, -- AGGIUNTO
    `idiscrizione` INT NOT NULL,
    `codpartita` INT NOT NULL,
    `pezzo` CHAR NOT NULL,
    `nuovopezzo` CHAR,
    `pezzocatturato` CHAR,
    `matto` BOOLEAN,
    `rigaarrivo` INT NOT NULL,
    `colonnaarrivo` CHAR(1) NOT NULL,
    `rigapartenza` INT NOT NULL,
    `colonnapartenza` CHAR(1) NOT NULL,
    `stringamossa` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`idmossa`),
    FOREIGN KEY (`codpartita`) REFERENCES `ChessOrg`.`partite` (`codpartita`) ON DELETE CASCADE,
    FOREIGN KEY (`rigaarrivo`,`colonnaarrivo`) REFERENCES `ChessOrg`.`caselle` (`riga`,`colonna`),
    FOREIGN KEY (`rigapartenza`,`colonnapartenza`) REFERENCES `ChessOrg`.`caselle` (`riga`,`colonna`),
    FOREIGN KEY (`idiscrizione`) REFERENCES `ChessOrg`.`iscrizioni` (`idiscrizione`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ChessOrg`.`turni`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ChessOrg`.`turni` (
    `codpartita` INT NOT NULL, -- da aggiungere mossa nera e mossa bianca
    `mossabianca` INT NOT NULL,
    `mossanera` INT , -- da valutare
    `numturno` INT NOT NULL,
    PRIMARY KEY (`codpartita`, `numturno`),
    FOREIGN KEY (`codpartita`) REFERENCES `ChessOrg`.`partite` (`codpartita`)
    ON DELETE CASCADE,
    FOREIGN KEY (`mossabianca`) REFERENCES `mosse` (`idmossa`),
    FOREIGN KEY (`mossanera`) REFERENCES `mosse` (`idmossa`))
ENGINE = InnoDB;

INSERT INTO `ChessOrg`.`caselle` (riga, colonna, visite) VALUES
(1, 'a'), (1, 'b'), (1, 'c'), (1, 'd'), (1, 'e'), (1, 'f'), (1, 'g'), (1, 'h'),
(2, 'a'), (2, 'b'), (2, 'c'), (2, 'd'), (2, 'e'), (2, 'f'), (2, 'g'), (2, 'h'),
(3, 'a'), (3, 'b'), (3, 'c'), (3, 'd'), (3, 'e'), (3, 'f'), (3, 'g'), (3, 'h'),
(4, 'a'), (4, 'b'), (4, 'c'), (4, 'd'), (4, 'e'), (4, 'f'), (4, 'g'), (4, 'h'),
(5, 'a'), (5, 'b'), (5, 'c'), (5, 'd'), (5, 'e'), (5, 'f'), (5, 'g'), (5, 'h'),
(6, 'a'), (6, 'b'), (6, 'c'), (6, 'd'), (6, 'e'), (6, 'f'), (6, 'g'), (6, 'h'),
(7, 'a'), (7, 'b'), (7, 'c'), (7, 'd'), (7, 'e'), (7, 'f'), (7, 'g'), (7, 'h'),
(8, 'a'), (8, 'b'), (8, 'c'), (8, 'd'), (8, 'e'), (8, 'f'), (8, 'g'), (8, 'h');