#pragma once
#include "VectorDinamic.h"

class IteratorVectorDinamic {

	friend class VectorDinamic;
private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container

	IteratorVectorDinamic(const VectorDinamic&);

	//contine o referinta catre containerul pe care il itereaza
	const VectorDinamic& v;

	/* aici e reprezentarea specifica a iteratorului */
	int curent; // pointer spre un element din vector
	 
public:

		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element() const;

};