#pragma once
#include<string>

using std::string;
class Student
{
	int id, varsta;
	string nume, facultate;
public:
	// constructor student
	Student(int i, string n, int v, string f) : id{ i }, nume{ n }, varsta{ v }, facultate{ f } {}

	// constructor de copiere
	Student(const Student &st) : id{ st.id }, nume{ st.nume }, varsta{ st.varsta }, facultate{ st.facultate } {}

	// returneaza id-ul studentului
	int getId()
	{
		return id;
	}

	// returneaza varsta studentului
	int getVarsta()
	{
		return varsta;
	}

	// returneaza numele studentului
	string getNume()
	{
		return nume;
	}

	// returneaza facultatea studentului
	string getFacultate()
	{
		return facultate;
	}

	// seteaza varsta
	void setVarsta(int val)
	{
		varsta = val;
	}
};

