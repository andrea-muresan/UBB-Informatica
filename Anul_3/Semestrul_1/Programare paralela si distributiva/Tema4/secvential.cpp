#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <unordered_map>
#include <chrono>
#include <queue>
#include <cstdlib>
#include <ctime>

using namespace std;

int last_id = 0;
const double PROBABILITATE_NEREZOLVARE = 0.1; // 10%
const double PROBABILITATE_FRAUDA = 0.02;     // 2%
const int MAX_PUNCTAJ = 10;
void generateAllFiles(int numCountries, int numParticipantsPerCountry, int numProblems)
{
    srand(static_cast<unsigned>(time(0))); // Inițializare generator random]

    for (int tara = 1; tara <= numCountries; tara++) {
        for (int problema = 1; problema <= numProblems; problema++) {
            std::string numeFisier = "Inputs\\RezultateC" + std::to_string(tara) + "_P" + std::to_string(problema) + ".txt";
            std::ofstream fisier(numeFisier);

            if (!fisier.is_open()) {
                std::cerr << "Eroare la scrierea fișierului: " << numeFisier << std::endl;
                return;
            }

            for (int id = 1; id <= numParticipantsPerCountry; id++) {
                double sansa = static_cast<double>(rand()) / RAND_MAX;

                // Frauda
                if (sansa < PROBABILITATE_FRAUDA) {
                    fisier << last_id + id<< " -1\n";
                    continue;
                }

                // Nerezolvare
                if (sansa < PROBABILITATE_FRAUDA + PROBABILITATE_NEREZOLVARE) {
                    // Nu se adaugă nimic pentru nerezolvare
                    continue;
                }

                // Rezolvare validă
                int punctaj = rand() % (MAX_PUNCTAJ + 1); // Între 0 și MAX_PUNCTAJ
                fisier << last_id + id << " " << punctaj << "\n";
            }
            fisier.close();
        }
        last_id += numParticipantsPerCountry;
    }


}

int generate(int numProblems, int numCountries, int numParticipantsPerCountry)
{
    generateAllFiles(numCountries, numParticipantsPerCountry, numProblems);

    return 0;
}


/**
 * Struct pentru informatiile participantului
 */
struct Participant
{
    int ID;
    int punctaj;
    int tara;
};

/**
 * Nod pentru lista dublu inlantuita
 */
class Node
{
public:
    Participant participant;
    Node *next;
    Node *prev;
    Node(const Participant &participant) : participant(participant), next(nullptr), prev(nullptr) {}
};


/**
 * Lista dublu inlantuita pentru a lucra cu participantii
 */
class DoublyLinkedListS
{
private:
    Node *head;
    Node *tail;
    vector<int> hashTableDescalificati;

public:
    DoublyLinkedListS() : head(nullptr), tail(nullptr) {}
    ~DoublyLinkedListS();

    // Insereaza un nod nou - se mentine ordinea
    void insertSorted(Node *newNode);
    // Adauga un participant in lista
    void addNode(const Participant &participant);
    // Function to delete a participant by their ID
    void deleteNode(int participantID);
    // Function to display the entire list into an output file
    void displayList();

    // Function to sort the list based on score (descending) and ID (ascending)
    void sortList()
    {
        Node *current = head;
        while (current)
        {
            Node *next = current->next;
            while (next)
            {
                if (current->participant.punctaj < next->participant.punctaj ||
                    (current->participant.punctaj == next->participant.punctaj &&
                     current->participant.ID > next->participant.ID))
                {
                    swap(current->participant, next->participant);
                }
                next = next->next;
            }
            current = current->next;
        }
    }
};

// Destructor
DoublyLinkedListS::~DoublyLinkedListS()
{
    Node *current = head;
    while (current)
    {
        Node *next = current->next;
        delete current;
        current = next;
    }
}

// Insereaza un nod in lista
void DoublyLinkedListS::insertSorted(Node *newNode)
{
    if (!head || newNode->participant.punctaj > head->participant.punctaj)
    {
        newNode->next = head;
        newNode->prev = nullptr;
        if (head)
        {
            head->prev = newNode;
        }
        head = newNode;
        if (!tail)
        {
            tail = head;
        }
        return;
    }

    Node *current = head;
    while (current->next && ((current->next->participant.punctaj > newNode->participant.punctaj) ||
        (current->next->participant.punctaj == newNode->participant.punctaj && current->next->participant.ID < newNode->participant.ID)))
    {
        current = current->next;
    }

    // Insert the new node at the found position
    newNode->next = current->next;
    newNode->prev = current;

    if (current->next)
    {
        current->next->prev = newNode;
    }
    else
    {
        tail = newNode;
    }

    current->next = newNode;
}

// Adauga un nou participant in lista
void DoublyLinkedListS::addNode(const Participant &participant)
{
    Node *newNode = new Node(participant);  // Create a new node for the participant

    // Verificam daca participantul a fost deja descalificat
    if (find(hashTableDescalificati.begin(), hashTableDescalificati.end(), participant.ID) != hashTableDescalificati.end())
    {
        delete newNode;
        return;
    }

    // Daca participantul are scor < 0, il stergem din lista
    if (participant.punctaj < 0)
    {
        if (!head)
        {
            hashTableDescalificati.push_back(participant.ID);
        }
        else
        {
            deleteNode(participant.ID);
            hashTableDescalificati.push_back(participant.ID);
        }
        return;
    }

    // Refacem clasamentul daca participantul exista deja in lista
    Node *current = head;
    while (current)
    {
        if (current->participant.ID == participant.ID)
        {
            // If participant exists, update their score and sort the list
            current->participant.punctaj += participant.punctaj;
            sortList();
            delete newNode;  // Delete unused node
            return;
        }
        current = current->next;
    }

    // Adaugam un participant nou, daca nu l-am gasit anterior
    insertSorted(newNode);
}

// Sterge un participant din lista
void DoublyLinkedListS::deleteNode(int participantID)
{
    Node *current = head;
    while (current)
    {
        if (current->participant.ID == participantID)
        {
            if (current->prev)
            {
                current->prev->next = current->next;
            }
            else
            {
                head = current->next;
            }

            if (current->next)
            {
                current->next->prev = current->prev;
            }
            else
            {
                tail = current->prev;
            }

            delete current;
            return;
        }
        current = current->next;
    }
}

// Scrie clasamentul in fisier
void DoublyLinkedListS::displayList()
{
    ofstream g("Outputs\\Clasament.txt");
    Node *current = head;

    while (current)
    {
        g << current->participant.ID
          << ", " << current->participant.punctaj
          << ", C" << current->participant.tara << "\n";
        current = current->next;
    }
    g.close();
}


int runSequential(int numProblems, int numCountries)
{
    auto startTime = chrono::high_resolution_clock::now();

    DoublyLinkedListS clasamentTari;  // Create a doubly linked list to hold the rankings

    for (int country = 1; country <= numCountries; ++country)
    {
        // Loop over each problem for the current country
        for (int problem = 1; problem <= numProblems; ++problem)
        {
            string tara = "C" + to_string(country);  // Create country identifier
            string filename = "Inputs\\Rezultate" + tara + "_P" + to_string(problem) + ".txt";  // Build file name
            ifstream inFile(filename);  // Open the file containing results

            if (!inFile.is_open())  // If the file cannot be opened, print an error and continue
            {
                cerr << "Eroare la deschiderea fisierului " << filename << endl;
                continue;
            }

            int ID, punctaj;

            while (inFile >> ID >> punctaj)
            {
                clasamentTari.addNode({ID, punctaj, country});
            }

            inFile.close();
        }
    }

    clasamentTari.displayList();

    auto endTime = chrono::high_resolution_clock::now();
    cout << chrono::duration<double, milli>(endTime - startTime).count();

    return 0;
}

int main(int argc, char *argv[])
{
    int numProblems = 10;
    int numCountries = 5;
    int numPartPerCountry = 80;

    // generate(numProblems, numCountries, numPartPerCountry);
    runSequential(numProblems, numCountries);
    return 0;
}
