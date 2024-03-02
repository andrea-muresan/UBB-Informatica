#pragma once
#include "Service.h"

#include <QtWidgets>

class GUI : public QWidget
{
	friend class Service;
private:
	Service& srv;

	// butoane
	QPushButton* btnSortAn = new QPushButton("Sortare An");
	QPushButton* btnSortModel = new QPushButton("Sortare Model");
	QPushButton* btnNotSort = new QPushButton("Nesortare");
	QPushButton* btnSerie = new QPushButton("Serie");

	// lien edit
	QLineEdit* edit = new QLineEdit;

	// tabel
	QListWidget* deviceList = new QListWidget;

public:
	GUI(Service& srv) : srv{srv}{}
	GUI(const GUI& gui) = delete;

	// construieste interfata
	void buildUI();

	// conecteaza butoanele
	void connect();
	
	// reincarca lista
	void reloadList(vector<Device> devices);
};

