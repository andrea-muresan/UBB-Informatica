#include <mpi.h>

#include <stdio.h>

#include <stdlib.h>

#include <string.h>

#include <iostream>

#include <string>

#include <cstdlib>

using namespace std;

MPI_Request recv_request[10]; // maxim 10 procese

void print(int* a, int n) {
    for (int i = 0; i < n; ++i) {
        cout << a[i] << ' ';
    }
    cout << endl;
}

//int main(int argc, char** argv) {
//
//    MPI_Init(&argc, &argv);
//
//    int world_rank;
//    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
//
//    int world_size;
//    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
//
//
//    const int n = 10;
//    int a[n], b[n], c[n];
//    int start, end;
//      MPI_Status status;
//    if (world_rank == 0) {
//        for (int i = 0; i < n; i++) {
//            a[i] = rand() % 10;
//            b[i] = rand() % 10;
//        }
//
//        const int p = world_rank - 1;
//        const int step = n / p;
//        int rest = n % p;
//        start = 0; end = step;
//        for (int i = 1; i < world_size; i++) {
//            if (rest) {
//                end++;
//                rest--;
//            }
//
//            MPI_Send(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//            MPI_Send(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//            MPI_Send(a + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD);
//            MPI_Send(b + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD);
//
//            start = end;
//            end += step;
//        }
//
//        for (int i = 1; i < world_size; i++) {
//            MPI_Recv(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//            MPI_Recv(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//            MPI_Recv(c + start, end - start , MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//        }
//
//        print(a, n);
//        print(b, n);
//    }
//    else {
//        MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//        MPI_Recv(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//        MPI_Recv(a + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//        MPI_Recv(b + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//   
//        for (int i = start; i < end; i++) {
//            c[i] = a[i] + b[i];
//
//            MPI_Send(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//            MPI_Send(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//            MPI_Send(c + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD);
//        }
//    
//    }
//
//    
//
//	MPI_Finalize();
//
//	return 0;
//
//}

int main(int argc, char** argv) {

    MPI_Init(&argc, &argv);

    int world_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);


    const int n = 10;
    int a[n], b[n], c[n];
    MPI_Status status;
    
    if (world_rank == 0) {
        for (int i = 0; i < n; i++) {
            a[i] = rand() % 10;
            b[i] = rand() % 10;
        }

        
    }
  
    int chunk_size = n / world_size;
    int* aux_a = new int[chunk_size];
    int* aux_b = new int[chunk_size];
    int* aux_c = new int[chunk_size];


    MPI_Scatter(a, chunk_size, MPI_INT, aux_a, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(b, chunk_size, MPI_INT, aux_b, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    for (int i = 0; i < chunk_size; i++) {
        aux_c[i] = aux_a[i] + aux_b[i];
    }

    MPI_Gather(aux_c, chunk_size, MPI_INT, c, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    if (world_rank == 0) {
        print(a, n);
        print(b, n);
        print(c, n);
    }
    // MPI_Scatterv
    // MPI_Gatherv
    // contolam start si end

    delete[] aux_a;
    delete[] aux_b;
    delete[] aux_c;

    MPI_Finalize();

    return 0;

}