
#include <iostream>
#include <fstream>

using namespace std;
ifstream in("graf.txt");

void citire(int& n, int& m, int mat[][15])
{
    in >> n >> m;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= m; j++)
            in >> mat[i][j];
}

void afis(int n, int mat[][15])
{
    for (int i = 1; i <= n; i++)
    {
        int j = 1;
        cout << i << ":";
        while (mat[i][j]) {
            cout << mat[i][j] << ' ';
            j++;
        }
            
        cout << '\n';
    }
}

void transf(int n, int m, int mat[][15])
{
    int lst[15][15] = { 0 };
    for (int i = 1; i <= m; i++)
    {
        int x, y=0;
        for (int j = 1; j <= n; j++)
        {
            if (mat[j][i] == 1)
            {
                x = y;
                y = j;

                
                int l = 1;
                while (lst[x][l])
                    l++;
                lst[x][l] = y;

                l = 1;
                while (lst[y][l])
                    l++;
                lst[y][l] = x;
            }
        }
    }

    afis(n, lst);
}

int main()
{
    int n, m, mat[15][15] = { 0 };
    citire(n, m, mat);

    transf(n, m, mat);

    

    return 0;
}