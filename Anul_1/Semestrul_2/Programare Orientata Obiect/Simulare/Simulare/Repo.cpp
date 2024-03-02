#include "Repo.h"
#include <fstream>

// returneaza lista de obiecte
vector<Device> Repo::getAll()
{
	return all;
}

// citire din fisier
void Repo::readFile()
{
	std::ifstream in("text.txt");

	int an, pret;
	string serie, tip, culoare, model;
		
	while (in >> serie)
	{
		in >> model >> tip >> culoare >> an >> pret;
		Device d{ serie, model, tip,culoare, an, pret };
		all.push_back(d);
	}
}

	
