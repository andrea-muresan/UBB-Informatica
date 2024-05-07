USE AstronomicalEvents2
GO

-- verifica daca un string contine doar litere si spatii
CREATE OR ALTER FUNCTION ONLY_LETTERS(@string varchar(100))
	RETURNS INT
AS
BEGIN
	IF PATINDEX('%[^A-Za-z ]%', @string) = 0
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- verifica daca lungimea unui string este >= decat un numar dat
CREATE OR ALTER FUNCTION MINIMUM_LENGTH(@string varchar(100), @min_len INT)
	RETURNS INT
AS
BEGIN
	IF LEN(@string) >= @min_len
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO

-- verifica daca o data nu este mai mare decat acea de azi
CREATE OR ALTER FUNCTION CHECK_DATE(@date DATE)
	RETURNS INT
AS
BEGIN
	IF @date <= GETDATE()
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO


-- valideaza un URL
CREATE OR ALTER FUNCTION WEBSITE_URL(@url_string VARCHAR(255))
	RETURNS INT
AS
BEGIN
	IF (@url_string IS NOT NULL AND
		(@url_string LIKE 'https://%[a-z,0-9]%.[A-Za-z]%[A-Za-z]%' OR 
		@url_string LIKE 'http://%[a-z,0-9]%.[A-Za-z]%[A-Za-z]%' OR
		@url_string LIKE 'www.[A-Za-z]%[A-Za-z].[A-Za-z]%[A-Za-z]%'
		))
	BEGIN
		RETURN 1;
	END

	RETURN 0;
END
GO


CREATE OR ALTER PROCEDURE ADD_OBSERVATORY_EVENT

	@e_name varchar(50), 
	@e_type varchar(30),
	@e_description VARCHAR(1000),

	@o_name VARCHAR(50),
	@o_website VARCHAR(255),
	@o_manager varchar(50),

	@oe_date DATE
	
AS
BEGIN
	PRINT 'AddObservatoryEvent'
	BEGIN TRAN
		PRINT 'Begin transaction'
		BEGIN TRY
			IF(dbo.MINIMUM_LENGTH(@e_name, 3) = 0)
				BEGIN RAISERROR('Event name not valid', 14, 1)
				END
			IF(dbo.ONLY_LETTERS(@e_type) = 0 OR dbo.MINIMUM_LENGTH(@e_type, 3) = 0)
				BEGIN RAISERROR('Event type not valid', 14, 1)
				END
			IF(dbo.MINIMUM_LENGTH(@e_description, 3) = 0)
				BEGIN RAISERROR('Event description not valid', 14, 1)
				END
			IF(dbo.ONLY_LETTERS(@o_name) = 0 OR dbo.MINIMUM_LENGTH(@o_name, 3) = 0)
				BEGIN RAISERROR('Observatory name not valid', 14, 1)
				END
			IF(dbo.WEBSITE_URL(@o_website) = 0)
				BEGIN RAISERROR('Observatory Website URL not valid', 14, 1)
				END
			IF(dbo.ONLY_LETTERS(@o_manager) = 0 OR dbo.MINIMUM_LENGTH(@o_manager, 3) = 0)
				BEGIN RAISERROR('Observatory manager not valid', 14, 1)
				END
			IF(dbo.CHECK_DATE(@oe_date) = 0)
				BEGIN RAISERROR('Date not valid', 14, 1)
				END

			PRINT 'Valid data';

			-- inserare in event
			INSERT INTO event (name, type, description)
				VALUES(@e_name, @e_type, @e_description);
			DECLARE @event_id INT = SCOPE_IDENTITY();

			-- inserare in observatory
			INSERT INTO observatory (name, website, manager)
				VALUES (@o_name, @o_website, @o_manager);
			DECLARE @observatory_id INT = SCOPE_IDENTITY();

			-- inserare in ObservatoryEvents
			INSERT INTO observatory_event (observatory_id, event_id, date)
				VALUES(@observatory_id, @event_id, @oe_date);

			COMMIT TRAN
			PRINT 'Tansaction commited'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			PRINT ERROR_MESSAGE()
			PRINT 'Transaction rollback'
		END CATCH
END

GO

Select * from event;
Select * from observatory;
Select * from observatory_event;
EXEC ADD_OBSERVATORY_EVENT 'e name', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2023-01-15';
--EXEC ADD_OBSERVATORY_EVENT 'e', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2023-01-15'; -- invalid name - MINIMUM_LENGTH
--EXEC ADD_OBSERVATORY_EVENT 'e name', 'e_type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2023-01-15'; -- invalid type - ONLY_LETTERS
--EXEC ADD_OBSERVATORY_EVENT 'e name', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory', 'o manager', '2023-01-15' -- invalid website - WEBSITE_URL
--EXEC ADD_OBSERVATORY_EVENT 'e name', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2027-01-15' -- invalid date - CHECK-DATE
Select * from event;
Select * from observatory;
Select * from observatory_event;

DELETE FROM event WHERE id > 5;
DELETE FROM observatory WHERE id > 6;


GO


CREATE OR ALTER PROCEDURE ADD_OBSERVATORY_EVENT2 
	@e_name varchar(50), 
	@e_type varchar(30),
	@e_description VARCHAR(1000),

	@o_name VARCHAR(50),
	@o_website VARCHAR(255),
	@o_manager varchar(50),

	@oe_date DATE
AS
BEGIN
	PRINT 'AddObservatoryEvent2'
	DECLARE @event_id INT = -1;
	DECLARE @observatory_id INT = -1;

	-- tran1
	BEGIN TRAN
		PRINT 'Begin transaction for event'
		BEGIN TRY
			-- validari
			IF(dbo.MINIMUM_LENGTH(@e_name, 3) = 0)
				BEGIN RAISERROR('Event name not valid', 14, 1)
				END
			IF(dbo.ONLY_LETTERS(@e_type) = 0 OR dbo.MINIMUM_LENGTH(@e_type, 3) = 0)
				BEGIN RAISERROR('Event type not valid', 14, 1)
				END
			IF(dbo.MINIMUM_LENGTH(@e_description, 3) = 0)
				BEGIN RAISERROR('Event description not valid', 14, 1)
				END
			
			-- inserare in event
			INSERT INTO event (name, type, description)
				VALUES(@e_name, @e_type, @e_description);
			SET @event_id = SCOPE_IDENTITY();
			COMMIT TRAN
			PRINT 'Commit transaction for event'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			PRINT ERROR_MESSAGE()
			PRINT 'Transaction rollback for event'
		END CATCH

	-- tran2
	BEGIN TRAN
		PRINT 'Begin transaction for observatory'
		BEGIN TRY
			IF(dbo.ONLY_LETTERS(@o_name) = 0 OR dbo.MINIMUM_LENGTH(@o_name, 3) = 0)
				BEGIN RAISERROR('Observatory name not valid', 14, 1)
				END
			IF(dbo.WEBSITE_URL(@o_website) = 0)
				BEGIN RAISERROR('Observatory Website URL not valid', 14, 1)
				END
			IF(dbo.ONLY_LETTERS(@o_manager) = 0 OR dbo.MINIMUM_LENGTH(@o_manager, 3) = 0)
				BEGIN RAISERROR('Observatory manager not valid', 14, 1)
				END

			-- inserare in observatory
			INSERT INTO observatory (name, website, manager)
				VALUES (@o_name, @o_website, @o_manager);
			SET @observatory_id = SCOPE_IDENTITY();
			COMMIT TRAN
			PRINT 'Commit transaction for observatory'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			PRINT ERROR_MESSAGE()
			PRINT 'Transaction rollback for observatory'
		END CATCH

	-- tran3
	BEGIN TRAN
		PRINT 'Begin transaction for observatory_event'
		BEGIN TRY
			IF(@event_id = -1 or @observatory_id = -1)
				BEGIN RAISERROR('Event or observatory could not be added', 14, 1)
				END
			
			-- validari
			IF(dbo.CHECK_DATE(@oe_date) = 0)
				BEGIN RAISERROR('Date not valid', 14, 1)
				END

			-- inserare in ObservatoryEvents
			INSERT INTO observatory_event (observatory_id, event_id, date)
				VALUES(@observatory_id, @event_id, @oe_date);

			COMMIT TRAN
			PRINT 'Commit transaction for observatory_event'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			PRINT ERROR_MESSAGE()
			PRINT 'Transaction rollback for observatory_event'
		END CATCH
END

GO


DELETE FROM event WHERE id > 5;
DELETE FROM observatory WHERE id > 6;

GO

Select * from event;
Select * from observatory;
Select * from observatory_event;
--EXEC ADD_OBSERVATORY_EVENT2 'e name', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2023-01-15';
EXEC ADD_OBSERVATORY_EVENT2 'e', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2023-01-15'; -- invalid name - MINIMUM_LENGTH
--EXEC ADD_OBSERVATORY_EVENT2 'e name', 'e_type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2023-01-15'; -- invalid type - ONLY_LETTERS
--EXEC ADD_OBSERVATORY_EVENT2 'e name', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory', 'o manager', '2023-01-15' -- invalid website - WEBSITE_URL
--EXEC ADD_OBSERVATORY_EVENT2 'e name', 'e type', 'e_desc1', 'o name', 'https://MalagaObservatory.es', 'o manager', '2027-01-15' -- invalid date - CHECK-DATE
Select * from event;
Select * from observatory;
Select * from observatory_event;