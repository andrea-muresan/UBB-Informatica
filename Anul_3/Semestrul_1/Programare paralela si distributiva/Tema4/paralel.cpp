#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <unordered_map>
#include <chrono>
#include <queue>
#include <mutex>
#include <thread>

using namespace std;


ofstream g("Outputs\\ClasamentParalel.txt");

struct Participant
{
    int ID;
    int punctaj;
    int tara;
};

// Coada pentru a stoca participantii care urmeaza sa fie procesati
class MyQueue
{
    queue<Participant> participants;
    mutex mtx;
    int numThreads;

public:
    MyQueue(int numThreads) : numThreads(numThreads) {}

    // Adauga un participant in coada
    void insert(const Participant &participant)
    {
        lock_guard<mutex> lock(mtx);
        participants.push(participant);
    }

    // Scoate un participant din coada
    pair<Participant, bool> get()
    {
        lock_guard<mutex> lock(mtx);
        if (participants.empty())
        {
            return {{}, false};
        }
        Participant participant = participants.front();
        participants.pop();
        return {participant, true};
    }

    // Verifica daca coada este goala
    bool isEmpty()
    {
        lock_guard<mutex> lock(mtx);
        return participants.empty();
    }

    // Verifica daca threadurile si-au terminat treaba
    bool isOver()
    {
        lock_guard<mutex> lock(mtx);
        return numThreads == 0;
    }

    // Decrementeaza numarul de threaduri
    void decrementNumThreads()
    {
        lock_guard<mutex> lock(mtx);
        --numThreads;
    }
};


// Clasa pentru datele participantului
class Node
{
public:
    Participant participant;
    Node *next;
    Node *prev;

    Node(const Participant &participant) : participant(participant), next(nullptr), prev(nullptr) {}
};

// Lista dublu inlantuita (ordonata) de participanti
class DoublyLinkedList
{
private:
    Node *head;
    Node *tail;
    vector<int> hashTableDescalificati;
    mutex listMtx;

public:
    DoublyLinkedList() : head(nullptr), tail(nullptr) {}
    ~DoublyLinkedList();

    // Insert the node in sorted order based on the score and ID
    void insertSorted(Node *newNode);

    // Add a new participant to the list
    void addNode(const Participant &participant);

    // Delete a participant node by their ID
    void deleteNode(int participantID);

    // Display the final ranking list to the output file
    void displayList();

    // Sorteaza lista de participanti
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

DoublyLinkedList::~DoublyLinkedList()
{
    Node *current = head;
    while (current)
    {
        Node *next = current->next;
        delete current;
        current = next;
    }
}

void DoublyLinkedList::insertSorted(Node *newNode) {
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

void DoublyLinkedList::addNode(const Participant &participant)
{
    lock_guard<mutex> lock(listMtx);
    Node *newNode = new Node(participant);

    if (find(hashTableDescalificati.begin(), hashTableDescalificati.end(), participant.ID) != hashTableDescalificati.end())
    {
        delete newNode;
        return;
    }

    if (participant.punctaj < 0)
    {
        // Adauga participantul in lista de descalificati si sterge-l din clasament
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

    Node *current = head;
    while (current)
    {
        if (current->participant.ID == participant.ID)
        {
            current->participant.punctaj += participant.punctaj;
            sortList();
            delete newNode;
            return;
        }
        current = current->next;
    }

    insertSorted(newNode);
}

void DoublyLinkedList::deleteNode(int participantID)
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

void DoublyLinkedList::displayList()
{
    Node *current = head;
    int position = 1;
    while (current)
    {
        g << current->participant.ID << ", " << current->participant.punctaj << ", C" << current->participant.tara << endl;
        current = current->next;
        ++position;
    }
    g.close();
}


void readerThread(const vector<pair<string, int>> &filenames, MyQueue &queue)
{
    for (auto [filename, country] : filenames)
    {
        ifstream inFile(filename);

        if (!inFile.is_open())
        {
            cerr << "Eroare la deschiderea fisierului " << filename << endl;
            continue;
        }

        int ID, punctaj;
        while (inFile >> ID >> punctaj)
        {
            queue.insert({ID, punctaj, country});
        }

        inFile.close();
    }
    queue.decrementNumThreads();
}

void workerThread(DoublyLinkedList &clasamentTari, MyQueue &queue)
{
    while (!queue.isOver())
    {
        {
            if (queue.isEmpty())
            {
                continue;
            }
            auto participant = queue.get();
            if (!participant.second)
            {
                continue;
            }
            clasamentTari.addNode(participant.first);
        }
    }
}


// Functie de verificare a rezultatelor
bool areFilesSame(const string& file1, const string& file2)
{
    ifstream f1(file1), f2(file2);

    if (!f1.is_open() || !f2.is_open())
    {
        cerr << "Error opening one of the files." << endl;
        return false;
    }

    string line1, line2;

    // Compara fisierele linie cu linie
    while (getline(f1, line1) && getline(f2, line2))
    {
        if (line1 != line2)
        {
            return false; // Files are not the same
        }
    }

    // Compara dimensiunea fisierelor
    if (getline(f1, line1) || getline(f2, line2))
    {
        return false;
    }

    return true; // Fisiere identice
}

int runParallel(int numProblems, int numCountries, int numThreads, int numReaders)
{

    vector<pair<string, int>> filenames;
    for (int problem = 1; problem <= numProblems; ++problem)
    {
        for (int country = 1; country <= numCountries; ++country)
        {
            string tara = "C" + to_string(country);
            string filename = "C:\\Users\\Lenovo\\Desktop\\Tema4-PPD\\Secvential\\cmake-build-release\\Inputs\\Rezultate" + tara + "_P" + to_string(problem) + ".txt";
            filenames.emplace_back(filename, country);
        }
    }

    auto startTime = chrono::high_resolution_clock::now();

    DoublyLinkedList clasamentTari;
    MyQueue queue = MyQueue(numReaders);

    vector<thread> readerThreads;
    int start = 0, end;
    int quotient = filenames.size() / numReaders;
    int remainder = filenames.size() % numReaders;
    for (int i = 1; i <= numReaders; ++i)
    {
        end = start + quotient;
        if (remainder > 0)
        {
            ++end;
            --remainder;
        }
        vector<pair<string, int>> filesSubset(filenames.begin() + start, filenames.begin() + end);
        readerThreads.emplace_back(readerThread, filesSubset, ref(queue));
        start = end;
    }

    vector<thread> workerThreads;
    int numWorkers = numThreads - numReaders;
    for (int i = 1; i <= numWorkers; ++i)
    {
        workerThreads.emplace_back(workerThread, ref(clasamentTari), ref(queue));
    }

    for (auto &thread : readerThreads)
    {
        thread.join();
    }
    for (auto &thread : workerThreads)
    {
        thread.join();
    }


    clasamentTari.displayList();

    auto endTime = chrono::high_resolution_clock::now();

    auto duration = chrono::duration_cast<chrono::milliseconds>(endTime - startTime).count();
    cout << duration;

    string file1 = "C:\\Users\\Lenovo\\Desktop\\Tema4-PPD\\Secvential\\cmake-build-release\\Outputs\\Clasament.txt";
    string file2 = "Outputs\\ClasamentParalel.txt";

    if (areFilesSame(file1, file2))
    {
        // cout << "The files are identical." << endl;
        return 0;
    }
    else
    {
        // cout << "The files are different." << endl;
        return 1;
    }

    return 0;
}

int main(int argc, char *argv[])
{
    int numProblems = 10;
    int numCountries = 5;
    int numPartPerCountry = 80;

    // int numThreads = std::stoi(argv[1]);
    int numThreads = 16;
    // int numReaders = std::stoi(argv[2]);
    int numReaders = 2;

    return runParallel(numProblems, numCountries, numThreads, numReaders);
}
