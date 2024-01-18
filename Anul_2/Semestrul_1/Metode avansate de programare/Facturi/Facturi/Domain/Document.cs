namespace Facturi.Domain;

public class Document : Entity<string>
{
    public string Nume { get; set; }
    public DateTime DataEmitere { get; set; }

    public Document(string id, string nume, DateTime dataEmitere)
    {
        base.Id = id;
        Nume = nume;
        DataEmitere = dataEmitere;
    }

    public Document ()
    {
    }

    public override string ToString()
    {
        return Nume + " " + DataEmitere;
    }
}