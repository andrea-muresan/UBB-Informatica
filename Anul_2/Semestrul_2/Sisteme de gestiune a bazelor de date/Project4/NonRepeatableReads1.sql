USE AstronomicalEvents2
GO

-- prima
INSERT INTO observatory(name, website, manager) VALUES
('Planetarium Brasov', 'https://BrasovObservatory.ro', 'Irina Avram');

SELECT * FROM observatory;

BEGIN TRANSACTION
	WAITFOR DELAY '00:00:10'
	UPDATE observatory SET manager = 'Matei Coste'
	WHERE  name LIKE 'Planetarium Brasov'
COMMIT TRAN;

SELECT * FROM observatory;

