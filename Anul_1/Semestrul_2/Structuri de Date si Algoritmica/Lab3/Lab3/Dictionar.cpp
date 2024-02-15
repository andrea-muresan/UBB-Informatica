#include "Dictionar.h"
#include <iostream>
#include <vector>
#include "IteratorDictionar.h"

// Teta(1)
Dictionar::Dictionar() {
	/// constructor , setam inceputul cu null
	this->Inceput = NULL;
	this->len = 0;
}


/// Teta(n)
Dictionar::~Dictionar() {
	/// trecem prim toate elementele ramase in lista pentru a le sterge din memorie
	while (this->Inceput != nullptr)
	{
		PNod p = this->Inceput;
		this->Inceput = this->Inceput->urm;
		delete p;
	}
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
TValoare Dictionar::adauga(TCheie c, TValoare v) {
	TElem e;
	e.first = c;
	e.second = v;
	PNod newp = new Nod(e, nullptr);

	if (this->Inceput == nullptr)
	{
		this->Inceput = newp;
		this->len = 1;
		return NULL_TVALOARE;
	}

	PNod p = this->Inceput;
	while (p->urmator() != nullptr)
	{
		if (c == p->element().first)
		{
			TValoare aux = p->e.second;
			p->e.second = v;
			return aux;
		}
		p = p->urmator();
	}

	if (c == p->element().first)
	{
		TValoare aux = p->e.second;
		p->e.second = v;
		return aux;
	}

	p->urm = newp;
	this->len++;

	return NULL_TVALOARE;
}


/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare Dictionar::cauta(TCheie c) const {
	/// Cautam in dictionar o cheie data
	/// returnam NULL_TVALOARE daca nu exista cheia

	PNod p = this->Inceput;
	while (p != nullptr)
	{
		if (c == p->element().first)
			return p->e.second;
		p = p->urmator();
	}

	return NULL_TVALOARE;
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
TValoare Dictionar::sterge(TCheie c) {
	/// stergem o cheie din dinctionar daca aceasta exista
	/// si ii returnam valoarea
	/// daca nu exista returnam NULL_TVALOARE
	/// + dam free la memoria stearsa

	if (this->Inceput == nullptr)
		return NULL_TVALOARE;

	PNod p = this->Inceput;
	if (p->element().first == c) {
		PNod p_aux = this->Inceput;
		TValoare aux = p->e.second;
		this->Inceput = p->urm;
		delete p_aux;
		this->len--;
		return aux;
	}

	while (p->urmator() != nullptr) {
		if (p->urmator()->element().first == c) {
			PNod p_aux = p->urm;
			TValoare aux = p->urm->e.second;
			p->urm = p->urm->urm;
			delete p_aux;
			this->len--;
			return aux;
		}

		p = p->urmator();
	}

	return NULL_TVALOARE;
}

// Teta(1)
int Dictionar::dim() const {
	// returnam variabila care tine cont de lungime
	return this->len;
}

// Teta(1)
bool Dictionar::vid() const {
	/// Verificam daca disctionarul este vid
	/// (verifica, daca inceputul listei este nullptr)
	return this->Inceput == nullptr;
}

// Teta(1)
IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}

/// caz favoranil : Teta(1)
/// caz defavorabil : Teta(n)
/// caz mediu : Teta(n)
/// overall case : O(n)
std::vector<TValoare> Dictionar::colectiaValorilor() const
{
	/*
		Functia returneaza un vector cu valorile dictionarului.
		Trecem prin toate elementele listei si esctragrem valoarea.
	*/
	std::vector<TValoare> vector_valori;
	PNod p = this->Inceput;
	while (p != nullptr)
	{
		vector_valori.push_back(p->element().second);
		p = p->urmator();
	}

	return vector_valori;
}

int Dictionar::actualizeazaValori(Dictionar& m)
{
	PNod pm = m.Inceput;
	int k = 0;
	while (pm != nullptr)
	{
		PNod pd = this->Inceput;
		while (pd != nullptr)
		{
			if (pm->element().first == pd->element().first && pm->element().second != pd->element().second)
			{
				pd->e.second = pm->e.second;
				k++;
			}
			pd = pd->urmator();
		}
		pm = pm->urmator();
	}
	return k;
}

/// Teta(1)
Nod::Nod(TElem e, PNod urm)
{
	/// constructor
	this->e = e;
	this->urm = urm;
}

// Teta(1)
TElem Nod::element()
{
	return e;
}

// Teta(1)
PNod Nod::urmator()
{
	return urm;
}
