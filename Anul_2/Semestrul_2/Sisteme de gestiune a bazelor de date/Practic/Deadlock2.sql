use S12
go

-- problema
BEGIN TRANSACTION
	UPDATE SucuriNaturale SET Pret = 37
	WHERE Gramaj = 500;

	WAITFOR DELAY '00:00:10'

	UPDATE TipuriSucuriNaturale SET NrStele = 5
	WHERE Categorie Like 'limonade';
COMMIT TRAN

/*
-- solutie
BEGIN TRANSACTION
	UPDATE TipuriSucuriNaturale SET NrStele = 5
	WHERE Categorie Like 'limonade';

	WAITFOR DELAY '00:00:10'

	UPDATE SucuriNaturale SET Pret = 37
	WHERE Gramaj = 500;
COMMIT TRAN
*/

