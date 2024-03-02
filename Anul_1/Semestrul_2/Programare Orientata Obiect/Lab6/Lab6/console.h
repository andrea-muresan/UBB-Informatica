#pragma once

#include "produsService.h"
#include "produs.h"
#include <string>


class ConsolUI {
	ProdusStore& ctr;
	/*
	Read the data from the keyboard and add a product
	throw exception if: it cannot be saved, it is not valid
	*/
	void addUI();

	/*
	Read the data from the keyboard and delete a product
	throw exception if: it cannot be deleted, it is not valid
	*/
	void delUI();

	/*
	Read the data from the keyboard and update a product
	throw exception if: it cannot be updated, it is not valid
	*/
	void updateUI(int what);

	/*
	Read the data from the keyboard and search for a product
	throw exception if: it does not exist
	*/
	void searchUI();

	/*
	Print a product
	*/
	void printProduct(Produs product);

	/*
	Print a list of products
	*/
	void tipareste(const Vector<Produs>& pets);


public:
	ConsolUI(ProdusStore& ctr) noexcept :ctr{ ctr }{
	}
	// do not allow copying of objects
	ConsolUI(const ConsolUI& ot) = delete;


	void start();
	void sortStart();
	void updateStart();
	void filtrationStart();

};

