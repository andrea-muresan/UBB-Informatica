import random
from random import sample
from math import factorial, perm, comb
from itertools import permutations, combinations


class Cobinatorica:
    def __init__(self, lista):
        self.lista = lista

    def permutation(self):
        for el in self.lista:
            for p in permutations(el):
                print(p)

    def permutations_number(self):
        for el in self.lista:
            print(factorial(len(el)))

    def random_permutare(self):
        for el in self.lista:
         print(sample(el, len(el)))

    def aranjamente(self, k):
        for el in self.lista:
            print(list(permutations('word', k)))

    def aranjamente_nr(self, k):
        for el in self.lista:
            print(perm(len(el), k))

    def random_aranjament(self, k):
        for el in self.lista:
            print(sample(el, k))

    def combinari(self, k):
        for el in self.lista:
            for c in combinations(el, k):
                print(c)

    def combinari_numar(self, k):
        for el in self.lista:
            print(comb(len(el), k))

    def random_combinare(self, k):
        for el in self.lista:
            for i in sorted(sample(range(len(el)), k)):
                print(el[i], end=" ")



cls = Cobinatorica(["ceva"])
cls.random_combinare(2)
