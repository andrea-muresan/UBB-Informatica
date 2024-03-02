#include "Service.h"
#include <assert.h>

#include<vector>

// returneaza lista de obiecte
// returneaza lista de obiecte
vector<Device> Service::getAll()
{
	return repo.getAll();
}

// citire din fisier
void Service::readFile()
{
	repo.readFile();
}

// sortare dupa an
vector<Device> Service::sortAn()
{
	vector<Device> lista = getAll();

	for(int i=0; i< lista.size() -1; i++)
		for (int j = i + 1; j < lista.size(); j++)
		{
			if (lista[i].getAn() > lista[j].getAn())
			{
				Device aux = lista[i];
				lista[i] = lista[j];
				lista[j] = aux;
			}
		}

	return lista;
}

// sortare dupa model
vector<Device> Service::sortModel()
{
	vector<Device> lista = getAll();

	for (int i = 0; i < lista.size() - 1; i++)
		for (int j = i + 1; j < lista.size(); j++)
		{
			if (lista[i].getModel() > lista[j].getModel())
			{
				Device aux = lista[i];
				lista[i] = lista[j];
				lista[j] = aux;
			}
		}

	return lista;
}

void testServive()
{
	Repo repo;
	Service srv{ repo };

	// test getall
	assert(srv.getAll().size() == 0);

	// test citire din fisier
	srv.readFile();
	assert(srv.getAll().size() != 0);

	// teste getteri
	assert(srv.getAll()[0].getSerie() == "1");
	assert(srv.getAll()[0].getModel() == "model1");
	assert(srv.getAll()[0].getTip() == "tip1");
	assert(srv.getAll()[0].getCuloare() == "culoare1");
	assert(srv.getAll()[0].getPret() == 7);

	// teste sortare an
	assert(srv.sortAn().size() != 0);
	assert(srv.sortAn()[0].getAn() == 2000);
	assert(srv.sortAn()[1].getAn() == 2001);
	assert(srv.sortAn()[9].getAn() == 2020);
	

	// teste sortare model
	assert(srv.sortModel().size() != 0);
	assert(srv.sortModel()[0].getModel() == "model0");
	assert(srv.sortModel()[1].getModel() == "model1");
	assert(srv.sortModel()[9].getModel() == "model9");
}
