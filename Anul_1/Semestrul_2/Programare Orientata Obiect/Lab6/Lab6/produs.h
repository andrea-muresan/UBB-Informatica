#pragma once

#include <string>
#include <iostream>

using std::string;
using std::cout;


class Produs {
	std::string name;
	std::string type;
	int price;
	std::string producer;

public:
	Produs(const string n, const string t, int p, const string prod):name{n},type{t},price{p},producer{prod}{}

	// copy const
	Produs(const Produs& pr) :name{ pr.name }, type{ pr.type }, price{ pr.price }, producer{ pr.producer } {
		//cout << "!!!!\n";
	}

	string getName() const {
		return name;
	}
	string getType() const {
		return type;
	}
	int getPrice() const noexcept {
		return price;
	}
	string getProducer() const {
		return producer;
	}

	void setName(string new_value) {
		this->name = new_value;
	}
	void setType(string new_value) {
		this->type = new_value;
	}
	void setPrice(int new_value) noexcept{
		this->price = new_value;
	}
	void setProducer(string new_value){
		this->producer = new_value;
	}

};

/*
Compare by name
: return: true if p1.name is lower than p2.tip
*/
bool cmpName(const Produs& p1, const Produs& p2);

/*
Compare by type
: return: true if p1.type is lower than p2.type
*/
bool cmpType(const Produs& p1, const Produs& p2);