
from domain.validator import *
from service.imobil_service import ImobilService

class Console:

    def __init__(self, imobil_srv):
        self.__imobil_srv = imobil_srv


    def __print_menu(self):
        print("\nMENU")
        print("Alegeti o optiune: ")
        print("1 pentru media pretului, pentru vanzare")
        print("2 pentru media pretului, pentru inchiriere")
        print("3 pentru efectuarea unei tranzactii")
        print("x pentru a iesi din aplicatie")

    def __call_get_media_vanzare(self):
        """
        Afiseaza media preturilor imobilelor de vanzare
        """
        media = self.__imobil_srv.get_media_vanzare()
        print(media)

    def __call_get_media_inchiriere(self):
        """
        Afiseaza media preturilor imobilelor de inchiriere
        """
        media = self.__imobil_srv.get_media_inchiriere()
        print(media)

    def __call_comision(self):
        """
        Afiseaza adresa si comisionul unei tranzactii
        """
        id = input("Introduceti ID-ul: ")
        try:
            pret = int(input("Introduceti pretul: "))
            if pret < 0:
                raise ValueError("Pretul trebuie sa fie un numar natural")
        except:
            raise ValueError("Pretul trebuie sa fie un numar natural")

        im = self.__imobil_srv.search_imobil_by_id(id)
        comision = self.__imobil_srv.comision(im.get_tip_oferta(), pret)

        print("ADRESA: " + im.get_adresa() + ', ' + "COMISION: " + str(comision))



    def start(self):

        while True:
            try:
                self.__print_menu()
                opt = input("Introduceti optiunea: ")
                if opt == '1':
                    self.__call_get_media_vanzare()
                elif opt == '2':
                    self.__call_get_media_inchiriere()
                elif opt == '3':
                    self.__call_comision()
                elif opt == 'x':
                    return
            except ValueError as ve:
                print(ve)
            except InputError as er:
                print(er.get_errors())
            except FileNotReadError:
                print(er.get_errors())
            except IdNotFoundError as er:
                print(er.get_errors())
