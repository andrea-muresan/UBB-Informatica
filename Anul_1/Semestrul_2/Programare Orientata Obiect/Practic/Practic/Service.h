#pragma once
#include"Repo.h"

class Service
{
	Repo& repo;
public:
	//constructor service
	Service(Repo& rep) : repo{ rep } {};

	// nu sepermite copierea
	Service(const Service& srv) = delete;

	// returneaza lista de studenti
	vector<Student> getAll();

	// citeste din fisier
	void loadFromFile();

	// scrie in fisier
	void writeFile();

	// modifica varsta
	string updateAge(int val);

	// sterge student dupa id
	void deleteStudent(int id);

	// returneaza studentul cu id-ul id
	Student getStudent(int id);

	// adauga student
	void addStudent(Student s);
};

void testService();