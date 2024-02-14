#include <iostream>
#include <fstream>

std::ifstream in("text.txt");

int L[100], mat[100][100], n, p = -1;

void E(int k)
{
    for(int i = 0; i <n; i++)
        if (mat[k][i])
        {
            // mat[k][i] = mat[i][k] = 0;
            for (int i = 0; i < n; i++)
                mat[k][i] = mat[i][k] = 0;

            E(i);
        }
    L[++p] = k;
}
int main()
{
    int m;
    in >> n >> m;
    for (int i = 0; i < m; i++)
    {
        int x, y;
        in >> x >> y;
        mat[x][y] = mat[y][x] = 1;
    }

    E(0);
    std::cout << 0 << ' ';
    for (int i = 0; i <= p; i++)
        std::cout << L[i] << ' ';

}
