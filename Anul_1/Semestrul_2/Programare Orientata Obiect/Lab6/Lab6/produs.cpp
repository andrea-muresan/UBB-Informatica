#include "produs.h"

bool cmpName(const Produs& p1, const Produs& p2) {
	return p1.getName() < p2.getName();
}

bool cmpType(const Produs& p1, const Produs& p2) {
	return p1.getType() < p2.getType();
}