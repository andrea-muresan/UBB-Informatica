
class Imobil:

    def __init__(self, id, adresa, pret, tip_oferta):
        self.__id = id
        self.__adresa = adresa
        self.__pret = pret
        self.__tip_oferta = tip_oferta

    def get_id(self):
        return self.__id

    def get_adresa(self):
        return self.__adresa

    def get_pret(self):
        return self.__pret

    def get_tip_oferta(self):
        return self.__tip_oferta
