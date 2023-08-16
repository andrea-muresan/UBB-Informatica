
def quick_sort(lst):
    """
    Devide the array in two, one with elements smaller than our pivot,
    and the other one with elements greater than it
    :param lst: the list of elements (integers)
    :return: the sorted list

    Time Complexity: O(n*log n)
    Auxiliary Space: O(log n)
    """

    if len(lst) <= 1:
        return lst
    pivot = lst.pop()
    smaller = quick_sort([x for x in lst if x < pivot])  # smaller elements;
    greater = quick_sort([x for x in lst if x >= pivot])  # greater elements

    return smaller + [pivot] + greater

def test():
    # 1
    lst = [1, 0, 3, 5, -3, 2, 7]
    assert quick_sort(lst) == [-3, 0, 1, 2, 3, 5, 7]

    # 2
    lst = [7]
    assert quick_sort(lst) == [7]

    # 3
    lst = [6, 5, 4, 3, 2, 1, 0]
    assert quick_sort(lst) == [0, 1, 2, 3, 4, 5, 6]

test()
