
from domain.entities import Imobil
class ImobilService:

    def __init__(self, repo, val):
        self.__repo = repo
        self.__val = val

    def get_media_vanzare(self):
        """
        Calculeaza media preturilor imobilelor de vanzare
        :return: media (int)
        """
        lst_vanzare = self.__repo.get_all_vanzare()

        if len(lst_vanzare) == 0:
            print("Nu exista nimic in lista de vanzare")
        else:
            nr = len(lst_vanzare)
            suma = 0
            for el in lst_vanzare:
                suma += int(el.get_pret())

            return int(suma / nr)


    def get_media_inchiriere(self):
        """
        Calculeaza media preturilor imobilelor de inchiriere
        :return: media (int)
        """
        lst_inchiriere = self.__repo.get_all_inchiriere()

        if len(lst_inchiriere) == 0:
            print("Nu exista nimic in lista de inchiriere")
        else:
            nr = len(lst_inchiriere)
            suma = 0
            for el in lst_inchiriere:
                suma += int(el.get_pret())

            return int(suma / nr)

    def search_imobil_by_id(self, id):
        """
        Cauta un imobil dupa id-ul sau
        :param id: id-ul imobilului
        :return: imobilul cu id_ul cerut
        """
        self.__val.validate_id(id)

        im = self.__repo.search_imobil_by_id(id)

        return im

    def comision(self, tip_oferta, pret):
        """
        Calculeaza comisionul
        :param tip_oferta: tipul ofertei
        :param pret: pretul
        :return: comisionul (int)
        """
        if tip_oferta == "vanzare":
            comision = int(2 * pret / 100)
        elif tip_oferta == "inchiriere":
            comision = int(pret / 2)

        return comision



