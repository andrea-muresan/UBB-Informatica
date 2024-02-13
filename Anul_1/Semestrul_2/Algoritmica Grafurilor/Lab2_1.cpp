#include <iostream>
#include <fstream>
#include <queue>

#define MAX 999999

using namespace std;

ifstream in("graf.txt");

typedef struct {
	int parinte, distanta;
}varf;

void citire(int mat_ad[][30], int &n) {
	// citim matricea de adiacenta
	int x, y;
	in >> n;
	while (in >> x >> y) 
		mat_ad[x][y] = 1;
		
}

void Moore(int mat_ad[][30], varf varfuri[], int start, int n) {
	varf vf;
	int i, u, v;
	queue<int> q;
	// initializam lista de varfuri
	for (i = 1; i <= n; i++) {
		vf.distanta = MAX;
		vf.parinte = -1;
		varfuri[i] = vf;
	}

	varfuri[start].distanta = 0;

	// actualizam lista de varfuri, aflam distanta minima dintre un varf si altul
	q.push(start);
	while (!q.empty()) {
		u = q.front();
		q.pop();
		for (v = 1; v <= n; v++)
			if (mat_ad[u][v] == 1)
				if (varfuri[v].distanta == MAX) {
					varfuri[v].distanta = varfuri[u].distanta + 1;
					varfuri[v].parinte = u;
					q.push(v);
				}
	}
}



void afis_mat(int mat_ad[][30], int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++)
			cout << mat_ad[i][j] << ' ';
		cout << '\n';
	}
}

void afisare(varf varfuri[], int n) {
	int i, p, d;
	for (i = 1; i <= n; i++) {
		if (varfuri[i].parinte != -1) {
			p = i;
			d = varfuri[i].distanta;
			while (d >= 0) {
				cout << p << " ";
				p = varfuri[p].parinte;
				d--;
			}
			cout << '\n';
		}
	}
}


int main()
{
	int mat_ad[30][30] = {0}, n, start;
	varf varfuri[30];

	cout << "Varful de start: ";
	cin >> start;
	
	citire(mat_ad, n);
	Moore(mat_ad, varfuri, start, n);
	afisare(varfuri, n);

	return 0;
}
