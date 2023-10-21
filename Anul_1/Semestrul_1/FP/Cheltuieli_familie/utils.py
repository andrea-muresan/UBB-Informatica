
from expense import *


def generate_expenses():
    """
    Generam o lista de cheltuieli
    :return: returnam lista de cheltuieli
    """
    # e1 = {"day": 7, "price": 125, "category": "haine"}
    # e2 = {"day": 21, "price": 120, "category": "mancare"}
    # e3 = {"day": 18, "price": 30, "category": "haine"}
    # e4 = {"day": 7, "price": 66, "category": "carti"}
    e1 = [7, 125, 'haine']
    e2 = [21, 120, 'mancare']
    e3 = [18, 30, 'haine']
    e4 = [7, 66, 'carti']
    expenses_list = [e1, e2, e3, e4]
    return expenses_list


def copy_list(lst):
    """
    Copiaza o lista
    :param lst: lista cere se copiaza
    :type: list (of dict)
    :return: Rerurneaza copia listei date
    :rtype: list (of dict)
    """
    cpy_list = [el for el in lst]
    return cpy_list


def add_expense_to_list(lst, day: int, price: int, category):
    """
    Adauga un element in lista
    :param lst: lista curenta
    :param day: ziua
    :param price: suma
    :param category: tipul
    :type: string
    :return: -; Se modifica lista curenta prin adaugarea elementului nou
    """
    e = create_expense(day, price, category)
    lst.append(e)


def add_to_list(lst, el):
    lst.append(el)


def keep_in_list_one_at(lst, f, attribute):
    """
    Pastreaza in lista elementele care indeplinesc o anumita cunditie
    :param lst: lista curenta
    :type: list
    :param f: conditia dupa care se pastreaza elementele, un singur atribut
    :type: function
    :param attribute: atributul functiei
    :return: Returneaza lista cu elementele care indeplinesc conditia
    :rtype: list
    """
    return [el for el in lst if f(el, attribute)]


def keep_in_list_two_at(lst, f, attribute1, attribute2):
    """
    Pastreaza in lista elementele care indeplinesc o anumita cunditie
    :param lst: lista curenta
    :param f: conditia dupa care se pastreaza elementele, doua atribute
    :type: function
    :param attribute1: atribut al functiei
    :param attribute2: atribut al functiei
    :return: Returneaza lista cu elementele care indeplinesc conditia
    :rtype: list
    """
    return [el for el in lst if f(el, attribute1, attribute2)]


def get_category_total_price(lst, category):
    """
    Gaseste suma totala pentru un anumit tip de cheltuiala
    :param lst: lista cheltuieli
    :param category: tipul care se cauta
    :type: string
    :return: Returneaza pretul total al cheltuielii de un tip
    """
    total_price = 0
    for expense in lst:
        if getCategory(expense) == category:
            total_price += getPrice(expense)
    return total_price


def get_day_max_total_price(lst):
    """
    Gaseste ziua in care suma cheltuita este maxima
    :param lst: lista cu cheltuieli
    :return: Returneaza o lista cu zilele in care cheltuiala este maxima
    """
    charact_list = [0] * 32
    for expense in lst:
        charact_list[getDay(expense)] += getPrice(expense)
    max_total_price = max(charact_list)
    new_list = [el for el in charact_list if el == max_total_price]
    return new_list


def is_in_list(lst, e):
    """
    Verifica daca mai exista o cheltuiala in lista efectuata in aceeasi zi si de acelasi tip, precumm e
    :param lst: lista de cheltuieli
    :param e: cheltuiala dupa care se cauta
    :type: dict
    :return: True - gasit / False - nu apare
    """
    for el in lst:
        if getDay(el) == getDay(e) and getCategory(el) == getCategory(e):
            return True
    return False


def modify_expense_in_list(lst, e):
    for el in lst:
        if getDay(el) == getDay(e) and getCategory(el) == getCategory(e):
            setPrice(el, getPrice(e))


def print_list(lst):
    """
    Printeaza lista
    :param lst: lista de printat
    :return: -;
    """
    if len(lst) == 0:
        print("Lista e goală.")
    else:
        for i in lst:
            print(i)


# prints
def invalid_option_message():
    print("Opțiunea este invalidă")
