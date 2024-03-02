#pragma once
#include"Service.h"
#include"TableModel.h"
#include<QtWidgets>
class GUI : public QWidget
{
private:
	Service& srv;
	vector<int> idDel;

	vector<int> undo; // 1 inc, 2 dec, 3 stergere
	vector<int> redo; // 1 inc, 2 dec, 3 adaugare
	vector<Student> undoDel; // studenti stersi undo
	vector<Student> redoAdd; // studenti de adaugat redo

	// tabel
	QTableView* tableView = new QTableView;
	TableModel* tableModel = new TableModel;

	// butoane
	QPushButton* btnInc = new QPushButton{ "Imbatranire" }; // creste varsta
	QPushButton* btnDec = new QPushButton{ "Intinerire" }; // scade varsta
	QPushButton* btnDel = new QPushButton{ "Sterge" }; // scade varsta
	QPushButton* btnUndo = new QPushButton{ "Undo" }; // undo
	QPushButton* btnRedo = new QPushButton{ "Redo" }; // redo

public:
	// constructor
	GUI(Service& s) : srv{ s } {}

	void buildUI(); // construieste interfata
	void connects(); // conectare butoane
	void reloadList(); // reincarca lista
};

