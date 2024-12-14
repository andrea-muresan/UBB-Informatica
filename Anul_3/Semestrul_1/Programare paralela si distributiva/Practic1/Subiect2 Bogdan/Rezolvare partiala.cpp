#include <iostream>
#include <fstream>
#include <mpi.h>
#include <chrono>
#include <vector>
#include "mpi.h"

using namespace std;

string file1 = "C:\\Users\\Lenovo\\Desktop\\numbers.txt";

void print_vector(int x[], int n)
{
    for (int i = 0; i < n; i++)
        cout << x[i] << " ";
    cout << "\n";
}

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Status status;
    int numere[10000];
    int result[10000];
    int numereSize = 0;
   
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    const int WORKERS = size - 1; // Procesele de lucru

    int X;
    if (rank == 0) {
        cout << "Citeste X:";
        cin >> X;
        std::ifstream file(file1);
        if (!file.is_open()) {
            std::cerr << "Eroare la deschiderea fisierului!" << std::endl;
            return 1; 
        }

        
        int numar;

       
        while (file >> numar) {
            numere[numereSize++] = numar;
        }

        file.close();

        int chunk_size = numereSize / WORKERS;
        int chunk_rest = numereSize % WORKERS;

        int start = 0, end = chunk_size;

        for (int i = 1; i <= WORKERS; i++) {
            if (chunk_rest > 0) {
                chunk_rest--;
                end++;
            }

            // send start and end
            MPI_Send(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            MPI_Send(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD);

            // send parts of the numbers
            MPI_Send(numere + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD);
           
            start = end;
            end += chunk_size;
        }
    } 

    // Broadcast: trimite x de la root (0) catre toate procesele
    MPI_Bcast(&X, 1, MPI_INT, 0, MPI_COMM_WORLD);

    if (rank > 0) {
        int start = 0, end = 0;

        MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

        MPI_Recv(numere + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
        

        for (int i = start; i < end; i++) {
            int nr = numere[i];
            int s = 0;
            while (nr > 0) {
                s += nr % 10;
                nr /= 10;
            }
            
            if (s < X) {
                result[i] = numere[i] * 2;
            }
            else {
                result[i] = numere[i] / 2;
            }
        }

        MPI_Send(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(result + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD);

    }

    if (rank == 0) {
        int start, end;
        for (int i = 1; i <= WORKERS; i++) {
            MPI_Recv(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
            MPI_Recv(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
            
            MPI_Recv(result + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
        }

        print_vector(result, numereSize);
    }
    

    

    MPI_Finalize();
    return 0;
}
