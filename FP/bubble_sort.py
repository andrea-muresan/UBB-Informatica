
def bubble_sort(lst):
    """
    Repeatedly swapping the adjacent elements if they are in the wrong order
    :param lst: the list of elements (integers)
    :return: the sorted list

    Time Complexity: O(n^2)
        - best case: Theta(n)
        - worst case: Theta(n^2)
    Auxiliary Space: O(1)
    """

    for i in range(len(lst) - 1):
        swapped = False
        for j in range(0, len(lst) - i - 1):
            if lst[j] > lst[j + 1]:
                lst[j], lst[j + 1] = lst[j + 1], lst[j]
                swapped = True

        # If no two elements were swapped by the inner loop,
        # then break - the list is sorted
        if swapped is False:
            break

    return lst


def test():
    # 1
    lst = [1, 0, 3, 5, -3, 2, 7]
    assert bubble_sort(lst) == [-3, 0, 1, 2, 3, 5, 7]

    # 2
    lst = [7]
    assert bubble_sort(lst) == [7]

    # 3
    lst = [6, 5, 4, 3, 2, 1, 0]
    assert bubble_sort(lst) == [0, 1, 2, 3, 4, 5, 6]


test()