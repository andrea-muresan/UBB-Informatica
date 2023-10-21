
def merge_sort(lst):
    """
    Devide the array in two halves, sort each of them and merge them back together
    :param lst: the list of elements (integers)
    :return: the sorted list

    Time Complexity: O(n*log n)
    Auxiliary Space: O(n)
    """

    if len(lst) <= 1:
        return lst

    mid = len(lst) // 2
    left = merge_sort(lst[:mid])
    right = merge_sort((lst[mid:]))

    i = 0
    j = 0
    final_list = []
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            final_list.append(left[i])
            i += 1
        else:
            final_list.append(right[j])
            j += 1

    final_list += left[i:]
    final_list += right[j:]
    return final_list

def test():
    # 1
    lst = [1, 0, 3, 5, -3, 2, 7]
    assert merge_sort(lst) == [-3, 0, 1, 2, 3, 5, 7]

    # 2
    lst = [7]
    assert merge_sort(lst) == [7]

    # 3
    lst = [6, 5, 4, 3, 2, 1, 0]
    assert merge_sort(lst) == [0, 1, 2, 3, 4, 5, 6]


test()
