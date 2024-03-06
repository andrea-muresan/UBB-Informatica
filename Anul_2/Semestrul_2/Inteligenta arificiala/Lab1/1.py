# Să se determine ultimul (din punct de vedere alfabetic) cuvânt care poate apărea într-un
# text care conține mai multe cuvinte separate prin ” ” (spațiu).
# De ex. ultimul (dpdv alfabetic) cuvânt din ”Ana are mere rosii si galbene” este cuvântul "si".


def ultim_cuvant_alfabetic(txt):
    """
    Returneaza ultimul cuvant dpdv alfabetic dintr-un text dat
    :param txt: textul dat
    :complexity - time:  O(n)
                - space: O(n)
    """
    # Elimina semnele de punctuatie
    for punctuatie in '.,?!':
        txt = txt.replace(punctuatie, '')

    cuvinte = txt.split()
    if len(cuvinte) != 0:
        el = cuvinte[0]
        for word in cuvinte:
            if word.lower() > el.lower():
                el = word

        return el
    else:
        return None


def test():
    assert ultim_cuvant_alfabetic("Ana are mere rosii si galbene") == "si"

    assert ultim_cuvant_alfabetic("Zece negri mititei de Agatha Christie") == "Zece"

    assert ultim_cuvant_alfabetic("apa trece, pietrele raman") == "trece"

    assert ultim_cuvant_alfabetic("A B C D 0 1 2 3") == "D"

    assert ultim_cuvant_alfabetic("") is None

    print("test 1 - gata")


test()

# Rezolvare ChatGPT:
# complexitate  - timp: O(n * log n) ; n numarul de cuvinte din text
#               - spatiu: O(n)
#
# ----------------------------------------------------------------------
# text = "Ana are mere rosii si galbene"
# words = text.split()  # Separăm cuvintele
# words.sort()  # Ordonăm alfabetic
# ultimul_cuvant = words[-1]  # Selectăm ultimul cuvânt din lista ordonată
#
# print("Ultimul cuvant din punct de vedere alfabetic este:", ultimul_cuvant)


