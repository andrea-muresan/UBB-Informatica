USE AstronomicalEvents2
GO

-- SET DEADLOCK_PRIORITY HIGH
SET DEADLOCK_PRIORITY LOW

BEGIN TRANSACTION
	UPDATE person SET occupation = 'actor'
	WHERE name LIKE 'Maria Metes';

	WAITFOR DELAY '00:00:10';

	UPDATE observatory SET manager = 'Iris Munteanu'
	WHERE name LIKE 'Paris Observatory';
COMMIT TRAN


select * from observatory;
select * from person;

