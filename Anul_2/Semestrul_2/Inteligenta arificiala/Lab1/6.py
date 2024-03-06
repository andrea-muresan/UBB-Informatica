# Pentru un șir cu n numere întregi care conține și duplicate, să se
# determine elementul majoritar (care apare de mai mult de n / 2 ori).
# De ex. 2 este elementul majoritar în șirul [2,8,7,2,2,5,2,3,1,2,2].


def aparitii(lst: list):
    """
    Returneaza elementul care apare de cel putin n / 2 ori, folosind un dictionar pentru a stoca frecventa numerelor
    :param lst: lista de numere
    :complexity - time: O(n)
                - space: O(n)
    """
    dictionary = {}
    for el in lst:
        if el in dictionary:
            dictionary[el] += 1
        else:
            dictionary[el] = 1

    for el in dictionary:
        if dictionary[el] > len(lst) / 2:
            return el
    return None


def test():
    assert aparitii([2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]) == 2

    assert aparitii([1, 3, 4, 5, 6]) is None

    assert aparitii([1, 1, 2, 3]) is None

    print('test 6 - gata')


test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n) ;
#               - spatiu: O(n)
#
# ----------------------------------------------------------------------
# def element_majoritar(lista):
#     majoritar = lista[0]  # Presupunem că primul element este majoritar
#     count = 1             # Contorizăm aparițiile elementului majoritar
#
#     for numar in lista[1:]:
#         if numar == majoritar:
#             count += 1
#         elif count == 0:
#             majoritar = numar
#             count = 1
#         else:
#             count -= 1
#
#     # Verificăm dacă elementul identificat este majoritar
#     count = 0
#     for numar in lista:
#         if numar == majoritar:
#             count += 1
#     if count > len(lista) // 2:
#         return majoritar
#     else:
#         return "Nu există element majoritar"
#
# # Testăm funcția
# lista = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
# print("Elementul majoritar în șirul", lista, "este:", element_majoritar(lista))