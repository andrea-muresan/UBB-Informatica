# Pentru un șir cu n elemente care conține valori din mulțimea {1, 2, ..., n - 1} astfel încât
# o singură valoare se repetă de două ori, să se identifice acea valoare care se repetă.
# De ex. în șirul [1,2,3,4,2] valoarea 2 apare de două ori.

def duplicat(arr: list):
    """
    Returneaza numarul care apare de doua ori intr-o lista = suma tuturor elementelor - gauss(n-1)
    :param arr: lista de numere
    :complexity - time: O(n)
                - space: O(1)
    """
    suma_lista = sum(arr)
    lungime = len(arr)
    suma_gauss = (lungime * (lungime - 1)) // 2
    return suma_lista - suma_gauss


def test():
    assert duplicat([1, 2, 3, 4, 2]) == 2

    assert duplicat([1, 4, 5, 1, 3, 2]) == 1

    print('test 5 - gata')

test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n) ;
#               - spatiu: O(1)
#
# ----------------------------------------------------------------------
# def valoare_repetata(lista):
#     n = len(lista)
#     suma_asteptata = (n - 1) * n // 2  # Suma tuturor numerelor din mulțimea {1, 2, ..., n - 1}
#     suma_reala = sum(lista)            # Suma elementelor din lista dată
#     valoare_repetata = suma_reala - suma_asteptata
#     return valoare_repetata
#
# # Testăm funcția
# lista = [1, 2, 3, 4, 2]
# print("Valoarea care se repetă de două ori în șirul", lista, "este:", valoare_repetata(lista))
