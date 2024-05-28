use S12
go

create index MyIndex 
on Fructe(denumire, LunaOptimaCulegere, PretMediu);
go

select denumire, LunaOptimaCulegere from Fructe 
with (index(MyIndex)) where PretMediu > 20;
