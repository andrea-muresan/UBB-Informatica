
from expense_manager import *
from utils import *
from expense import *
from termcolor import colored


# print
def print_run():
    print("Cum dorești să lucrezi?")
    print("1. Meniu")
    print("2. Comenzi")


def print_main_menu():
    print("MENIU")
    print("1. ADĂUGARE")
    print("2. ȘTERGERE")
    print("3. CĂUTĂRI")
    print("4. RAPOARTE")
    print("5. FILTRARE")
    print("6. UNDO")
    print("P. Printare listă curentă")
    print("7. IEȘIRE")


def print_add_menu():
    print("MENIU ADĂUGARE")
    print("1. Adaugă o cheltuială")
    print("2. Actualizează o cheltuială")


def print_delete_menu():
    print("MENIU ȘTERGERE")
    print("1. Șterge toate cheltuielile dintr-o zi")
    print("2. Șterge toate cheltuielile dintr-un interval de timp")
    print("3. Șterge toate cheltuielile de un anumit tip")


def print_search_menu():
    print("MENIU CĂUTARE")
    print("1. Tipărește cheltuielile mai mari decât o sumă")
    print("2. Tipărește cheltuielile efectuate înainte de o zi și mai mici decât o sumă")
    print("3. Tipărește cheltuielile de un anumit tip")


def print_report_menu():
    print("MENIU RAPOARTE")
    print("1. Tipărește suma totală pentru un anumit tip de cheltuială")
    print("2. Tipărește ziua în care suma cheltuită e maximă")
    print("3. Tipărește cheltuielile ce au o anumită sumă")
    print("4. Tipărește cheltuielile sortate după tip")


def print_filter_menu():
    print("MENIU FILTRARE")
    print("1. Elimină tranzacțiile de un anumit tip")
    print("2. Elimină tranzacțiile mai mici decât o sumă dată, care au tipul specificat")


# read
def read_option():
    x = input("Introduceți opțiunea: ")
    return x


def read_valid_day():
    try:
        day = int(input("Introduceți ziua: "))
        if day < 1 or day > 31:
            raise ValueError
        return day
    except:
        print(colored("Ziua trebuie să fie un număr întreg - minim 1, maxim 31."), 'red')


def read_two_valid_dates():
    try:
        day1 = int(input("Introduceți prima zi: "))
        day2 = int(input("Introduceți a doua zi: "))
        if day1 < 1 or day1 > 31 or day2 < 1 or day2 > 31:
            raise ValueError
        if day1 > day2:
            raise ValueError

        return day1, day2
    except:
        print(colored("Ziua trebuie să fie un număr întreg - minim 1, maxim 31, iar prima zi trebuie să fie  <= cu a doua."), 'red')


def read_valid_price():
    try:
        price = int(input("Introduceți suma: "))
        return price
    except:
        print(colored("Suma trebuie să fie un număr întreg.", 'red'))


def read_valid_day_and_price():
    try:
        day = read_valid_day()
        if not day:
            raise ValueError
        price = read_valid_price()
        if not price:
            raise ValueError
        return day, price
    except:
        pass


def read_valid_expense():
    try:
        day = int(input("Introduceți ziua: "))
        if day < 1 or day > 31:
            raise ValueError("data invalida")
        price = int(input("Introduceți suma: "))
        category = input("Introduceți tipul (ex: haine, îmbrăcăminte...): ")
        expense = create_expense(day, price, category)
        return expense
    except:
        print(colored("Ziua și suma trebuie să fie numere întregi. (Ziua poate fi minim 1, maxim 31)", 'red', 'on_red'))


# run
def run_add_menu(expense_manager):
    opt_add = read_option()
    if opt_add == '1':  # adaugare cheltuiala
        expense = read_valid_expense()
        add_expense_to_manager(expense_manager, expense)
        # add_expense(expense_manager)
    elif opt_add == '2':  # modificare cheltuiala
        expense = read_valid_expense()
        modify_expense_in_manager(expense_manager, expense)
        # modify_expense(expense_manager)
    else:
        invalid_option_message()


def run_delete_menu(expense_manager):
    opt_delete = read_option()
    crt_list = get_expense_list(expense_manager)
    if opt_delete == '1':  # se sterg cheltuielile dintr-o zi
        day = read_valid_day()
        future_list = keep_in_list_one_at(crt_list, lambda el, d: getDay(el) != d, day)  # viitoarea lista, dupa stergere
        delete_expense_from_manager(expense_manager, future_list)
    elif opt_delete == '2':  # se sterg cheltuielile dintr-un interval de timp
        day1, day2 = read_two_valid_dates()
        future_list = keep_in_list_two_at(crt_list, lambda x, d1, d2: getDay(x) < d1 or getDay(x) > d2, day1, day2)
        delete_expense_from_manager(expense_manager, future_list)
    elif opt_delete == '3':  # se sterg cheltuielile de un anumit tip
        category = input("Introduceți tipul: ")
        future_list = keep_in_list_one_at(crt_list, lambda el, c: getCategory(el) != c, category)
        delete_expense_from_manager(expense_manager, future_list)
    else:
        invalid_option_message()


def run_search_menu(expense_manager):
    opt_search = read_option()
    crt_list = get_expense_list(expense_manager)
    if opt_search == '1':  # cauta cheltuielile mai mari decat o suma
        price = read_valid_price()
        new_list = keep_in_list_one_at(crt_list, lambda el, p: getPrice(el) > p, price)
        print_list(new_list)
    elif opt_search == '2':  # cauta cheltuielile dinaintea unei zile si mai mici decat o suma
        day, price = read_valid_day_and_price()
        new_list = keep_in_list_two_at(crt_list, lambda el, d, p: getDay(el) < d and getPrice(el) < p, day, price)
        print_list(new_list)
    elif opt_search == '3':  # cauta cheltuielile de un anumit tip
        category = input("Introduceți tipul: ")
        new_list = keep_in_list_one_at(crt_list, lambda el, c: getCategory(el) == c, category)
        print_list(new_list)
    else:
        invalid_option_message()


def run_report_menu(expense_manager):
    opt_report = read_option()
    crt_list = copy_list(get_expense_list(expense_manager))
    if opt_report == '1':  # tipareste suma totala pentru un anumit tip de cheltuiala
        category = input("Introduceți tipul: ")
        print(get_category_total_price(crt_list, category))
    elif opt_report == '2':  # tipareste ziua in care cheltuiala totala este maxima
        print_list(get_day_max_total_price(crt_list))
    elif opt_report == '3':  # tipareste cheltuielile de o anumita suma
        price = read_valid_price()
        new_list = keep_in_list_one_at(crt_list, lambda el, p: getPrice(el) == p, price)
        if price:
            print_list(new_list)
    elif opt_report == '4':  # cheltuieste cheltuielile sortate dupa tip
        crt_list.sort(key=getCategory)
        print_list(crt_list)
    else:
        invalid_option_message()


def run_filter_menu(expense_manager):
    opt_filter = read_option()
    crt_list = get_expense_list(expense_manager)
    if opt_filter == '1':  # elimina cheltuielile de o anumita categorie
        category = input("Introduceți tipul: ")
        new_list = keep_in_list_one_at(crt_list, lambda el, c: getCategory(el) != c, category)
        print_list(new_list)
    elif opt_filter == '2':  # elimina cheltuielile mai mici decât o sumă dată care au tipul specificat
        price = read_valid_price()
        category = input("Introduceți categoria: ")
        new_list = keep_in_list_two_at(crt_list, lambda el, p, c: getPrice(el) > p and getCategory(el) == c, price, category)
        print_list(new_list)
    else:
        invalid_option_message()

def run_menu():
    # [crt_show_list, undo_list]
    expense_manager = setup_expense_manager(True)  # lista de cheltuieli este vida (True = lista populata)
    finished = False
    while not finished:
        try:
            print_main_menu()
            opt = read_option()
            if opt == '1':  # adaugare
                print_add_menu()
                run_add_menu(expense_manager)
            elif opt == '2':  # stergere
                print_delete_menu()
                run_delete_menu(expense_manager)
            elif opt == '3':  # cautare
                print_search_menu()
                run_search_menu(expense_manager)
            elif opt == '4':  # raport
                print_report_menu()
                run_report_menu(expense_manager)
            elif opt == '5':  # filtrare
                print_filter_menu()
                run_filter_menu(expense_manager)
            elif opt == '6':  # undo
                undo(expense_manager)
            elif opt.upper() == 'P':  # printare lista curenta
                print_list(get_expense_list(expense_manager))
            elif opt == '7':  # iesire din aplicatie
                finished = True
            else:
                invalid_option_message()
        except:
            pass


# command menu
def getCommandName(lst):
    return lst[0]


def getCommandType(lst):
    return lst[1]


def getfirstParameter(lst):
    return lst[2]


def read_command_line():
    print("Avertizare: Comenzile trebuie să fie despărțite prin ';', iar elementele comenzii prin spațiu, altfel lista de comenzi/ comanda nu va funcționa.")
    line = input("Introduceți lista de comenenzi după forma: filtrare 2 haine; cautare 1; ...: ")
    command_line = [el for el in line.split(';')]  # separam linia de comenzi in lista de comenzi
    return command_line


def create_command_list(command_line):
    command_list = []
    for command in command_line:  # transformam fiecare comanda intr-o lista de detalii ale comenzii
        element = [el for el in command.split()]
        add_to_list(command_list, element)
    return command_list


def run_command():
    expense_manager = setup_expense_manager(True) # lista populata
    crt_list = get_expense_list(expense_manager)
    command_line = read_command_line()
    command_list = create_command_list(command_line)
    for el in command_list:
        try:
            if getCommandName(el) == 'filtrare':
                if getCommandType(el) == '1':  # elimina cheltuielile de un anumit tip
                    new_list = keep_in_list_one_at(crt_list, lambda x, c: getCategory(x) != getfirstParameter(el), el)
                    print_list(new_list)
                    print()
                else:
                    invalid_option_message()
            elif getCommandName(el) == 'raport':
                if getCommandType(el) == '4':  # tipareste cheltuielile sortate dupa tip
                    new_list = copy_list(crt_list)
                    new_list.sort(key=getCategory)
                    print_list(new_list)
                    print()
                else:
                    invalid_option_message()
            elif getCommandName(el) == 'cautare':
                if getCommandType(el) == '1':  # tipărește toate cheltuielile mai mari decât o sumă dată
                    new_list = keep_in_list_one_at(crt_list, lambda x, c: getPrice(x) > int(getfirstParameter(el)), el)
                    print_list(new_list)
                    print()
                else:
                    invalid_option_message()
        except:
            print("Comanda introdusa gresit")
# start
def start():
    print_run()
    opt = read_option()
    if opt == '1':
        run_menu()
    elif opt == '2':
        run_command()
    else:
        invalid_option_message()

