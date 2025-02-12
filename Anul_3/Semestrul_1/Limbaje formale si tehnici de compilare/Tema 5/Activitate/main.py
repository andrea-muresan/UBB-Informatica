class Gramatica:
    productii = {}

    def read_from_file(self, file_path):
        with open(file_path, 'r') as f:
            for line in f:
                line = line.strip()
                if '->' in line:
                    simbol, reguli = line.split('->')
                    simbol = simbol.strip()
                    reguli = [r.strip() for r in reguli.split('|')]
                    self.adauga_productie(simbol, reguli)

    def adauga_productie(self, simbol, reguli):
        if simbol not in self.productii:
            self.productii[simbol] = []
        self.productii[simbol].extend(reguli)

    def afisare_productii(self):
        print("Gramatica:")
        for simbol, reguli in self.productii.items():
            reguli_str = ' | '.join(reguli)
            print(f"{simbol} -> {reguli_str}")

    def productii_recursive(self):
        recursive_productions = {}
        for simbol, reguli in self.productii.items():
            recursive = [r for r in reguli if r.startswith(simbol)]
            if recursive:
                recursive_productions[simbol] = recursive
        return recursive_productions

    def afisare_recursive(self):
        recursive_productions = self.productii_recursive()
        if not recursive_productions:
            print("\nFara productii recursive")
        else:
            print("\nProductii recursive la stanga:")
            for simbol, reguli in recursive_productions.items():
                reguli_str = ' | '.join(reguli)
                print(f"{simbol} -> {reguli_str}")


gramatica = Gramatica()

gramatica.read_from_file("input.txt")
gramatica.afisare_productii()
gramatica.afisare_recursive()
