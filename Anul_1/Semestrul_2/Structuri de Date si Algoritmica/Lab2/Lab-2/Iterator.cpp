#include "Iterator.h"
#include "DO.h"
#include <exception>
#include <stdexcept>


using namespace std;

int partition(TElem arr[], int low, int high, Relatie r) {
	TElem pivot = arr[high];
	int i = (low - 1);

	for (int j = low; j <= high - 1; j++) {
		if (r(arr[j].first, pivot.first)) { // folosim relatia
			i++;
			swap(arr[i], arr[j]);
		}
	}

	swap(arr[i + 1], arr[high]);
	return (i + 1);
}

void quickSort(TElem arr[], int low, int high, Relatie r) {
	if (low < high) {
		int pi = partition(arr, low, high, r);

		quickSort(arr, low, pi - 1, r);
		quickSort(arr, pi + 1, high, r);
	}
}

Iterator::Iterator(const DO& d) : dict(d) {
	this->elems = new TElem[dict.n];
	this->currentIndex = 0;

	for (size_t i = 0; i < dict.m; i++)
		if (dict.elems[i] != NULL_TPAIR)
			this->elems[this->currentIndex++] = dict.elems[i];

	this->currentIndex = 0;
	quickSort(this->elems, 0, this->dict.n - 1, this->dict.rel);
}

void Iterator::prim() {
	this->currentIndex = 0;
}

void Iterator::urmator() {
	if (!this->valid())
		throw std::runtime_error{ "Iterator invalid - urmator()" };

	this->currentIndex++;
}

bool Iterator::valid() const {
	return this->currentIndex < this->dict.n;
}

TElem Iterator::element() const {
	if (!this->valid())
		throw std::runtime_error{ "Iterator invalid - element()" };

	return this->elems[this->currentIndex];
}


