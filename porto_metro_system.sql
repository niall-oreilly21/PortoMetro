/*
	****************** PORTO METRO SYSTEM *******************
*/


/*-----------------------------------------------------------------CREATES-------------------------------------------------------------------------------------*/

/*CREATES the porto metro system database*/
CREATE DATABASE IF NOT EXISTS porto_metro_system DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE porto_metro_system;

/*DROPS any of the tables that may already exists*/
DROP TABLE IF EXISTS station_facilities, facilities, schedules, timetables, routes, trains,  metro_lines, passengers_journey_planners, journey_planners, stations, blue_cards, timer_cards, cards_zones, cards_prices, passengers_cards, cards, students_universities, universities, users, zones;


/*CREATE zones table*/
CREATE TABLE zones 
(
    zone_id INT NOT NULL AUTO_INCREMENT,
    zone_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (zone_id)
);


/*CREATE users table*/
CREATE TABLE users
(
    user_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user_type ENUM("passenger", "administrator", "student") NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (email)
);


/*CREATE universities table*/
CREATE TABLE universities
(
    university_id VARCHAR(30) NOT NULL,
    university_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (university_id)
);


/*CREATE students_universities table*/
CREATE TABLE students_universities
(
    user_id INT NOT NULL,
    university_id VARCHAR(30) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (university_id) REFERENCES universities(university_id),
    UNIQUE (user_id)
);


/*CREATE cards table*/
CREATE TABLE cards
(
    card_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    card_type ENUM("blue card", "grey card", "student card", "tour card") NOT NULL,
    access_type ENUM("All zones", "3 zones") NOT NULL,
    card_price DECIMAL(5, 2),
    PRIMARY KEY(card_id),
    UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);


/*CREATE table card_zones*/
CREATE TABLE cards_zones
(
    card_id INT NOT NULL,
    zone_id INT NOT NULL,
    FOREIGN KEY (card_id) REFERENCES cards(card_id),
    FOREIGN KEY (zone_id) REFERENCES zones(zone_id),
    UNIQUE (card_id, zone_id)
);


/*CREATE blue card table*/
CREATE TABLE timer_cards 
(
    card_id INT NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (card_id) REFERENCES cards(card_id),
    UNIQUE(card_id)
);


/*CREATE card types table*/
CREATE TABLE blue_cards
(
    card_id INT NOT NULL,
    total_trips_allowed INT,
    FOREIGN KEY (card_id) REFERENCES cards(card_id),
    UNIQUE(card_id)
);


/*CREATE stations table*/
CREATE TABLE stations
(
    station_id VARCHAR(3) NOT NUll,
    zone_id INT NOT NULL,
    station_name VARCHAR(50),
    PRIMARY KEY (station_id),
    FOREIGN KEY (zone_id) REFERENCES zones(zone_id)
);


/*CREATE journey_planners table*/
CREATE TABLE journey_planners
(
    journey_planner_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    start_station_id VARCHAR(3) NOT NULL,
    end_station_id VARCHAR(3) NOT NULL,
    start_time TIME NOT NULL,
    timetable_day_type ENUM("Monday-Friday", "Saturday","Sunday") NOT NULL,
    PRIMARY KEY (journey_planner_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (start_station_id) REFERENCES stations(station_id),
    FOREIGN KEY (end_station_id) REFERENCES stations(station_id),
    UNIQUE(user_id, start_station_id, end_station_id, start_time, timetable_day_type)
);


/*CREATE lines table*/
CREATE TABLE metro_lines
(
    line_id VARCHAR(1) NOT NULL,
    line_name VARCHAR(255),
    PRIMARY KEY (line_id)
);

/*CREATE trains table*/
CREATE TABLE trains
(
    train_id VARCHAR(4) NOT NULL,
    line_id VARCHAR(1) NOT NULL,
    train_model ENUM("Eurotram", "Flexity Swift LRVs") NOT NULL,
    carriages INT(1),
    capacity INT(3),
    PRIMARY KEY (train_id),
    FOREIGN KEY (line_id) REFERENCES metro_lines(line_id)
);

/*CREATE routes table*/
CREATE TABLE routes
(
    route_id INT NOT NULL AUTO_INCREMENT,
    line_id VARCHAR(1) NOT NULL,
    end_station_id VARCHAR(3) NOT NULL,
    PRIMARY KEY (route_id),
    FOREIGN KEY (line_id) REFERENCES metro_lines(line_id),
    FOREIGN KEY (end_station_id) REFERENCES stations(station_id)
);

/*CREATE timetable table*/
CREATE TABLE timetables
(
    timetable_id INT NOT NULL AUTO_INCREMENT,
    route_id INT NOT NULL,
    timetable_day_type ENUM("Monday-Friday", "Saturday","Sunday") NOT NULL,
    PRIMARY KEY (timetable_id),
    FOREIGN KEY (route_id) REFERENCES routes(route_id)
);

/*CREATE schedules table*/
CREATE TABLE schedules
(
    timetable_id INT NOT NULL,
    station_id VARCHAR(3),
    row_number INT NOT NULL, 
    departure_time TIME NOT NULL,
    FOREIGN KEY (timetable_id) REFERENCES timetables(timetable_id),
    FOREIGN KEY (station_id) REFERENCES stations(station_id),
    UNIQUE(timetable_id, row_number, station_id, departure_time)
);


/*CREATE facilities table*/
CREATE TABLE facilities
(
    facility_id INT NOT NUll AUTO_INCREMENT,
    facility_description VARCHAR(255),
    PRIMARY KEY (facility_id)
);


/*CREATE facilities table*/
CREATE TABLE station_facilities
(
    station_id VARCHAR(3) NOT NUll,
    facility_id INT NOT NUll,
    FOREIGN KEY (station_id) REFERENCES stations(station_id),
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id)
);


/*-----------------------------------------------------------------INSERTIONS-------------------------------------------------------------------------------------*/


/*INSERTS data INTO the zones table*/
INSERT INTO zones (zone_name) VALUES
("PV_VC"),
("VCD3"),
("VCD8"),
("MTS1"),
("MAI1"),
("MAI2"),
("MAI4"),
("PRT1"),
("PRT2"),
("PRT3"),
("GDM1"),
("VNG1");


/*INSERTS data INTO the universities table*/
INSERT INTO universities (university_id, university_name) VALUES
("UOP", "University of Porto"),
("PIP", "Polytechnic Institute of Porto"),
("FPU", "Fernando Pessoa University"),
("PUI", "Portucalense University Infante D. Henrique"),
("LUP", "Lusíada University of Porto"),
("CUP", "Catholic University of Portugal - Porto"),
("PIL", "Portucalense Institute for Legal Research"),
("HEH", "Higher Education School of Health Sciences of Porto"),
("HEM", "Higher Education School of Music and Performing Arts of Porto"),
("HEE", "Higher Education School of Education of Porto"),
("PIM", "Polytechnic Institute of Maia"),
("PICA", "Polytechnic Institute of Cávado and Ave"),
("HIM", "Higher Institute of Maia"),
("HIA", "Higher Institute of Accounting and Administration of Porto"),
("ISVOUGA", "Instituto Superior de Entre Douro e Vouga"),
("ISAG", "Instituto Superior de Administração e Gestão"),
("ESEP", "Escola Superior de Enfermagem do Porto"),
("ESHT", "Escola Superior de Hotelaria e Turismo do Porto"),
("ESB-UCP", "Escola Superior de Biotecnologia da Universidade Católica Portuguesa"),
("ESAP", "Escola Superior Artística do Porto"),
("ESPQ", "Escola Superior de Educação Jean Piaget (ESE Jean Piaget)"),
("ES-ESVA", "Escola Superior de Saúde do Vale do Ave"),
("ESTSP", "Escola Superior de Tecnologia da Saúde do Porto"),
("ISAVE", "Instituto Superior de Saúde do Alto Ave"),
("ISPGAYA", "Instituto Superior Politécnico Gaya"),
("ISTA", "Instituto Superior de Tecnologias Avançadas"),
("IPAM", "Instituto Português de Administração de Marketing"),
("ULP", "Universidade Lusófona do Porto"),
("IPP", "Instituto Português de Psicologia e Outras Ciências"),
("ESTGF", "Escola Superior de Tecnologia e Gestão de Felgueiras (ESTGF)");


/*INSERTS data INTO the stations table*/    
INSERT INTO stations (station_id, zone_id, station_name) VALUES
("SEM", 4, "Senhor De Matosinhos"),
("MER", 4, "Mercado"),
("BRI", 4, "Brito Capelo"),
("MAT", 4, "Matosinhos Sul"),
("CAM", 4, "Câmara Matosinhos"),
("PAR", 4, "Parque de Real"),
("PED", 4, "Pedro Hispano"),
("EST", 4, "Estádio Do Mar"),
("VAS", 4, "Vasco Da Gama"),
("VIS", 9, "Viso"),
("RAM", 9, "Ramalde"),
("CMS", 8, "Carolina Michaelis"),
("LAP", 8, "Lapa"),
("TRI", 8, "Trindade"),
("BOL", 8, "Bolhão"),
("HER", 8, "Heroísmo"),
("CPH", 8, "Campanhã"),
("PDV", 1, "Póvoa De Varzim"),
("SBR", 1, "São Brás"),
("PFR", 1, "Portas Fronhas"),
("ADP", 1, "Alto De Pega"),
("VDC", 1, "Vila Do Conde"),
("STC", 1, "Santa Clara"),
("AZR", 2, "Azurara"),
("ARV", 2, "Árvore"),
("VRZ", 2, "Varziela"),
("ESN", 2, "Espaço Natureza"),
("MDL", 2, "Mindelo"),
("VCO", 3, "VC Fashion Outlet I Modivas"),
("MDC", 3, "Modivas Centro"),
("MDS", 3, "Modivas Sul"),
("VLP", 3, "Vilar do Pinheiro"),
("LDR", 3, "Lidador"),
("PDR", 3, "Pedras Rubras"),
("VDS", 3, "Verdes"),
("CRT", 3, "Crestins"),
("CTS", 5, "Custóias"),
("FDC", 6, "Fonte Do Cuco"),
("CDM", 8, "Casa Da Música"),
("ISM", 5, "ISMAI"),
("CAS", 5, "Castêlo Da Maia"),
("MAN", 5, "Mandim"),
("ZIN", 5, "Zona Indústrial"),
("FMA", 6, "Fórum Maia"),
("PMA", 6, "Parque Maia"),
("CUS", 6, "Custió"),
("ARA", 6, "Araújo"),
("PIA", 6, "Pias"),
("CDR", 6, "Cândido Dos Reis"),
("FRA", 8, "Francos"),
("C24", 8, "24 De Agosto"),
("STO", 12, "Santo Ovídio"),
("DJ2", 12, "D. João II"),
("JDD", 12, "João De Deus"),
("CDG", 12, "Câmara De Gaia"),
("GTO", 12, "General Torres"),
("JDM", 12, "Jardim Do Morro"),
("SBO", 8, "São Bento"),
("ALD", 8, "Aliados"),
("FGU", 8, "Faria Guimarães"),
("MRQ", 8, "Marquês"),
("CTE", 8, "Combatentes"),
("SLG", 8, "Salgueiros"),
("PLU", 10, "Pólo Universitário"),
("IPO", 10, "IPO"),
("HSJ", 10, "Hospital De São João"),
("DRG", 8, "Estádio Do Dragão"),
("SEB", 9, "Sete Bicas"),
("ESP", 5, "Esposade"),
("BOT", 3, "Botica"),
("APO", 3, "Aeroporto"),
("SDH", 9, "Senhora Da Hora"),
("COL", 10, "Contumil"),
("NAS", 10, "Nasoni"),
("NAV", 10, "Nau Vitória"),
("LEV", 7, "Levada"),
("RTO", 7, "Rio Tinto"),
("CPA", 7, "Campainha"),
("BGM", 7, "Baguim"),
("CAR", 7, "Carreira"),
("VEN", 11, "Venda Nova"),
("FNZ", 11, "Fânzeres");


/*INSERTS data INTO the metro_lines table*/    
INSERT INTO metro_lines (line_id, line_name) VALUES
("A", "Blue"),
("B", "Red"),
("C", "Green"),
("D", "Yellow"),
("E", "Purple"),
("F", "Orange");


/*INSERTS data INTO the trains table*/ 
INSERT INTO trains (train_id, line_id, train_model, carriages, capacity) VALUES
("FB98", "A", "Eurotram", 2, 134),
("KO52", "B", "Flexity Swift LRVs", 3, 100),
("DL13", "A", "Eurotram", 2, 134),
("KA66", "B", "Flexity Swift LRVs", 3, 100),
("IK94", "C", "Eurotram", 2, 134),
("SN37", "C", "Flexity Swift LRVs", 3, 100),
("JW29", "C", "Flexity Swift LRVs", 3, 100),
("UP66", "D", "Eurotram", 2, 134),
("QR68", "C", "Flexity Swift LRVs", 3, 100),
("GG57", "E", "Eurotram", 2, 134),
("OB07", "B", "Flexity Swift LRVs", 3, 100),
("KL74", "F", "Eurotram", 2, 134),
("TP27", "D", "Eurotram", 2, 134),
("ON09", "F", "Eurotram", 2, 134);

-- /*INSERTS data INTO the routes table*/
INSERT INTO routes(line_id, end_station_id) VALUES
("A", "SEM"),
("A", "DRG"),
("B", "PDV"),
("B", "DRG"),
("C", "ISM"),
("C", "CPH"),
("D", "HSJ"),
("D", "STO"),
("E", "APO"),
("E", "DRG"),
("F", "SDH"),
("F", "FNZ");


/*INSERTS data INTO the facilities table*/
INSERT INTO facilities (facility_description) VALUES
("wheelchair accessible"),
("toilets"),
("café"),
("lockers"),
("lift"), 
("taxi rank"),
("bus stop"),
("airport"),
("parking"),
("hospital"),
("grocery shop");


/*INSERTS data INTO the station_facilities table*/
INSERT INTO station_facilities (station_id, facility_id) VALUES
("SEM", 1),
("SEM", 2),
("SEM", 3),
("SEM", 4),
("SEM", 5),
("SEM", 6),
("SEM", 7),
("SEM", 9),
("MER", 7),
("MER", 6),
("BRI", 3),
("MAT", 2),
("MAT", 7),
("CAM", 1),
("CAM", 6),
("CAM", 9),
("PAR", 2),
("PAR", 7),
("PED", 3),
("PED", 9),
("EST", 6),
("EST", 1),
("EST", 3),
("VAS", 2), 
("SDH", 1),
("SDH", 2),
("SDH", 3),
("SDH", 4),
("SDH", 5),
("SDH", 6),
("SDH", 7),
("SDH", 9),
("SEB", 3),
("SEB", 9), 
("SEB", 7),
("RAM", 1), 
("RAM", 2), 
("RAM", 4),
("RAM", 6),
("RAM", 10), 
("RAM", 11), 
("FRA", 4),
("CDM", 1),
("CDM", 2),
("CDM", 5), 
("CDM", 7),
("LAP", 1), 
("LAP", 7),
("LAP", 9), 
("LAP", 10),
("TRI", 1),
("TRI", 2),
("TRI", 3),
("TRI", 4),
("TRI", 5),
("TRI", 6),
("TRI", 7),
("TRI", 9),
("TRI", 11),
("BOL", 7),
("BOL", 9),
("C24", 1),
("C24", 2),
("C24", 7),
("C24", 10),
("HER", 6), 
("HER", 7),
("DRG", 1),
("DRG", 2),
("DRG", 3),
("DRG", 4),
("DRG", 5),
("DRG", 6),
("DRG", 7),
("DRG", 9),
("DRG", 11),
("COL", 7),
("NAV", 5), 
("NAV", 9), 
("LEV", 3), 
("CPA", 7),
("CPA", 9),
("BGM", 2), 
("BGM", 11), 
("FNZ", 2),
("FNZ", 7),
("FNZ", 11),
("STO", 1),
("STO", 2),
("STO", 6),
("STO", 7),
("STO", 9),
("STO", 11),
("DJ2", 3),
("DJ2", 7),
("JDD", 7), 
("JDD", 9),
("CDG", 7),
("CDG", 9),
("GTO", 1),
("GTO", 6), 
("GTO", 7),
("GTO", 10), 
("SBO", 1),
("SBO", 2), 
("SBO", 3), 
("SBO", 4), 
("SBO", 5),  
("SBO", 6),
("SBO", 7),
("ALD", 1),    
("ALD", 2),
("ALD", 5),
("ALD", 7),
("ALD", 10),
("FGU", 7),
("MRQ", 1),
("MRQ", 2),
("MRQ", 6),
("MRQ", 7),
("MRQ", 10),
("MRQ", 11),
("CTE", 9), 
("SLG", 1),
("SLG", 5),
("PLU", 1), 
("PLU", 3),
("PLU", 10),  
("IPO", 2), 
("IPO", 4), 
("IPO", 11),
("HSJ", 1),
("HSJ", 2),
("HSJ", 6),  
("HSJ", 7), 
("HSJ", 10), 
("FDC", 6),
("FDC", 7),
("FDC", 11),
("CMS", 1),
("CMS", 10),
("CTS", 2),
("CTS", 7),
("ESP", 5),
("ESP", 7),
("CRT", 3),
("CRT", 11),
("VDS", 2),
("VDS", 6), 
("VDS", 7), 
("BOT", 6), 
("BOT", 9),
("APO", 1),
("APO", 2),
("APO", 3),
("APO", 4),
("APO", 5),
("APO", 6),
("APO", 7),
("APO", 8),
("APO", 9),
("APO", 11),
("VIS", 5), 
("VIS", 7), 
("VIS", 9), 
("ISM", 1), 
("ISM", 2),
("ISM", 3),
("ISM", 5),
("ISM", 6),
("ISM", 7),
("ISM", 9),
("CAS", 2), 
("CAS", 5), 
("CAS", 7), 
("MAN", 2),
("MAN", 7),
("ZIN", 7), 
("ZIN", 11), 
("FMA", 9),
("PMA", 1),
("PMA", 6), 
("CUS", 2), 
("CUS", 6),
("CUS", 9),
("ARA", 1),
("ARA", 11),
("PIA", 2),
("PIA", 3),
("PIA", 6),
("CPH", 2),
("CPH", 3), 
("CPH", 6),
("CPH", 7),
("PDV", 1), 
("PDV", 2),
("PDV", 4),
("PDV", 5),
("PDV", 7),
("PDV", 9),
("PDV", 11),
("SBR", 1), 
("SBR", 7), 
("PFR", 5),
("PFR", 9), 
("AZR", 7),
("AZR", 11), 
("ARV", 2),
("ARV", 6),
("VRZ", 2), 
("VRZ", 6), 
("VRZ", 7), 
("ESN", 5),
("ESN", 9), 
("VCO", 3), 
("VCO", 9), 
("VCO", 11), 
("MDC", 3), 
("MDC", 4), 
("MDS", 5), 
("MDS", 9),
("VLP", 2), 
("VLP", 4),
("VLP", 7),
("LDR", 3), 
("LDR", 7), 
("PDR", 7), 
("PDR", 9), 
("PDR", 11); 


INSERT INTO users (user_id, email, user_password, user_type, first_name, last_name) VALUES
(1,"niall.blackrock@gmail.com", "12345", "student", "Niall", "O' Reilly"),
(2, "luana.blackrock@gmail.com", "123454", "administrator", "Luana", "Kimley"),
(3, "johndoe@gmail.com", "abcdef", "passenger", "John", "Doe"),
(4, "janedoe@gmail.com", "ghijkl", "passenger", "Jane", "Doe");

INSERT INTO students_universities (user_id, university_id) VALUES
(1,"UOP");


/* Insert sample data into cards table */
INSERT INTO cards (card_id, user_id, card_type, access_type, card_price) VALUES
(1, 1, 'blue card', 'All zones', 10.00),
(2, 2, 'grey card', '3 zones', 15.00),
(3, 3, 'student card', '3 zones', 5.00),
(4, 4, 'tour card', 'All zones', 20.00);


/* Insert sample data into cards_zones table */
INSERT INTO cards_zones (card_id, zone_id) VALUES
(2, 1),
(2, 2),
(2, 3);

/* Insert sample data into timer_cards table */
INSERT INTO timer_cards (card_id, end_date) VALUES
(1, '2023-04-30'),
(2, '2023-05-31'),
(3, '2023-06-30'),
(4, '2023-07-31');

/* Insert sample data into blue_cards table */
INSERT INTO blue_cards (card_id, total_trips_allowed) VALUES
(1, 10),
(2, 5);



/*-----------------------------------------------------------------INDEXES-------------------------------------------------------------------------------------*/

ALTER TABLE users
DROP INDEX IF EXISTS users_index;

/*CREATES an index called users_index on user_id in users*/
CREATE INDEX users_index
ON users(user_id);


ALTER TABLE students_universities 
DROP INDEX IF EXISTS student_universities_index;

/*CREATES an index called student_universities_index on user_id and university_id in students_universities*/
CREATE INDEX student_universities_index
ON students_universities(user_id, university_id);


ALTER TABLE cards 
DROP INDEX IF EXISTS cards_index;

/*CREATES an index called cards_index on card_id and user_id in cards*/
CREATE INDEX cards_index
ON cards(card_id, user_id);


ALTER TABLE cards_zones
DROP INDEX IF EXISTS cards_zones_index;

/*CREATES an index called cards_prices_index on card_id and zone_id in cards_zones*/
CREATE INDEX cards_zones_index
ON cards_zones(card_id, zone_id);


ALTER TABLE timer_cards
DROP INDEX IF EXISTS timer_cards_index;

/*CREATES an index called timer_cards_index on card_id in timer_cards*/
CREATE INDEX timer_cards_index
ON timer_cards(card_id);


ALTER TABLE blue_cards
DROP INDEX IF EXISTS blue_cards_index;

/*CREATES an index called blue_cards_index on card_id  in blue_cards*/
CREATE INDEX blue_cards_index
ON blue_cards(card_id);


ALTER TABLE facilities
DROP INDEX IF EXISTS facilities_index;

/*CREATES an index called facilities_index on facility_id in facilities*/
CREATE INDEX facilities_index
ON facilities(facility_id);


ALTER TABLE station_facilities 
DROP INDEX IF EXISTS station_facilities_index;

/*CREATES an index called station_facilities_index on station_id and facility_id in station_facilities*/
CREATE INDEX station_facilities_index
ON station_facilities(station_id, facility_id);


ALTER TABLE journey_planners  
DROP INDEX IF EXISTS journey_planners_index;

-- /*CREATES an index called journey_planners_index on user_id and journey_planners*/
CREATE INDEX journey_planners_index
ON journey_planners(user_id);


ALTER TABLE schedules
DROP INDEX IF EXISTS schedules_index;

/*CREATES an index called facilities_index on timetable_id and row_number in schedules*/
CREATE INDEX schedules_index
ON schedules(timetable_id, row_number);

/*-----------------------------------------------------------------VIEWS-------------------------------------------------------------------------------------*/


DROP VIEW IF EXISTS all_users, all_cards;

/*CREATE A VIEW which shows all users*/
CREATE VIEW all_users AS
SELECT users.*, cards.card_id, students_universities.university_id FROM users
LEFT JOIN cards ON users.user_id = cards.user_id
LEFT JOIN students_universities ON users.user_id = students_universities.user_id;

/*CREATE A VIEW which shows all cards*/
CREATE VIEW all_cards AS
SELECT cards.*, timer_cards.end_date, blue_cards.total_trips_allowed FROM cards
LEFT JOIN timer_cards ON cards.card_id = timer_cards.card_id
LEFT JOIN blue_cards ON cards.card_id = blue_cards.card_id
LEFT JOIN cards_zones ON cards.card_id = cards_zones.card_id;
     

/*-----------------------------------------------------------------TRIGGERS-------------------------------------------------------------------------------------*/


/*DELETE TRIGGERS to remove card details*/
DROP TRIGGER IF EXISTS delete_cards_foreign_key_timer_cards;

CREATE TRIGGER delete_cards_foreign_key_timer_cards

BEFORE DELETE ON cards
FOR EACH ROW

DELETE FROM timer_cards WHERE timer_cards.card_id = OLD.card_id;


DROP TRIGGER IF EXISTS delete_cards_foreign_key_blue_cards;

CREATE TRIGGER delete_cards_foreign_key_blue_cards

BEFORE DELETE ON cards
FOR EACH ROW

DELETE FROM blue_cards WHERE blue_cards.card_id = OLD.card_id;


DROP TRIGGER IF EXISTS delete_cards_foreign_key_cards_zones;

CREATE TRIGGER delete_cards_foreign_key_cards_zones

BEFORE DELETE ON cards
FOR EACH ROW

DELETE FROM cards_zones WHERE cards_zones.card_id = OLD.card_id;


/*DELETE TRIGGERS to remove user details*/
DROP TRIGGER IF EXISTS delete_users_foreign_key_students_universities;

CREATE TRIGGER delete_users_foreign_key_students_universities

BEFORE DELETE ON users
FOR EACH ROW

DELETE FROM students_universities WHERE students_universities.user_id = OLD.user_id;


DROP TRIGGER IF EXISTS delete_users_foreign_key_cards;

CREATE TRIGGER delete_users_foreign_key_cards

BEFORE DELETE ON users
FOR EACH ROW

DELETE FROM cards WHERE cards.user_id = OLD.user_id;


-- @DELIMITER //
CREATE TRIGGER limit_card_id_rows
BEFORE INSERT ON cards_zones FOR EACH ROW
BEGIN
  DECLARE card_count INT;
  SELECT COUNT(*) INTO card_count FROM cards_zones WHERE card_id = NEW.card_id;
  IF card_count >= 3 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Exceeded limit for card_id in cards_zones table';
  END IF;
END// 
-- @DELIMITER ;





