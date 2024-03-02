#include "validator.h"
#include <assert.h>
#include <sstream>

void ProdusValidator::validate(const Produs& p) {
	vector<string> msgs;
	if (p.getPrice() < 0) msgs.push_back("Pret negativ!!!");
	if (p.getType().size() == 0) msgs.push_back("Tip vid!!!");
	if (p.getName().size() == 0) msgs.push_back("Nume vid!!!");
	if (p.getProducer().size() == 0) msgs.push_back("Produs vid!!!");
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}
}

ostream& operator<<(ostream& out, const ValidateException& ex) {
	for (const auto& msg : ex.msgs) {
		out << msg << " ";
	}
	return out;
}

void testValidator() {
	ProdusValidator v;
	Produs p{ "","",-1, ""};
	try {
		v.validate(p);
	}
	catch (const ValidateException& ex) {
		std::stringstream sout;
		sout << ex;
		const auto mesaj = sout.str();
		assert(mesaj.find("negativ") >= 0);
		assert(mesaj.find("vid") >= 0);
	}

}