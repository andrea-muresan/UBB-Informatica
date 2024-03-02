#pragma once

#include "produs.h"
#include "produsRepo.h"
#include <string>
#include <vector>

#include <functional>
#include "validator.h"

using std::vector;
using std::function;

class ProdusStore {
	ProdusRepo& rep;
	ProdusValidator& val;

	/*
	 Functie de sortare generala
	 maiMareF - functie care compara 2 Produs, verifica daca are loc relatia mai mare
	          - poate fi orice functe (in afara clasei) care respecta signatura (returneaza bool are 2 parametrii Produs)
			  - poate fi functie lambda (care nu capteaza nimic in capture list)
	 returneaza o lista sortata dupa criteriu dat ca paramatru
	*/
	Vector<Produs> generalSort(bool (*maiMicF)(const Produs&, const Produs&));
	/*
	Functie generala de filtrare
	fct - poate fi o functie 
	fct - poate fi lambda, am folosit function<> pentru a permite si functii lambda care au ceva in capture list
	returneaza doar animalele care trec de filtru (fct(Produs)==true)
	*/
	Vector<Produs> filtreaza(function<bool(const Produs&)> fct);
public:
	ProdusStore(ProdusRepo& rep, ProdusValidator& val) noexcept:rep{ rep }, val{ val } {
	}
	// do not allow copying of ProdusStore objects
	ProdusStore(const ProdusStore& ot) = delete;

	/*
	 returns all animals in the order in which they were added
	*/
	const Vector<Produs>& getAll() noexcept {
		return rep.getAll();
	}

	/*
	Add a new product
	throw exception if it can't be saved, it is not valid
	*/
	void addProdus(const string& name, const string& type, int price, const string& producer);

	/*
	Delete a new product
	throw exception if it can't be deleted
	*/
	void delProdus(const string& name, const string& type, const string& producer);

	/*
	Delete all products
	*/
	void delAll();

	/*
	Update a product
	throw exception if the product can't be updated - doesn't exist
	*/
	void updateProdus(const string& name, const string& type, const string& producer, const string& new_value, int what);

	/*
	Search a product
	throw exception if the product does not exist
	*/
	Produs searchProdus(const string& name, const string& type, const string& producer);

	/*
	Sort by type
	*/
	Vector<Produs> sortByType();

	/*
	Sort by name
	*/
	Vector<Produs> sortByName();


	/*
	Sort by name+type
	*/
	Vector<Produs> sortByNameType();

	
	/*
	Only returns products of a certain type
	*/
	Vector<Produs> filtrareTip(const string& type);

	/*
	Return only the products with a price between the two prices
	*/
	Vector<Produs> filtrarePret(int pretMin, int pretMax);

	/*
	Only returns products of a certain producer
	*/
	Vector<Produs> filtrareProducator(const string& producer);

	// ~ProdusStore();

	void adaugaCateva();
};


void testCtr();