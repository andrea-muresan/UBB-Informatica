
def insertion_sort(lst):
    """
    Insert the element on it's right position in a sorted subsequence
    :param lst: the list of elements (integers)
    :return: the sorted list

    Time Complexity: O(n^2)
    Auxiliary Space: O(1)
    """
    for i in range(1, len(lst)):
        el = lst[i]
        while el < lst[i - 1] and i >= 1:
            lst[i] = lst[i - 1]
            i -= 1
        lst[i] = el

    return lst

def test():
    # 1
    lst = [1, 0, 3, 5, -3, 2, 7]
    assert insertion_sort(lst) == [-3, 0, 1, 2, 3, 5, 7]

    # 2
    lst = [7]
    assert insertion_sort(lst) == [7]

    # 3
    lst = [6, 5, 4, 3, 2, 1, 0]
    assert insertion_sort(lst) == [0, 1, 2, 3, 4, 5, 6]


test()
