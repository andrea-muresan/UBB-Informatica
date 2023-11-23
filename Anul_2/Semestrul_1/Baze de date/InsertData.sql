USE AstronomicalEvents2
GO

INSERT INTO event (name, type, description)
VALUES
('Supermoon', 'Celestial Phenomenon', 'A supermoon occurs when the full moon coincides with the moon being closest to Earth in its orbit, appearing larger and brighter than usual.'),
('Perseid Meteor Shower', 'Meteor Shower', 'The Perseid meteor shower is an annual event characterized by a high rate of meteors entering Earth''s atmosphere, associated with the comet Swift-Tuttle.'),
('Halley''s Comet', 'Comet', 'Halley''s Comet is one of the most famous comets, visible from Earth every 76 years.'),
('Comet NEOWISE', 'Comet', 'Comet NEOWISE, discovered in 2020 by the NEOWISE space telescope, became visible to the naked eye and dazzled observers around the world.'),
('Jupiter and Saturn Conjunction', 'Celestial Event', 'The conjunction of Jupiter and Saturn, also known as the "Christmas Star," was a rare alignment where the two planets appeared extremely close together in the night sky in December 2020.'),
('Annular Solar Eclipse', 'Eclipse', 'An annular solar eclipse occurs when the Moon covers the center of the Sun, leaving a ring of sunlight visible around the edges.'),
('Total Solar Eclipse', 'Eclipse', 'A total solar eclipse occurs when the Moon completely covers the Sun, casting a shadow on Earth and turning day into night for a brief period.'),
('Total Lunar Eclipse', 'Eclipse', 'A total lunar eclipse occurs when the Earth comes between the Sun and the Moon, causing the Moon to enter Earth''s shadow and take on a reddish hue.');


INSERT INTO image (event_id, photographer, date_taken, image_url, city, country)
VALUES
(1, 'Avery Grabs', '2023-01-15', 'https://example.com/supermoon_image.jpg', 'New York', 'USA'),
(2, 'Mara Mateescu', '2022-07-27', 'https://example.com/metheor_shower_image.jpg', 'Sibiu', 'Romania'),
(2, 'Tudor Hawethorne', '2021-08-28', 'https://example.com/metheorShowerFranceImage.jpg', 'Nice', 'France'),
(3, 'Mara Mateescu', '2020-05-17', 'https://example.com/HalleysCommet.jpg', 'Sibiu', 'Romania'),
(7, 'Avery Grabs', '2009-10-14', 'https://example.com/solar_eclipse.jpg', 'Lisbon', 'Portugal'),
(7, 'Ignacio Garcia', '2009-10-14', 'https://example.com/solar_eclipse_Spain.jpg', 'Madrid', 'Spain'),
(8, 'Ignacio Garcia', '2014-06-11', 'https://example.com/lunar_eclipse_Spain.jpg', 'Zaragoza', 'Spain');

INSERT INTO object (name, type)
VALUES
('Sun', 'star'),
('Moon', 'satellite'),
('Jupiter', 'planet'),
('Saturn', 'planet'),
('Halley', 'comet'),
('NEOWISE', 'comet'),
('Sirius', 'star'),
('Andromeda', 'galaxy');

INSERT INTO observatory (name, website, manager)
VALUES
('Paris Observatory', 'https://ParisObservatory.fr', 'Guilleume Pirotte'),
('Royal Observatory of Madrid', 'https://MadridObservatory.es', 'Didac Garcia'),
('One World Observatory', 'https://NewyYorkObservatory.com', 'Jaime Potter'),
('Lisbon Astronomical Observatory', 'https://LisbonObservatory.com', 'Ana Santos'),
('OMAU Malaga', 'https://MalagaObservatory.es', 'Elvira Sanchez'),
('Planetariu Baia Mare', 'https://BaiaMareObservatory.ro', 'Vlad Koblicica');

DECLARE @first AS INT = 1
DECLARE @last AS INT = 7
WHILE(@first <= @last)
BEGIN	
	INSERT INTO opening_hours (observatory_id, week_day, opening_time, closing_time)
		VALUES
		((select id from observatory where name='Paris Observatory'), @first, '07:00', '15:30'),
		((select id from observatory where name='Royal Observatory of Madrid'), @first, '08:00', '16:30'),
		((select id from observatory where name='One World Observatory'), @first, '09:00', '17:00'),
		((select id from observatory where name='Lisbon Astronomical Observatory'), @first, '12:00', '20:30'),
		((select id from observatory where name='Planetariu Baia Mare'), @first, '13:00', '22:00');
    SET @first += 1
END


INSERT INTO person (name, age, gender, occupation)
VALUES
('Raluca Pop', 19, 'female', 'student'),
('Alex Baicus', 30, 'male', 'traveller'),
('Larisa Coroama', 23, 'female', 'engineer'),
('Diana Coroama', 23, 'female', 'engineer'),
('Ion Pop', 27, 'male', 'teacher'),
('Alin Maidan', 40, 'male', 'teacher'),
('Luca Dunca', 20, 'male', 'student'),
('Maria Metes', 32, 'female', 'teacher')
;

INSERT INTO address (id, country, city)
VALUES
((select id from observatory where name='Paris Observatory'), 'France', 'Paris'),
((select id from observatory where name='Royal Observatory of Madrid'), 'Spain', 'Madrid'),
((select id from observatory where name='One World Observatory'), 'USA', 'New York'),
((select id from observatory where name='Lisbon Astronomical Observatory'), 'Portugal', 'Lisbon'),
((select id from observatory where name='Planetariu Baia Mare'), 'Romania', 'Baia Mare');
;

INSERT INTO observatory_event (observatory_id, event_id, date)
VALUES
((select id from observatory where name='Paris Observatory'),
(select id from event where name='Total Solar Eclipse'),
'2012-10-14'),
((select id from observatory where name='Planetariu Baia Mare'),
(select id from event where name='Total Lunar Eclipse'),
'2022-02-24'),
((select id from observatory where name='Planetariu Baia Mare'),
(select id from event where name='Total Solar Eclipse'),
'2017-11-04'),
((select id from observatory where name='One World Observatory'),
(select id from event where name='Supermoon'),
'2020-12-14'),
((select id from observatory where name='One World Observatory'),
(select id from event where name='Jupiter and Saturn Conjunction'),
'2007-02-24'),
((select id from observatory where name='One World Observatory'),
(select id from event where name='Perseid Meteor Shower'),
'2020-12-14'),
((select id from observatory where name='Lisbon Astronomical Observatory'),
(select id from event where name='Perseid Meteor Shower'),
'2016-11-04'),
((select id from observatory where name='Royal Observatory of Madrid'),
(select id from event where name='Perseid Meteor Shower'),
'2016-11-04')
;

INSERT INTO object_event (object_id, event_id)
VALUES
((select id from object where name='Moon'), (select id from event where name='Supermoon')),
((select id from object where name='Jupiter'), (select id from event where name='Jupiter and Saturn Conjunction')),
((select id from object where name='Saturn'), (select id from event where name='Jupiter and Saturn Conjunction')),
((select id from object where name='Moon'), (select id from event where name='Total Solar Eclipse')),
((select id from object where name='Sun'), (select id from event where name='Total Solar Eclipse')),
((select id from object where name='Moon'), (select id from event where name='Total Lunar Eclipse')),
((select id from object where name='Halley'), (select id from event where name='Halley''s Comet')),
((select id from object where name='NEOWISE'), (select id from event where name='Comet NEOWISE'))
;

INSERT INTO visitor_check_in (person_id, observatory_id, check_in_date)
VALUES
((select id from person where name='Alex Baicus'), (select id from observatory where name='Royal Observatory of Madrid'), '2016-11-04'),
((select id from person where name='Raluca Pop'), (select id from observatory where name='Planetariu Baia Mare'), '2022-02-24'),
((select id from person where name='Larisa Coroama'), (select id from observatory where name='One World Observatory'), '2007-02-24'),
((select id from person where name='Diana Coroama'), (select id from observatory where name='One World Observatory'), '2007-02-24'),
((select id from person where name='Luca Dunca'), (select id from observatory where name='Planetariu Baia Mare'), '2022-02-24'),
((select id from person where name='Luca Dunca'), (select id from observatory where name='Paris Observatory'), '2016-11-15'),
((select id from person where name='Alex Baicus'), (select id from observatory where name='Paris Observatory'), '2021-01-15'),
((select id from person where name='Alex Baicus'), (select id from observatory where name='One World Observatory'), '2007-02-24')
;