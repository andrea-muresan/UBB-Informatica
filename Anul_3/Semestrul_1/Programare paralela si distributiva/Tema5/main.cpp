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
#include <condition_variable>

using namespace std;

ofstream g("Outputs\\ClasamentParalel.txt");

struct Participant
{
    int ID;
    int punctaj;
    int tara;
};

class MyQueue
{
    queue<Participant> participants;
    mutex mtx;
    condition_variable insertCV;
    condition_variable getCV;
    int numThreads;
    const int MAX_CAPACITY = 50;

public:
    MyQueue(int numThreads) : numThreads(numThreads) {}

    // Adauga un participant in coada daca spatiul e suficient
    void insert(const Participant &participant)
    {
        unique_lock<mutex> lock(mtx);
        insertCV.wait(lock, [this]
                      { return participants.size() < MAX_CAPACITY; });
        participants.push(participant);
        getCV.notify_one();
    }

    // Ia un participant din coada daca e posibil
    pair<Participant, bool> get()
    {
        unique_lock<mutex> lock(mtx);
        getCV.wait(lock, [this]
                   { return !participants.empty() || numThreads == 0; });
        if (participants.empty())
        {
            return {{}, false};
        }

        Participant participant = participants.front();
        participants.pop();
        insertCV.notify_one();
        return {participant, true};
    }

    // Verifica daca coada poate procesa elemente in continuare
    bool canContinue()
    {
        lock_guard<mutex> lock(mtx);
        return !participants.empty() || numThreads > 0;
    }

    // Decrements the count of active producer threads when one finishes
    void decrementNumThreads()
    {
        lock_guard<mutex> lock(mtx);
        --numThreads;
        getCV.notify_all();
    }
};

class Node
{
public:
    Participant participant;
    Node *next;
    mutex nodeMtx;

    Node(const Participant &participant) : participant(participant), next(nullptr) {}
};

class SinglyLinkedList
{
private:
    Node *head;
    vector<int> participantiDescalificati;
    mutex descalificatiMutex;

public:
    SinglyLinkedList() : head(new Node({-1, 0, 0})) {}

    ~SinglyLinkedList()
    {
        Node *current = head;
        while (current)
        {
            Node *next = current->next;
            delete current;
            current = next;
        }
    }

    void addNode(const Participant &participant);
    void displayList();
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

// Adds or updates a Participant in the list based on their score
void SinglyLinkedList::addNode(const Participant &participant)
{
    bool negativeScore = participant.punctaj < 0;

    // Verificam daca participantul e descalificat
    descalificatiMutex.lock();
    for (int part: participantiDescalificati)
    {
        if (part == participant.ID) {
            descalificatiMutex.unlock();
            return;
        }
    }

    // Daca punctaj < 0, descalificam participantul
    if (negativeScore)
    {
        participantiDescalificati.push_back(participant.ID);
    }

    Node *prev = head;
    prev->nodeMtx.lock();
    Node *current = head->next;

    descalificatiMutex.unlock();
    bool alreadyExists = false;
    // Verificam daca participantul exista
    while (current != nullptr && !alreadyExists)
    {
        current->nodeMtx.lock();
        if (current->participant.ID == participant.ID)
        {
            alreadyExists = true;
            // Actualizam scorul participantului daca il gasim si punctaj >= 0
            if (participant.punctaj >= 0)
            {
                current->participant.punctaj += participant.punctaj;
            }
            else // Daca punctaj < 0, stergem nodul
            {
                prev->next = current->next;
            }
        }
        prev->nodeMtx.unlock();
        prev = current;
        current = current->next;
    }

    // Daca nu exitsta, adaugam participantul daca are punctaj >= 0
    if (!alreadyExists && participant.punctaj >= 0)
    {
        Node *newNode = new Node(participant);
        prev->next = newNode;
    }
    prev->nodeMtx.unlock();
    // Eliberam spatiul nodului sters
    if (alreadyExists && participant.punctaj < 0)
    {
        delete prev;
    }
}

void SinglyLinkedList::displayList()
{
    Node *current = head;
    int position = 1;
    while (current->participant.ID != -1)
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

void workerThread(SinglyLinkedList &clasamentTari, MyQueue &queue)
{
    while (queue.canContinue())
    {
        auto participant = queue.get();
        if (participant.second)
        {
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

int main(int argc, char *argv[])
{
    // if (argc < 3)
    // {
    //     cerr << "Usage: " << argv[0] << " <numWorkers> <numReaders>" << endl;
    //     return 1;
    // }

    int numWorkers = 6;
    int numReaders = 2;
    ofstream k("Outputs\\parametri.txt");
    k << numWorkers << " " << numReaders;
    k.close();

    int numProblems = 10;
    int numCountries = 5;

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

    SinglyLinkedList clasamentTari;
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

    for (int i = 0; i < numWorkers; ++i)
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

    clasamentTari.sortList();
    clasamentTari.displayList();

    auto endTime = chrono::high_resolution_clock::now();

    auto duration = chrono::duration_cast<chrono::milliseconds>(endTime - startTime).count();

    auto durationString = to_string(duration);
    cout << durationString;

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
