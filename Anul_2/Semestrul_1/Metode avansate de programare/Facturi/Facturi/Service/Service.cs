using Facturi.Domain;
using Facturi.Repository;

namespace Facturi.Service;

public class Service
{
    private IRepository<string, Document> documente;
    private IRepository<string, Factura> facturi;
    private IRepository<string, Achizitie> achizitii;

    public Service(IRepository<string, Document> documente, IRepository<string, Factura> facturi, IRepository<string, Achizitie> achizitii)
    {
        this.documente = documente;
        this.facturi = facturi;
        this.achizitii = achizitii;
    }

    public IEnumerable<Document> Documente2023()
    {
        return from doc in documente.FindAll()
            where doc.DataEmitere.Year == 2023
            select doc;
    }
    
    public IEnumerable<Factura> FacturiScadenteLunaCurenta()
    {
        return from fact in facturi.FindAll()
            where fact.DataScadenta.Month == DateTime.Now.Month // & fact.DataScadenta.Year == DateTime.Now.Year
            select fact;
    }
    
    public class FacturaCantitate
    {
        public Factura Factura { get; set; }
        public int Cantitate { get; set; }
    }
    public IEnumerable<FacturaCantitate> FacturiMinimTreiProduse()
    {
        return from fact in facturi.FindAll()
            join achizitie in achizitii.FindAll() on fact.Id equals achizitie.Factura.Id into cantitateGroup
            let totalCantitate = cantitateGroup.Sum(p => p.cantitate)
            where totalCantitate >= 3
            select new FacturaCantitate
            {
                Factura = fact,
                Cantitate = totalCantitate
            };
    }
}