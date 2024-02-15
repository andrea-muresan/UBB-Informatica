#include "IteratorVectorDinamic.h"
#include "VectorDinamic.h"
#include <exception>


IteratorVectorDinamic::IteratorVectorDinamic(const VectorDinamic& _v) :

		v(_v) {
	// seteaza iteratorul pe prima pozitie din vector
	curent = 0;
}



void IteratorVectorDinamic::prim() {
	// seteaza iteratorul pe prima pozitie din vector
	curent = 0;
}



bool IteratorVectorDinamic::valid() const{
	return curent < v.dim();
}



TElem IteratorVectorDinamic::element() const{
	// arunca exceptie daca iteratorul nu e valid
	if (curent >= v.dim())
		throw std::exception("index in afara listei");

	// elementul curent
	return v.element(curent);
}



void IteratorVectorDinamic::urmator() {
	// arunca exceptie daca iteratorul nu e valid
	if (curent >= v.dim())
		throw std::exception("index in afara listei");

	// trecem la urmatorul element
	curent++;
}

