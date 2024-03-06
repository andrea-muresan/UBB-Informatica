# Considerându-se o matrice cu n x m elemente binare (0 sau 1), să se înlocuiască cu 1
# toate aparițiile elementelor egale cu 0 care sunt complet înconjurate de 1.

# De ex. matricea
# [[1,1,1,1,0,0,1,1,0,1],
# [1,0,0,1,1,0,1,1,1,1],
# [1,0,0,1,1,1,1,1,1,1],
# [1,1,1,1,0,0,1,1,0,1],
# [1,0,0,1,1,0,1,1,0,0],
# [1,1,0,1,1,0,0,1,0,1],
# [1,1,1,0,1,0,1,0,0,1],
# [1,1,1,0,1,1,1,1,1,1]]
# *devine *
# [[1,1,1,1,0,0,1,1,0,1],
# [1,1,1,1,1,0,1,1,1,1],
# [1,1,1,1,1,1,1,1,1,1],
# [1,1,1,1,1,1,1,1,0,1],
# [1,1,1,1,1,1,1,1,0,0],
# [1,1,1,1,1,1,1,1,0,1],
# [1,1,1,0,1,1,1,0,0,1],
# [1,1,1,0,1,1,1,1,1,1]]\

from queue import Queue


def matrice_de_inceput(mat):
    """
    Returneaza o copie a matricei initiale in care 0 devine -1
    :complexity - time: O(n * m)
                - space: O(n * m)
    """
    mat_rez = [[-1 if el == 0 else el for el in linie] for linie in mat]

    return mat_rez


def lee(mat, x, y):
    """
    Marcheaza cu 0-uri blocul de -1-uri care incepe la pozitia mat[x][y]
    :param mat: matricea de numere data (val: 0, 1, -1)
    :param x: linia punctului de inceput
    :param y: coloana punctului de inceput
    :complexity - time:O(n * m)
                - space: O(n * m)
    """
    queue = Queue()
    queue.put((x, y))

    while queue.empty() is False:
        poz = queue.get()
        x = poz[0]
        y = poz[1]

        mat[x][y] = 0
        if x > 0 and mat[x - 1][y] == -1:
            queue.put((x - 1, y))
        if x < len(mat) - 1 and mat[x + 1][y] == -1:
            queue.put((x + 1, y))
        if y > 0 and mat[x][y - 1] == -1:
            queue.put((x, y - 1))
        if y < len(mat[0]) - 1 and mat[x][y + 1] == -1:
            queue.put((x, y + 1))

    return mat


def elementele_neingradite(mat):
    """
    Aplica algoritmul lui Lee pentru -1 -urile care se afla pe marginile matricei
    :complexity - time: O(n * m)
                - space: O(1)
    """
    n = len(mat)
    m = len(mat[0])
    for i in range(n):
        if mat[i][0] == -1:
            lee(mat, i, 0)
        if mat[i][m - 1] == -1:
            lee(mat, i, m - 1)

    for j in range(m):
        if mat[0][j] == -1:
            lee(mat, 0, j)
        if mat[n - 1][j] == -1:
            lee(mat, n - 1, j)


def matrice_final(mat):
    """
    Inlocuieste in matrice -1 cu 1
    :complexity - time: O(n * m)
                - space: O(n * m)
    """
    n = len(mat)
    m = len(mat[0])
    for i in range(n):
        for j in range(m):
            if mat[i][j] == -1:
                mat[i][j] = 1


def inlocuieste_zerouri_incradite(mat):
    """
    Inlocuieste cu 1 toate 0-urile care sunt complet înconjurate de 1
    :param mat: matricea initiala
    :return: matricea rezultata
    :complexity - time: O(n * m)
                - space: O(n * m)
    """
    rez = matrice_de_inceput(mat)  # Inlocuieste 0 cu -1
    elementele_neingradite(rez)  # Inlocuieste -1-urile neingradite de 1 cu 0
    matrice_final(rez)  # Inlocuieste -1-urile ramase cu 1

    return rez


def test():
    assert inlocuieste_zerouri_incradite(
        [[1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
         [1, 1, 1, 1, 1, 0, 1, 1, 1, 1],
         [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
         [1, 1, 1, 1, 1, 1, 1, 1, 0, 1],
         [1, 1, 1, 1, 1, 1, 1, 1, 0, 0],
         [1, 1, 1, 1, 1, 1, 1, 1, 0, 1],
         [1, 1, 1, 0, 1, 1, 1, 0, 0, 1],
         [1, 1, 1, 0, 1, 1, 1, 1, 1, 1]]
    ) == [[1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
          [1, 1, 1, 1, 1, 0, 1, 1, 1, 1],
          [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
          [1, 1, 1, 1, 1, 1, 1, 1, 0, 1],
          [1, 1, 1, 1, 1, 1, 1, 1, 0, 0],
          [1, 1, 1, 1, 1, 1, 1, 1, 0, 1],
          [1, 1, 1, 0, 1, 1, 1, 0, 0, 1],
          [1, 1, 1, 0, 1, 1, 1, 1, 1, 1]]

    assert inlocuieste_zerouri_incradite(
        [[1, 1, 1, 1],
         [1, 0, 1, 0],
         [0, 1, 1, 0]
         ]
    ) == [[1, 1, 1, 1],
          [1, 1, 1, 0],
          [0, 1, 1, 0]]

    assert inlocuieste_zerouri_incradite([[0, 0, 0], [0, 0, 0], [0, 0, 0]]) \
           == [[0, 0, 0], [0, 0, 0], [0, 0, 0]]

    print('test 11 - gata')

test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n * m) ;
#               - spatiu: O(n * m)
#
# ----------------------------------------------------------------------
#
# from collections import deque
#
#
# def replace_surrounded_zeros(matrix):
#     if not matrix:
#         return matrix
#
#     rows, cols = len(matrix), len(matrix[0])
#     visited = set()  # Pentru a marca celulele deja vizitate
#
#     # Funcție pentru a verifica dacă un punct este valid în cadrul matricei
#     def is_valid(r, c):
#         return 0 <= r < rows and 0 <= c < cols
#
#     # Marcam toate zerourile de pe margini ca fiind deja vizitate
#     for i in range(rows):
#         for j in range(cols):
#             if (i == 0 or i == rows - 1 or j == 0 or j == cols - 1) and matrix[i][j] == 0:
#                 visited.add((i, j))
#
#     # Funcție pentru a găsi toate zerourile înconjurate de 1
#     def bfs(r, c):
#         queue = deque([(r, c)])
#         while queue:
#             cr, cc = queue.popleft()
#             for dr, dc in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
#                 nr, nc = cr + dr, cc + dc
#                 if is_valid(nr, nc) and matrix[nr][nc] == 0 and (nr, nc) not in visited:
#                     visited.add((nr, nc))
#                     queue.append((nr, nc))
#
#     # Apelăm funcția BFS pentru toate zerourile de pe margini
#     for i in range(rows):
#         for j in range(cols):
#             if (i == 0 or i == rows - 1 or j == 0 or j == cols - 1) and matrix[i][j] == 0:
#                 bfs(i, j)
#
#     # Înlocuim zerourile înconjurate de 1 cu 1
#     for i in range(rows):
#         for j in range(cols):
#             if matrix[i][j] == 0 and (i, j) not in visited:
#                 matrix[i][j] = 1
#
#     return matrix
#
#
# # Exemplu de utilizare
# matrix = [
#     [1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
#     [1, 0, 0, 1, 1, 0, 1, 1, 1, 1],
#     [1, 0, 0, 1, 1, 1, 1, 1, 1, 1],
#     [1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
#     [1, 0, 0, 1, 1, 0, 1, 1, 0, 0],
#     [1, 1, 0, 1, 1, 0, 0, 1, 0, 1],
#     [1, 1, 1, 0, 1, 0, 1, 0, 0, 1],
#     [1, 1, 1, 0, 1, 1, 1, 1, 1, 1]
# ]
#
# new_matrix = replace_surrounded_zeros(matrix)
# for row in new_matrix:
#     print(row)