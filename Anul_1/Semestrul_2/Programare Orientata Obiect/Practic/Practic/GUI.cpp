#include "GUI.h"

// construieste interfata
void GUI::buildUI()
{
	QVBoxLayout* lyMain = new QVBoxLayout;
	this->setLayout(lyMain);

	// tabel
	tableView->setModel(tableModel);
	lyMain->addWidget(tableView);

	lyMain->addWidget(btnInc); // buton incrementeaza varsta
	lyMain->addWidget(btnDec); // buton decrementeaza varsta
	lyMain->addWidget(btnDel); // buton stergere
	lyMain->addWidget(btnUndo); // buton stergere
	lyMain->addWidget(btnRedo); // buton stergere
}

// conecteaza butoanele
void GUI::connects()
{
	// conectare buton de incrementare
	QObject::connect(btnInc, &QPushButton::clicked, [&]() {
		srv.updateAge(1);
		undo.push_back(1);
		reloadList();
		});

	// conectare buton de dectrementare
	QObject::connect(btnDec, &QPushButton::clicked, [&]() {
		string error = srv.updateAge(-1);
		if (error != "")
			QMessageBox::warning(this, QString::fromStdString("Error"), QString::fromStdString(error));
		else undo.push_back(2);
		reloadList();
		});

	// conectare selectie tabel
	QObject::connect(tableView->selectionModel(), &QItemSelectionModel::selectionChanged, [&]() {
		auto sel = tableView->selectionModel()->selectedIndexes();
		if (!sel.isEmpty())
		{
			idDel.clear();
			for (int i = 0; i < sel.size(); i++)
			{
				int id = tableView->model()->data(tableView->model()->index(sel[i].row(), 0)).toInt();
				idDel.push_back(id);
			}
		}
		});

	// conectare buton de stergere
	QObject::connect(btnDel, &QPushButton::clicked, [&]() {
		for (auto id : idDel)
		{
			undo.push_back(3);
			undoDel.push_back(srv.getStudent(id));
			srv.deleteStudent(id);
		}
		idDel.clear();
		reloadList();
		});

	// conectare buton de Undo
	QObject::connect(btnUndo, &QPushButton::clicked, [&]() {
		if (undo.size() != 0)
		{
			if (undo.back() == 1) // undo inc
			{
				srv.updateAge(-1);
				redo.push_back(1); // inc
			}
			else if (undo.back() == 2) // undo rec
			{
				srv.updateAge(1);
				redo.push_back(2); // dec
			}
			else if (undo.back() == 3) // undo delete
			{
				srv.addStudent(undoDel.back());
				redo.push_back(3);
				redoAdd.push_back(undoDel.back());
				undoDel.pop_back();
			}

			undo.pop_back();
			reloadList();
		}
		else QMessageBox::warning(this, QString::fromStdString("Error"), QString::fromStdString("Nu se poate face undo"));

		
		});

	// conectare buton de Redo
	QObject::connect(btnRedo, &QPushButton::clicked, [&]() {
		if (redo.size() > 0)
		{
			if (redo.back() == 1) // redo inc
			{
				srv.updateAge(1);
			}
			else if (redo.back() == 2) // redo rec
			{
				srv.updateAge(-1);
			}
			else if (redo.back() == 3) // undo delete
			{
				srv.deleteStudent(redoAdd.back().getId());
				// redo.push_back(3);
				redoAdd.pop_back();
				
			}

			redo.pop_back();
			reloadList();
		}
		else QMessageBox::warning(this, QString::fromStdString("Error"), QString::fromStdString("Nu se poate face redo"));


		});
}

// reincarca lista
void GUI::reloadList()
{
	tableModel->setStudenti(srv.getAll());
}
