#pragma once
#include<vector>
#include<fstream>
#include<algorithm>
#include"Student.h"
using std::vector;

class Repo
{
	vector<Student> all;
	string fileName;

public:
	// constructor repo
	Repo(string f) : fileName{ f } {};

	// nu se permite copiere repo
	Repo(const Repo& rep) = delete;

	// returneaza lista cu toti studentii, sortati dupa varsta
	vector<Student> getAll();

	// citire din fisier
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

