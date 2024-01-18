using Facturi.Domain;

namespace Facturi.Repository.FileRepository;

public class FacturaFileRepo : FileRepo<string, Factura>
{
    private DocumentFileRepo documente;

    public FacturaFileRepo(string fileName, DocumentFileRepo documente)
    {
        this.documente = documente;
        readFromFile(fileName);
    }

    protected override Factura entityFromString(string data)
    {
        var parti = data.Split(',');
        var doc = documente.FindOne(parti[0]);
        Enum.TryParse<Categorie>(parti[2], out Categorie categorie);
        return new Factura(doc.Id, doc.Nume, doc.DataEmitere, Convert.ToDateTime(parti[1]), categorie);
    }
}