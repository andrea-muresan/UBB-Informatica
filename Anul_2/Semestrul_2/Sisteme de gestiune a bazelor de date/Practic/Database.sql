create database S12
go
use S12
go

create table TipuriFructe(
Tid int primary key identity,
Tip varchar(50),
Descriere varchar(50),
NrSortimente int)

create table Fructe(
Fid int primary key identity,
Denumire varchar(50),
Culoare varchar(50),
LunaOptimaCulegere varchar(50),
PretMediu int,
Tid int foreign key references TipuriFructe(Tid))

create table TipuriSucuriNaturale(
TSid int primary key identity,
Denumire varchar(50),
Categorie varchar(50),
NrStele int)

create table SucuriNaturale(
Sid int primary key identity,
Denumire varchar(50),
Producator varchar(50),
Gramaj int,
Pret int,
DataExpirare date,
TSid int foreign key references TipuriSucuriNaturale(TSid))

create table Achizitii(
Sid int foreign key references SucuriNaturale(Sid),
Fid int foreign key references Fructe(Fid),
DataAchizitie date,
Cantitate int,
constraint pk_Achizitii primary key(Sid, Fid))

-- 1-n: TipuriSucuriNaturale-SucuriNaturale

select * from TipuriFructe
select * from Fructe
select * from TipuriSucuriNaturale
select * from SucuriNaturale
select * from Achizitii

insert into TipuriFructe values('de sezon primavaratec', 'lunile aprilie - mai', 10),
('de sezon varatec', 'lunile iunie, iulie, august', 10)

insert into Fructe values('Capsuni', 'rosii', 'mai', 15, 1),
('Cirese', 'rosii', 'iunie', 30, 2)

insert into TipuriSucuriNaturale values('Fresh capsuni', 'fresh', 5), 
('Limonada cu cirese', 'limonade', 4 )

insert into SucuriNaturale values('Fresh de capsuni cu cirese', 'S.C. Steluta', 1000, 40, '10/07/2023', 1), 
('Fresh de capsuni de gradina', 'S.C. Capsunata', 500, 30, '06/07/2023', 1)

insert into Achizitii values(1, 1, '11/05/2022', 3),
(1, 2, '11/05/2023', 5)

