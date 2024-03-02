#include <stdio.h>

void meniu() 
{
    /*
    Functia afiseaza meniul aplicatiei:
    - Optiunea 1 - genereaza numerel prime mai mici decat un numar dat
    - Optiunea 2 - genereaza primele n numere prime
    - Optiunea 0 - inchiderea aplicatiei
    */
    printf("__________________________\n");
    printf("MENIU\n");
    printf("__________________________\n");
    printf("1 - numere prime mai mici\n");
    printf("2 - primele n numere prime\n");
    printf("0 - iesire\n");
    printf("__________________________\n");
}

bool e_prim(int n)
{
    /*
    Verifica daca un numar - n este prim
    : param n: numarul dat
    : type n: int
    : return: true - numar prim | false - numar neprim
    */
    if (n < 2)
        return false;
    if (n > 2 && n % 2 == 0)
        return false;
    for (int i = 3; i * i <= n; i++)
        if (n % i == 0)
            return false;
    return true;
}

void prime_mai_mici(int n)
{
    /*
    Afiseaza toate numerele prime <= cu un numar dat - n.
    Afiseaza un mesaj daca nu sunt numere de afisat.
    : param n: numarul dat
    : type n: int
    */
    if (n > 1) {
        printf("2");
        for (int i = 3; i <= n; i += 2)
            if (e_prim(i) == true)
                printf(" %d", i);
    }
    else printf("Nu exista");
        
    printf("\n\n");
}

void primele_prime(int n)
{
    /*
    Functia afiseaza primele n numere prime.
    Afiseaza un mesaj daca nu s-au gasit numere.
    : param n - numarul de numere prime care trebuie afisat
    : type n - int
    */
    if (n < 1)
        printf("Nu avem ce afisa");
    else {
        printf("2 ");
        int prim = 3;
        for (int i = 2; i <= n; i++) {
            while (e_prim(prim) == false)
                prim ++;
            printf("%d ", prim);
            prim ++;
        }  
    }
    printf("\n\n");
}

int main()
{
    while (true)
    {
        meniu();
        int opt;

        scanf_s("%d", &opt);
        if (opt == 0) {
            printf("Pe data viitoare :)\n");
            return false;
        }
        else if (opt == 1) {
            int n;
            printf("Introduceti un numar: ");
            scanf_s("%d", &n);

            prime_mai_mici(n);

        }
        else if (opt == 2) {
            int n;
            printf("Introduceti un numar: ");
            scanf_s("%d", &n);

            primele_prime(n);
        }
        else printf("Optiune invalida\n");


    }
    return 0;
}
