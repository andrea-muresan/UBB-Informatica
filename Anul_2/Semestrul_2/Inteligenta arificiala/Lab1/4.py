# Să se determine cuvintele unui text care apar exact o singură dată în acel text.
# De ex. cuvintele care apar o singură dată în ”ana are ana are mere rosii ana" sunt: 'mere' și 'rosii'.

def apare_o_data(txt):
    """
    Returneaza o lista cu cuvintele care apar o singura data intr-un text
    :complexity - time: O(n)
                - space: O(n)
    """
    # Elimina semnele de punctuatie
    for punctuatie in '.,?!':
        txt = txt.replace(punctuatie, '')

    dictionary = {}
    for word in txt.split():
        if word in dictionary:
            dictionary[word] += 1
        else:
            dictionary[word] = 1

    return [el for el in dictionary if dictionary[el] == 1]


def test():
    assert apare_o_data('ana are ana are mere rosii ana') == ['mere', 'rosii']

    assert apare_o_data('') == []

    assert apare_o_data('apa trece, pietrele raman, trece') == ['apa', 'pietrele', 'raman']

    assert apare_o_data('a b a b a') == []

    print('test 4 - gata')


test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n) ;
#               - spatiu: O(n)
#
# ----------------------------------------------------------------------
# def cuvinte_cu_aparitie_unica(text):
#     # Eliminăm semnele de punctuație și trecem textul la litere mici
#     text = text.lower()
#     for punctuatie in '.,?!':
#         text = text.replace(punctuatie, '')
#
#     # Divizăm textul în cuvinte
#     cuvinte = text.split()
#
#     # Inițializăm un dicționar pentru a număra aparițiile fiecărui cuvânt
#     frecventa_cuvinte = {}
#     for cuvant in cuvinte:
#         if cuvant in frecventa_cuvinte:
#             frecventa_cuvinte[cuvant] += 1
#         else:
#             frecventa_cuvinte[cuvant] = 1
#
#     # Selectăm cuvintele cu o singură apariție
#     cuvinte_aparitie_unica = [cuvant for cuvant, frecventa in frecventa_cuvinte.items() if frecventa == 1]
#
#     return cuvinte_aparitie_unica
#
# # Testăm funcția
# text = "ana are ana are mere rosii ana"
# rezultat = cuvinte_cu_aparitie_unica(text)
# print("Cuvintele cu apariție unică în text sunt:", rezultat)