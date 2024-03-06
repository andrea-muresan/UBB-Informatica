# Să se determine distanța Euclideană între două locații identificate prin perechi de numere.
# De ex. distanța între (1,5) și (4,1) este 5.0

from math import sqrt


def distanta(punct1:tuple, punct2:tuple):
    """
    Returneaza distanta euclidiana dintre doua puncte dupa formula
                √( (x1 - x2)^2 * (y1-y2)^2 ),
    unde x1, x2, y1, y2 sunt coordonatele punctelor: punct1(x1, y1) , punct2(x2, y2)
    :complexity - time:  O(1)
                - space: O(1)
    """
    return sqrt((punct1[0] - punct2[0])**2 + (punct1[1] - punct2[1])**2)


def test():
    assert distanta((1, 5), (4, 1)) == 5.0

    assert distanta((2, 3), (2, 7)) == 4.0

    print("test 2 - gata")

test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(1) ;
#               - spatiu: O(1)
#
# ----------------------------------------------------------------------
# import math
#
# def distanta_euclidiana(punct1, punct2):
#     x1, y1 = punct1
#     x2, y2 = punct2
#     distanta = math.sqrt((x2 - x1)**2 + (y2 - y1)**2)
#     return distanta
#
# # Testăm funcția
# punct1 = (1, 5)
# punct2 = (4, 1)
# print("Distanța euclidiană între", punct1, "și", punct2, "este:", distanta_euclidiana(punct1, punct2))