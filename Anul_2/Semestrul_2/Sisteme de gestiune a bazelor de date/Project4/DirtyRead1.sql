USE AstronomicalEvents2
GO

-- prima
BEGIN TRANSACTION
	UPDATE observatory SET
	manager='Evelyne Musso' WHERE id = 1;
	WAITFOR DELAY '00:00:10'
ROLLBACK TRANSACTION

select * from observatory

