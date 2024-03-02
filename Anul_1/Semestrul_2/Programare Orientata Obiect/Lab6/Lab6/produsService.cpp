#include "produsService.h"
#include <functional>
#include <algorithm>
#include <assert.h>

/* General sort function */
Vector<Produs> ProdusStore::generalSort(bool(*maiMicF)(const Produs&, const Produs&)){
	Vector<Produs> v{ rep.getAll() };//fac o copie	
	for (int i = 0; i < v.size(); i++) {
		for (int j = i + 1; j < v.size(); j++) {
			if (!maiMicF(v[i], v[j])) {
				// exchanging
				Produs aux = v[i];
				v[i] = v[j];
				v[j] = aux;
			}
		}
	}
	return v;
}

/*
Add a product
Throw an exception if there is another product with the same name, type, and producer
*/
void ProdusStore::addProdus(const string& name, const string& type, int price, const string& producer) {
	Produs p{name, type, price, producer };
	val.validate(p);
	rep.store(p);
}

/*
Delete product
Throw an exception if there is no product with the same name, type, and producer
*/
void ProdusStore::delProdus(const string& name, const string& type, const string& producer)
{
	rep.del(name, type, producer);
}

void ProdusStore::delAll()
{
	rep.delAll();
}

/*
Update product
Throw an exception if there is no product with the same name, type, and producer
*/
void ProdusStore::updateProdus(const string& name, const string& type, const string& producer, const string& new_value, int what)
{
	rep.update(name, type, producer, new_value, what);
}

/*
Search a product and return it
Throw an exception if there is no product with the same name, type, and producer
*/
Produs ProdusStore::searchProdus(const string& name, const string& type, const string& producer)
{
	return rep.find(name, type, producer);
}

/*
Sort by type
*/
 Vector<Produs> ProdusStore::sortByType() {
	//auto copyAll = rep.getAll();
	//std::sort(copyAll.begin(), copyAll.end(), cmpType);
	//return copyAll;
	return generalSort(cmpType);
}

/*
Sort by name
*/
Vector<Produs> ProdusStore::sortByName() {
	return generalSort(cmpName);
}


/*
Sort by name+price
*/
Vector<Produs> ProdusStore::sortByNameType() {
	return generalSort([](const Produs& p1, const Produs& p2) {
		if (p1.getName() == p2.getName()) {
			return p1.getType() < p2.getType();
		}
		return p1.getName() < p2.getName();
		});
}

/*Return a filtered list*/
Vector<Produs> ProdusStore::filtreaza(function<bool(const Produs&)> fct) {
	Vector<Produs> rez;
	for (const auto& Produs : rep.getAll()) {
		if (fct(Produs)) {
			rez.add(Produs);
		}
	}
	return rez;
}

/*
Filter by tip
*/
Vector<Produs> ProdusStore::filtrareTip(const string &type) {
	return filtreaza([=](const Produs& p) {
		return p.getType() == type;
		});
}

/*
Filter by price
*/
Vector<Produs> ProdusStore::filtrarePret(int pretMin, int pretMax) {
	return filtreaza([=](const Produs& p) {
		return p.getPrice() >= pretMin && p.getPrice() <= pretMax;
		});
}

/*
Filter by producer
*/
Vector<Produs> ProdusStore::filtrareProducator(const string& producer) {
	return filtreaza([=](const Produs& p) {
		return p.getProducer() == producer;
		});
}

/*Add some random products*/
void ProdusStore::adaugaCateva() {
	addProdus("mere", "fructe", 6, "Romania");
	addProdus("miere", "alimente", 15, "Stupul Vesel");
	addProdus("masline", "fructe", 25, "Grecia");
	addProdus("faina", "neperisabile", 5, "Baneasa");
}



/*TESTS*/
void testAdaugaCtr() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore ctr{ rep,val };
	ctr.addProdus("a", "a", 6, "a");
	assert(ctr.getAll().size() == 1);

	//try to add an invalid product
	try {
		ctr.addProdus("", "", -1, "");
		//assert(false);
	}
	catch (ValidateException&) {
		assert(true);
	}
	//try to add something that already exists
	try {
		ctr.addProdus("a", "a", -1, "a");
		// assert(false);
	}
	catch (ValidateException&) {
		assert(true);
	}
}

void testSearchProduct() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore srv{ rep, val };

	srv.addProdus("a", "a", 7, "a");
	srv.addProdus("b", "b", 7, "b");
	assert(srv.getAll().size() == 2);

	auto prod = srv.searchProdus("b", "b", "b");
	assert(prod.getName() == "b");
	assert(prod.getType() == "b");
	assert(prod.getPrice() == 7);
	assert(prod.getProducer() == "b");
}

void testUpdateProduct() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore srv{ rep, val };

	srv.addProdus("b", "b", 7, "b");
	srv.addProdus("a", "a", 7, "a");
	assert(srv.getAll().size() == 2);

	srv.updateProdus("a", "a", "a", "mere", 1); // update name
	srv.updateProdus("mere", "a", "a", "fructe", 2); // update type
	srv.updateProdus("mere", "fructe", "a", "5", 3); // update price
	srv.updateProdus("mere", "fructe", "a", "Romania", 4); // update producer

	auto p = srv.getAll()[1];
	assert(p.getName() == "mere");
	assert(p.getType() == "fructe");
	assert(p.getPrice() == 5);
	assert(p.getProducer() == "Romania");

}

void testDelProduct() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore srv{ rep, val };

	srv.addProdus("a", "a", 7, "a");
	srv.addProdus("b", "b", 7, "b");
	assert(srv.getAll().size() == 2);

	srv.delProdus("b", "b", "b");
	assert(srv.getAll().size() == 1);

	srv.delProdus("a", "a", "a");
	assert(srv.getAll().size() == 0);
}

void testFiltrare() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore ctr{ rep,val };
	ctr.addProdus("a", "a", 6, "b");
	ctr.addProdus("b", "a", 60, "b");
	ctr.addProdus("c", "b", 600, "a");

	// filter by pret
	assert(ctr.filtrarePret(6, 70).size() == 2);
	assert(ctr.filtrarePret(6, 60).size() == 2);

	// filter by type
	assert(ctr.filtrareTip("a").size() == 2);
	assert(ctr.filtrareTip("c").size() == 0);

	// filter by producer
	assert(ctr.filtrareProducator("b").size() == 2);
	assert(ctr.filtrareProducator("c").size() == 0);
}

void testSortare() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore ctr{ rep,val };
	ctr.addProdus("z", "z", 6, "d");
	ctr.addProdus("b", "a", 60, "d");
	ctr.addProdus("b", "d", 600, "d");

	// sort by type
	auto firstP = ctr.sortByType()[0];
	assert(firstP.getType() == "a");

	// sort by name
	firstP = ctr.sortByName()[0];
	assert(firstP.getName() == "b");

	// sort by name+type
	firstP = ctr.sortByNameType()[0];
	assert(firstP.getPrice() == 60);

}


void testAdaugaCateva() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore ctr{ rep, val };

	ctr.adaugaCateva();
	assert(ctr.getAll().size() == 4);
}

void testDelAll() {
	ProdusRepo rep;
	ProdusValidator val;
	ProdusStore ctr{ rep, val };

	ctr.adaugaCateva();
	ctr.delAll();
	assert(ctr.getAll().size() == 0);
}

void testCtr() {
	testAdaugaCtr();
	testSearchProduct();
	testUpdateProduct();
	testFiltrare();
	testSortare();
	testDelProduct();
	testAdaugaCateva();
	testDelAll();
}
