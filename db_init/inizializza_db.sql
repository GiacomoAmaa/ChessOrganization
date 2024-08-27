INSERT INTO giocatori (idgiocatore, username, password, cf, nome, cognome) VALUES
(1, 'player', 'root', 'PNIBNCIDWE56N345', 'Pino', 'Bianchi'),
(2, 'aliceW', 'pwdAlice1', 'WHTLCA80A01H501T', 'Alice', 'White'),
(3, 'bobM', 'pwdBob2', 'MRTBBN85M01H501B', 'Bob', 'Martin'),
(4, 'charlieS', 'pwdCharlie3', 'SMTHCL90L01H501S', 'Charlie', 'Smith'),
(5, 'davidJ', 'pwdDavid4', 'JHNSND80D01H501J', 'David', 'Johnson'),
(6, 'evaB', 'pwdEva5', 'BRNEVA85E01H501B', 'Eva', 'Brown'),
(7, 'frankT', 'pwdFrank6', 'TLYFRK70F01H501T', 'Frank', 'Taylor'),
(8, 'graceH', 'pwdGrace7', 'HRSGRC80G01H501H', 'Grace', 'Harris'),
(9, 'henryC', 'pwdHenry8', 'CLRHRY85H01H501C', 'Henry', 'Clark'),
(10, 'isabelL', 'pwdIsabel9', 'LWSISB80I01H501L', 'Isabel', 'Lewis'),
(11, 'jackD', 'pwdJack10', 'DVSJCK75J01H501D', 'Jack', 'Davis'),
(12, 'kateR', 'pwdKate11', 'RBKATE80K01H501R', 'Kate', 'Robinson'),
(13, 'leoW', 'pwdLeo12', 'WKLLEO85L01H501W', 'Leo', 'Walker'),
(14, 'maryH', 'pwdMary13', 'HLLMRY80M01H501H', 'Mary', 'Hall'),
(15, 'nickA', 'pwdNick14', 'LLNNCK75N01H501A', 'Nick', 'Allen'),
(16, 'oliviaM', 'pwdOlivia15', 'MTCOLV80O01H501M', 'Olivia', 'Mitchell'),
(17, 'paulY', 'pwdPaul16', 'YNGPUL85P01H501Y', 'Paul', 'Young'),
(18, 'quinnS', 'pwdQuinn17', 'SCTQNN80Q01H501S', 'Quinn', 'Scott');


INSERT INTO organizzatori (idadmin, nome, cognome, cf, username, password) VALUES 
(1, 'Giorgio', 'Brighi', 'GBDNEFN3944N4UB4', 'admin', 'root');

INSERT INTO sedi (indirizzo, indicazioni) VALUES 
('Via Garibaldi, 34, Bologna', 'Vicino alla piazza centrale.'),
('Corso Vittorio Emanuele, 12, Bari', 'Di fronte al teatro comunale.'),
('Piazza del Duomo, 1, Milano', 'Dietro alla cattedrale principale.');

INSERT INTO arbitri (numtessera, nome, cognome, cf , username, password) VALUES
(1, 'Pierluigi', 'Collina', 'PRLCLLIDWE56N345', 'ref', 'root'),
(2, 'Marco', 'Rossi', 'RSSMRC75A01H501Z', 'marco.rossi', 'r4U9J5kLnHqZcB8x1D0FmNnQzY7gP4Tr'),
(3, 'Luca', 'Verdi', 'VRDLUC85B10F205N', 'luca.verdi', 'D3tL0xR2qV1WpS8zA6F9GnYkU0bI7H5m'),
(4, 'Giulia', 'Bianchi', 'BNCGLI92C12C532S', 'giulia.bianchi', 'Y9T1WqF4cK6mR3Z7N8pL0gX2V5dQ9J1r'),
(5, 'Francesco', 'Neri', 'NRIFNC70D20E121R', 'francesco.neri', 'P6kQ1L7nC3X9zA0gV8tW4H2yR5mJ8B1d'),
(6, 'Elena', 'Russo', 'RSSENN80E15L845P', 'elena.russo', 'V7Z9tR2wN6mL0Q5gX3pF1D8kY4J3B2c'),
(7, 'Matteo', 'Ferrari', 'FRRMTE90F25C151T', 'matteo.ferrari', 'N3mL9P7yX6T1kW2rF4G8dV0zQ5J3C2n'),
(8, 'Sara', 'Esposito', 'SPSRSE85G10B013M', 'sara.esposito', 'Q4X9tW7pN3K2gL8dF1J6mR0yV5Z1C7n'),
(9, 'Davide', 'Conti', 'CNTDVD85H25D963O', 'davide.conti', 'L2J8P1rQ7V6X3mK5gF0Y9W4dT3B9Z6n'),
(10, 'Anna', 'Galli', 'GLLNNA90I15E098U', 'anna.galli', 'B1D4tN6mR9J3X7kP2G8L5Y0W9F3Q1Z2c'),
(11, 'Giovanni', 'Romano', 'RMNGVN95L30H211E', 'giovanni.romano', 'Z2P9tQ4W7mL8X1gN3F0V5J6kY3R9C7d'),
(12, 'Chiara', 'Marini', 'MRNCHR70D15C351B', 'chiara.marini', 'R5X1W9pQ2J7tL3mV8G6F0K4nY3B9C2d');


INSERT INTO gestione (indirizzo, numtessera) VALUES 
('Via Garibaldi, 34, Bologna', 1),
('Via Garibaldi, 34, Bologna', 2),
('Via Garibaldi, 34, Bologna', 3),
('Via Garibaldi, 34, Bologna', 4),
('Corso Vittorio Emanuele, 12, Bari', 1),
('Corso Vittorio Emanuele, 12, Bari', 5),
('Corso Vittorio Emanuele, 12, Bari', 6),
('Corso Vittorio Emanuele, 12, Bari', 7),
('Corso Vittorio Emanuele, 12, Bari', 8),
('Piazza del Duomo, 1, Milano', 1),
('Piazza del Duomo, 1, Milano', 9),
('Piazza del Duomo, 1, Milano', 10),
('Piazza del Duomo, 1, Milano', 11),
('Piazza del Duomo, 1, Milano', 12);

INSERT INTO `annunci`(`idannuncio`, `nome`, `indirizzo`, `scadenza`, `maxiscrizioni`, `miniscrizioni`, `idadmin`) VALUES
(1,'Torneo Estivo A','Via Garibaldi, 34, Bologna','2024-08-22',10,2,1),
(2,'Torneo Estivo B','Via Garibaldi, 34, Bologna','2024-08-23',10,2,1),
(3,'Torneo Estivo C','Corso Vittorio Emanuele, 12, Bari','2024-08-24',10,2,1),
(4,'Torneo Estivo D','Piazza del Duomo, 1, Milano','2024-08-25',10,2,1);

INSERT INTO `tornei`(`codtorneo`, `nome`, `indirizzo`, `datainizio`, `numpartecipanti`, `idannuncio`) VALUES
(1,'Torneo Estivo A','Via Garibaldi, 34, Bologna','2024-08-23',2,1),
(2,'Torneo Estivo B','Via Garibaldi, 34, Bologna','2024-08-24',3,2),
(3,'Torneo Estivo C','Corso Vittorio Emanuele, 12, Bari','2024-08-25',2,3),
(4,'Torneo Estivo D','Piazza del Duomo, 1, Milano','2024-08-26',3,4);

INSERT INTO designazioni(coddesignazione, numtessera, codtorneo) VALUES
(1, 1, 4),
(2, 2, 1),
(3, 3, 2),
(4, 6, 3);

INSERT INTO iscrizioni(idiscrizione, idannuncio, idgiocatore, data) VALUES
(1, 1, 1, '2024-08-19'),
(2, 1, 2, '2024-08-19'),
(3, 2, 1, '2024-08-19'),
(4, 2, 2, '2024-08-19'),
(5, 2, 3, '2024-08-19'),
(6, 3, 1, '2024-08-19'),
(7, 3, 4, '2024-08-19'),
(8, 4, 6, '2024-08-19'),
(9, 4, 5, '2024-08-19'),
(10, 4, 3, '2024-08-19');

INSERT INTO `partite`(`codpartita`, `codtorneo`, `data`, `vincitore`) VALUES
(1,1,'2024-08-23','Bianco'),
(2,1,'2024-08-23','Nero'),
(3,2,'2024-08-24','Bianco'),
(4,2,'2024-08-24','Bianco'),
(5,2,'2024-08-24','Pari'),
(6,2,'2024-08-24','Nero'),
(7,2,'2024-08-24','Pari'),
(8,2,'2024-08-24','Pari'),
(9,3,'2024-08-25','Nero'),
(10,3,'2024-08-25','Bianco'),
(11,4,'2024-08-26',''),
(12,4,'2024-08-26',''),
(13,4,'2024-08-26',''),
(14,4,'2024-08-26',''),
(15,4,'2024-08-26',''),
(16,4,'2024-08-26','');

INSERT INTO partecipanti(codpartita, idiscrizione, fazione) VALUES
(1, 1, 'Bianco'),(1, 2, 'Nero'),(2, 1, 'Nero'),(2, 2, 'Bianco'),(3, 3, 'Bianco'),
(3, 4, 'Nero'),(4, 4, 'Bianco'),(4, 3, 'Nero'),(5, 3, 'Bianco'),(5, 5, 'Nero'),
(6, 5, 'Bianco'),(6, 3, 'Nero'),(7, 4, 'Bianco'),(7, 5, 'Nero'),(8, 5, 'Bianco'),
(8, 4, 'Nero'),(9, 6, 'Bianco'),(9, 7, 'Nero'),(10, 7, 'Bianco'),(10, 6, 'Nero'),
(11, 8, 'Bianco'),(11, 9, 'Nero'),(12, 9, 'Bianco'),(12, 8, 'Nero'),(13, 8, 'Bianco'),
(13, 10, 'Nero'),(14, 10, 'Bianco'),(14, 8, 'Nero'),(15, 9, 'Bianco'),(15, 10, 'Nero'),
(16, 10, 'Bianco'),(16, 9, 'Nero');


INSERT INTO mosse (idmossa, idiscrizione, codpartita, pezzo, nuovopezzo, pezzocatturato, matto, rigaarrivo, colonnaarrivo, rigapartenza, colonnapartenza, stringamossa) VALUES
-- Vittoria Bianco
(1, 1, 1, 'P', NULL, NULL, 0, 4, 'd', 2, 'd', 'P:d2:::d4'),
(2, 2, 1, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(3, 1, 1, 'P', NULL, NULL, 0, 5, 'd', 4, 'd', 'P:d4:::d5'),
(4, 2, 1, 'N', NULL, NULL, 0, 4, 'd', 6, 'c', 'N:c6:::d4'),
(5, 1, 1, 'B', NULL, NULL, 0, 3, 'e', 1, 'c', 'B:c1:::e3'),
(6, 2, 1, 'N', NULL, 'P', 0, 2, 'e', 4, 'd', 'N:d4:x:P:e2'),
(7, 1, 1, 'Q', NULL, 'N', 0, 2, 'e', 1, 'd', 'Q:d1:x:N:e2'),
(8, 2, 1, 'P', NULL, NULL, 0, 5, 'f', 7, 'f', 'P:f7:::f5'),
(9, 1, 1, 'B', NULL, NULL, 0, 6, 'h', 3, 'e', 'B:e3:::h6'),
(10, 2, 1, 'P', NULL, 'B', 0, 6, 'h', 7, 'g', 'P:g7:x:B:h6'),
(11, 1, 1, 'Q', NULL, NULL, 0, 5, 'e', 2, 'e', 'Q:e2:::e5'),
(12, 2, 1, 'P', NULL, NULL, 0, 6, 'd', 7, 'd', 'P:d7:!::d6'),
-- Vittoria Nero
(13, 2, 2, 'P', NULL, NULL, 0, 4, 'e', 2, 'e', 'P:e2:::e4'),
(14, 1, 2, 'P', NULL, NULL, 0, 5, 'e', 7, 'e', 'P:e7:::e5'),
(15, 2, 2, 'N', NULL, NULL, 0, 3, 'f', 1, 'g', 'N:g1:::f3'),
(16, 1, 2, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(17, 2, 2, 'N', NULL, NULL, 0, 3, 'c', 1, 'b', 'N:b1:::c3'),
(18, 1, 2, 'P', NULL, NULL, 0, 6, 'g', 7, 'g', 'P:g7:::g6'),
(19, 2, 2, 'P', NULL, NULL, 0, 3, 'd', 2, 'd', 'P:d2:::d3'),
(20, 1, 1, 'B', NULL, NULL, 0, 7, 'g', 8, 'f', 'B:f8:::g7'),
(21, 2, 2, 'B', NULL, NULL, 0, 5, 'g', 1, 'c', 'B:c1:::g5'),
(22, 1, 2, 'P', NULL, NULL, 0, 6, 'f', 7, 'f', 'P:f7:::f6'),
(23, 2, 2, 'B', NULL, NULL, 0, 4, 'h', 5, 'g', 'B:g5:::h4'),
(24, 1, 2, 'P', NULL, NULL, 0, 5, 'h', 7, 'h', 'P:h7:::h5'),
(25, 2, 2, 'P', NULL, NULL, 0, 3, 'h', 2, 'h', 'P:h2:!::h3'),
-- Vittoria Bianco
(26, 3, 3, 'P', NULL, NULL, 0, 4, 'd', 2, 'd', 'P:d2:::d4'),
(27, 4, 3, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(28, 3, 3, 'P', NULL, NULL, 0, 5, 'd', 4, 'd', 'P:d4:::d5'),
(29, 4, 3, 'N', NULL, NULL, 0, 4, 'd', 6, 'c', 'N:c6:::d4'),
(30, 3, 3, 'B', NULL, NULL, 0, 3, 'e', 1, 'c', 'B:c1:::e3'),
(31, 4, 3, 'N', NULL, 'P', 0, 2, 'e', 4, 'd', 'N:d4:x:P:e2'),
(32, 3, 3, 'Q', NULL, 'N', 0, 2, 'e', 1, 'd', 'Q:d1:x:N:e2'),
(33, 4, 3, 'P', NULL, NULL, 0, 5, 'f', 7, 'f', 'P:f7:::f5'),
(34, 3, 3, 'B', NULL, NULL, 0, 6, 'h', 3, 'e', 'B:e3:::h6'),
(35, 4, 3, 'P', NULL, 'B', 0, 6, 'h', 7, 'g', 'P:g7:x:B:h6'),
(36, 3, 3, 'Q', NULL, NULL, 0, 5, 'e', 2, 'e', 'Q:e2:::e5'),
(37, 4, 3, 'P', NULL, NULL, 0, 6, 'd', 7, 'd', 'P:d7:!::d6'),
-- Vittoria Bianco
(38, 4, 4, 'P', NULL, NULL, 0, 4, 'd', 2, 'd', 'P:d2:::d4'),
(39, 3, 4, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(40, 4, 4, 'P', NULL, NULL, 0, 5, 'd', 4, 'd', 'P:d4:::d5'),
(41, 3, 4, 'N', NULL, NULL, 0, 4, 'd', 6, 'c', 'N:c6:::d4'),
(42, 4, 4, 'B', NULL, NULL, 0, 3, 'e', 1, 'c', 'B:c1:::e3'),
(43, 3, 4, 'N', NULL, 'P', 0, 2, 'e', 4, 'd', 'N:d4:x:P:e2'),
(44, 4, 4, 'Q', NULL, 'N', 0, 2, 'e', 1, 'd', 'Q:d1:x:N:e2'),
(45, 3, 4, 'P', NULL, NULL, 0, 5, 'f', 7, 'f', 'P:f7:::f5'),
(46, 4, 4, 'B', NULL, NULL, 0, 6, 'h', 3, 'e', 'B:e3:::h6'),
(47, 3, 4, 'P', NULL, 'B', 0, 6, 'h', 7, 'g', 'P:g7:x:B:h6'),
(48, 4, 4, 'Q', NULL, NULL, 0, 5, 'e', 2, 'e', 'Q:e2:::e5'),
(49, 3, 4, 'P', NULL, NULL, 0, 6, 'd', 7, 'd', 'P:d7:!::d6'),

-- Pari
(50, 3, 5, 'P', NULL, NULL, 0, 4, 'd', 2, 'd', 'P:d2:::d4'),
(51, 5, 5, 'P', NULL, NULL, 0, 5, 'e', 7, 'e', 'P:e7:::e5'),
(52, 3, 5, 'P', NULL, 'P', 0, 5, 'e', 4, 'd', 'P:d4:x:P:e5'),
(53, 5, 5, 'P', NULL, NULL, 0, 6, 'd', 7, 'd', 'P:d7:::d6'),
(54, 3, 5, 'P', NULL, 'P', 0, 6, 'd', 5, 'e', 'P:e5:x:P:d6'),
(55, 5, 5, 'Q', NULL, 'P', 0, 6, 'd', 8, 'd', 'Q:d8:x:P:d6'),
(56, 3, 5, 'Q', NULL, 'Q', 0, 6, 'd', 1, 'd', 'Q:d1:x:Q:d6'),
(57, 5, 5, 'B', NULL, 'Q', 0, 6, 'd', 8, 'f', 'B:f8:x:Q:d6'),
(58, 3, 5, 'P', NULL, NULL, 0, 4, 'e', 2, 'e', 'P:e2:::e4'),
(59, 5, 5, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(60, 3, 5, 'P', NULL, NULL, 0, 3, 'f', 2, 'f', 'P:f2:::f3'),
(61, 5, 5, 'B', NULL, NULL, 0, 7, 'd', 8, 'c', 'B:c8:::d7'),
(62, 3, 5, 'B', NULL, NULL, 0, 3, 'd', 1, 'f', 'B:f1:::d3'),
(63, 5, 5, 'K', NULL, NULL, 0, 8, 'c', 8, 'e', 'e8:c8:O-O:a8:d8'),
(64, 3, 5, 'N', NULL, NULL, 0, 3, 'c', 1, 'b', 'N:b1:::c3'),
(65, 5, 5, 'P', NULL, NULL, 0, 6, 'g', 7, 'g', 'P:g7:?::g6'),
-- Vittoria Nero
(66, 5, 6, 'P', NULL, NULL, 0, 4, 'e', 2, 'e', 'P:e2:::e4'),
(67, 3, 6, 'P', NULL, NULL, 0, 5, 'e', 7, 'e', 'P:e7:::e5'),
(68, 5, 6, 'N', NULL, NULL, 0, 3, 'f', 1, 'g', 'N:g1:::f3'),
(69, 3, 6, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(70, 5, 6, 'N', NULL, NULL, 0, 3, 'c', 1, 'b', 'N:b1:::c3'),
(71, 3, 6, 'P', NULL, NULL, 0, 6, 'g', 7, 'g', 'P:g7:::g6'),
(72, 5, 6, 'P', NULL, NULL, 0, 3, 'd', 2, 'd', 'P:d2:::d3'),
(73, 3, 6, 'B', NULL, NULL, 0, 7, 'g', 8, 'f', 'B:f8:::g7'),
(74, 5, 6, 'B', NULL, NULL, 0, 5, 'g', 1, 'c', 'B:c1:::g5'),
(75, 3, 6, 'P', NULL, NULL, 0, 6, 'f', 7, 'f', 'P:f7:::f6'),
(76, 5, 6, 'B', NULL, NULL, 0, 4, 'h', 5, 'g', 'B:g5:::h4'),
(77, 3, 6, 'P', NULL, NULL, 0, 5, 'h', 7, 'h', 'P:h7:::h5'),
(78, 5, 6, 'P', NULL, NULL, 0, 3, 'h', 2, 'h', 'P:h2:!::h3'),
-- Pari
(79, 4, 7, 'P', NULL, NULL, 0, 4, 'd', 2, 'd', 'P:d2:::d4'),
(80, 5, 7, 'P', NULL, NULL, 0, 5, 'e', 7, 'e', 'P:e7:::e5'),
(81, 4, 7, 'P', NULL, 'P', 0, 5, 'e', 4, 'd', 'P:d4:x:P:e5'),
(82, 5, 7, 'P', NULL, NULL, 0, 6, 'd', 7, 'd', 'P:d7:::d6'),
(83, 4, 7, 'P', NULL, 'P', 0, 6, 'd', 5, 'e', 'P:e5:x:P:d6'),
(84, 5, 7, 'Q', NULL, 'P', 0, 6, 'd', 8, 'd', 'Q:d8:x:P:d6'),
(85, 4, 7, 'Q', NULL, 'Q', 0, 6, 'd', 1, 'd', 'Q:d1:x:Q:d6'),
(86, 5, 7, 'B', NULL, 'Q', 0, 6, 'd', 8, 'f', 'B:f8:x:Q:d6'),
(87, 4, 7, 'P', NULL, NULL, 0, 4, 'e', 2, 'e', 'P:e2:::e4'),
(88, 5, 7, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(89, 4, 7, 'P', NULL, NULL, 0, 3, 'f', 2, 'f', 'P:f2:::f3'),
(90, 5, 7, 'B', NULL, NULL, 0, 7, 'd', 8, 'c', 'B:c8:::d7'),
(91, 4, 7, 'B', NULL, NULL, 0, 3, 'd', 1, 'f', 'B:f1:::d3'),
(92, 5, 7, 'K', NULL, NULL, 0, 8, 'c', 8, 'e', 'e8:c8:O-O:a8:d8'),
(93, 4, 7, 'N', NULL, NULL, 0, 3, 'c', 1, 'b', 'N:b1:::c3'),
(94, 5, 7, 'P', NULL, NULL, 0, 6, 'g', 7, 'g', 'P:g7:?::g6'),
-- Pari
(95, 5, 8, 'P', NULL, NULL, 0, 4, 'd', 2, 'd', 'P:d2:::d4'),
(96, 4, 8, 'P', NULL, NULL, 0, 5, 'e', 7, 'e', 'P:e7:::e5'),
(97, 5, 8, 'P', NULL, 'P', 0, 5, 'e', 4, 'd', 'P:d4:x:P:e5'),
(98, 4, 8, 'P', NULL, NULL, 0, 6, 'd', 7, 'd', 'P:d7:::d6'),
(99, 5, 8, 'P', NULL, 'P', 0, 6, 'd', 5, 'e', 'P:e5:x:P:d6'),
(100, 4, 8, 'Q', NULL, 'P', 0, 6, 'd', 8, 'd', 'Q:d8:x:P:d6'),
(101, 5, 8, 'Q', NULL, 'Q', 0, 6, 'd', 1, 'd', 'Q:d1:x:Q:d6'),
(102, 4, 8, 'B', NULL, 'Q', 0, 6, 'd', 8, 'f', 'B:f8:x:Q:d6'),
(103, 5, 8, 'P', NULL, NULL, 0, 4, 'e', 2, 'e', 'P:e2:::e4'),
(104, 4, 8, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(105, 5, 8, 'P', NULL, NULL, 0, 3, 'f', 2, 'f', 'P:f2:::f3'),
(106, 4, 8, 'B', NULL, NULL, 0, 7, 'd', 8, 'c', 'B:c8:::d7'),
(107, 5, 8, 'B', NULL, NULL, 0, 3, 'd', 1, 'f', 'B:f1:::d3'),
(108, 4, 8, 'K', NULL, NULL, 0, 8, 'c', 8, 'e', 'e8:c8:O-O:a8:d8'),
(109, 5, 8, 'N', NULL, NULL, 0, 3, 'c', 1, 'b', 'N:b1:::c3'),
(110, 4, 8, 'P', NULL, NULL, 0, 6, 'g', 7, 'g', 'P:g7:?::g6'),
-- Vittoria Nero
(111, 6, 9, 'P', NULL, NULL, 0, 4, 'e', 2, 'e', 'P:e2:::e4'),
(112, 7, 9, 'P', NULL, NULL, 0, 5, 'e', 7, 'e', 'P:e7:::e5'),
(113, 6, 9, 'N', NULL, NULL, 0, 3, 'f', 1, 'g', 'N:g1:::f3'),
(114, 7, 9, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(115, 6, 9, 'N', NULL, NULL, 0, 3, 'c', 1, 'b', 'N:b1:::c3'),
(116, 7, 9, 'P', NULL, NULL, 0, 6, 'g', 7, 'g', 'P:g7:::g6'),
(117, 6, 9, 'P', NULL, NULL, 0, 3, 'd', 2, 'd', 'P:d2:::d3'),
(118, 7, 9, 'B', NULL, NULL, 0, 7, 'g', 8, 'f', 'B:f8:::g7'),
(119, 6, 9, 'B', NULL, NULL, 0, 5, 'g', 1, 'c', 'B:c1:::g5'),
(120, 7, 9, 'P', NULL, NULL, 0, 6, 'f', 7, 'f', 'P:f7:::f6'),
(121, 6, 9, 'B', NULL, NULL, 0, 4, 'h', 5, 'g', 'B:g5:::h4'),
(122, 7, 9, 'P', NULL, NULL, 0, 5, 'h', 7, 'h', 'P:h7:::h5'),
(123, 6, 9, 'P', NULL, NULL, 0, 3, 'h', 2, 'h', 'P:h2:!::h3'),
-- Vittoria Bianco
(124, 7, 10, 'P', NULL, NULL, 0, 4, 'd', 2, 'd', 'P:d2:::d4'),
(125, 6, 10, 'N', NULL, NULL, 0, 6, 'c', 8, 'b', 'N:b8:::c6'),
(126, 7, 10, 'P', NULL, NULL, 0, 5, 'd', 4, 'd', 'P:d4:::d5'),
(127, 6, 10, 'N', NULL, NULL, 0, 4, 'd', 6, 'c', 'N:c6:::d4'),
(128, 7, 10, 'B', NULL, NULL, 0, 3, 'e', 1, 'c', 'B:c1:::e3'),
(129, 6, 10, 'N', NULL, 'P', 0, 2, 'e', 4, 'd', 'N:d4:x:P:e2'),
(130, 7, 10, 'Q', NULL, 'N', 0, 2, 'e', 1, 'd', 'Q:d1:x:N:e2'),
(131, 6, 10, 'P', NULL, NULL, 0, 5, 'f', 7, 'f', 'P:f7:::f5'),
(132, 7, 10, 'B', NULL, NULL, 0, 6, 'h', 3, 'e', 'B:e3:::h6'),
(133, 6, 10, 'P', NULL, 'B', 0, 6, 'h', 7, 'g', 'P:g7:x:B:h6'),
(134, 7, 10, 'Q', NULL, NULL, 0, 5, 'e', 2, 'e', 'Q:e2:::e5'),
(135, 6, 10, 'P', NULL, NULL, 0, 6, 'd', 7, 'd', 'P:d7:!::d6');

UPDATE giocatori SET punteggio = 1025 WHERE idgiocatore = 1;
UPDATE giocatori SET punteggio = 950 WHERE idgiocatore = 2;
UPDATE giocatori SET punteggio = 975 WHERE idgiocatore = 3;
UPDATE giocatori SET punteggio = 1050 WHERE idgiocatore = 4;

INSERT INTO `turni`(`codpartita`, `mossabianca`, `mossanera`, `numturno`) VALUES
(1, 1, 2, 0), (1, 3, 4, 1), (1, 5, 6, 2), (1, 7, 8, 3), (1, 9, 10, 4), (1, 11, 12, 5),
(2, 13, 14, 0), (2, 15, 16, 1), (2, 17, 18, 2), (2, 19, 20, 3), (2, 21, 22, 4), (2, 23, 24, 5), (2, 25, NULL, 6),
(3, 26, 27, 0), (3, 28, 29, 1), (3, 30, 31, 2), (3, 32, 33, 3), (3, 34, 35, 4), (3, 36, 37, 5),
(4, 38, 39, 0), (4, 40, 41, 1), (4, 42, 43, 2), (4, 44, 45, 3), (4, 46, 47, 4), (4, 48, 49, 5),
(5, 50, 51, 0), (5, 52, 53, 1), (5, 54, 55, 2), (5, 56, 57, 3), (5, 58, 59, 4), (5, 60, 61, 5), (5, 62, 63, 6), (5, 64, 65,7),
(6, 66, 67, 0), (6, 68, 69, 1), (6, 70, 71, 2), (6, 72, 73, 3), (6, 74, 75, 4), (6, 76, 77, 5), (6, 78, NULL, 6),
(7, 79, 80, 0), (7, 81, 82, 1), (7, 83, 84, 2), (7, 85, 86, 3), (7, 87, 88, 4), (7, 89, 90, 5), (7, 91, 92, 6), (7, 93, 94,7),
(8, 95, 96, 0), (8, 97, 98, 1), (8, 99, 100, 2), (8, 101, 102, 3), (8, 103, 104, 4), (8, 105, 106, 5), (8, 107, 108, 6), (8, 109, 110, 7),
(9, 111, 112, 0), (9, 113, 114, 1), (9, 115, 116, 2), (9, 117, 118, 3), (9, 119, 120, 4), (9, 121, 122, 5), (9, 123, NULL, 6),
(10, 124, 125, 0), (10, 126, 127, 1), (10, 128, 129, 2), (10, 130, 131, 3), (10, 132, 133, 4), (10, 134, 135, 5);
