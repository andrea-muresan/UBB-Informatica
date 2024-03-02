#include "GUI.h"

void GUI::buildUI()
{
	QHBoxLayout* lyMain = new QHBoxLayout;
	this->setLayout(lyMain);

	// partea din stanga
	QWidget* left = new QWidget;
	QVBoxLayout* lyLeft = new QVBoxLayout;
	left->setLayout(lyLeft);

	lyLeft->addWidget(btnSortAn);
	lyLeft->addWidget(btnSortModel);
	lyLeft->addWidget(btnNotSort);

	lyLeft->addWidget(edit);
	lyLeft->addWidget(btnSerie);

	lyMain->addWidget(left);

	// partea din dreapta
	QWidget* right = new QWidget;
	QVBoxLayout* lyRight = new QVBoxLayout;
	right->setLayout(lyRight);

	lyRight->addWidget(deviceList);

	lyMain->addWidget(right);
}


void GUI::connect()
{
	// buton sortare an
	QObject::connect(btnSortAn, &QPushButton::clicked, [&]() {
		reloadList(srv.sortAn());
		});

	// buton sortare model
	QObject::connect(btnSortModel, &QPushButton::clicked, [&]() {
		reloadList(srv.sortModel());
		});

	// buton nesortare
	QObject::connect(btnNotSort, &QPushButton::clicked, [&]() {
		reloadList(srv.getAll());
		});

	// buton serie
	QObject::connect(btnSerie, &QPushButton::clicked, [&]() {
		int nr = edit->text().toInt();

		edit->clear();

		edit->setText(QString::fromStdString(srv.getAll()[nr].getSerie()));
		});
	
}

// actualizeaza lista
void GUI::reloadList(vector<Device> devices)
{
	deviceList->clear();
	deviceList->setAlternatingRowColors(7);
	for (auto& d : devices)
	{

		deviceList->addItem("- Model: " + QString::fromStdString(d.getModel()) + ", Pret: " + QString::number(d.getPret()));
		
	}
}

