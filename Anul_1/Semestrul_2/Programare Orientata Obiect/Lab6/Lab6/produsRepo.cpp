#include "produsRepo.h"
#include <assert.h>
#include <iostream>
#include <sstream>

using std::ostream;
using std::stringstream;

/*
Save product
Throw an exception if there is another product with the same name, type, and producer
*/
void ProdusRepo::store(const Produs& p)
{
	if (exist(p)) {
		throw ProdusRepoException("Exista deja produsul: " + p.getName() + " tip: " + p.getType() + " producator: " + p.getProducer());
	}
	all.add(p);
}

/*
Delete product
Throw an exception if there is no product with the same name, type, and producer
*/
void ProdusRepo::del(const string& name,const string& type,const string& producer)
{
	if (!exist(Produs(name, type, 7, producer))) {
		throw ProdusRepoException("Nu exista produsul: " + name + " tip: " +  type + " producator: " + producer);
	}
	for (int i = 0; i < all.size(); i++)
		if (all[i].getName() == name && all[i].getType() == type && all[i].getProducer() == producer)
			all.erase(i);
		//[name, type, producer](Produs p) { return (p.getName() == name && p.getType() == type && p.getProducer() == producer); }),
		//all.end());
}

void ProdusRepo::delAll()
{
	int l = all.size();
	for (int i = 0; i < l; i++)
			all.erase(0);
}



/*
Update product
Throw an exception if there is no product with the same name, type, and producer
*/
void ProdusRepo::update(const string& name, const string& type, const string& producer, const string& new_value, int what)
{
	if (!exist(Produs(name, type, 7, producer))) {
		throw ProdusRepoException("Nu exista produsul: " + name + " tip: " + type + " producator: " + producer);
	}
	for (Produs& p : all)
		if (p.getName() == name && p.getType() == type && p.getProducer() == producer)
			if (what == 1)
				p.setName(new_value);
			else if (what == 2)
				p.setType(new_value);
			else if (what == 3)
				p.setPrice(stoi(new_value));
			else if (what == 4)
				p.setProducer(new_value);
}

/*
Check if a product exists
*/
bool ProdusRepo::exist(const Produs& p) const {
	try {
		find(p.getName(), p.getType(), p.getProducer());
		return true;
	}
	catch (ProdusRepoException&) {
		return false;}
}

/*
Search a product
*/
const Produs& ProdusRepo::find(string name, string type, string producer) const {
	for (const auto& p : all) {
		if (p.getName() == name && p.getType() == type && p.getProducer() == producer) {
			return p;	
		}
	}
	//daca nu exista arunc o exceptie
	throw ProdusRepoException("Nu exista produsul: " + name + " tip: " + type + " producator: " + producer);}

/*
Return all saved products
*/
const Vector<Produs>& ProdusRepo::getAll() const noexcept {
	return all;
}

ostream& operator<<(ostream& out, const ProdusRepoException& ex) {
	out << ex.msg;
	return out;
}

void testAdd() {
	ProdusRepo rep;
	rep.store(Produs{ "mere","a",4, "a"});
	assert(rep.getAll().size() == 1);
	assert(rep.find("mere", "a", "a").getName() == "mere");

	rep.store(Produs{ "b","b",4,"b" });
	assert(rep.getAll().size() == 2);

	//can't add 2 products with the same name, type and producer
	try {
		rep.store(Produs{ "mere","a",4, "a"});
		//assert(false);
	}
	catch (const ProdusRepoException&) {
		assert(true);
	}
	// search for a product that does not exist
	try {
		rep.find("c", "c", "c");
		//assert(false);
	}
	catch (const ProdusRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}
}

void testDel() {
	ProdusRepo rep;
	rep.store(Produs{ "mere","a",4, "a" });
	assert(rep.getAll().size() == 1);

	rep.store(Produs{ "b","b",4,"b" });
	assert(rep.getAll().size() == 2);

	// delete a product
	rep.del("b", "b", "b");
	assert(rep.getAll().size() == 1);
	
	rep.del("mere", "a", "a");
	assert(rep.getAll().size() == 0);

	try {
		rep.del("mere", "a", "a");
	}
	catch (ProdusRepoException&){
		assert(true);
	}
}

void testUpdate() {
	ProdusRepo rep;
	rep.store(Produs{ "a","a",4, "a" });
	rep.store(Produs{ "b","b",4, "b" });

	// update a product
	rep.update("a", "a", "a", "mere", 1);
	rep.update("mere", "a", "a", "fructe", 2);
	rep.update("mere", "fructe", "a", "5", 3);
	rep.update("mere", "fructe", "a", "Romania", 4);
	auto p = rep.getAll()[0];
	assert(p.getName() == "mere");
	assert(p.getType() == "fructe");
	assert(p.getPrice() == 5);
	assert(p.getProducer() == "Romania");

	try {
		rep.update("mere", "a", "a", "b", 3);
	}
	catch (ProdusRepoException&) {
		assert(true);
	}
}

void testCauta() {
	ProdusRepo rep;
	rep.store(Produs{ "a","a",4, "a"});
	rep.store(Produs{ "b","b",4, "b"});

	auto p = rep.find("a", "a", "a");
	assert(p.getName() == "a");
	assert(p.getType() == "a");
	assert(p.getProducer() == "a");

	// throw exception if the product does not exist
	try {
		rep.find("a", "b", "c");
		//assert(false);
	}
	catch (ProdusRepoException&) {
		assert(true);
	}
}



void testsRepo() {
	testAdd();
	testDel();
	testUpdate();
	testCauta();
}