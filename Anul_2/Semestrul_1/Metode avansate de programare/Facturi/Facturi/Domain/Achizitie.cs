namespace Facturi.Domain;

public class Achizitie : Entity<string>
{
    public string produs { get; set; }
    public int cantitate { get; set; }
    public double PretProdus { get; set; }
    public Factura Factura { get; set; }

    public Achizitie(string id, string produs, int cantitate, double pretProdus, Factura factura)
    {
        base.Id = id;
        this.produs = produs;
        this.cantitate = cantitate;
        PretProdus = pretProdus;
        Factura = factura;
    }
}