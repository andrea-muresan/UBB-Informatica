/*CREATE DATABASE AstronomicalEvents
GO*/
USE AstronomicalEvents2
GO

CREATE TABLE event (
id INT PRIMARY KEY IDENTITY,
name VARCHAR(50),
type varchar(30),
description VARCHAR(1000))

CREATE TABLE image (
id INT PRIMARY KEY IDENTITY,
event_id INT FOREIGN KEY REFERENCES event(id) ON DELETE CASCADE,
photographer VARCHAR(50),
date_taken DATE,
image_url VARCHAR(255),
city VARCHAR(30),
country VARCHAR(30)
)

CREATE TABLE object (
id INT PRIMARY KEY IDENTITY,
name VARCHAR(30),
type VARCHAR(30))

CREATE TABLE observatory (
id INT PRIMARY KEY IDENTITY,
name VARCHAR(50),
website VARCHAR(255),
manager varchar(50))

CREATE TABLE opening_hours (
id INT PRIMARY KEY IDENTITY,
observatory_id INT FOREIGN KEY REFERENCES observatory(id) ON DELETE CASCADE,
week_day VARCHAR(10),
opening_time TIME,
closing_time TIME,)

CREATE TABLE person (
id INT PRIMARY KEY IDENTITY,
name varchar(50),
age int,
gender varchar(30),
occupation varchar(50))

CREATE TABLE address (
id INT FOREIGN KEY REFERENCES observatory(id) ON DELETE CASCADE,
country VARCHAR(30),
city VARCHAR(30),
street VARCHAR(30),
number VARCHAR(30),
CONSTRAINT pk_observatory_address PRIMARY KEY(id))


CREATE TABLE observatory_event (
observatory_id INT FOREIGN KEY REFERENCES observatory(id) ON DELETE CASCADE,
event_id INT FOREIGN KEY REFERENCES event(id) ON DELETE CASCADE,
date DATE,
CONSTRAINT pk_observatory_event PRIMARY KEY(observatory_id, event_id))

CREATE TABLE object_event (
id  INT PRIMARY KEY IDENTITY,
object_id INT,
event_id INT,
FOREIGN KEY (object_id) REFERENCES object(id) ON DELETE CASCADE,
FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
)

CREATE TABLE visitor_check_in (
id  INT PRIMARY KEY IDENTITY,
person_id INT,
observatory_id INT,
check_in_date DATE,
FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
FOREIGN KEY (observatory_id) REFERENCES observatory(id) ON DELETE CASCADE,
)