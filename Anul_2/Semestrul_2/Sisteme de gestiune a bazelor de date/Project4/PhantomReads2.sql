USE AstronomicalEvents2
GO

-- a doua
-- PROBLEM: 
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
--SET TRANSACTION ISOLATION LEVEL SERIALIZABLE 
BEGIN TRAN
	SELECT * FROM observatory
	WAITFOR DELAY '00:00:13'
	SELECT * FROM observatory
COMMIT TRAN

delete from observatory where name = 'Planetarium Brasov';
select * from observatory;