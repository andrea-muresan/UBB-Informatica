namespace Facturi.Domain;

public enum Categorie
{
    Utilities,
    Groceries,
    Fashion,
    Entertainment,
    Electronics
}

public class Factura : Document
{
    public DateTime DataScadenta { get; set; }
    public List<Achizitie> Achizitii { get; set; }
    public Categorie Categorie { get; set; }

    public Factura(string id, string nume, DateTime dataEmitere, DateTime dataScadenta, Categorie categorie) : base(id, nume, dataEmitere)
    {
        DataScadenta = dataScadenta;
        Achizitii = new List<Achizitie>();
        Categorie = categorie;
    }
    

    public void AdaugaAchizitie(Achizitie achizitie)
    {
        this.Achizitii?.Add(achizitie);
    }

    public override string ToString()
    {
        return base.Nume + " " + DataScadenta;
    }
}