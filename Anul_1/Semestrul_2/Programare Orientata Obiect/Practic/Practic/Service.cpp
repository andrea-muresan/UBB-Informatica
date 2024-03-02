#include "Service.h"
#include <assert.h>
#include <iostream>

// returneaza o lista cu toti studentii
vector<Student> Service::getAll()
{
    return repo.getAll();
}

// incarca datele din fisier
void Service::loadFromFile()
{
    repo.loadFromFile();
}

// incarca datele in fisier
void Service::writeFile()
{
    repo.writeFile();
}

// modifica varsta
string Service::updateAge(int val)
{
    string error = repo.updateAge(val);
    writeFile();
    return error;
}

// sterge student
void Service::deleteStudent(int id)
{
    repo.deleteStudent(id);
    writeFile();
}

// cauta studentul cu un anumit id
Student Service::getStudent(int id)
{
    return repo.getStudent(id);
}

// adauga student
void Service::addStudent(Student s)
{
    repo.addStudent(s);
    writeFile();
}

// teste
void testService()
{
    Repo rep{ "textTest.txt" };
    Service srv{ rep };

    assert(srv.getAll().size() == 0);
    
    // test adaugare
    Student s{ 3, "ana", 17, "mate" };
    srv.addStudent(s);
    assert(srv.getAll().size() == 1);

    // test getters
    assert(s.getId() == 3);
    assert(s.getNume() == "ana");
    assert(s.getVarsta() == 17);
    assert(s.getFacultate() == "mate");

    // test incrementeaza varsta
    srv.updateAge(1);
    assert(srv.getAll()[0].getVarsta() == 18);

    // test decrementeaza varsta
    srv.updateAge(-1);
    assert(srv.getAll()[0].getVarsta() == 17);

    // test varsta negativa
    for (int i = 0; i < 17; i++)
        assert(srv.updateAge(-1) == "");

    assert(srv.updateAge(-1) !="");

    // test stergere student
    srv.deleteStudent(3);
    assert(srv.getAll().size() == 0);
}
