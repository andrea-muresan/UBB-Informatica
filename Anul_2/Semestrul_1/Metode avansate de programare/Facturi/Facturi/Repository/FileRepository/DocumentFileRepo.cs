using Facturi.Domain;

namespace Facturi.Repository.FileRepository;

public class DocumentFileRepo : FileRepo<string, Document>
{
    public DocumentFileRepo(string fileName) : base(fileName)
    {
    }

    protected override Document entityFromString(string data)
    {
        var parti = data.Split(',');
        return new Document(parti[0], parti[1], Convert.ToDateTime(parti[2]));
    }
}