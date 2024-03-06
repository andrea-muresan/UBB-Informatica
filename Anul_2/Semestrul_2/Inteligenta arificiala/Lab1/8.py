# Să se genereze toate numerele (în reprezentare binară) cuprinse între 1 și n.
# De ex. dacă n = 4, numerele sunt: 1, 10, 11, 100.

from queue import Queue


def genereaza_binar(n):
    """
    Returneaza o lista cu numerele de la 1 la n in binar
    Foloseste o coada pentru a construi urmatorele numere prin adaugarea de 0 sau 1 la cel curent

    :complexity - time: O(n)
                - space: O(n)
    """
    q = Queue()
    lst = []

    q.put("1")
    while n > 0:
        n -= 1
        s1 = q.get()
        lst.append(int(s1))

        if q.qsize() < n:  # previne popularea inutila a cozii
            q.put(s1+"0")
            q.put(s1+"1")

    return lst


def test():
    assert genereaza_binar(4) == [1, 10, 11, 100]

    assert genereaza_binar(0) == []

    assert genereaza_binar(12) == [1, 10, 11, 100, 101, 110, 111, 1000, 1001, 1010, 1011, 1100]

    print('test 8 - gata')

test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n log n) ;
#               - spatiu: O(1)
#
# ----------------------------------------------------------------------
# def numere_binar(n):
#     for i in range(1, n + 1):
#         print(bin(i)[2:])
#
# # Testăm funcția
# n = 4
# print("Numerele în reprezentare binară cuprinse între 1 și", n, "sunt:")
# numere_binar(n)