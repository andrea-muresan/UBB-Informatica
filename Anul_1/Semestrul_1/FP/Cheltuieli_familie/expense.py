
def create_expense(day, price, category):
    """
    Creeaza o cheltuiala
    :param day: ziua cheltuielii
    :type: int
    :param price: pretul cheltuielii
    :type: int
    :param category: tipul cheltuielii (haine, mancare...)
    :type: string
    :return: cheltuiala creata
    :rtype: dict {chei: day, price, category}
    """
    # return {
    #     "day": day,
    #     "price": price,
    #     "category": category,
    # }
    return [day, price, category]
    # [0] -> day    [1] -> price    [2] -> category


# getters
def getDay(expense):
    # return expense["day"]
    return expense[0]


def getPrice(expense):
    # return expense["price"]
    return expense[1]


def getCategory(expense):
    # return expense["category"]
    return expense[2]


# setters
def setDay(expense, new_day):
    # expense["day"] = new_day
    expense[0] = new_day


def setPrice(expense, new_price):
    # expense["price"] = new_price
    expense[1] = new_price


def setCategory(expense, new_category):
    # expense["category"] = new_category
    expense[2] = new_category
