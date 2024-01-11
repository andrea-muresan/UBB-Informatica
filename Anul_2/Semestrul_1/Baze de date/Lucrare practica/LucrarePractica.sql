USE Test2
GO

CREATE TABLE tip_film (
	id INT PRIMARY KEY IDENTITY,
	denumire VARCHAR(50),
	descriere VARCHAR(100),
);


CREATE TABLE platou_filmare (
	id INT PRIMARY KEY IDENTITY,
	denumire VARCHAR(50),
	strada VARCHAR(50),
	numar VARCHAR(5),
	localitate VARCHAR(50),
);

CREATE TABLE film (
	id INT PRIMARY KEY IDENTITY,
	denumire VARCHAR(50),
	durata int,
	platou_fimare_id INT REFERENCES platou_filmare(id) ON DELETE CASCADE,
	tip_id INT REFERENCES tip_film(id) ON DELETE CASCADE,
);


CREATE TABLE regizor (
	id INT PRIMARY KEY IDENTITY,
	nume VARCHAR(50),
	prenume VARCHAR(50),
	gen VARCHAR(20),
	varsta INT,
);


CREATE TABLE regizor_platou (
	id INT PRIMARY KEY IDENTITY,
	regizor_id INT FOREIGN KEY REFERENCES regizor(id) ON DELETE CASCADE,
	platou_filmare_id INT FOREIGN KEY REFERENCES platou_filmare(id) ON DELETE CASCADE,
	data_incepere DATE,
	data_finalizare DATE,
);
GO

INSERT INTO tip_film (denumire, descriere)
VALUES 
('comedie', 'descriere_comedie'),
('aventura', 'descriere_aventura');

INSERT INTO platou_filmare (denumire, strada, numar, localitate)
VALUES 
('platou1', 'strada1', '1A', 'Bucuresti'),
('platou2', 'strada2', '18B', 'Iasi'),
('platou3', 'strada3', '10C', 'Bucuresti');

INSERT INTO film (denumire, durata, platou_fimare_id, tip_id)
VALUES ('Film1', 130, 1, 1), ('Film2', 170, 2, 2);

INSERT INTO film (denumire, durata, platou_fimare_id, tip_id)
VALUES ('Film3', 140, 1, 1), ('Film4', 160, 1, 2),  ('Film5', 100, 2, 2), ('Film6', 100, 3, 2) ;

INSERT INTO regizor(nume, prenume, gen, varsta)
VALUES ('Ana', 'Popescu', 'femeie', 33), ('Marian', 'Ratiu', 'barbat', 23);

INSERT INTO regizor_platou (regizor_id, platou_filmare_id, data_incepere, data_finalizare)
VALUES (1, 1, '2023-01-15', '2023-08-15'), (1, 2, '2011-06-23', '2013-08-15');

GO

SELECT * FROM film;
GO

create or alter procedure adaugaRegizorPlatou 
	@regizor_id int, 
	@platou_id int,
	@data_incepere DATE,
	@data_finalizare DATE
as
begin
	if(exists(select * from regizor where id=@regizor_id) and exists(select * from platou_filmare where id=@platou_id))
		begin
			if(exists(select * from regizor_platou where regizor_id=@regizor_id and platou_filmare_id=@platou_id))
				begin
					update regizor_platou set data_incepere=@data_incepere, data_finalizare=@data_finalizare 
					where regizor_id=@regizor_id and platou_filmare_id=@platou_id
					print 'Datele s-au actualizat!'
				end
			else
				begin
					insert into regizor_platou(regizor_id, platou_filmare_id, data_incepere, data_finalizare)
					values (@regizor_id, @platou_id, @data_incepere, @data_finalizare)
					print 'Masina a fost adaugata cursei!'
				end
		end
	else
		begin
			print 'Datele sunt invalide!'
		end 
end
GO

EXEC adaugaRegizorPlatou 2, 3, '2017-01-15', '2017-04-15';
select * FROM regizor_platou
GO

create or alter function functie(@n INT)
	returns table
as
	return select p.denumire, count(*) as film
	from platou_filmare as p inner join film on film.platou_fimare_id=p.id
	group by p.denumire
	having count(*) >= @n;
GO

select * from dbo.functie(1)