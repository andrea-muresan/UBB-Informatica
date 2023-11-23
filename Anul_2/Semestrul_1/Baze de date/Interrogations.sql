USE AstronomicalEvents
GO

/*1. Evenimentele fotografiate care au continut stele*/
SELECT DISTINCT event.name, object.name as star FROM event
JOIN image ON image.event_id = event.id
JOIN object_event ON object_event.event_id = event.id
JOIN object ON object_event.object_id = object.id
WHERE object.type = 'star'

/* 2. Muzeele pentru care media varstei vizitatorilor este <= 25*/
SELECT observatory.name, AVG(person.age) as medie FROM person
JOIN visitor_check_in ON person.id = visitor_check_in.person_id
JOIN observatory ON observatory.id = visitor_check_in.observatory_id
GROUP BY observatory.name
HAVING AVG(person.age) <= 25

/*3. Media varstelor persoanelor care au vizitat un muzeu din New York*/
SELECT AVG(person.age) as medie FROM person
JOIN visitor_check_in ON visitor_check_in.person_id = person.id
JOIN observatory ON visitor_check_in.observatory_id = observatory.id
JOIN address ON address.id = observatory.id
WHERE address.city = 'New York'

/*4. Planetarii + adrese care au participat la evenimente inainte de 2015*/
SELECT observatory.name, address.country, address.city FROM observatory
JOIN observatory_event ON observatory.id = observatory_event.observatory_id
JOIN address ON address.id = observatory.id
WHERE observatory_event.date < '2015-01-01'

/*5. Ocupatiile persoanelor care au vizitat cel putin un planetariu din State*/
SELECT DISTINCT person.occupation FROM person
JOIN visitor_check_in ON person.id = visitor_check_in.person_id
JOIN observatory ON observatory.id = visitor_check_in.observatory_id
JOIN address ON address.id = observatory_id
WHERE address.country = 'USA'

/*6. Pentru fiecare muzeu - varsta minima si varsta maxima a participantilor*/
SELECT observatory.name,  MIN(person.age) as min_age, MAX(person.age) as max_age FROM person
JOIN visitor_check_in ON person.id = visitor_check_in.person_id
JOIN observatory ON observatory.id = visitor_check_in.observatory_id
GROUP BY observatory.name

/*7. Tarile care au planetarii care au participat la 2 sau mai multe evenimente*/
SELECT DISTINCT address.country, COUNT(observatory_event.observatory_id) as events FROM address
JOIN observatory ON observatory.id = address.id
JOIN observatory_event ON observatory_event.observatory_id = observatory.id 
GROUP BY address.country
HAVING COUNT(observatory_event.observatory_id) >= 2

/*8. Numele managerilor planetariilor care au avut mai mult de 2 vizitatori*/
SELECT observatory.manager FROM observatory
JOIN visitor_check_in ON visitor_check_in.observatory_id = observatory.id
JOIN person ON person_id = visitor_check_in.person_id
GROUP BY observatory.manager
HAVING COUNT(visitor_check_in.check_in_date) > 2;

/*9. Toti fotografii care au fotografiat un eveniment despre soare*/
SELECT photographer FROM image
JOIN event ON event.id = image.event_id
JOIN object_event ON object_event.event_id = event.id
JOIN object ON object_event.object_id = object.id
WHERE object.name = 'Sun';

/*10. Numele si adresa planetariilor deschise martea, dupa ora 17:00*/
SELECT observatory.name, opening_hours.closing_time, address.country, address.city FROM observatory
JOIN address ON address.id = observatory.id
JOIN opening_hours ON opening_hours.observatory_id = observatory.id
WHERE opening_hours.week_day = 2 AND opening_hours.closing_time >= '17:00';




