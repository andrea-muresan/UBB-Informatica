
def mergesort(lst, *, key=lambda x: x, key2=lambda x: x, reverse=False, reverse2=False):
    """
    MergeSort implementation
    :param lst: the list that will be sorted
    :param key: the function by witch it is sorted
    :param key2: the second function by witch it is sorted
    :param reverse: True - ascending sort, False - descending sort
    :param reverse2: True - ascending sort for the second function, False - descending sort
    :return: return the sorted list
    """
    if len(lst) > 1:
        pivot = len(lst) // 2
        left_half = lst[:pivot]
        right_half = lst[pivot:]

        mergesort(left_half, key=key, key2=key2, reverse=reverse, reverse2=reverse2)
        mergesort(right_half, key=key, key2=key2, reverse=reverse, reverse2=reverse2)

        i = 0
        j = 0
        k = 0
        while i < len(left_half) and j < len(right_half):
            if reverse is False:  # ascending sort for first criteria
                if key(left_half[i]) < key(right_half[j]):
                    lst[k] = left_half[i]
                    i += 1
                elif key(left_half[i]) == key(right_half[j]):  # if the left and the right part are equal we sort by the second criteria
                    if reverse2 is False:  # ascending sort for second criteria
                        if key2(left_half[i]) < key2(right_half[j]):
                            lst[k] = left_half[i]
                            i += 1
                        else:
                            lst[k] = right_half[j]
                            j += 1
                    else:   # descending sort for second criteria
                        if key2(left_half[i]) < key2(right_half[j]):
                            lst[k] = right_half[j]
                            j += 1
                        else:
                            lst[k] = left_half[i]
                            i += 1
                else:
                    lst[k] = right_half[j]
                    j += 1
                k += 1
            elif reverse is True:  # descending sort for the first criteria
                if key(left_half[i]) < key(right_half[j]):
                    lst[k] = right_half[j]
                    j += 1
                elif key(left_half[i]) == key(right_half[j]):  # if the left and the right part are equal we sort by the second criteria
                    if reverse2 is False:  # ascending sort for the second criteria
                        if key2(left_half[i]) < key2(right_half[j]):
                            lst[k] = left_half[i]
                            i += 1
                        else:
                            lst[k] = right_half[j]
                            j += 1
                    else:  # descending sort for the second criteria
                        if key2(left_half[i]) > key2(right_half[j]):
                            lst[k] = left_half[i]
                            i += 1
                        else:
                            lst[k] = right_half[j]
                            j += 1
                else:
                    lst[k] = left_half[i]
                    i += 1
                k += 1

        while i < len(left_half):
            lst[k] = left_half[i]
            i += 1
            k += 1

        while j < len(right_half):
            lst[k] = right_half[j]
            j += 1
            k += 1

    return lst


def bingosort(lst, cmp):
    """
    BingoSort implementation
    :param lst: the list that will be sorted
    :param key: the function by witch it is sorted
    :param reverse: True - ascending sort, False - descending sort
    :return: return the sorted list
    """

    n = len(lst)
    for i in range(n):
        for j in range(n - 1 - i):
            if cmp((lst[j]), (lst[j + 1])):
                lst[j], lst[j + 1] = lst[j + 1], lst[j]
                break
    # if reverse is True:
    #     return list[::-1]
    return lst

