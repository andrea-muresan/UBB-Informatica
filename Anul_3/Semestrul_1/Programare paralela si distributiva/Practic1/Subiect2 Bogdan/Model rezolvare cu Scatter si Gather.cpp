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
    }

    // Broadcast: trimite x de la root (0) catre toate procesele
    MPI_Bcast(&numereSize, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&X, 1, MPI_INT, 0, MPI_COMM_WORLD);

    // Calculeaza chunk size 
    int chunk_size = numereSize / WORKERS;

    int* aux_numere = new int[chunk_size];
    int* aux_result = new int[chunk_size];


    // Impartim in chunk-uri `vector1` si `vector2` pentru toate procesele
    MPI_Scatter(numere, chunk_size, MPI_INT, aux_numere, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    for (int i = 0; i < chunk_size; i++) {
        int nr = numere[i];
        int s = 0;
        while (nr > 0) {
            s += nr % 10;
            nr /= 10;
        }

        if (s < X) {
            aux_result[i] = aux_numere[i] * 2;
        }
        else {
            aux_result[i] = aux_numere[i] / 2;
        }
    }

    // Adunam rezultatele
    MPI_Gather(aux_result, chunk_size, MPI_INT, result, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    if (rank == 0) {
        for (int i = 0; i < numereSize; i++) {
            cout << result[i] << " ";
        }
    }

    MPI_Finalize();
    return 0;
}
