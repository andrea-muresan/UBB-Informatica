#include "VectorDinamic.h"
#include "IteratorVectorDinamic.h"
#include <exception>

using namespace std;

void VectorDinamic::redim() {
	// alocam un spatiu de capacitate dubla
	TElem* eNou = new int[2 * cp];

	// copiem vechile valori in zona noua
	for (int i = 0; i < n; i++)
		eNou[i] = e[i];

	// dublam capacitatea
	cp = 2 * cp;

	// eliberam zona veche
	delete[] e;

	// vectorul indica spre noua zona
	e = eNou;
}


VectorDinamic::VectorDinamic(int cp) {
	// arunca exceptie in cazul in care capacitate e <=0
	if (cp <= 0)
		throw exception("capacitate invalida");

	// setam capacitatea
	this->cp = cp;

	// alocam spatiu de memorie vectorului
	e = new TElem[cp];

	// setam numarul de elemente
	this->n = 0;
}



VectorDinamic::~VectorDinamic() {
	// eliberam zona de memorie alocata vectorului
	delete[] e;
}



int VectorDinamic::dim() const{
	return n;
}



TElem VectorDinamic::element(int i) const{
	// arunca exceptie daca i nu e valid
	if (i >= n || i < 0)
		throw exception("index in afara listei");

	// returnam elementul de pe pozitia i
	return e[i];
}



void VectorDinamic::adaugaSfarsit(TElem e) {
	// daca s-a atins capacitatea maxima, redimensionam
	if (n == cp)
		redim();

	// adaugam elementul
	this->e[n++] =  e;
}


void VectorDinamic::adauga(int i, TElem e) {
	// arunca exceptie daca i nu e valid
	if (i > n || i < 0)
		throw exception("index in afara listei");

	// daca s-a atins capacitatea maxima, redimensionam
	if (n == cp)
		redim();

	// crestem dimensiunea
	n++;

	// mutam elementele cu o pozitie la dreapta, de la poz i incolo 
	for (int j = n - 2; j >= i; j--)
		this->e[j + 1] = this->e[j];

	// adaugam elementul in lista
	this->e[i] = e;

}


TElem VectorDinamic::modifica(int i, TElem e) {
	//arunca exceptie daca i nu e valid
	if (i >= n || i < 0)
		throw exception("index in afara listei");

	// facem o copie a elementului care va fi modificat
	TElem cpy_e = this->e[i];

	// modificam elementul
	this->e[i] = e;

	//returnam elementul dinaintea modificarii
	return cpy_e;
}


TElem VectorDinamic::sterge(int i) {
	// arunca exceptie daca i nu este valid
	if (i >= n || i < 0)
		throw exception("index in afara listei");

	// facem o copie elementul care va fi sters
	TElem cpy_e = this->e[i];

	// mutam elementele cu o pozitie la stanga, de la poz i + 1 incolo 
	for (int j = i + 1; j < n; j++)
		this->e[j - 1] = this->e[j];

	// scadem dimensiunea
	n--;

	// returnam elementul sters
	return cpy_e;
}

IteratorVectorDinamic VectorDinamic::iterator() {
	return IteratorVectorDinamic(*this);
}

int VectorDinamic::elementeUnice(int i, int j) const {
	// arunca exceptie daca i si j nu sunt valide
	if (i >= n || j >= n || i < 0 || j < 0)
		throw exception("index in afara listei");

	// daca i >= j inversam
	if (i >= j) {
		int aux = i;
		i = j;
		j = aux;
	}

	// numaram elementele valide
	int count = 0;

	// pentru fiecare element verificam de cate ori apare in lista
	for (int x = i; x <= j; x++) {
		int gasit = 0;
		for (int y = i; y <= j; y++)
			if (this->e[x] == this->e[y])
				gasit++;
		

		// daca elementul e unic il contorizam
		if (gasit == 1)
			count++;
	}

	// returnam numarul de elemente valide
	return count;
}