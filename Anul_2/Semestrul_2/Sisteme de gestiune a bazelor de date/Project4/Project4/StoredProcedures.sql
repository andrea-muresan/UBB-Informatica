use AstronomicalEvents2
go

CREATE OR ALTER PROCEDURE startThread1
AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
	BEGIN TRAN
		UPDATE person SET occupation = 'actor' WHERE name LIKE 'Maria Metes'
		WAITFOR DELAY '00:00:05'
		UPDATE observatory SET manager = 'Andrada Malinescu' WHERE name LIKE 'Paris Observatory'
	COMMIT TRAN
END
GO

CREATE OR ALTER PROCEDURE startThread2
AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
	BEGIN TRAN
		UPDATE observatory SET manager = 'Evelyne Musso' WHERE name LIKE 'Paris Observatory'
		WAITFOR DELAY '00:00:05'
		UPDATE person SET occupation = 'writer' WHERE name LIKE 'Maria Metes'
	COMMIT TRAN
END

select * from observatory
select * from person


update observatory set manager = 'Guilleume Pirotte'
	where name LIKE 'Paris Observatory';
update person set occupation = 'teacher'
	where name LIKE 'Maria Metes';