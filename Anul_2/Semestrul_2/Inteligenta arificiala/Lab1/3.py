# Să se determine produsul scalar a doi vectori rari care conțin numere reale.
# Un vector este rar atunci când conține multe elemente nule. Vectorii pot avea oricâte dimensiuni.
# De ex. produsul scalar a 2 vectori unidimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4.

def lista_neliniara(lst):
    """
    Verifica daca o lista este neliniara
    :return: - True -> lista neliniara | False -> lista liniara
    :complexity - time: O(n)
                - space: O(1)
    """
    for elem in lst:
        if isinstance(elem, list):
            return True
    return False


def produs_scalar(arr1, arr2):
    """
    Returneaza produsul scalar a doi vectori
    :complexity - time: O(n^p), p - numarul de dimensiuni ale vectorului
                - space: O(1)
    """
    if not lista_neliniara(arr1):
        produs = 0
        for el1, el2 in zip(arr1, arr2):
            produs += el1 * el2
        return produs

    produs = 0
    for el1, el2 in zip(arr1, arr2):
        produs += produs_scalar(el1, el2)
    return produs


def test():
    assert produs_scalar([1, 0, 2, 0, 3], [1, 2, 0, 3, 1]) == 4

    assert produs_scalar([[1, 2], [1, 3]], [[2, 2], [2, 3]]) == 17

    assert produs_scalar([[[1]], [[1], [2]], [[3]]], [[[2]], [[1], [2]], [[4]]]) == 19

    print("test 3 - gata")


test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n) ;
#               - spatiu: O(1)
# !!! Merge doar pe vectori unidimensionali
# ----------------------------------------------------------------------
# def produs_scalar(vector1, vector2):
#     if len(vector1) != len(vector2):
#         return "Dimensiunile vectorilor nu coincid"
#
#     rezultat = 0
#     for i in range(len(vector1)):
#         rezultat += vector1[i] * vector2[i]
#     return rezultat
#
# # Testăm funcția
# vector1 = [1, 0, 2, 0, 3]
# vector2 = [1, 2, 0, 3, 1]
# print("Produsul scalar al celor doi vectori este:", produs_scalar(vector1, vector2))