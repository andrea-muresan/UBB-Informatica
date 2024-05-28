USE AstronomicalEvents2
GO

-- prima
BEGIN TRANSACTION
	WAITFOR DELAY '00:00:10'
	INSERT INTO observatory(name, website, manager) VALUES
	('Planetarium Brasov', 'https://BrasovObservatory.ro', 'Matei Coste');
COMMIT TRAN;

select * from observatory;
