
def iterative_binary_search(lst, key):
    """
    Compare key with the middle element
    If the middle element == key, return the middle index
    Else if it is smaller we search the element in the left part
    Else we search the element in the right part

    :param lst: the list of elements
    :param key: the searched element
    :return: the index where the element is found, -1 if it isn't

    Time Complexity: O(log n)
    Auxiliary Space: O(1)
    """

    left = 0
    right = len(lst) - 1

    while left <= right:
        mid = (left + right) // 2

        # If we find the key
        if lst[mid] == key:
            return mid

        # If it is smaller than the middle element
        elif key < lst[mid]:
            right = mid - 1

        # If it is greater
        else:
            left = mid + 1

    return -1


def recursive_binary_search(lst, st, dr, key):
    """
    Compare key with the middle element
    If the middle element == key, return the middle index
    Else if it is smaller we search the element in the left part
    Else we search the element in the right part

    :param lst: the list of elements
    :param st: the start index  (integer)
    :param dr: the end index (integer)
    :param key: the searched element
    :return: the index where the element is found, -1 if it isn't

    Time Complexity: O(log n)
    Auxiliary Space: O(log n)
    """

    if dr >= st:
        mij = (st + dr) // 2

        # If we find the element
        if lst[mij] == key:
            return mij

        # If element is smaller than [mij], then we search
        # it in the left part
        elif key < lst[mij]:
            return recursive_binary_search(lst, st, mij - 1, key)

        # Else we search it in the right part
        else:
            return recursive_binary_search(lst, mij + 1, dr, key)

    else:
        return -1

def test():
    lst = [2, 3, 5, 6, 7, 12]

    # iterative
    assert iterative_binary_search(lst, 3) == 1
    assert iterative_binary_search(lst, 12) == 5
    assert iterative_binary_search(lst, 13) == -1

    # recursive
    assert recursive_binary_search(lst, 0, len(lst) - 1, 3) == 1
    assert recursive_binary_search(lst, 0, len(lst) - 1, 12) == 5
    assert recursive_binary_search(lst, 0, len(lst) - 1, 13) == -1

test()