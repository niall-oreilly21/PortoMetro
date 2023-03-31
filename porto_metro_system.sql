/*
	****************** PORTO METRO SYSTEM *******************
*/


/*-----------------------------------------------------------------CREATES-------------------------------------------------------------------------------------*/

/*CREATES the porto metro system database*/
CREATE DATABASE IF NOT EXISTS porto_metro_system DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE porto_metro_system;

/*DROPS any of the tables that may already exists*/
DROP TABLE IF EXISTS station_facilities, facilities, stations_in_schedules, schedules, trains, station_lines, metro_lines, stations_in_journey_routes, journey_routes, stations, blue_cards, timer_cards, cards_zones, cards_prices, cards, students_universities, universities, users, zones;


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
    user_id INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_type ENUM('passenger', 'administrator', 'student') NOT NULL,
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
    PRIMARY KEY(user_id, university_id)
);

/*CREATE cards table*/
CREATE TABLE cards
(
    card_id INT NOT NULL,
    user_id INT NOT NULL,
    card_type ENUM('blue card', 'grey card', 'student card', 'tour card') NOT NULL,
    card_is_active BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    PRIMARY KEY(card_id)
);


/*CREATE table cards_prices*/
CREATE TABLE cards_prices
(
    card_price_id INT NOT NULL AUTO_INCREMENT,
    card_id INT NOT NULL,
    access_type ENUM('All zones', '3 zones') NOT NULL,
    card_price DECIMAL(5, 2),
    FOREIGN KEY (card_id) REFERENCES cards(card_id),
    PRIMARY KEY(card_price_id)
);

/*CREATE table card_zones*/
CREATE TABLE cards_zones
(
    card_id INT NOT NULL,
    zone_id INT NOT NULL,
    FOREIGN KEY (card_id) REFERENCES cards(card_id),
    FOREIGN KEY (zone_id) REFERENCES zones(zone_id),
    PRIMARY KEY(card_id, zone_id)
);

/*CREATE blue card table*/
CREATE TABLE timer_cards 
(
    card_id INT NOT NULL,
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME NOT NULL,
    timer DATETIME,
    PRIMARY KEY(card_id),
    FOREIGN KEY (card_id) REFERENCES cards(card_id)
);


/*CREATE card types table*/
CREATE TABLE blue_cards
(
    card_id INT NOT NULL,
    zone_id INT NOT NULL,
    total_trips_allowed INT,
    PRIMARY KEY(card_id),
    FOREIGN KEY (card_id) REFERENCES cards(card_id),
    FOREIGN KEY (zone_id) REFERENCES zones(zone_id)
);


/*CREATE stations table*/
CREATE TABLE stations
(
    station_id VARCHAR(5) NOT NUll,
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
    start_station_id VARCHAR(5) DEFAULT NULL,
    end_station_id VARCHAR(5) DEFAULT NULL,
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


CREATE TABLE station_lines 
(
  station_id VARCHAR(5) NOT NULL,
  line_id VARCHAR(1) NOT NULL,
  PRIMARY KEY (station_id, line_id),
  FOREIGN KEY (station_id) REFERENCES stations(station_id),
  FOREIGN KEY (line_id) REFERENCES metro_lines(line_id)
);


/*CREATE trains table*/
CREATE TABLE trains
(
    train_id VARCHAR(4) NOT NULL,
    line_id VARCHAR(1),
    train_model VARCHAR(255),
    carriages INT(2),
    capacity INT(3),
    PRIMARY KEY (train_id),
    FOREIGN KEY (line_id) REFERENCES metro_lines(line_id)
);


/*CREATE schedules table*/
CREATE TABLE schedules
(
    schedule_id VARCHAR(7) NOT NULL,
    train_id VARCHAR(4),
    schedule_description VARCHAR(255),
    PRIMARY KEY (schedule_id),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);


/*CREATE stations_in_schedules table*/
CREATE TABLE stations_in_schedules
(
    station_id VARCHAR(5) NOT NUll,
    schedule_id VARCHAR(7) NOT NULL,
    arrival_time DATETIME,
    PRIMARY KEY (station_id, schedule_id),
    FOREIGN KEY (station_id) REFERENCES stations(station_id),
    FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id)
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
    station_id VARCHAR(5) NOT NUll,
    facility_id INT NOT NUll,
    PRIMARY KEY (station_id, facility_id),
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
("ESP", "Escola Superior de Educação Jean Piaget (ESE Jean Piaget)"),
("ES-ESVA", "Escola Superior de Saúde do Vale do Ave"),
("ESTSP", "Escola Superior de Tecnologia da Saúde do Porto"),
("ISAVE", "Instituto Superior de Saúde do Alto Ave"),
("ISPGAYA", "Instituto Superior Politécnico Gaya"),
("ISTA", "Instituto Superior de Tecnologias Avançadas"),
("IPAM", "Instituto Português de Administração de Marketing"),
("ULP", "Universidade Lusófona do Porto"),
("IPP", "Instituto Português de Psicologia e Outras Ciências"),
("ESTGF", "Escola Superior de Tecnologia e Gestão de Felgueiras (ESTGF)");
