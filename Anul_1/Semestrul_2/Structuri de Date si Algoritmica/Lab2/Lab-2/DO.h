#pragma once
#define MAX 10

typedef int TCheie;
typedef int TValoare;

#define NULL_TVALOARE -111111
#define NULL_TPAIR std::pair<TCheie, TValoare>(-111111, -111111)

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

class Iterator;

typedef bool(*Relatie)(TCheie, TCheie);
typedef int(*HashFn)(TCheie);

class DO {
	friend class Iterator;
private:
	
	int m, n;
	TElem* elems;
	int* urm;
	int primLiber;
	Relatie rel;
	HashFn hashFn;

	// functia de dispersie
	int getIndex(TCheie c) const;

	// actualizare prim liber
	void actPrimLiber();

	// redimensionare
	void redim();


public:

	// constructorul implicit al dictionarului
	DO(Relatie r);


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
	// iteratorul va returna perechile in ordine dupa relatia de ordine (pe cheie)
	Iterator iterator() const;

	// destructorul dictionarului
	~DO();

	
	// returneaza valoarea care apare cel mai frecvent în dicționar.
	// Dacă mai multe valori apar cel mai frecvent, se returnează una(oricare) dintre ele.
	// Dacă dicționarul este vid, operațiea returnează NULL_TVALOARE
	TValoare ceaMaiFrecventaValoare() const;

};
