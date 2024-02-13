#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

struct muchie {
    int nod1, nod2, cost;
};

muchie v[400005], ans[400005];

// relatie de ordine
bool ord(muchie a, muchie b) {
    if (a.cost < b.cost)
        return true;
    if (a.cost < b.cost && a.nod1 < b.nod1)
        return true;
    return false;
}

int sef[200005] = { 0 };

int eSef(int x)
{
    if (sef[x] == x)
        return x;
    return sef[x] = eSef(sef[x]);
}

void uneste(int x, int y)
{
    sef[eSef(x)] = eSef(y);
}

// citirea din fisier
void citire(int& n, int& m, char* argv1)
{
    ifstream in(argv1);
    in >> n >> m;
    for (int i = 1; i <= m; i++)
        in >> v[i].nod1 >> v[i].nod2 >> v[i].cost;
    
    in.close();
}

// afisarea in fisier
void afisare(int n, int costmin, char* argv2)
{
    ofstream out(argv2);
    out << costmin << '\n' << n - 1 << '\n';
    for (int i = 1; i <= n - 1; i++)
        out << ans[i].nod1 << " " << ans[i].nod2 << '\n';

    out.close();
}

void Kruskal(int n, int m, int &costmin)
{
    // sortam tabloul de muchii v dupa ponderi
    sort(v + 1, v + m + 1, ord);

    // initializam reprezentantii
    for (int i = 1; i <= n; ++i)
        sef[i] = i;

    // determinam APM
    int rez = 0;
    costmin = 0;
    for(int i = 1; i<=m && rez<n-1; i++)
        if (eSef(v[i].nod1) != eSef(v[i].nod2)) // daca extremitatile fac parte din subarbori diferiti
        {
            costmin += v[i].cost;
            ans[++rez] = v[i];

            // reunim subarborii
            uneste(eSef(v[i].nod1), eSef(v[i].nod2));
        }
}
int main(int argc, char* argv[])
{    
    int n, m, costmin = 0;
    citire(n, m, argv[1]);

    Kruskal(n, m, costmin);
    
    afisare(n, costmin, argv[2]);
    return 0;
}