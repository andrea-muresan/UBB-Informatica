#pragma once
#include"Repo.h"

class Service
{
	Repo& repo;

public:
	Service(Repo& repo) : repo{repo} {}

	Service(const Service& srv) = delete;

	// returneaza lista
	vector<Device> getAll();

	// citeste din fisier
	void readFile();
	
	// sortare dupa an
	vector<Device> sortAn();

	// sortare dupa model
	vector<Device> sortModel();
};

void testServive();