
def merge_sort(lista):
    """
    Sorteaza o lista folosind merge sort
    :param lista: lista care trebuie sortata
    """
    if len(lista) == 0:
        return
    if len(lista) == 1:
        return lista

    mijloc = len(lista) // 2
    st = merge_sort(lista[:mijloc])
    dr = merge_sort((lista[mijloc:]))

    i= 0
    j = 0
    lista_finala = []
    while i <= len(st) and j <= len(dr):
        if lista[i] <lista[j]:
            lista_finala.append(lista[i])
            i += 1
        else:
            lista_finala.append(lista[j])
            j += 1

    lista_finala += lista[i:]
    lista_finala += lista[j:]
    return lista_finala