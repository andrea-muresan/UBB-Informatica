INSERT INTO public.users(
	first_name, last_name, email)
	VALUES ('Ana', 'Popescu', 'ana@user.com'),
	('Tudor', 'Metes', 'tudor@user.com'),
	('Remus', 'Rus', 'remus@user.com'),
	('Mara', 'Mateescu', 'mara@user.com'),
	('Nicu', 'Ionescu', 'nicu@user.com'),
	('Marian', 'Ratiu', 'marian@user.com'),
	('Raluca', 'Pop', 'raluca@user.com');
	
SELECT * FROM users

INSERT INTO public.friendships(
	user1_id, user2_id, friends_from)
	VALUES ((select id from users where email='ana@user.com'), (select id from users where email='remus@user.com'), '2023-05-11 17:23:14'),
	((select id from users where email='ana@user.com'), (select id from users where email='marian@user.com'), '2023-05-23 23:19:03'),
	((select id from users where email='raluca@user.com'), (select id from users where email='nicu@user.com'), '2023-07-17 12:09:01'),
	((select id from users where email='nicu@user.com'), (select id from users where email='mara@user.com'), '2023-06-19 14:54:44'),
	((select id from users where email='mara@user.com'), (select id from users where email='tudor@user.com'), '2023-08-23 08:08:12')
	;
	
SELECT * FROM friendships	
	
	
