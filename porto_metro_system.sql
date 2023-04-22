/*
	****************** PORTO METRO SYSTEM *******************
*/


/*-----------------------------------------------------------------CREATES-------------------------------------------------------------------------------------*/

/*CREATES the porto metro system database*/
CREATE DATABASE IF NOT EXISTS porto_metro_system DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE porto_metro_system;

/*DROPS any of the tables that may already exists*/
DROP TABLE IF EXISTS station_facilities, facilities, schedules, timetables, routes, trains,  metro_lines, stations_in_journey_routes, journey_routes, stations, blue_cards, timer_cards, cards_zones, cards_prices, passengers, cards, students_universities, universities, users, zones;


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
    user_type ENUM("passenger", "administrator", "student") NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (user_id, email, user_password)
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
    card_id INT NOT NULL,
    card_type ENUM("blue card", "grey card", "student card", "tour card") NOT NULL,
    access_type ENUM("All zones", "3 zones") NOT NULL,
    card_price DECIMAL(5, 2),
    PRIMARY KEY(card_id)
);

/*CREATE users table*/
CREATE TABLE passengers
(
    user_id INT NOT NULL,
    card_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (card_id) REFERENCES cards(card_id),
    UNIQUE (user_id, card_id)
);

-- /*CREATE table cards_prices*/
-- CREATE TABLE cards_prices
-- (
--     card_price_id INT NOT NULL AUTO_INCREMENT,
--     card_id INT NOT NULL,
--     access_type ENUM("All zones", "3 zones") NOT NULL,
--     card_price DECIMAL(5, 2),
--     FOREIGN KEY (card_id) REFERENCES cards(card_id),
--     PRIMARY KEY(card_price_id)
-- );

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
    end_datetime DATETIME NOT NULL,
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


/*CREATE journey_routes table*/
CREATE TABLE journey_routes 
(
    journey_route_id INT(3) NOT NULL,
    user_id INT NOT NULL,
    start_station_id VARCHAR(3) DEFAULT NULL,
    end_station_id VARCHAR(3) DEFAULT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    fare DECIMAL(5,2) NOT NULL,
    journey_route_description VARCHAR(255),
    PRIMARY KEY (journey_route_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (start_station_id) REFERENCES stations(station_id),
    FOREIGN KEY (end_station_id) REFERENCES stations(station_id)
);


/*CREATE stations_in_journey_routes table*/
CREATE TABLE stations_in_journey_routes 
(
    journey_route_id INT(3) NOT NULL,
    station_id VARCHAR(5) NOT NULL,
    PRIMARY KEY (journey_route_id, station_id),
    FOREIGN KEY (journey_route_id) REFERENCES journey_routes(journey_route_id),
    FOREIGN KEY (station_id) REFERENCES stations(station_id)
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
    schedule_id INT NOT NULL AUTO_INCREMENT,
    route_id INT NOT NULL,
    scheduled_day_type ENUM("Monday-Friday", "Saturday","Sunday") NOT NULL,
    PRIMARY KEY (schedule_id),
    FOREIGN KEY (route_id) REFERENCES routes(route_id)
);

/*CREATE schedules table*/
CREATE TABLE schedules
(
    schedule_id INT NOT NULL,
    station_id VARCHAR(3),
    departure_time TIME NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES timetables(schedule_id),
    FOREIGN KEY (station_id) REFERENCES stations(station_id)
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
("ADP", 1, "Alto Da Pega"),
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
("VLP", 3, "Vilar de Pinheiro"),
("LDR", 3, "Lidador"),
("PDR", 3, "Pedras Rubras"),
("VDS", 3, "Verdes"),
("CRT", 3, "Crestins"),
("CDC", 5, "Custóias"),
("FDC", 6, "Fonte Do Cuco"),
("CDM", 8, "Casa Da Música"),
("ISM", 5, "ISMAI"),
("CAS", 5, "Castêlo Da Maia"),
("MAN", 5, "Mandim"),
("ZIN", 5, "Zona Industrial"),
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
("CGV", 12, "Câmara De Gaia"),
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
("CON", 10, "Contumil"),
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
("Grocery shop");


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
("CON", 7),
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
("CGV", 7),
("CGV", 9),
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
("CDC", 2),
("CDC", 7),
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


-- ALTER TABLE cards 
-- DROP INDEX IF EXISTS cards_index;

/*CREATES an index called cards_index on user_id in cards*/
-- CREATE INDEX cards_index
-- ON cards(user_id);


-- ALTER TABLE cards_prices
-- DROP INDEX IF EXISTS cards_prices_index;

-- /*CREATES an index called cards_prices_index on card_id in cards_prices*/
-- CREATE INDEX cards_prices_index
-- ON cards_prices(card_id);


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


-- ALTER TABLE blue_cards
-- DROP INDEX IF EXISTS blue_cards_index;

-- /*CREATES an index called blue_cards_index on card_id and zone_id in timer_cards*/
-- CREATE INDEX blue_cards_index
-- ON blue_cards(card_id, zone_id);


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


-- ALTER TABLE stations_in_metro_lines  
-- DROP INDEX IF EXISTS stations_in_metro_lines_index;

-- /*CREATES an index called stations_in_metro_lines_index on station_id and line_id in stations_in_metro_lines*/
-- CREATE INDEX stations_in_metro_lines_index
-- ON stations_in_metro_lines(station_id, line_id);


/*-----------------------------------------------------------------VIEWS-------------------------------------------------------------------------------------*/


DROP VIEW IF EXISTS all_users, all_cards;

-- /*CREATE A VIEW which shows passengers*/
-- CREATE VIEW passenger_details AS
-- SELECT users.user_id, users.email, users.password, passengers. universities.* FROM users
-- JOIN students_universities ON users.user_id = students_universities.user_id
-- JOIN universities ON students_universities.university_id = universities.university_id
-- WHERE users.user_type = "student"; 

/*CREATE A VIEW which shows students*/
CREATE VIEW all_users AS
SELECT users.*, passengers.card_id, students_universities.university_id FROM users
LEFT JOIN students_universities ON users.user_id = students_universities.user_id
LEFT JOIN passengers ON users.user_id = passengers.user_id;

CREATE VIEW all_cards AS
SELECT cards.*, timer_cards.end_datetime, blue_cards.total_trips_allowed,cards_zones.zone_id FROM cards
LEFT JOIN timer_cards ON cards.card_id = timer_cards.card_id
LEFT JOIN blue_cards ON cards.card_id = blue_cards.card_id
LEFT JOIN cards_zones ON cards.card_id = cards_zones.card_id;
                    

/*CREATE A VIEW which shows blue_cards*/
-- CREATE VIEW blue_cards_details AS
-- SELECT cards.*, cards_prices.card_price_id, cards_prices.access_type, cards_prices.card_price, blue_cards.zone_id, blue_cards.total_trips_allowed  FROM cards
-- JOIN blue_cards ON cards.card_id = blue_cards.card_id
-- JOIN cards_prices ON cards.card_id = cards_prices.card_id
-- WHERE cards.card_type = "blue card";


/*-----------------------------------------------------------------PROCEDURES-------------------------------------------------------------------------------------*/


/*Create a procedure that gets the grey card details*/
DROP PROCEDURE IF EXISTS get_timer_cards_details;

-- -- @DELIMITER //
-- CREATE PROCEDURE get_timer_cards_details(IN card_type ENUM("grey card", "student card", "tour card"))
-- BEGIN 
-- SELECT cards.*, cards_price_id, cards_prices.access_type, cards_prices.card_price, timer_cards.start_datetime, timer_cards.end_datetime, timer_cards.timer  FROM cards
-- JOIN timer_cards ON cards.card_id = timer_cards.card_id
-- JOIN cards_prices ON cards.card_id = cards_prices.card_id
-- WHERE cards.card_type = card_type;
-- END //
-- -- @DELIMITER ;

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

INSERT INTO users (user_id, email, user_password, user_type) VALUES
(1,"niall.blackrock@gmail.com", "12345", "student"),
(2, "luana.blackrock@gmail.com", "123454", "administrator"),
(3, "johndoe@gmail.com", "abcdef", "passenger"),
(4, "janedoe@gmail.com", "ghijkl", "passenger");

INSERT INTO students_universities (user_id, university_id) VALUES
(1,"UOP");


/* Insert sample data into cards table */
INSERT INTO cards (card_id, card_type, access_type, card_price) VALUES
(1, 'blue card', 'All zones', 10.00),
(2, 'grey card', '3 zones', 15.00),
(3, 'student card', '3 zones', 5.00),
(4, 'tour card', 'All zones', 20.00);

/* Insert sample data into passengers table */
INSERT INTO passengers (user_id, card_id) VALUES
(1, 1),
(3, 2),
(4, 4);

/* Insert sample data into cards_zones table */
INSERT INTO cards_zones (card_id, zone_id) VALUES
(2, 1),
(2, 2),
(2, 3);

/* Insert sample data into timer_cards table */
INSERT INTO timer_cards (card_id, end_datetime) VALUES
(1, '2023-04-30 23:59:59'),
(2, '2023-05-31 23:59:59'),
(3, '2023-06-30 23:59:59'),
(4, '2023-07-31 23:59:59');

/* Insert sample data into blue_cards table */
INSERT INTO blue_cards (card_id, total_trips_allowed) VALUES
(1, 10),
(2, 5);
