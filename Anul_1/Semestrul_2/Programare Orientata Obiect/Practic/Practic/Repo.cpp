#include "Repo.h"

// returneaza lista de studenti
vector<Student> Repo::getAll()
{
    std::sort(all.begin(), all.end(), [](Student a, Student b) {return a.getVarsta() < b.getVarsta(); });
    return all;
}

// citire din fisier
void Repo::loadFromFile()
{
    std::ifstream in(fileName);
    int id, varsta;
    string nume, facultate;

    while (in >> id)
    {
        in >> nume >> varsta >> facultate;
        Student s{ id, nume, varsta, facultate };
        all.push_back(s);
    }
}

// incarca date in fisier
void Repo::writeFile()
{
    std::ofstream out(fileName);
    for (auto p : all)
    {
        out << p.getId() << " " << p.getNume() << " " << p.getVarsta() << " " << p.getFacultate() << '\n';
    }
}

// modifica varsta
string Repo::updateAge(int val)
{
    for (int i = 0; i < all.size(); i++)
    {
        if (all[i].getVarsta() == 0)
            return "Varsta negativa";
        all[i].setVarsta(all[i].getVarsta() + val);
    }

    return "";
}

// sterge student dupa id
void Repo::deleteStudent(int id)
{
    for (int i = 0; i < all.size(); i++)
    {
        if (all[i].getId() == id)
            all.erase(all.begin() + i);
    }
}

// returneaza student dupa id
Student Repo::getStudent(int id)
{
    for (int i = 0; i < all.size(); i++)
    {
        if (all[i].getId() == id)
            return all[i];
    }
}

// adauga student
void Repo::addStudent(Student s)
{
    all.push_back(s);
}


