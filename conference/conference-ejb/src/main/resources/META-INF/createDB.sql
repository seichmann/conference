CREATE DATABASE `conference`;

CREATE TABLE `conference`.`conference` (
  `id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `start` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);
GRANT ALL ON TABLE `conference`.`conference` TO `conference`;

CREATE TABLE `conference`.`room` (
  `id` int NOT NULL,
  `capacity` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
); 
GRANT ALL ON TABLE `conference`.`room` TO `conference`;

CREATE TABLE `conference`.`speaker` (
  `id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);
GRANT ALL ON TABLE `conference`.`speaker` TO `conference`;

CREATE TABLE `conference`.`talk` (
  `id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `room_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `room_id` FOREIGN KEY (`room_id`) REFERENCES `conference`.`room` (`id`)
); 
GRANT ALL ON TABLE `conference`.`talk` TO `conference`;

CREATE TABLE `conference`.`conference_talk` (
  `conference_id` int NOT NULL,
  `talks_id` int NOT NULL,
  KEY `talks_id` (`talks_id`),
  KEY `conference_id` (`conference_id`),
  CONSTRAINT `conference_id` FOREIGN KEY (`conference_id`) REFERENCES `conference`.`conference` (`id`),
  CONSTRAINT `talks_id` FOREIGN KEY (`talks_id`) REFERENCES `conference`.`talk` (`id`)
);
GRANT ALL ON TABLE `conference`.`conference_talk` TO `conference`;

CREATE TABLE `conference`.`hibernate_sequence` (
  `next_val` int DEFAULT NULL
);
GRANT ALL ON TABLE `conference`.`hibernate_sequence` TO `conference`;
INSERT INTO `conference`.`hibernate_sequence`(next_val) VALUES (0);

CREATE TABLE `conference`.`talk_speaker` (
  `talk_id` int NOT NULL,
  `speakers_id` int NOT NULL,
  PRIMARY KEY (`talk_id`,`speakers_id`),
  KEY `speakers_id` (`speakers_id`),
  KEY `talk_id` (`talk_id`),
  CONSTRAINT `talk_id` FOREIGN KEY (`talk_id`) REFERENCES `conference`.`talk` (`id`),
  CONSTRAINT `speakers_id` FOREIGN KEY (`speakers_id`) REFERENCES `conference`.`speaker` (`id`)
); 
GRANT ALL ON TABLE `conference`.`talk_speaker` TO `conference`;


