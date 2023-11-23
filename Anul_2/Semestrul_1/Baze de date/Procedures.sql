
-- Cream tabelul cu versiunea Bazei de date
CREATE TABLE version_db(
	version_no INT DEFAULT 0
);

INSERT INTO version_db
VALUES(0);

SELECT * FROM version_db;
GO

-- PROCEDURI
-- 1 DO : modifica tipul unei coloane
CREATE PROCEDURE modify_column_type
AS
	ALTER TABLE person
	ALTER COLUMN name nvarchar(50);
GO

-- 1 UNDO : anuleaza modificarea tipul coloanei
CREATE PROCEDURE modify_column_type_back
AS
BEGIN
	ALTER TABLE person
	ALTER COLUMN name varchar(50);
END
GO

-- 2 DO : adauga o costrangere de “valoare implicita” pentru o coloana
CREATE PROCEDURE add_default_constraint
AS
BEGIN
	ALTER TABLE image
	ADD CONSTRAINT df_photographer
	DEFAULT 'anonim' FOR photographer;
END
GO

-- 2 UNDO : setrge o costrangere de “valoare implicita” pentru o coloana
CREATE PROCEDURE remove_default_constraint
AS
BEGIN
	ALTER TABLE image
	DROP CONSTRAINT df_photographer;
END
GO

-- 3 DO : creeaza o tabela noua
CREATE PROCEDURE create_new_table
AS
BEGIN
	CREATE TABLE manager_contact(
	id INT PRIMARY KEY IDENTITY,
	phone VARCHAR(15)
	);
END
GO

-- 3 UNDO : sterge o tabela
CREATE PROCEDURE remove_table
AS
BEGIN
	DROP TABLE manager_contact;
END
GO

-- 4 DO : adauga o coloana noua
CREATE PROCEDURE add_new_column
AS
BEGIN
	ALTER TABLE manager_contact
	ADD email VARCHAR(60)
END
GO

-- 4 UNDO : sterge o coloana
CREATE PROCEDURE remove_column
AS
BEGIN
	ALTER TABLE manager_contact
	DROP COLUMN email
END
GO

-- 5 DO : creare constrangere de cheie straina
CREATE PROCEDURE create_foreign_key_constraint
AS
BEGIN
	ALTER TABLE manager_contact
	ADD CONSTRAINT fk_id_manager_contact
	FOREIGN KEY (id) REFERENCES observatory(id);
END
GO

-- 5 UNDO : sterge constrangere de cheie straina
CREATE PROCEDURE remove_foreign_key_constraint
AS
BEGIN
	ALTER TABLE manager_contact
	DROP CONSTRAINT fk_id_manager_contact;
END
GO

----------------------------------------------------------
-- cream doua tabele auxiliara
CREATE TABLE do_procedure_list(
	id INT PRIMARY KEY,
	procedure_name VARCHAR(100)
);

INSERT INTO do_procedure_list
VALUES
(0, 'modify_column_type'),
(1, 'add_default_constraint'),
(2, 'create_new_table'),
(3, 'add_new_column'),
(4, 'create_foreign_key_constraint');

CREATE TABLE undo_procedure_list(
	id INT PRIMARY KEY,
	procedure_name VARCHAR(100)
);


INSERT INTO undo_procedure_list
VALUES
(1, 'modify_column_type_back'),
(2, 'remove_default_constraint'),
(3, 'remove_table'),
(4, 'remove_column'),
(5, 'remove_foreign_key_constraint');


SELECT * FROM do_procedure_list;
SELECT * FROM undo_procedure_list;

GO

------------------------------------------------
-- main
CREATE PROCEDURE main
	@new_version_char VARCHAR(5)
AS
BEGIN
	DECLARE @current_version INT;
	DECLARE @proc VARCHAR(100);
	DECLARE @new_version INT;

	SELECT @current_version = version_no
	FROM version_db;

	PRINT 'Versiunea curenta este: ';
	PRINT @current_version;
	PRINT 'Schimba la versiunea: ';
	PRINT @new_version_char;

	-- IF @new_version < 0 or @new_version > 5 or @new_version % 1 != 0
	IF @new_version_char NOT LIKE '[0-5]'
		BEGIN
			PRINT 'Parametrul poate fi doar: 0, 1, 2, 3, 4 sau 5';
		END
	ELSE
		BEGIN
			SET @new_version = CONVERT(int, @new_version_char)
			IF @current_version = @new_version
				BEGIN
					PRINT 'Baza de date este deja in versiunea ceruta';
				END
			IF @current_version < @new_version
			BEGIN
				WHILE @current_version != @new_version
				BEGIN
					SELECT @proc = procedure_name
					FROM do_procedure_list
					where @current_version=id;

					SET @current_version = @current_version + 1

					EXECUTE @proc;
				END
				UPDATE version_db
				SET version_no = @new_version;
				PRINT 'GATA!';
			END

			IF @current_version > @new_version
			BEGIN
				WHILE @current_version != @new_version
				BEGIN

					SELECT @proc = procedure_name
					FROM undo_procedure_list
					WHERE @current_version=id;

					SET @current_version = @current_version-1;

					EXECUTE @proc;
				END
				UPDATE version_db
				SET version_no = @new_version;
				PRINT 'GATA!';
			END
		END
END
GO

EXEC main 0;
EXEC main 1;
EXEC main 2;
EXEC main 3;
EXEC main 4;
EXEC main 5;
EXEC main 1.57;
 
DROP PROCEDURE main

UPDATE version_db
SET version_no = 0;
