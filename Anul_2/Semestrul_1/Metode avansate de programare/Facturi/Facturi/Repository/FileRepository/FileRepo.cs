using System.Runtime.InteropServices;
using Facturi.Domain;

namespace Facturi.Repository.FileRepository;

public abstract class FileRepo<TId, TE> : InMemoryRepository<TId, TE> where TE : Entity<TId>
{
    protected FileRepo() { }
    public FileRepo(string fileName)
    {
        readFromFile(fileName);
    }
    protected void readFromFile(string fileName)
    {
        StreamReader streamReaderreader = new StreamReader(fileName);
        string data;
        while(true)
        {
            data = streamReaderreader.ReadLine();
            if (data == null)
                break;

            Save(entityFromString(data));

        }
    }

    protected abstract TE entityFromString(string data);

}