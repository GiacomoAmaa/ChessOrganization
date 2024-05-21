-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Mon May 20 16:26:22 2024 
-- * LUN file: C:\Users\Samuele\OneDrive - Alma Mater Studiorum Università di Bologna\Università\DB\ProgettoDB\design\DBProject.lun 
-- * Schema: ChessOrgREL2/1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database ChessOrgREL2;
use ChessOrgREL2;


-- Tables Section
-- _____________ 

create table ANNUNCI TORNEO (
     Indirizzo char(50) not null,
     Scadenza date not null,
     MaxIscrizioni int not null,
     MinIscrizioni int not null,
     IdAdmin int not null,
     constraint IDANNUNCIO TORNEO primary key (Indirizzo, Scadenza));

create table ARBITRI (
     NumTessera int not null,
     Password char(32) not null,
     CF char(16) not null,
     Nome char(30) not null,
     Cognome char(30) not null,
     constraint IDARBITRO primary key (NumTessera));

create table CASELLE (
     Riga int not null,
     Colonna char(1) not null,
     Visite int not null,
     constraint IDCASELLA primary key (Riga, Colonna));

create table DESIGNAZIONI (
     NumTessera int not null,
     CodDesignazione char(15) not null,
     CodTorneo char(15) not null,
     constraint IDDESIGNAZIONE primary key (NumTessera, CodDesignazione));

create table GESTIONE (
     Indirizzo char(50) not null,
     NumTessera int not null,
     constraint IDGESTIONE primary key (NumTessera, Indirizzo));

create table GIOCATORI (
     NumGiocatore int not null,
     Punteggio int not null,
     Password char(32) not null,
     CF char(16) not null,
     Nome char(30) not null,
     Cognome char(30) not null,
     constraint IDGIOCATORE primary key (NumGiocatore));

create table ISCRIZIONI (
     NumGiocatore int not null,
     Indirizzo char(50) not null,
     Scadenza date not null,
     Data date not null,
     NumRichiesta int not null,
     constraint IDISCRIZIONE primary key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta));

create table MOSSE (
     Indirizzo char(50) not null,
     Scadenza date not null,
     NumGiocatore int not null,
     NumRichiesta int not null,
     CodTorneo char(15) not null,
     CodPartita char(6) not null,
     NumTurno int not null,
     Fazione char not null,
     Pezzo -- Compound attribute -- not null,
     NuovoPezzo char(1),
     PezzoCatturato char(1),
     Matto char,
     RigaArrivo int not null,
     ColonnaArrivo char(1) not null,
     RigaPartenza int not null,
     ColonnaPartenza char(1) not null,
     constraint IDMOSSA primary key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta, CodTorneo, CodPartita, NumTurno));

create table ORGANIZZATORI (
     IdAdmin int not null,
     Password char(32) not null,
     CF char(16) not null,
     Nome char(30) not null,
     Cognome char(30) not null,
     constraint IDORGANIZZATORE primary key (IdAdmin));

create table PARTECIPANTI (
     CodTorneo char(15) not null,
     CodPartita char(6) not null,
     Indirizzo char(50) not null,
     Scadenza date not null,
     NumGiocatore int not null,
     NumRichiesta int not null,
     Fazione char not null,
     constraint IDPARTECIPANTI primary key (CodTorneo, CodPartita, Indirizzo, Scadenza, NumGiocatore, NumRichiesta));

create table PARTITE (
     CodTorneo char(15) not null,
     Livello char(1) not null,
     CodPartita char(6) not null,
     Data date not null,
     constraint IDPARTITA_ID primary key (CodTorneo, CodPartita));

create table SEDI (
     Indirizzo char(50) not null,
     Indicazioni varchar(1),
     constraint IDSEDE primary key (Indirizzo));

create table TORNEI (
     CodTorneo char(15) not null,
     Indirizzo char(50) not null,
     Scadenza date not null,
     DataInizio date not null,
     NumPartecipanti int not null,
     constraint IDTORNEO primary key (CodTorneo),
     constraint FKUFFICIALIZZA_ID unique (Indirizzo, Scadenza));

create table TURNI (
     CodTorneo char(15) not null,
     CodPartita char(6) not null,
     NumTurno int not null,
     constraint IDTURNO_ID primary key (CodTorneo, CodPartita, NumTurno));


-- Constraints Section
-- ___________________ 

alter table ANNUNCI TORNEO add constraint FKOSPITA
     foreign key (Indirizzo)
     references SEDI (Indirizzo);

alter table ANNUNCI TORNEO add constraint FKPUBBLICAZIONE
     foreign key (IdAdmin)
     references ORGANIZZATORI (IdAdmin);

alter table DESIGNAZIONI add constraint FKRICEVE
     foreign key (NumTessera)
     references ARBITRI (NumTessera);

alter table DESIGNAZIONI add constraint FKR
     foreign key (CodTorneo)
     references TORNEI (CodTorneo);

alter table GESTIONE add constraint FKGES_ARB
     foreign key (NumTessera)
     references ARBITRI (NumTessera);

alter table GESTIONE add constraint FKGES_SED
     foreign key (Indirizzo)
     references SEDI (Indirizzo);

alter table ISCRIZIONI add constraint FKREGISTRAZIONE
     foreign key (Indirizzo, Scadenza)
     references ANNUNCI TORNEO (Indirizzo, Scadenza);

alter table ISCRIZIONI add constraint FKRICHIESTA
     foreign key (NumGiocatore)
     references GIOCATORI (NumGiocatore);

alter table MOSSE add constraint FKAPPARTENENZA
     foreign key (CodTorneo, CodPartita, NumTurno)
     references TURNI (CodTorneo, CodPartita, NumTurno);

alter table MOSSE add constraint FKARRIVO
     foreign key (RigaArrivo, ColonnaArrivo)
     references CASELLE (Riga, Colonna);

alter table MOSSE add constraint FKPARTENZA
     foreign key (RigaPartenza, ColonnaPartenza)
     references CASELLE (Riga, Colonna);

alter table MOSSE add constraint FKPARTENITA'
     foreign key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta)
     references ISCRIZIONI (Indirizzo, Scadenza, NumGiocatore, NumRichiesta);

alter table PARTECIPANTI add constraint FKPAR_ISC
     foreign key (Indirizzo, Scadenza, NumGiocatore, NumRichiesta)
     references ISCRIZIONI (Indirizzo, Scadenza, NumGiocatore, NumRichiesta);

alter table PARTECIPANTI add constraint FKPAR_PAR
     foreign key (CodTorneo, CodPartita)
     references PARTITE (CodTorneo, CodPartita);

-- Not implemented
-- alter table PARTITE add constraint IDPARTITA_CHK
--     check(exists(select * from PARTECIPANTI
--                  where PARTECIPANTI.CodTorneo = CodTorneo and PARTECIPANTI.CodPartita = CodPartita)); 

alter table PARTITE add constraint FKCOMPOSIZIONE
     foreign key (CodTorneo)
     references TORNEI (CodTorneo);

alter table TORNEI add constraint FKUFFICIALIZZA_FK
     foreign key (Indirizzo, Scadenza)
     references ANNUNCI TORNEO (Indirizzo, Scadenza);

-- Not implemented
-- alter table TURNI add constraint IDTURNO_CHK
--     check(exists(select * from MOSSE
--                  where MOSSE.CodTorneo = CodTorneo and MOSSE.CodPartita = CodPartita and MOSSE.NumTurno = NumTurno)); 

alter table TURNI add constraint FKIN
     foreign key (CodTorneo, CodPartita)
     references PARTITE (CodTorneo, CodPartita);


-- Index Section
-- _____________ 

