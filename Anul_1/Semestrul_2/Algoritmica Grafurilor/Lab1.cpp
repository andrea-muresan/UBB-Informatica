#include <iostream>
#include <fstream>

using namespace std;

ifstream in("file.txt");

void mat_0(int mat[][100])
{
    for (int i = 0; i <= 99; i++)
        for (int j = 0; j <= 99; j++)
            mat[i][j] = 0;
}


int main()
{   
    int n, mat[100][100] = { 0 }, x, y, lst[100][100] = { 0 }, mat_in[100][100] = { 0 }, m = 0;

    /// matricea de adiacenta
    in >> n;
    while(in>>x>>y){
        mat[x][y] = mat[y][x] = 1;
        m++;
    }

    cout << "Matricea de adiacenta:\n";
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++)
            cout << mat[i][j] << ' ';
        cout << '\n';
    }
    
    //lista de adiacenta
    cout << '\n';
    for (int i = 1; i <= n; i++) { /// parcurgem matricea de adiacenta
        int k = 0;
        for (int j = 1; j <= n; j++) /// pe linia i a listei de incidenta trecem vecinii nodului i
            if (mat[i][j] == 1)
                lst[i][k++] = j;
    }

    cout << "\nLista de adiacenta:\n";  /// afisam lista de adiacenta
    for (int i = 1; i <= n; i++) {
        cout << i << ": ";
        int j = 0;
        while (lst[i][j]) {
            cout << lst[i][j] << ", ";
            j++;
        }
        cout << '\n';
    }

    
    ///matricea de incidenta
    cout << "\nMuchiile: ";

    int k = 0;
    for (int i = 1; i <= n; i++) {
        int j = 0;
        while (lst[i][j]) {
            if (lst[i][j] > i) {
                cout << "[" << i << ", " << lst[i][j] << "] "; ///aflam muchiile din lista de incidenta
                mat_in[i][k] = mat_in[lst[i][j]][k++] = 1; ///pt fiecare muchie, punem 1 in dreptul nodurilor pe care le contine
            }
            j++;
        }
    }
    cout << "\nMatricea de incidenta: \n"; /// afisam matricea de incidenta
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j < m; j++)
            cout << mat_in[i][j] << ' ';
        cout << '\n';
    }

    ///lista de adiacenta2
    cout << "\nLista de adiacenta: \n";
    mat_0(lst);
    for (int j = 0; j <= m; j++) {
        int x=0, y=0;
        for (int i = 1; i <= n; i++) // aflam nodurile fiecarei muchii
            if (mat_in[i][j]) {
                x = y;
                y = i;
        }
        k = 0;
        while (lst[x][k]) // punem nodurile muchiei in lista, unde avem loc liber
            k++;
        lst[x][k] = y;

        k = 0;
        while (lst[y][k])
            k++;
        lst[y][k] = x;
    }
      
    for (int i = 1; i <= n; i++) { ///afisam lista de adiacenta
        cout << i << ": ";
        int j = 0;
        while (lst[i][j]) {
            cout << lst[i][j] << ", ";
            j++;
        }
        cout << '\n';
    }

    /// matricea de adiacenta 2
    mat_0(mat);
    for (int i = 0; i < n; i++)  /// luam muchiile din lista de adiacenta si marcam cu unu muchia formata din ele in matricea de adiacenta
        for (int j = 0; j < n; j++)
            if (lst[i][j])
                mat[i][lst[i][j]] = mat[lst[i][j]][i] = 1;

    cout << "\nMatricea de adiacenta: \n"; /// afisam matricea de adiacenta
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++)
            cout << mat[i][j] << ' ';
        cout << '\n';
    }


    return 0;
}

