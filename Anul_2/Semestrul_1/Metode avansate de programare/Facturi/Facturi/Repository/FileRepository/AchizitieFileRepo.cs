using Facturi.Domain;

namespace Facturi.Repository.FileRepository;

public class AchizitieFileRepo : FileRepo<string, Achizitie>
{
    private InMemoryRepository<string, Factura> Facturi;

    public AchizitieFileRepo(string fileName, InMemoryRepository<string, Factura> facturi)
    {
        Facturi = facturi;
        readFromFile(fileName);
    }

    protected override Achizitie entityFromString(string data)
    {
        var parti = data.Split(',');
        var factura = Facturi.FindOne(parti[4]);
        
        var achizitie = new Achizitie(parti[0], parti[1], Convert.ToInt32(parti[2]), Convert.ToDouble(parti[3]), factura);
        factura.AdaugaAchizitie(achizitie);
        
        return achizitie;
    }
}