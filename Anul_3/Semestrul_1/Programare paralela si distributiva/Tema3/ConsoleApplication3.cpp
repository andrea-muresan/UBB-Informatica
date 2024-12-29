#include <iostream>
#include <fstream>
#include <mpi.h>
#include <chrono>
#include <vector>
#include "mpi.h"

using namespace std;

string file1 = "C:\\Users\\Lenovo\\Desktop\\ConsoleApplication3\\number1.txt";
string file2 = "C:\\Users\\Lenovo\\Desktop\\ConsoleApplication3\\number2.txt";
string file3 = "C:\\Users\\Lenovo\\Desktop\\ConsoleApplication3\\number3.txt";

void generateNumber(int digitsCount, string filename) {
    ofstream fout(filename);
    if (fout.is_open()) {
        fout << digitsCount << "\n";
        for (int i = 0; i < digitsCount; i++) {
            int digit = rand() % 10;
            fout << digit;
        }
        fout.close();
    }
    else {
        cout << "Unable to open file.";
    }
}


// Implementare secventiala
void run0() {
    chrono::steady_clock::time_point startTime, endTime;
    startTime = chrono::high_resolution_clock::now();

    ifstream fin1(file1), fin2(file2);
    ofstream fout(file3);

    if (!fin1 || !fin2) {
        cerr << "Unable to open files." << endl;
        return;
    }

    // Citirea numerelor invers
    int n1, n2;
    fin1 >> n1;
    fin2 >> n2;

    vector<int> num1(n1), num2(n2), result(max(n1, n2) + 1, 0);

    for (int i = n1 - 1; i >= 0; i--) {
        char ch;
        fin1 >> ch;
        num1[i] = ch - '0';
    }
    for (int i = n2 - 1; i >= 0; i--) {
        char ch;
        fin2 >> ch;
        num2[i] = ch - '0';
    }

    fin1.close();
    fin2.close();


    // Adunarea cifrelor
    int carry = 0, n = max(n1, n2);
    for (int i = 0; i < n; i++) {
        int digit1 = (i < n1) ? num1[i] : 0;
        int digit2 = (i < n2) ? num2[i] : 0;
        int sum = digit1 + digit2 + carry;

        result[i] = sum % 10;
        carry = sum / 10;
    }

    // Adaugarea transportului final, daca exista
    if (carry > 0) {
        result[n] = carry;
        n++;
    }

    // Scrierea rezultatului
    fout << n << "\n";
    for (int i = n - 1; i >= 0; i--) {
        fout << result[i];
    }
    fout.close();


    endTime = chrono::high_resolution_clock::now();
    cout << chrono::duration<double, milli>(endTime - startTime).count();
    cout << "\n";
}




#define MAX_DIGITS 10000  // limita maxima de cifre pentru un numar mare


int run1(int argc, char* argv[]) {
    chrono::steady_clock::time_point startTime, endTime;
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (size < 2) {
        if (rank == 0) {
            std::cerr << "E nevoie de cel puțin 2 procese (1 master și 1 worker).\n";
        }
        MPI_Finalize();
        return -1;
    }

    const int WORKERS = size - 1; // Procesele de lucru

    if (rank == 0) {
        startTime = chrono::high_resolution_clock::now();

        // Procesul master
        std::ifstream in1(file1), in2(file2);
        if (!in1.is_open() || !in2.is_open()) {
            std::cerr << "Eroare la deschiderea fișierelor.\n";
            MPI_Finalize();
            return -1;
        }

        // Citim dimensiunea și datele vectorilor
        int size1, size2;
        in1 >> size1;
        in2 >> size2;


        std::vector<int> vector1(size1), vector2(size2);
        for (int i = size1 - 1; i >= 0; i--) {
            char ch;
            in1 >> ch;
            vector1[i] = ch - '0';
        }
        for (int i = size2 - 1; i >= 0; i--) {
            char ch;
            in2 >> ch;
            vector2[i] = ch - '0';
        }

        in1.close();
        in2.close();

        // Determinăm dimensiunea maximă și dimensiunea chunk-ului
        int N_max = std::max(size1, size2);
        int chunk_size = (N_max + WORKERS - 1) / WORKERS; // Rotunjire în sus


        // Trimitem dimensiunea chunk-urilor la workeri
        for (int i = 1; i <= WORKERS; ++i) {
            MPI_Send(&chunk_size, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
        }

        std::vector<int> final_result;

        // Trimitem bucățile de date la workeri
        for (int i = 0; i < WORKERS; ++i) {
            int start = i * chunk_size;

            std::vector<int> chunk1(chunk_size, 0), chunk2(chunk_size, 0);
            for (int j = 0; j < chunk_size; ++j) {
                if (start + j < size1) chunk1[j] = vector1[start + j];
                if (start + j < size2) chunk2[j] = vector2[start + j];
            }

            MPI_Send(chunk1.data(), chunk_size, MPI_INT, i + 1, 1, MPI_COMM_WORLD);
            MPI_Send(chunk2.data(), chunk_size, MPI_INT, i + 1, 2, MPI_COMM_WORLD);
            

            // Primim rezultatele de la workeri si le adaugam in resultatul final
            std::vector<int> partial_result(chunk_size);
            MPI_Recv(partial_result.data(), chunk_size, MPI_INT, i + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
           
            
            for (int nr : partial_result) {
                //std::cout << nr;
                final_result.push_back(nr);
            }

            

        }

        int prev_carry = 0;
        MPI_Recv(&prev_carry, 1, MPI_INT, WORKERS, 4, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        // Elimina 0-urile
        while(final_result.size() != N_max) {
            final_result.pop_back();
        }
        // Adauga ultimul carry flag
        if (prev_carry != 0) {
            final_result.push_back(prev_carry);
        }

        
        /*for (int i = final_result.size() - 1; i >= 0; i--) {
            std::cout << final_result.at(i);
        }
        cout << endl;*/
        

        std::ifstream verifIn(file3);
        int nr;
        verifIn >> nr;
        if (nr != final_result.size())
            return 1;
        for (int i = final_result.size() - 1; i >= 0; i--) {
            char ch;
            verifIn >> ch;
            if (final_result.at(i) != ch - '0')
                 return 1;
        }
        
        verifIn.close();

    }
    else {
        // Procesele worker
        int chunk_size;
        MPI_Recv(&chunk_size, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        std::vector<int> recv1(chunk_size), recv2(chunk_size);
        MPI_Recv(recv1.data(), chunk_size, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(recv2.data(), chunk_size, MPI_INT, 0, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);


        std::vector<int> result(chunk_size);
        int carry = 0;

        // Calculăm suma și carry-ul
        for (int i = 0; i < chunk_size; ++i) {
            int sum = recv1[i] + recv2[i] + carry;
            result[i] = sum % 10;
            carry = sum / 10;
        }



        if (rank == 1) {
            // Dam startul la trimiterea carry-ului
             MPI_Send(&carry, 1, MPI_INT, 2, 4, MPI_COMM_WORLD);
            //Trimitem date la procesul 0
            MPI_Send(result.data(), chunk_size, MPI_INT, 0, 0, MPI_COMM_WORLD);
        }
        if (rank > 1) { //// Primim carry-ul de la procesul anterior, dacă există
            int prev_carry = 0;
            MPI_Recv(&prev_carry, 1, MPI_INT, rank - 1, 4, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            //std::cout << "Procesul " << rank << " a primit carryul:\n" << prev_carry;
            for (int i = 0; i < chunk_size && prev_carry > 0; ++i) {
                int sum = result[i] + prev_carry;
                result[i] = sum % 10;
                prev_carry = sum / 10;
            }

            if (rank < WORKERS) {
                MPI_Send(&carry, 1, MPI_INT, rank + 1, 4, MPI_COMM_WORLD);
            }
              
            // Trimitem date la procesul 0
            MPI_Send(result.data(), chunk_size, MPI_INT, 0, 0, MPI_COMM_WORLD);

            if (rank == WORKERS) {
                MPI_Send(&carry, 1, MPI_INT, 0, 4, MPI_COMM_WORLD);
            }
        }

    }

    MPI_Finalize();

    if (rank == 0) {
        endTime = chrono::high_resolution_clock::now();
        cout << chrono::duration<double, milli>(endTime - startTime).count();
    }

    return 0;
}


int run2(int argc, char* argv[]) {
    chrono::steady_clock::time_point startTime, endTime; 
    MPI_Status status; 
    int p, myRank;

    int vector1[10000] = { 0 }, vector2[10000] = { 0 }, result[10000] = { 0 }; 
    int N_max = 0; 

    MPI_Init(&argc, &argv); 

    MPI_Comm_size(MPI_COMM_WORLD, &p); 
    MPI_Comm_rank(MPI_COMM_WORLD, &myRank);

    ifstream fin1(file1); 
    ifstream fin2(file2); 

    // Citim dimensiunile vectorilor
    int n1, n2, n;
    fin1 >> n1;
    fin2 >> n2;

    // Dimensiunea maxima
    if (n1 > n2) n = n1;
    else n = n2;

    N_max = n; // Copie n initial

    // Modifica n astfel incat p|n
    while (n % p != 0) n++;

   
    if (myRank == 0) {
        startTime = chrono::high_resolution_clock::now();

      
        for (int i = n1 - 1; i >= 0; i--) {
            char ch;
            fin1 >> ch;
            vector1[i] = ch - '0';
        }

        for (int i = n2 - 1; i >= 0; i--) {
            char ch;
            fin2 >> ch;
            vector2[i] = ch - '0';
        }

        fin1.close();
        fin2.close(); 
    }

    
    // Calculeaza chunk size 
    int chunk_size = n / p;
    
    int* aux_vector1 = new int[chunk_size];
    int* aux_vector2 = new int[chunk_size];
    int* aux_result = new int[chunk_size];

    // Impartim in chunk-uri `vector1` si `vector2` pentru toate procesele
    MPI_Scatter(vector1, chunk_size, MPI_INT, aux_vector1, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(vector2, chunk_size, MPI_INT, aux_vector2, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    int carry = 0; 

    // Primim carry de la procesele anterioare
    if (myRank > 0) {
        MPI_Recv(&carry, 1, MPI_INT, myRank - 1, 0, MPI_COMM_WORLD, &status);
    }

    // Calculam suma local, incluzand carry-ul
    for (int i = 0; i < chunk_size; i++) {
        int digit = aux_vector1[i] + aux_vector2[i] + carry;
        if (digit > 9) {
            carry = 1;
            digit %= 10;
        }
        else {
            carry = 0;
        }
        aux_result[i] = digit;
    }

    // Trimitem carry-ul mai departe
    if (myRank < p - 1) {
        MPI_Send(&carry, 1, MPI_INT, myRank + 1, 0, MPI_COMM_WORLD);
    }
    else {
        // Trimitem carryul ultimului proces la 0
        MPI_Send(&carry, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    // Adunam rezultatele
    MPI_Gather(aux_result, chunk_size, MPI_INT, result, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    if (myRank == 0) {
        // Ultimul carry
        MPI_Recv(&carry, 1, MPI_INT, p - 1, 0, MPI_COMM_WORLD, &status);

        // Eliminam 0-urile
        while (n != N_max) {
            n--;
        }

        // Verificam raspunsul
        ifstream verifIn(file3);
        if (carry != 0) n++;
        int nr;
        verifIn >> nr;
        if (carry != 0) {
            char ch;
            verifIn >> ch;
            if (ch - '0' != carry)
                return 1; 
        }
        for (int i = n - 1; i >= 0; i--) {
            char ch;
            verifIn >> ch;
            if (ch - '0' != result[i])
                return 1; 
        }
    }

    MPI_Finalize(); 

    if (myRank == 0) {
        endTime = chrono::high_resolution_clock::now();
        cout << chrono::duration<double, milli>(endTime - startTime).count();
    }

    return 0; 
}


int main(int argc, char** argv) {
    generateNumber(100, file1);
    generateNumber(1000, file2);

    //run0();
    

    return run2(argc, argv);
    return 0;
}
