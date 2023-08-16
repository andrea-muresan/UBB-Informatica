
from utils import *


def setup_expense_manager(add_predefined):
    """
    Initializeaza un obiect de tip expense manager
    :param add_predefined: indicator pentru adaugarea cheltuielilor predefinite in lista curenta de seriale
            daca add_predefined==True se incepe cu o lista populata de cheltuieli predefinite, altfel cu o lista
            goala de cheltuieli
    :type add_predefined: bool
    :return: o lista cu 2 pozitii care reprezinta expense_manager-ul, expense_manager[0] = lista curenta de cheltuieli
                    expense_manager[1] = undo_list
    :rtype: list
    """
    if add_predefined:
        crt_expense_list = generate_expenses()
    else:
        crt_expense_list = []

    undo_list = []
    return [crt_expense_list, undo_list]


# getters
def get_expense_list(expense_manager):
    return expense_manager[0]  # returneaza lista curenta de cheltuieli


def get_undo_list(expense_manager):
    return expense_manager[1]  # returneaza lista curenta de undo



# setters
def set_expense_list(expense_manager, new_expense_list):
    expense_manager[0] = new_expense_list  # lista curenta (de cheltuieli) ia valoarea noii liste


def set_undo_list(expense_manager, new_undo_list):
    expense_manager[1] = new_undo_list  # lista curenta (de undo) ia valoarea noii liste


# functii care implementeaza partea de logica a programului pentru entitatea expense_manager
def add_expense_to_manager(expense_manager, the_expense):
    """
    Adauga o cheltuiala pentru a fi manageriata
    :param expense_manager: obiect de tip expense manager
    :type expense_manager: list (len(expense_manager)=2, expense_manager[0] = lista crt de expense-uri, expense_manager[1] = lista de undo
    :param the_expense: cheltuiala care va fi adaugat
    :type the_expense: dict
    :return: -; Se modifica lista curenta de cheltuieli prin adaugarea celei noi
    """
    crt_expense_list = get_expense_list(expense_manager)  # crt_expense_list = lista curenta de cheltuieli
    undo_list = get_undo_list(expense_manager)

    if is_in_list(crt_expense_list, the_expense):  # mesaj de eroare daca exista deja o cheltuiala de acelasi tip & aceeasi zi
        print("Există deja o cheltuială în ziua respectivă, de același tip.")
    else:
        add_to_list(undo_list, copy_list(crt_expense_list))  # adaugam in lista undo lista curenta de cheltuieli

        add_to_list(get_expense_list(expense_manager), the_expense)  # adaugam noua cheltuiala in lista de cheltuieli


def modify_expense_in_manager(expense_manager, the_expense):
    """
    Modifica o cheltuiala prin adaugare ei la cea curenta
    :param expense_manager: obiect de tip expense manager
    :type expense_manager: list (len(expense_manager)=2, expense_manager[0] = lista crt de expense-uri, expense_manager[1] = lista de undo
    :param the_expense: cheltuiala care va fi adaugata la cea actuala
    :type the_expense: dict
    :return: -; Se modifica lista curenta de cheltuieli prin modificarea unei cheltuieli
    """
    crt_expense_list = get_expense_list(expense_manager)  # crt_expense_list = lista curenta de cheltuieli
    undo_list = get_undo_list(expense_manager)

    if is_in_list(crt_expense_list, the_expense) is False:  # mesaj de eroare daca nu se gaseste o cheltuiala anterioara de acelasi tip & aceeasi zi
        print("Nu e în listă.")
    else:
        add_to_list(undo_list, copy_list(crt_expense_list))  # adaugam in lista undo lista curenta de cheltuieli

        modify_expense_in_list(crt_expense_list, the_expense)  # modificam cheltuiala in lista de cheltuieli


def delete_expense_from_manager(expense_manager, future_list):
    """
    Sterge din lista anumite cheltuieli
    :param expense_manager: obiect de tip expense manager
    :type expense_manager: list (len(expense_manager)=2, expense_manager[0] = lista crt de expense-uri, expense_manager[1] = lista de undo
    :param future_list: lista care se va afla in expense_manager[0], dupa stergere
    :type: list
    :return: -;Pastreaza in lista doar lista buna
    """
    crt_expense_list = get_expense_list(expense_manager)  # crt_expense_list = lista curenta de cheltuieli
    undo_list = get_undo_list(expense_manager)

    if future_list == crt_expense_list:
        print("Nu s-a șters nimic.")
    else:
        add_to_list(undo_list, copy_list(crt_expense_list))  # adaugam in lista undo lista curenta de cheltuieli

        set_expense_list(expense_manager, future_list)  # stergem din lista curenta de cheltuilei anumite elemente



def undo(expense_manager):
    """
    Face undo la ultima operatie de adaugare sau stergere
    :param expense_manager: obiect de tip expense manager
    :type expense_manager: list (len(show_manager)=2, expense_manager[0] = lista crt de expense-uri, expense_manager[1] = lista de undo
    :return: lista curenta de expense-uri revine la starea de dinainte de ultima operatie
    """
    undo_list = get_undo_list(expense_manager)

    if len(undo_list) == 0:  # daca lista este goala => mesaj de eroare
        print("Nu se mai poate face undo. ")
    else:
        previous_list = undo_list[-1]

        set_expense_list(expense_manager, previous_list)  # lista curenta (de cheltuieli) ia valoarea listei anterioare
        set_undo_list(expense_manager, undo_list[:-1])  # stergem ultimul element din lista de undo
