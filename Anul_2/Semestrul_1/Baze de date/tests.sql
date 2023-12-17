USE AstronomicalEvents2
GO

/*
	event: 1 PK, no FK
	image: 1 PK + FK
	observatory_event: 2 PK
*/

-- Tabela TABLES
INSERT INTO tables(name) VALUES
('event'),
('image'),
('observatory_event');
GO

/*ALTER TABLE observatory_event
ADD CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE;

ALTER TABLE image
ADD CONSTRAINT fk_event_id_img FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE;

ALTER TABLE observatory_event
ADD CONSTRAINT fk_observatory_id FOREIGN KEY (observatory_id) REFERENCES observatory(id) ON DELETE CASCADE;*/

SELECT * FROM tables;
GO

-- View - 1 PK, no FK
CREATE VIEW view_event AS
 SELECT id, name, type
 FROM event;
GO

-- View - 1 PK + FK
CREATE VIEW view_image AS
 SELECT image.id, image.photographer, event.name
 FROM image 
 Inner Join event
 ON image.event_id = event.id;
GO

-- View - 2 PK
CREATE OR ALTER VIEW view_observatory_event AS
 SELECT observatory.name as o_name, event.name as e_name, event.type as e_type, date 
 FROM observatory
 INNER JOIN observatory_event
 ON observatory.id = observatory_event.observatory_id
 INNER JOIN event
 ON observatory_event.event_id = event.id
 group by observatory.id, observatory.name, event.id, event.name, event.type, date;
GO

-- Adaugare view-uri in tabel
INSERT INTO views VALUES
	('view_event'),
	('view_image'),
	('view_observatory_event');
GO

SELECT * FROM views

-- Adaugare teste in tabel 
INSERT INTO tests(name) VALUES 
	('div_event_10'),
	('div_image_23'),
	('div_observatory_event_7')
GO

SELECT * FROM tests

-- Legatura intre teste si tabele
INSERT INTO test_tables(test_id, table_id, no_of_rows, position) VALUES
	-- div_event_10
	(1, 1, 10, 1),
	-- div_image_23
	(2, 2, 23, 2),
	-- div_observatory_event_23
	(3, 1, 7, 3),
	(3, 3, 7, 4);

SELECT * FROM test_tables;
GO

-- Legatura intre teste si view-uri
INSERT INTO test_views(test_id, view_id) VALUES
	(1, 1),
	(2, 2),
	(3, 3);

SELECT * FROM test_views;
GO


-- CREAREA PROCEDURILOR --

-- Inserare - event
CREATE OR ALTER PROCEDURE insert_event (@rows int)
AS
BEGIN
	DECLARE @counter INT = 0;
	DECLARE @last_id INT = (SELECT MAX(event.id) FROM event) + 1

	WHILE @counter < @rows
	BEGIN
		INSERT INTO event(name, type, description)
        VALUES ('EventName' + CAST(@last_id AS VARCHAR), 'Type' + CAST(@last_id AS VARCHAR), 'Description' + CAST(@last_id AS VARCHAR));

		SET @last_id = @last_id + 1
		SET @counter = @counter + 1;
	END
	PRINT 'S-au inserat ' + CONVERT(VARCHAR(10), @counter) + ' event.'
END
GO

-- Inserare - image
CREATE OR ALTER PROCEDURE insert_image (@rows int)
AS
BEGIN
	DECLARE @counter INT = 0;
	DECLARE @last_id INT = (SELECT MAX(image.id) FROM image) + 1
	DECLARE @random_date DATE;
	DECLARE @fk INT = (Select TOP 1  event.id from event);
	WHILE @counter < @rows
	BEGIN
		SET @random_date = DATEADD(DAY, CAST(RAND() * 365 AS INT), '2020-01-01');
		INSERT INTO image(event_id, photographer, date_taken, image_url, city, country)
        VALUES (@fk, 'Photographer' + CAST(@last_id AS VARCHAR), @random_date, 'URL' + CAST(@last_id AS VARCHAR),
			'City' + CAST(@last_id AS VARCHAR), 'Country' + CAST(@last_id AS VARCHAR));

		SET @last_id = @last_id + 1
		SET @counter = @counter + 1;
	END

	PRINT 'S-au inserat ' + CONVERT(VARCHAR(10), @counter) + ' image.'
END
GO


-- Inserare - observatory_event
CREATE OR ALTER PROCEDURE insert_observatory_event (@rows int)
AS
BEGIN
	DECLARE @counter INT = 0;
	DECLARE @random_date DATE;
	DECLARE @observatory_id INT = (Select TOP 1 id from observatory);
	DECLARE @event_id INT;

	DECLARE cursor_event CURSOR FAST_FORWARD FOR
		SELECT event.id FROM event;

	OPEN cursor_event;
	FETCH NEXT FROM cursor_event INTO @event_id;
	WHILE (@counter < @rows) AND (@@FETCH_STATUS = 0)
	BEGIN
		SET @random_date = DATEADD(DAY, CAST(RAND() * 365 AS INT), '2020-01-01');
		INSERT INTO observatory_event(event_id, observatory_id, date) VALUES (@event_id, @observatory_id, @random_date);

		SET @counter = @counter + 1;

		FETCH NEXT FROM cursor_event INTO @event_id;
	END

	CLOSE cursor_event;
	DEALLOCATE cursor_event;

	PRINT 'S-au inserat ' + CONVERT(VARCHAR(10), @counter) + ' valori in observatory_event.'
END
GO


-- Stergere event
CREATE OR ALTER PROCEDURE delete_event
AS
BEGIN
	SET NOCOUNT ON;
	DELETE FROM event
	WHERE event.name LIKE 'EventName%';
	PRINT 'S-au sters ' + CONVERT(VARCHAR(10), @@ROWCOUNT) + ' valori din event.';
END
GO


-- Stergere image
CREATE OR ALTER PROCEDURE delete_image
AS
BEGIN
	SET NOCOUNT ON;
	DELETE FROM image
	WHERE image.photographer LIKE 'Photographer%';
	PRINT 'S-au sters ' + CONVERT(VARCHAR(10), @@ROWCOUNT) + ' valori din image.';
END
GO

-- Stergere observatory_event
CREATE OR ALTER PROCEDURE delete_observatory_event
AS
BEGIN
	SET NOCOUNT ON;
	DELETE FROM observatory_event
	PRINT 'S-au sters ' + CONVERT(VARCHAR(10), @@ROWCOUNT) + ' valori din observatory_event.';
END
GO



-- Procedura main de inserare
CREATE OR ALTER PROCEDURE inseration_main
@test_id INT
AS
BEGIN
	DECLARE @test_name NVARCHAR(50) = (SELECT name FROM tests WHERE test_id = @test_id);
	DECLARE @table_name NVARCHAR(50);
	DECLARE @no_of_rows INT;
	DECLARE @procedure NVARCHAR(50);

	DECLARE cursor_tab CURSOR FORWARD_ONLY FOR
		SELECT tab.name, test_tables.no_of_rows FROM test_tables 
		INNER JOIN tables tab ON test_tables.table_id = tab.table_id
		WHERE test_tables.test_id = @test_id
		ORDER BY test_tables.position;
	OPEN cursor_tab;

	FETCH NEXT FROM cursor_tab INTO @table_name, @no_of_rows;
	WHILE (@test_name NOT LIKE N'div_' + @table_name + N'_' + CONVERT(NVARCHAR(10), @no_of_rows)) AND (@@FETCH_STATUS = 0)
	BEGIN
		SET @procedure = N'insert_' + @table_name;
		EXECUTE @procedure @no_of_rows;
		FETCH NEXT FROM cursor_tab INTO @table_name, @no_of_rows;
	END

	SET @procedure = N'insert_' + @table_name;
	EXECUTE @procedure @no_of_rows;

	CLOSE cursor_tab;
	DEALLOCATE cursor_tab;
END
GO

EXECUTE  inseration_main 1;
GO

-- Procedura main de stergere
CREATE OR ALTER PROCEDURE delete_main
@test_id INT
AS
BEGIN
	DECLARE @test_name NVARCHAR(50) = (SELECT name FROM tests WHERE test_id = @test_id);
	DECLARE @table_name NVARCHAR(50);
	DECLARE @no_of_rows INT;
	DECLARE @procedure NVARCHAR(50);

	DECLARE cursor_tab CURSOR FORWARD_ONLY FOR
		SELECT tab.name, test_tables.no_of_rows FROM test_tables 
		INNER JOIN tables tab ON test_tables.table_id = tab.table_id
		WHERE test_tables.test_id = @test_id
		ORDER BY test_tables.position DESC;
	OPEN cursor_tab;

	FETCH NEXT FROM cursor_tab INTO @table_name, @no_of_rows;
	WHILE (@test_name NOT LIKE N'div_' + @table_name + N'_' + CONVERT(NVARCHAR(10), @no_of_rows)) AND (@@FETCH_STATUS = 0)
	BEGIN
		SET @procedure = N'delete_' + @table_name;
		EXECUTE @procedure @no_of_rows;
		FETCH NEXT FROM cursor_tab INTO @table_name, @no_of_rows;
	END

	SET @procedure = N'delete_' + @table_name;
	EXECUTE @procedure ;

	CLOSE cursor_tab;
	DEALLOCATE cursor_tab;
END
GO

EXECUTE  delete_main 1;
GO

-- Procedura main de view
CREATE PROCEDURE view_main
@test_id INT
AS
BEGIN
	DECLARE @view_name NVARCHAR(50) = 
		(SELECT views.Name FROM views
		INNER JOIN test_views ON test_views.view_id = views.view_id
		WHERE test_views.test_id = @test_id);

	DECLARE @command NVARCHAR(55) = 
		N'SELECT * FROM ' + @view_name;
	
	EXECUTE (@command);
END
GO

EXEC view_main 2
GO

-- Procedura test
CREATE OR ALTER PROCEDURE run_test
@test_id INT
AS
BEGIN
	DECLARE @start_time DATETIME;
	DECLARE @inter_time DATETIME;
	DECLARE @end_time DATETIME;

	SET @start_time = GETDATE();
	
	EXECUTE delete_main @test_id;
	EXECUTE inseration_main @test_id;
	
	SET @inter_time = GETDATE();
	
	EXECUTE view_main @test_id;

	SET @end_time = GETDATE();

	-- var pt insert
	DECLARE @test_name NVARCHAR(50) =
		(SELECT T.Name FROM tests T WHERE T.test_id = @test_id);
	INSERT INTO test_runs VALUES (@test_name, @start_time, @end_time);

	DECLARE @view_id INT =
		(SELECT V.view_id FROM views V
		INNER JOIN test_views TV ON TV.view_id = V.view_id
		WHERE TV.test_id = @test_id);
	DECLARE @table_id INT =
		(SELECT TB.table_id FROM tests T
		INNER JOIN test_tables TT ON T.test_id = TT.test_id
		INNER JOIN tables TB ON TB.table_id = TT.table_id
		WHERE T.test_id = @test_id AND 
		T.Name LIKE N'div_' + TB.name + N'_' + CONVERT(NVARCHAR(10), TT.no_of_rows));
	DECLARE @test_run_id INT = 
		(SELECT TOP 1 T.test_run_id FROM test_runs T
		WHERE T.description = @test_name
		ORDER BY T.test_run_id DESC);
	
	INSERT INTO test_run_tables VALUES (@test_run_id, @table_id, @start_time, @inter_time);
	INSERT INTO test_run_views VALUES (@test_run_id, @view_id, @inter_time, @end_time);

	PRINT CHAR(10) + '*** TEST COMPLETAT CU SUCCES IN ' + 
		 CONVERT(VARCHAR(10), DATEDIFF(millisecond, @start_time, @end_time)) +
		 ' milisecunde. ***'
END
GO

EXECUTE run_test 3;
GO

SELECT * FROM test_run_views

SELECT * FROM event
SELECT * FROM observatory
SELECT * FROM observatory_event
SELECT * FROM image
SELECT * FROM views

DELETE FROM event

SELECT * FROM observatory_event

INSERT INTO observatory_event
VALUES ('2', '105', '2014-08-08')