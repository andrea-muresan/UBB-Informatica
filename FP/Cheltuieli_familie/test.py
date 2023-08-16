
from expense_manager import *
from utils import *
from expense import *


# console
def test_get_day_max_total_price():
    lst = generate_expenses()

    l1 = get_day_max_total_price(lst)
    assert (len(l1) == 1)


def test_get_category_total_price():
    lst = generate_expenses()

    p = get_category_total_price(lst, 'haine')
    assert (p == 155)


# utils
def test_add_expense_to_list():
    l1 = []
    # e = {
    #     "day": 12,
    #     "price": 130,
    #     "category": 'haine',
    # }
    add_expense_to_list(l1, 12, 130, 'haine')
    assert (len(l1) == 1)


def test_copy_list():
    lst = [3, 5, 9]
    cpy_lst = copy_list(lst)
    assert (cpy_lst == lst)


def test_keep_in_list_one_at():
    lst = generate_expenses()

    # cheltuieli fara o zi
    l1 = keep_in_list_one_at(lst, lambda x, d: getDay(x) != d, 7)
    assert (len(l1) == 2)

    # cheltuieli mai mari decat o suma
    l2 = keep_in_list_one_at(lst, lambda x, p: getPrice(x) > p, 60)
    assert (len(l2) == 3)


def test_keep_in_list_two_at():
    lst = generate_expenses()

    # elimina cheltuielile dintre doua zile
    l1 = keep_in_list_two_at(lst, lambda x, d1, d2: getDay(x) < d1 or getDay(x) > d2, 5, 19)
    assert (len(l1) == 1)

    # elimina cheltuielile dinainte de o zi si mai mici decat o suma
    l2 = keep_in_list_two_at(lst, lambda x, d, p: getDay(x) > d and getPrice(x) > p, 8, 100)
    assert (len(l2) == 1)


# expense
def test_create_expense():
    e1 = create_expense(7, 15, 'haine')
    # assert (type(e1) == dict)
    assert (type(e1) == list)
    assert (getDay(e1) == 7)
    assert (getPrice(e1) == 15)
    assert (getCategory(e1) == 'haine')


# manager
def test_add_expense_to_manager():
    test_manager = setup_expense_manager(True)
    expense1 = create_expense(5, 17, 'mancare')
    expense2 = create_expense(3, 25, 'haine')

    add_expense_to_manager(test_manager, expense1)
    add_expense_to_manager(test_manager, expense2)

    assert(len(get_expense_list(test_manager)))


def test_delete_expense_from_manager():
    test_manager = setup_expense_manager(True)
    expense1 = create_expense(5, 17, 'mancare')
    expense2 = create_expense(3, 25, 'haine')

    add_expense_to_manager(test_manager, expense1)
    add_expense_to_manager(test_manager, expense2)

    delete_expense_from_manager(test_manager, [expense1, expense2])
    assert (len(get_expense_list(test_manager)) == 2)


def test_undo():
    # add
    test_manager = setup_expense_manager(False)
    expense1 = create_expense(7, 125, 'haine')
    expense2 = create_expense(7, 66, 'carti')
    expense3 = create_expense(28, 433, 'vacanta')

    add_expense_to_manager(test_manager, expense3)
    assert (len(get_expense_list(test_manager)) == 1)

    undo(test_manager)
    assert (len(get_expense_list(test_manager)) == 0)

    # delete
    test_manager = setup_expense_manager(True)
    delete_expense_from_manager(test_manager, [expense1, expense2])
    delete_expense_from_manager(test_manager, [expense1])

    undo(test_manager)
    assert (len(get_expense_list(test_manager)) == 2)
    assert (len(get_expense_list(test_manager)) == 2)

    undo(test_manager)
    assert (len(get_expense_list(test_manager)) == 4)


def test_modify_expense():
    test_manager = setup_expense_manager(True)

    expense = create_expense(7, 34, "carti")
    modify_expense_in_manager(test_manager, expense)

    assert (getPrice(get_expense_list(test_manager)[3]) == 34)


# toate testele
def tests():
    test_get_day_max_total_price()
    test_get_category_total_price()
    test_keep_in_list_one_at()
    test_copy_list()
    test_add_expense_to_list()
    test_keep_in_list_two_at()
    test_create_expense()
    test_delete_expense_from_manager()
    test_add_expense_to_manager()
    test_undo()
    test_modify_expense()


tests()
