
from domain.entities import Imobil

class ImobilValidator:

    def validate(self, im):
        errors = []
        if im.get_id().isdigit() is False:
            errors.append("ID-ul trebuie sa contina cifre")
        if len(im.get_adresa) < 2:
            errors.append("Numele trevbuie sa aiba cel putin 2 caractere")
        if im.get_pret().isdigit() is False:
            errors.append("Pretul trebuie sa fie un numar natural")
        if im.get_tip_oferta() != 'vanzare' or im.get_tip_oferta() != 'inchiriere':
            errors.append("Tipul ofertei poate fi vanzare sau cumparare")

        if len(errors) > 0:
            error_string = '\n'.join(errors)
            raise InputError(error_string)

    def validate_id(self, id):
        if id.isdigit() is False:
            raise InputError("ID-ul trebuie sa contina cifre")

    def validate_price(self, price):
        if price.isdigit() is False:
            raise InputError("Pretul trebuie sa fie un numar natural")


class InputError(Exception):
    """
    Returneaza mesaj pentru date de intrare gresite
    """
    def __init__(self, errors):
        self.__errors = errors

    def get_errors(self):
        return self.__errors

class FileNotReadError(Exception):
    """
    Returneaza mesaj pentru fisier invalid
    """
    def __init__(self):
        self.__errors = "Fisierul nu a putut fi citit"

    def get_errors(self):
        return self.__errors

class IdNotFoundError(Exception):
    """
    Returneaza mesaj pentru id negasit
    """
    def __init__(self):
        self.__errors = "ID-ul nu a fost gasit"

    def get_errors(self):
        return self.__errors
