#pragma once
#include<string>
#include<QAbstractTableModel>
#include<QtWidgets>
#include<QBrush>

#include"Student.h"

using std::vector;
class TableModel : public QAbstractTableModel
{
	vector<Student> all;
public:
	TableModel() {};

	// numar de linii
	int rowCount(const QModelIndex& parent = QModelIndex()) const override {
		return all.size();
	}

	// numar de coloane
	int columnCount(const QModelIndex& parent = QModelIndex()) const override {
		return 4;
	}

	QVariant data(const QModelIndex& index, int role) const override {
		Student s = all[index.row()];
		// completeaza campuri
		if (role == Qt::DisplayRole)
		{
			if (index.column() == 0)
				return QString::number(s.getId());
			if (index.column() == 1)
				return QString::fromStdString(s.getNume());
			if (index.column() == 2)
				return QString::number(s.getVarsta());
			if (index.column() == 3)
				return QString::fromStdString(s.getFacultate());
		}
		// colorare
		if (role == Qt::BackgroundRole)
		{
			if (s.getFacultate() == "mate")
				return QBrush{ Qt::yellow };
			if (s.getFacultate() == "info")
				return QBrush{ Qt::darkMagenta };
			if (s.getFacultate() == "mate-info")
				return QBrush{ Qt::blue };
			if (s.getFacultate() == "ai")
				return QBrush{ Qt::gray };
		}

		return QVariant{};
	}

	// seteaza tabelul
	void setStudenti(vector<Student> st)
	{
		all = st;
		QModelIndex topLeft = createIndex(0, 0);
		QModelIndex buttonRight = createIndex(rowCount(), columnCount());

		emit dataChanged(topLeft, buttonRight);
		emit layoutChanged();
	}
};

