# Considerându-se o matrice cu n x m elemente binare (0 sau 1) sortate crescător pe linii,
# să se identifice indexul liniei care conține cele mai multe elemente de 1.
#
# De ex. în matricea
# [[0,0,0,1,1],
# [0,1,1,1,1],
# [0,0,1,1,1]]
# a doua linie conține cele mai multe elemente 1.

def index_linie_max1(mat):
    """
    Returneaza linia care contine cei mai multi de 1
    Foloseste cautarea binara pentru a afla pe ce pozitie se afla cel mai din stanga 1
    :param mat:
    :complexity - time: O(n * log m)
                - space: O(1)
    """

    if len(mat) == 0:
        return None

    st = 0
    dr = len(mat[0]) - 1

    while st <= dr:
        pivot = (st + dr) // 2
        contor = 0
        index_linie = -1
        len_mat = len(mat)
        for i in range(0, len_mat):
            if mat[i][pivot] == 1:
                contor += 1
                index_linie = i

        if contor == 1:
            return index_linie
        elif contor == 0:
            st = pivot + 1
        else:
            dr = pivot - 1

def test():
    assert index_linie_max1([[0, 0, 0, 1, 1], [0, 1, 1, 1, 1], [0, 0, 1, 1, 1]]) == 1

    assert index_linie_max1([[0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 1]]) == 2

    assert index_linie_max1([[0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0]]) is None

    assert index_linie_max1([]) is None

    print('test 10 - gata')

test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n * log m) ;
#               - spatiu: O(1)
#
# ----------------------------------------------------------------------
# def count_ones(row):
#     """Funcție care numără elementele de 1 într-o linie."""
#     count = 0
#     for num in row:
#         if num == 1:
#             count += 1
#     return count
#
#
# def find_max_ones(matrix):
#     max_ones_row_index = -1
#     max_ones_count = -1
#
#     for i, row in enumerate(matrix):
#         # Căutăm prima poziție a elementului 1 folosind căutarea binară
#         first_one_index = 0
#         left, right = 0, len(row) - 1
#         while left <= right:
#             mid = (left + right) // 2
#             if row[mid] == 1:
#                 first_one_index = mid
#                 right = mid - 1
#             else:
#                 left = mid + 1
#
#         # Numărăm elementele de 1 din prima poziție găsită până la sfârșitul liniei
#         ones_count = count_ones(row[first_one_index:])
#
#         # Actualizăm indexul liniei cu cele mai multe elemente de 1 și numărul maxim de elemente de 1 găsite
#         if ones_count > max_ones_count:
#             max_ones_count = ones_count
#             max_ones_row_index = i
#
#     return max_ones_row_index
#
#
# # Exemplu de utilizare
# matrix = [
#     [0, 0, 0, 1, 1],
#     [0, 1, 1, 1, 1],
#     [0, 0, 1, 1, 1]
# ]
#
# max_ones_row_index = find_max_ones(matrix)
# print("Linia cu cele mai multe elemente de 1 este:", max_ones_row_index + 1)
