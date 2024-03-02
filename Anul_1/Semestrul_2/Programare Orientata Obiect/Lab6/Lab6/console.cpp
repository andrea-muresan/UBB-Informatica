#include "console.h"

#include "produs.h"
#include <iostream>
#include <string>
#include <iomanip>
#include <string>

#define tablew 13
#define menuw 30

using std::cout;
using std::cin;
using std::setw;
using std::left;
using std::setfill;

/* Print the main menu*/
void menu() {
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << setfill(' ') <<std::right<< setw((menuw + strlen("meniu") - 1) / 2) << "MENIU" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << "1. Adaugare" <<"\n";
	cout << "2. Cautare" <<"\n";
	cout << "3. Modificare" <<"\n";
	cout << "4. Stergere" <<"\n";
	cout << "5. Filtrare" <<"\n";
	cout << "6. Sortare" << "\n";
	cout << "p. Printeaza" << "\n";
	cout << "x. Iesire" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";

}

/*Print the sort menu*/
void sortMenu() {

	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << setfill(' ') << std::right << setw((menuw + strlen("meniu sortare") - 1) / 2) << "MENIU SORTARE" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << "1. Sorteaza dupa nume" << "\n";
	cout << "2. Sorteaza dupa tip" << "\n";
	cout << "3. Sorteaza dupa nume si tip" << "\n";
	cout << "x. Iesire" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";

}

/*Print the update menu*/
void updateMenu() {

	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << setfill(' ') << std::right << setw((menuw + strlen("meniu modificare") - 1) / 2) << "MENIU MODIFICARE" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << "1. Modifica numele" << "\n";
	cout << "2. Modifica tipul" << "\n";
	cout << "3. Modifica pretul" << "\n";
	cout << "4. Modifica producatorul" << "\n";
	cout << "x. Iesire" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";

}

/*Print the filtartion menu*/
void filtrationMenu() {

	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << setfill(' ') << std::right << setw((menuw + strlen("meniu modificare") - 1) / 2) << "MENIU MODIFICARE" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";
	cout << "1. Filtrare dupa tip" << "\n";
	cout << "2. Filtrare dupa pret" << "\n";
	cout << "3. Filtrare dupa producator" << "\n";
	cout << "x. Iesire" << "\n";
	for (int i = 1; i <= menuw; i++) cout << '-'; cout << "\n";

}

/*Print a product*/
void ConsolUI::printProduct(Produs product) {


	cout << left << setw(tablew) << product.getName() << left << setw(tablew) << product.getType() << left
		<< setw(tablew) << product.getPrice() << left << setw(tablew) << product.getProducer() << "\n";
}

/*Print a list of products*/
void ConsolUI::tipareste(const Vector<Produs>& products) {
	
	cout <<left<< setw(tablew) << "Name" << left << setw(tablew) << "Type" << left
		<< setw(tablew) << "Pret" << left << setw(tablew) << "Producator" << "\n";
	//cout << "Produse:\n";
	for (const auto& product : products) {
		
		Produs prod(product);
		printProduct(prod);
	}
	cout << "\n";
}

/* Add a product in the list from UI*/
void ConsolUI::addUI() {
	string ty, nm, prod;
	int price;
	cout << "Nume: ";
	cin >> nm;
	cout << "Tip: ";
	cin >> ty;
	cout << "Pret: ";
	cin >> price;
	cout << "Producator: ";
	cin >> prod;

	ctr.addProdus(nm, ty, price, prod);
	cout << "Adaugat cu succes\n\n";
}

/*Delete a product from the list in UI*/
void ConsolUI::delUI() {
	string ty, nm, prod;
	cout << "Nume: ";
	cin >> nm;
	cout << "Tip: ";
	cin >> ty;
	cout << "Producator: ";
	cin >> prod;

	ctr.delProdus(nm, ty, prod);
	cout << "Sters cu succes!\n\n";
}

/*Update a product in the list*/
void ConsolUI::updateUI(int what) {
	string ty, nm, prod, new_val;
	cout << "Datele actuale ale produsului:\n";
	cout << "- nume: ";
	cin >> nm;
	cout << "- tip: ";
	cin >> ty;
	cout << "- producator: ";
	cin >> prod;

	cout << "Noua valoare pentru ";
	if (what == 1) cout << "nume: ";
	else if (what == 2) cout << "tip: ";
	else if (what == 3) cout << "pret";
	else if (what == 4) cout << "producator";
	cin >> new_val;

	ctr.updateProdus(nm, ty, prod, new_val, what);
	cout << "Modificat cu succes!\n\n";
}

/*Execution of sort menu*/
void ConsolUI::sortStart() {
	while (true) {
		sortMenu();
		cout << "Comanda: ";
		char cmd;
		cin >> cmd;
		cout << "\n";

		switch (cmd) {
		case '1':
			tipareste(ctr.sortByName());
			break;
		case '2':
			tipareste(ctr.sortByType());
			break;
		case '3':
			tipareste(ctr.sortByNameType());
			break;
		case 'x':
			return;
		default:
			cout << "Comanda invalida\n";
		}
		cout << "\n";
	}
}

/*Execution of update menu*/
void ConsolUI::updateStart() {
	while (true) {
		updateMenu();
		cout << "Comanda: ";
		char cmd;
		cin >> cmd;
		cout << "\n";

		switch (cmd) {
		case '1':
			updateUI(1);
			break;
		case '2':
			updateUI(2);
			break;
		case '3':
			updateUI(3);
			break;
		case '4':
			updateUI(4);
			break;
		case 'x':
			return;
		default:
			cout << "Comanda invalida\n";
		}
		cout << "\n";
	}
}

/*Execution of filtration menu*/
void ConsolUI::filtrationStart() {
	while (true) {
		filtrationMenu();
		cout << "Comanda: ";
		char cmd;
		cin >> cmd;
		cout << "\n";

		switch (cmd) {
		case '1': { // filtrare dupa tip
			string type;
			cout << "Tip: ";
			cin >> type;
			tipareste(ctr.filtrareTip(type));
			break; 
		}
		case '2': // filtrare dupa pret
			int pretMin, pretMax;
			cout << "Pret minim: ";
			cin >> pretMin;
			cout << "Pret maxim: ";
			cin >> pretMax;
			tipareste(ctr.filtrarePret(pretMin, pretMax));
			break;
		case '3': { // filtrare dupa producator
			string prod;
			cout << "Producator: ";
			cin >> prod;
			tipareste(ctr.filtrareProducator(prod));
			break; 
		}
		case 'x':
			return;
		default:
			cout << "Comanda invalida\n";
		}
		cout << "\n";
	}
}

/*Search and print a product from the list*/
void ConsolUI::searchUI() {
	string ty, nm, prod;
	cout << "Nume: ";
	cin >> nm;
	cout << "Tip: ";
	cin >> ty;
	cout << "Producator: ";
	cin >> prod;

	printProduct(ctr.searchProdus(nm, ty, prod));
}

void ConsolUI::start() {
	// User interface
	while (true) {
		menu();
		cout << "Comanda: ";
		char cmd;
		cin >> cmd;
		cout << "\n";

		try {
			switch (cmd) {
			case '1':
				addUI();
				break;
			case '2':
				searchUI();
				break;
			case '3':
				updateStart();
				break;
			case '4':
				delUI();
				break;
			case '5':
				filtrationStart();
				break;
			case '6': 
				sortStart();
				break;
			case 'p':
				tipareste(ctr.getAll());
				break;
			case 'x':
				cout << "Pe data viitoare! ";
				ctr.delAll();
				return;
			default:
				cout << "Comanda invalida\n";
			}
		}
		catch (const ProdusRepoException& ex) {
			cout << ex << '\n';
		}
		catch (const ValidateException& ex) {
			cout << ex << '\n';
		}
	}
}