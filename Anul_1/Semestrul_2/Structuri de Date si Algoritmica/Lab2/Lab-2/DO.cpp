#include "Iterator.h"
#include "DO.h"
#include <iostream>

#include <exception>
using namespace std;


//functia care da <hashCode>-ul unui element
int defaultHash(TCheie c) {
	return abs(c);
}

int DO::getIndex(TCheie c) const
{
	return hashFn(c) % this->m;
}

void DO::actPrimLiber()
{
	for (int i = 0; i < this->m; i++)
		if (this->elems[i] == NULL_TPAIR)
		{
			this->primLiber = i;
			return;
		}
	this->primLiber = -1;
}

void DO::redim()
{
	// copiem tabla veche de dispersie
	int cpyM = this->m;
	TElem* cpyElems = this->elems;
	int* cpyUrm = this->urm;

	// update
	this->m *= 2;
	this->n = 0;
	this->primLiber = 0;
	this->elems = new TElem[this->m];
	this->urm = new int[this->m];

	// Initializam vectorii
	for (int i = 0; i < this->m; i++) {
		this->elems[i] = NULL_TPAIR;
		this->urm[i] = -1;
	}

	// Adaugam elementele din vechea tabela
	for (int i = 0; i < cpyM; i++)
		if (cpyElems[i] != NULL_TPAIR)
			this->adauga(cpyElems[i].first, cpyElems[i].second);

	delete[] cpyElems;
	delete[] cpyUrm;
}

DO::DO(Relatie r) {
	this->m = MAX;
	this->elems = new TElem[MAX];
	this->urm = new int[MAX];
	this->rel = r;
	this->hashFn = defaultHash;

	// Initializam vectorii
	for (int i = 0; i < m; i++)
	{
		elems[i] = NULL_TPAIR;
		urm[i] = -1;
	}

	this->primLiber = 0;
	this->n = 0;
}

//adauga o pereche (cheie, valoare) in dictionar
//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
//daca nu exista cheia, adauga perechea si returneaza null
TValoare DO::adauga(TCheie c, TValoare v) {
	
	if (this->m == this->n)
		this->redim();

	int index = getIndex(c);

	// pozitia indexului este libera
	if (this->elems[index] == NULL_TPAIR)
	{
		this->elems[index].first = c;
		this->elems[index].second = v;
		this->urm[index] = -1;

		// actualizam prima pozitie libera daca e cazul
		if (index == primLiber)
			this->actPrimLiber();

		this->n++;
		return NULL_TVALOARE;
	}

	// primul nod din lista este cel cautat
	if (this->elems[index].first == c)
	{
		TValoare cpyVal = this->elems[index].second;
		this->elems[index].second = v;

		return cpyVal;
	}

	int currentIndex = index;

	// parcurgem lista in cautarea elementului cerut
	while (this->urm[currentIndex] != -1 && this->elems[currentIndex].first != c)
		currentIndex = this->urm[currentIndex];

	// daca nu l-am gasit
	if (this->urm[currentIndex] == -1 && this->elems[currentIndex].first != c)
	{


		currentIndex = index;

		// parcurgem lista in cautarea pozitiei noului element
		while (this->urm[currentIndex] != -1 && rel(this->elems[currentIndex].first, c))
			currentIndex = this->urm[currentIndex];

		
		// mutam nodul current pe prima poz libera
		this->elems[this->primLiber].first = this->elems[currentIndex].first;
		this->elems[this->primLiber].second = this->elems[currentIndex].second;
		this->urm[this->primLiber] = this->urm[currentIndex];

		// actualizam nodul current
		this->elems[currentIndex].first = c;
		this->elems[currentIndex].second = v;
		this->urm[currentIndex] = primLiber;
		

		this->actPrimLiber();
		this->n++;
		return NULL_TVALOARE;

	}
	else // daca am gasit un element cu aceasi cheie
	{
		TValoare cpyValue = this->elems[currentIndex].second;
		this->elems[currentIndex].second = v;

		return cpyValue;
	}

	return NULL_TVALOARE;
}

//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) const {
	int index = getIndex(c);
	// daca nu exista nicio lista pornind de la index
	if (this->elems[index] == NULL_TPAIR)
		return NULL_TVALOARE;

	// daca elementul cautat e primul nod in lista
	if (this->elems[index].first == c)
		return this->elems[index].second;

	// parcurgem lista in cautarea elementului
	while (this->urm[index] != -1 && this->elems[index].first != c)
		index = this->urm[index];

	// daca nu am gasit elementul
	if (this->urm[index] == -1 && this->elems[index].first != c)
		return NULL_TVALOARE;
	else // daca am gasit elementul
		return this->elems[index].second;
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) {

	int index = this->getIndex(c);
	int j = -1; // precedentul lui i
	int k = 0;
	bool gata;

	// parcurgem tabela pentru a verifica daca pozitia i are vreun anterior
	while (k < this->n && j == -1)
	{
		if (this->urm[k] == index)
			j = k;
		else k++;
	}
	// localizam cheia care trebuie stearsa, actualizand și precedentul
	while (index != -1 && this->elems[index].first != c)
	{
		j = index;
		index = this->urm[index];
	}

	//verificam existenta cheii
	if (index == -1)
		return NULL_TVALOARE;
	else
	{	
		// facem o copie a valorii
		int cpyVal = this->elems[index].second;
		this->n--;
		gata = false;

		// cautam, in inlantuirea incepand de pe pozitia i o prima alta cheie care se disperseaza in i(pozitia de sters)
		do {
			int p = this->urm[index];
			int pp = index;
			// cat timp mai avem chei in inlantuire si nu am gasit o cheie care se disperseaza pe pozitia i,
			// avansam in inlanțuire, urmand legaturile, cu pozitia curenta si cu cea anterioara ei
			while (p != -1 && this->getIndex(this->elems[p].first) != index)
			{
				pp = p;
				p = this->urm[p];
			}

			if (p == -1) // nu exista o cheie care se disperseaza in pozitia i (pozitia de stergere)
				gata = true;
			else
			{
				this->elems[index] = this->elems[p]; // aducem cheia de pe pozitia p pe pozitia i
				j = pp; //pozitia anterioara noii pozitii de sters devine pp
				index = p; // iar noua pozitie de sters devine p
			}
		} while (!gata);
		// nemaifiind chei care se disperseaza pe pozitia de sters, stergem aceasta pozitie din inlantuire
		
		if (j != -1) // refacem inlantuirea daca e cazul
			this->urm[j] = this->urm[index];
	
		this->elems[index] = NULL_TPAIR;
		this->urm[index] = -1;

		this->actPrimLiber();
		return cpyVal;
	}
	return NULL_TVALOARE;
}

//returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const {
	return this->n;
}

//verifica daca dictionarul e vid
bool DO::vid() const {
	return this->n == 0;
}

Iterator DO::iterator() const {
	return  Iterator(*this);
}

DO::~DO() {
	delete[] this->elems;
	delete[] this->urm;
}

TValoare DO::ceaMaiFrecventaValoare() const
{
	// 
	// Complexitate: Theta(m^2)
	// pre : dictOrd - dictionar ordonat
	// post: valoarea returnata - TVALOARE
	// Subprogram ceaMaiFrecventaValoare(dictOrd)
	//		Daca dictOrd.n = 0 atunci
	//			ceaMaiFrecventaValoare <- NULL_TVALOARE
	//		Sfarsit daca
	//		
	//		valMax <- NULL_TVALOARE
	//		maxi <- 0
	//		Pentru i <- 0, m - 2 executa
	//			Daca dict.elems[i].second != NULL_TVALOARE atunci
	//				count <- 1
	//				Pentru j<-i+1, m - 1 executa
	//					Daca dict.elems[i].second == dict.elems[j].second atunci
	//						count <- count + 1
	//					Sfarsit daca
	//				Sfarsit pentru
	//				Daca count > maxi atunci
	//					maxi <- count
	//					valMax <- dict.elems[i].second
	//				Sfarsit daca
	//			Sfarsit daca
	//		Sfarsit pentru
	//		
	//		ceaMaiFrecventaValoare <- valMax
	// Sfarsit subalgoritm
	//

	if (this->n == 0)
		return NULL_TVALOARE;


	TValoare valMax = NULL_TVALOARE;
	int maxi = 0;
	for (int i = 0; i < this->m - 1; i++)
	{
		if (this->elems[i].second != NULL_TVALOARE)
		{
			int count = 1;
			for (int j = i + 1; j < this->m; j++)
			{
				if (this->elems[i].second == this->elems[j].second)
					count++;
			}
			
			if (count > maxi)
			{
				maxi = count;
				valMax = this->elems[i].second;
			}
		}
	}

	return valMax;
}
