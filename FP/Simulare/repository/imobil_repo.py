
from domain.entities import Imobil
from domain.validator import *


class InMemoryImobilFile:

    def __init__(self, file_name):
        self.__file_name = file_name
        self.__imobile = []
        self.__load_from_file()

    def __load_from_file(self):
        """
        Incarca imobilele din fisier
        """

        try:
            f = open(self.__file_name, 'r')
        except:
            raise FileNotReadError()

        line = f.readline().strip()
        while line != "":
            part = line.split(",")
            im = Imobil(part[0].strip(), part[1].strip(), part[2].strip(), part[3].strip())
            self.__imobile.append(im)
            line = f.readline().strip()

        f.close()

    def get_all_vanzare(self):
        """
        Returneaza o lista cu toate imobilele de vanzare
        """
        return [el for el in self.__imobile if el.get_tip_oferta() == 'vanzare']

    def get_all_inchiriere(self):
        """
        Returneaza o lista cu toate imobilele de inchiriat
        """
        return [el for el in self.__imobile if el.get_tip_oferta() == 'inchiriere']

    def search_imobil_by_id(self, id):
        """
        Cauta un imobil dupa id-ul, daca nu il gaseste afiseaza mesaj de eroare
        :param id: is-il imobilului
        :return: imobilul
        """
        for el in self.__imobile:
            if el.get_id() == id:
                return el
        raise IdNotFoundError()

