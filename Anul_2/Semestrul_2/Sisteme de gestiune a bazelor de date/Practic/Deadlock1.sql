use S12
go

BEGIN TRANSACTION
	UPDATE TipuriSucuriNaturale SET NrStele = 3
	WHERE Categorie Like 'limonade';

	WAITFOR DELAY '00:00:10'

	UPDATE SucuriNaturale SET Pret = 35
	WHERE Gramaj = 500;
COMMIT TRAN