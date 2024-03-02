#pragma once
#include "produs.h"
#include "vector.h"
#include <string>
#include <ostream>

using std::string;
using std::ostream;

class ProdusRepo {
	Vector <Produs> all;
	/*
	the private method checks if p already exists in the repository
	*/
	bool exist(const Produs& p) const;
public:
	ProdusRepo() = default;

	//we do not allow copying of objects
	ProdusRepo(const ProdusRepo& ot) = delete;

	/*
	Save product
	Throw an exception if there is another product with the same name, type, and producer
	*/
	void store(const Produs& p);

	/*
	Delete product
	Throw an exception if there is no product with the same name, type, and producer
	*/
	void del(const string& name,const string& type,const string& producer);

	/*
	Delete all products
	*/
	void delAll();

	/*
	Update product
	Throw an exception if there is no product with the same name, type, and producer
	*/
	void update(const string& name, const string& type, const string& producer, const string& new_value, int what);

	/*
	Search a product and return it
	Throw an exception if there is no product with the same name, type, and producer
	*/
	const Produs& find(string name, string type, string producer) const;

	/*
	Return a list with all products
	*/
	const Vector<Produs>& getAll() const noexcept;

	

};

/*
Used to signal exceptional situations that may appear in the repo
*/
class ProdusRepoException {
	string msg;
public:
	ProdusRepoException(string m) :msg{ m } {}
	//functie friend (I want to use msg private member)
	friend ostream& operator<<(ostream& out, const ProdusRepoException& ex);
};

ostream& operator<<(ostream& out, const ProdusRepoException& ex);

void testsRepo();