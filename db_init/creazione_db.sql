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
(1, 'a', 0), (1, 'b', 0), (1, 'c', 0), (1, 'd', 0), (1, 'e', 0), (1, 'f', 0), (1, 'g', 0), (1, 'h', 0),
(2, 'a', 0), (2, 'b', 0), (2, 'c', 0), (2, 'd', 0), (2, 'e', 0), (2, 'f', 0), (2, 'g', 0), (2, 'h', 0),
(3, 'a', 0), (3, 'b', 0), (3, 'c', 0), (3, 'd', 0), (3, 'e', 0), (3, 'f', 0), (3, 'g', 0), (3, 'h', 0),
(4, 'a', 0), (4, 'b', 0), (4, 'c', 0), (4, 'd', 0), (4, 'e', 0), (4, 'f', 0), (4, 'g', 0), (4, 'h', 0),
(5, 'a', 0), (5, 'b', 0), (5, 'c', 0), (5, 'd', 0), (5, 'e', 0), (5, 'f', 0), (5, 'g', 0), (5, 'h', 0),
(6, 'a', 0), (6, 'b', 0), (6, 'c', 0), (6, 'd', 0), (6, 'e', 0), (6, 'f', 0), (6, 'g', 0), (6, 'h', 0),
(7, 'a', 0), (7, 'b', 0), (7, 'c', 0), (7, 'd', 0), (7, 'e', 0), (7, 'f', 0), (7, 'g', 0), (7, 'h', 0),
(8, 'a', 0), (8, 'b', 0), (8, 'c', 0), (8, 'd', 0), (8, 'e', 0), (8, 'f', 0), (8, 'g', 0), (8, 'h', 0);