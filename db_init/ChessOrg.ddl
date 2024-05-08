-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Mon May  6 16:32:56 2024 
-- * LUN file: C:\Users\Samuele\OneDrive - Alma Mater Studiorum Università di Bologna\Università\DB\ProgettoDB\design\DBProject.lun 
-- * Schema: ChessOrgREL/1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database ChessOrgREL;
use ChessOrgREL;


-- Tables Section
-- _____________ 

create table ANNUNCIOTORNEO (
     Indirizzo char(40) not null,
     Scadenza date not null,
     MaxIscrizioni int not null,
     MinIscrizioni int not null,
     Id numeric(7) not null,
     constraint IDANNUNCIOTORNEO primary key (Indirizzo, Scadenza));

create table ARBITRO (
     Password char(32) not null,
     CF char(16) not null,
     Nome char(30) not null,
     Cognome char(30) not null,
     NumTessera char(15) not null,
     constraint IDARBITRO primary key (NumTessera));

create table CASELLA (
     Riga int not null,
     Colonna char(1) not null,
     Visite int not null,
     constraint IDCASELLA primary key (Riga, Colonna));

create table DESIGNAZIONE (
     CodTorneo numeric(10) not null,
     NumTessera char(15) not null,
     CodDesignazione numeric(6) not null,
     constraint IDDESIGNAZIONE primary key (NumTessera, CodTorneo, CodDesignazione));

create table GESTIONE (
     Indirizzo char(40) not null,
     NumTessera char(15) not null,
     constraint IDGESTIONE primary key (NumTessera, Indirizzo));

create table GIOCATORE (
     Password char(32) not null,
     CF char(16) not null,
     Nome char(30) not null,
     Cognome char(30) not null,
     NumGiocatore numeric(10),
     Punteggio int not null,
     constraint IDGIOCATORE primary key (NumGiocatore));

create table ISCRIZIONE (
     Indirizzo char(40) not null,
     Scadenza date not null,
     NumGiocatore numeric(10),
     Data date not null,
     NumRichiesta numeric(15) not null,
     constraint IDISCRIZIONE primary key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta));

create table MOSSA (
     Indirizzo char(40) not null,
     Scadenza date not null,
     NumGiocatore numeric(10) not null,
     NumRichiesta numeric(15) not null,
     CodTorneo numeric(10) not null,
     CodPartita numeric(16) not null,
     NumTurno int not null,
     # fazione: true -> bianco, false -> nero
     Fazione boolean not null,
     Pezzo char(1) not null,
     NuovoPezzo char(1),
     Matto boolean not null,
     PezzoCatturato char(1),
     RigaArrivo int not null,
     ColonnaArrivo char(1) not null,
     RigaPartenza int not null,
     ColonnaPartenza char(1) not null,
     constraint IDMOSSA primary key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta, CodTorneo, CodPartita, NumTurno));

create table ORGANIZZATORE (
     Password char(32) not null,
     CF char(16) not null,
     Nome char(30) not null,
     Cognome char(30) not null,
     Id numeric(7) not null,
     constraint IDORGANIZZATORE primary key (Id));

create table PARTECIPANTE (
     CodTorneo numeric(10) not null,
     CodPartita numeric(16) not null,
     Indirizzo char(40) not null,
     Scadenza date not null,
     NumGiocatore numeric(10),
     NumRichiesta numeric(15) not null,
     Fazione boolean not null,
     constraint IDPARTECIPANTE primary key (CodTorneo, CodPartita, Indirizzo, Scadenza, NumGiocatore, NumRichiesta));

create table PARTITA (
     CodTorneo numeric(10) not null,
     Livello int not null,
     CodPartita numeric(16) not null,
     Data date not null,
     constraint IDPARTITA_ID primary key (CodTorneo, CodPartita));

create table SEDE (
     Indirizzo char(40) not null,
     Indicazioni varchar(1),
     constraint IDSEDE primary key (Indirizzo));

create table TORNEO (
     CodTorneo numeric(10) not null,
     Indirizzo char(40) not null,
     Scadenza date not null,
     DataInizio date not null,
     NumPartecipanti int not null,
     constraint IDTORNEO_ID primary key (CodTorneo),
     constraint FKUFFICIALIZZA_ID unique (Indirizzo, Scadenza));

create table TURNO (
     CodTorneo numeric(10) not null,
     CodPartita numeric(16) not null,
     NumTurno int not null,
     constraint IDTURNO_ID primary key (CodTorneo, CodPartita, NumTurno));


-- Constraints Section
-- ___________________ 

alter table ANNUNCIOTORNEO add constraint FKPUBBLICAZIONE
     foreign key (Id)
     references ORGANIZZATORE (Id);

alter table ANNUNCIOTORNEO add constraint FKOSPITA
     foreign key (Indirizzo)
     references SEDE (Indirizzo);

alter table DESIGNAZIONE add constraint FKRICEVE
     foreign key (NumTessera)
     references ARBITRO (NumTessera);

alter table DESIGNAZIONE add constraint FKSUPERVISIONE
     foreign key (CodTorneo)
     references TORNEO (CodTorneo);

alter table GESTIONE add constraint FKGES_ARB
     foreign key (NumTessera)
     references ARBITRO (NumTessera);

alter table GESTIONE add constraint FKGES_SED
     foreign key (Indirizzo)
     references SEDE (Indirizzo);

alter table ISCRIZIONE add constraint FKRICHIESTA
     foreign key (NumGiocatore)
     references GIOCATORE (NumGiocatore);

alter table ISCRIZIONE add constraint FKREGISTRAZIONE
     foreign key (Indirizzo, Scadenza)
     references ANNUNCIOTORNEO (Indirizzo, Scadenza);

alter table MOSSA add constraint FKARRIVO
     foreign key (RigaArrivo, ColonnaArrivo)
     references CASELLA (Riga, Colonna);

alter table MOSSA add constraint FKPARTENZA
     foreign key (RigaPartenza, ColonnaPartenza)
     references CASELLA (Riga, Colonna);

alter table MOSSA add constraint FKAPPARTENENZA
     foreign key (CodTorneo, CodPartita, NumTurno)
     references TURNO (CodTorneo, CodPartita, NumTurno);

alter table MOSSA add constraint FKPARTENITA
     foreign key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta)
     references ISCRIZIONE (Indirizzo, Scadenza, NumGiocatore, NumRichiesta);

alter table PARTECIPANTE add constraint FKPAR_ISC
     foreign key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta)
     references ISCRIZIONE (Indirizzo, Scadenza, NumGiocatore, NumRichiesta);

alter table PARTECIPANTE add constraint FKPAR_PAR
     foreign key (CodTorneo, CodPartita)
     references PARTITA (CodTorneo, CodPartita);

-- Not implemented
-- alter table PARTITA add constraint IDPARTITA_CHK
--     check(exists(select * from PARTECIPANTE
--                  where PARTECIPANTE.Cod Torneo = Cod Torneo and PARTECIPANTE.Cod Partita = Cod Partita)); 

alter table PARTITA add constraint FKCOMPOSIZIONE
     foreign key (CodTorneo)
     references TORNEO (CodTorneo);

-- Not implemented
-- alter table TORNEO add constraint IDTORNEO_CHK
--     check(exists(select * from DESIGNAZIONE
--                  where DESIGNAZIONE.Cod Torneo = Cod Torneo)); 

alter table TORNEO add constraint FKUFFICIALIZZA_FK
     foreign key (Indirizzo, Scadenza)
     references ANNUNCIOTORNEO (Indirizzo, Scadenza);

-- Not implemented
-- alter table TURNO add constraint IDTURNO_CHK
--     check(exists(select * from MOSSA
--                  where MOSSA.Cod Torneo = Cod Torneo and MOSSA.Cod Partita = Cod Partita and MOSSA.Num. Turno = Num. Turno)); 

alter table TURNO add constraint FKIN
     foreign key (CodTorneo, CodPartita)
     references PARTITA (CodTorneo, CodPartita);


-- Index Section
-- _____________ 

