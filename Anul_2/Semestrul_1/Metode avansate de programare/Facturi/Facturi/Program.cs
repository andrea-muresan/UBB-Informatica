// See https://aka.ms/new-console-template for more information

using System.ComponentModel;
using Facturi.Domain;
using Facturi.Repository.FileRepository;
using Facturi.Service;

string documenteFilePath = "/Users/Lenovo/Desktop/Github/UBB-Informatica/Anul_2/Semestrul_1/Metode avansate de programare/Facturi/Facturi/Data/documente.txt";
string facturiFilePath = "/Users/Lenovo/Desktop/Github/UBB-Informatica/Anul_2/Semestrul_1/Metode avansate de programare/Facturi/Facturi/Data/facturi.txt";
string achizitiiFilePath = "/Users/Lenovo/Desktop/Github/UBB-Informatica/Anul_2/Semestrul_1/Metode avansate de programare/Facturi/Facturi/Data/achizitii.txt";

var documenteFileRepo = new DocumentFileRepo(documenteFilePath);
var facturiFileRepo = new FacturaFileRepo(facturiFilePath, documenteFileRepo);
var achizitiiFileRepo = new AchizitieFileRepo(achizitiiFilePath, facturiFileRepo);

var service = new Service(documenteFileRepo, facturiFileRepo, achizitiiFileRepo);

while (true)
{
    Console.WriteLine("\nMeniu:");
    Console.WriteLine("1. Documente din 2023");
    Console.WriteLine("2. Facturi scadente in luna curenta");
    Console.WriteLine("3. Facturi cu minim 3 produse");
    Console.Write("\nCommanda: ");

    string command = Console.ReadLine();
    if (command == "1")
    {
        Console.WriteLine("\n Lista - Documente emise in 2023:");
        foreach (var element in service.Documente2023())
        {
            Console.WriteLine(element);
        }
    } 
    else if (command == "2")
    {
        Console.WriteLine("\nLista - Facturi scadente luna curenta:");
        foreach (var element in service.FacturiScadenteLunaCurenta())
        {
            Console.WriteLine(element);
        }
    }
    else if (command == "3")
    {
        Console.WriteLine("\nLista - Facturi cu minim trei produse:");
        foreach (var element in service.FacturiMinimTreiProduse())
        {
            Console.WriteLine(element.Factura.Nume + " " + element.Cantitate);
        }
    }
    else if (command == "x")
    {
        Console.WriteLine("La revedere!");
        break;
    }
    else 
    {
        Console.WriteLine("Comanda invalida");
    }
}