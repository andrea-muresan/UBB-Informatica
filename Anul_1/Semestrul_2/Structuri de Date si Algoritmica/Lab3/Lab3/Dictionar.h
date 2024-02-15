#pragma once

#include <vector>

#define NULL_TVALOARE -1
typedef int TCheie;
typedef int TValoare;

class IteratorDictionar;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

//referire a clasei Nod
class Nod;

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod* PNod;

class Nod {
public:
	friend class Dictionar;
	//constructor
	Nod(TElem e, PNod urm);
	TElem element();
	PNod urmator();

private:
	TElem e;
	PNod urm;
};

class Dictionar {
	friend class IteratorDictionar;

private:
	PNod Inceput;
	int len;
public:

	// constructorul implicit al dictionarului
	Dictionar();

	// adauga o pereche (cheie, valoare) in dictionar	
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c) const;

	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c);

	//returneaza numarul de perechi (cheie, valoare) din dictionar 
	int dim() const;

	//verifica daca dictionarul e vid 
	bool vid() const;

	// se returneaza iterator pe dictionar
	IteratorDictionar iterator() const;

	// returneaza un vector cu toate valorile dictionarului
	std::vector<TValoare> colectiaValorilor() const;

	// modifică, în dicționarul curent, valorile asociate cheilor existente cu valorile asociate acelorași chei în dicționarul d, dacă acestea diferă 
	//returnează numărul de perechi modificate 
	int actualizeazaValori(Dictionar& m);

	// destructorul dictionarului	
	~Dictionar();

};


