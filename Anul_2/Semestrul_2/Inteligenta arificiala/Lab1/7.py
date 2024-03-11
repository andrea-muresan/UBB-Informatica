# Să se determine al k-lea cel mai mare element al unui șir de numere cu n elemente (k < n).
# De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7.


def k_element(lst, k):
    """
    Returneaza al k-lea cel mai mare element dintr-un sir de numere
    :param lst: lista de numere
    :param k: pozitia elementul cerut
    :complexity - time: O(n * log n)
                - space: O(1)
    """
    lst.sort(reverse=True)
    return lst[k - 1]


def test():
    # 1
    lst = [1, 0, 3, 5, -3, 2, 7]
    assert k_element(lst, 3) == 3

    # 2
    lst = [7]
    assert k_element(lst, 1) == 7

    # 3
    lst = [7, 4, 6, 3, 9, 1]
    assert k_element(lst, 2) == 7

    print('test 7 - gata')


test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n log n) ;
#               - spatiu: O(n)
#
# ----------------------------------------------------------------------
# def kth_max_element(lista, k):
#     lista_sortata = sorted(lista, reverse=True)
#     if k <= len(lista_sortata):
#         return lista_sortata[k-1]
#     else:
#         return "k este mai mare decât lungimea listei"
#
# # Testăm funcția
# lista = [7, 4, 6, 3, 9, 1]
# k = 2
# print(f"Al {k}-lea cel mai mare element din șirul {lista} este:", kth_max_element(lista, k))