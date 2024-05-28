USE AstronomicalEvents2
GO
-- SET DEADLOCK_PRIORITY LOW
-- SET DEADLOCK_PRIORITY HIGH

-- SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;

BEGIN TRANSACTION
	UPDATE observatory SET manager = 'Evelyne Musso'
	WHERE NAME LIKE 'Paris Observatory';

	WAITFOR DELAY '00:00:10'

	UPDATE person SET occupation = 'painter'
	WHERE name LIKE 'Maria Metes';
COMMIT TRAN



select * from observatory;
select * from person;



/*
update observatory set manager = 'Guilleume Pirotte'
	where name LIKE 'Paris Observatory';
update person set occupation = 'teacher'
	where name LIKE 'Maria Metes';*/