# Considerându-se o matrice cu n x m elemente întregi și o listă cu perechi formate din
# coordonatele a 2 căsuțe din matrice ((p,q) și (r,s)), să se calculeze suma elementelor
# din sub-matricile identificate de fieare pereche.
import copy


def matrice_sume_partiale(mat):
    """
    Returneaza matricea sumelor partiele ale unei matrice date
    :param mat: matricea data
    :complexity - time: O(n * m)
                - space: O(n * m)
    """
    mat2 = copy.deepcopy(mat)

    n = len(mat2)
    m = len(mat2[0])

    for i in range(1, m):
        mat2[0][i] += mat2[0][i - 1]

    for j in range(1, n):
        mat2[j][0] += mat2[j - 1][0]

    for i in range(1, n):
        for j in range(1, m):
            mat2[i][j] += mat2[i - 1][j] + mat2[i][j - 1] - mat2[i - 1][j - 1]

    return mat2


def suma_submatrice(mat, stanga_sus:tuple, dreapta_jos:tuple):
    """
    Returneaza suma unei submatrici, stiind coltul stang-sus si cel drept-jos
    (pentru o matrice de sume partiale)
    :param mat: matricea de sume partiale
    :param stanga_sus: coorronatele coltului din stanga sus
    :param dreapta_jos: coordonatele coltului din dreapta jos
    :complexity - time: O(1)
                - space: O(1)
    """

    suma_submatrice = mat[dreapta_jos[0]][dreapta_jos[1]]
    if dreapta_jos[0] > 0:
        suma_submatrice -= mat[stanga_sus[0] - 1][dreapta_jos[1]]
    if stanga_sus[1] > 0:
        suma_submatrice -= mat[dreapta_jos[0]][stanga_sus[1] - 1]
    if stanga_sus[0] > 0 and dreapta_jos[1] > 0:
        suma_submatrice += mat[stanga_sus[0] - 1][stanga_sus[1] - 1]


    return suma_submatrice

def test():
    mat = [[0, 2, 5, 4, 1],
           [4, 8, 2, 3, 7],
           [6, 3, 4, 6, 2],
           [7, 3, 1, 8, 3],
           [1, 5, 7, 9, 4]]

    mat2 = matrice_sume_partiale(mat)

    assert suma_submatrice(mat2, (1, 1), (3, 3)) == 38

    assert suma_submatrice(mat2, (2, 2), (4, 4)) == 44

    assert suma_submatrice(mat2, (2, 2), (2, 2)) == 4

    matrice_sume_partiale(mat)

    print('test 9 - gata')


test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n * m) ;
#               - spatiu: O(1)
#
# ----------------------------------------------------------------------
# def compute_prefix_sum(matrix):
#     rows = len(matrix)
#     cols = len(matrix[0])
#     prefix_sum = [[0] * cols for _ in range(rows)]
#
#     # Calcularea sumelor pentru fiecare rând
#     for i in range(rows):
#         for j in range(cols):
#             prefix_sum[i][j] = matrix[i][j]
#             if i > 0:
#                 prefix_sum[i][j] += prefix_sum[i - 1][j]
#             if j > 0:
#                 prefix_sum[i][j] += prefix_sum[i][j - 1]
#             if i > 0 and j > 0:
#                 prefix_sum[i][j] -= prefix_sum[i - 1][j - 1]
#
#     return prefix_sum
#
# def compute_submatrix_sum(prefix_sum, p, q, r, s):
#     total_sum = prefix_sum[r][s]
#     if q > 0:
#         total_sum -= prefix_sum[r][q - 1]
#     if p > 0:
#         total_sum -= prefix_sum[p - 1][s]
#     if p > 0 and q > 0:
#         total_sum += prefix_sum[p - 1][q - 1]
#     return total_sum
#
# # Exemplu de utilizare
# matrix = [
#     [1, 2, 3],
#     [4, 5, 6],
#     [7, 8, 9]
# ]
#
# # Calcularea sumei pentru fiecare submatrice
# prefix_sum = compute_prefix_sum(matrix)
# print("Suma pentru submatricea (0,0) - (2,2):", compute_submatrix_sum(prefix_sum, 0, 0, 2, 2))
# print("Suma pentru submatricea (1,1) - (2,2):", compute_submatrix_sum(prefix_sum, 1, 1, 2, 2))
# print("Suma pentru submatricea (0,0) - (1,1):", compute_submatrix_sum(prefix_sum, 0, 0, 1, 1))
