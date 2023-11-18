
def selection_sort(lst):
    """
    Search the smallest element and place it on the first position,
    search the second-smallest element and place it on the second position...
    :param lst: the list of elements (integers)
    :return: the sorted list

    Time Complexity: O(n^2)
    Auxiliary Space: O(1)
    """

    for i in range(0, len(lst) - 1):
        index = i
        for j in range(i + 1, len(lst)):
            if lst[j] < lst[index]:
                index = j

        if i < index:
            lst[i], lst[index] = lst[index], lst[i]

    return lst


def test():
    # 1
    lst = [1, 0, 3, 5, -3, 2, 7]
    assert selection_sort(lst) == [-3, 0, 1, 2, 3, 5, 7]

    # 2
    lst = [7]
    assert selection_sort(lst) == [7]

    # 3
    lst = [6, 5, 4, 3, 2, 1, 0]
    assert selection_sort(lst) == [0, 1, 2, 3, 4, 5, 6]


test()
