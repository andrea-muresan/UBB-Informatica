#include <iostream>
#include <crtdbg.h>
#include <fstream>

std::ifstream in("graf.txt");

void citire(int mat_ad[][30], int& n) {
    in >> n;
    int x, y;
    while (in >> x >> y)
        mat_ad[x][y] = 1; 
    in.close();
}

void afisare(int mat_ad[][30], int n) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++)
            std::cout << mat_ad[i][j] << ' ';
        std::cout << '\n';
    }
}

void initializare(int mat_tranz[][30], int mat_ad[][30], int n) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++)
            mat_tranz[i][j] = mat_ad[i][j];
        
        mat_tranz[i][i] = 1;
    }
}

void inchidere(int mat_tranz[][30], int mat_ad[][30], int n) {
    initializare(mat_tranz, mat_ad, n);
    for (int k = 1; k <= n; k++)
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                if (mat_tranz[i][j] == 0)
                    mat_tranz[i][j] = mat_tranz[i][k] * mat_tranz[k][j];

}   

int main()
{
    int n, mat_ad[30][30] = { 0 }, mat_tranz[30][30] = { 0 };
    citire(mat_ad, n);
    inchidere(mat_tranz, mat_ad, n);
    afisare(mat_tranz, n);
    


    return 0;
}

