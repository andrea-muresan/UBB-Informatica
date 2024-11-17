#include <iostream>
#include <fstream>
#include <chrono>
#include <string>
#include <thread>
#include <vector>
#include "my_barrier.hpp"

string imagePath = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\data.txt";
string kernelPath = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\filter.txt";
string resultPath = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\result.txt";

int **kernel;
int **image;
int N, M, K, P = 2;

/*
 * Verify result
 */
bool verify(string filePath) {
    ifstream fin(filePath);
    int value;
    fin >> value >> value;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fin >> value;
            if (image[i][j] != value) {
                return false;
            }
        }
    }
            
    fin.close();

    return true;
}

int **allocateMatrix(int n, int m)
{
    int **matrix = new int *[n];
    for (int i = 0; i < n; ++i)
        matrix[i] = new int[m];
    return matrix;
}

void deallocateMatrix(int **matrix, int n)
{
    for (int i = 0; i < n; ++i)
        delete[] matrix[i];
    delete[] matrix;
}

void readMatrixFromFile(const string &filePath, int **matrix, int n, int m)
{
    ifstream fin(filePath);
    int nothing;
    fin >> nothing >> nothing;
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < m; ++j)
            fin >> matrix[i][j];
    fin.close();
}

/**
 * Retrieves the matrix element at the specified position,
 * adjusting out-of-bounds indices to the nearest valid values for safe access.
 */
int matrixElemExtended(int i, int j, int **mat, int N, int M) {
    if (i < 0) {
        i = 0;
    } else if (i >= N) {
        i = N - 1;
    }
    if (j < 0) {
        j = 0;
    } else if (j >= M) {
        j = M - 1;
    }
    return mat[i][j];
}

/**
 * Applies the convolution kernel to a specific element in the matrix at position (i, j)
 */
int oneKernel(int **image, int j) {
    int newValue = 0;
    int offset = K / 2;
    // Apply the kernel to the current position
    for (int ki = -offset; ki <= offset; ki++) {
        for (int kj = -offset; kj <= offset; kj++) {
            int mi = 1 + ki;
            int mj = j + kj;
            newValue += matrixElemExtended(mi, mj, image, N, M) * kernel[ki + offset][kj + offset];
        }
    }

    return newValue;
}

void applyConvolution(int start, int end, my_barrier &barrier)
{
    int *buffFrontLineStart = new int[M];
    int *buffCurrentLine = new int[M];
    int *buffFrontLineEnd = new int[M];

    // get the last row - previous thread
    if (start == 0)
    {
        copy(image[0], image[0] + M, buffFrontLineStart);
    }
    else
    {
        copy(image[start - 1], image[start - 1] + M, buffFrontLineStart);
    }

    // get the first row - next thread
    if (end == N)
    {
        copy(image[N - 1], image[N - 1] + M, buffFrontLineEnd);
    }
    else
    {
        copy(image[end], image[end] + M, buffFrontLineEnd);
    }

    barrier.wait();

    // current row
    copy(image[start], image[start] + M, buffCurrentLine);

    int **buffer = allocateMatrix(3, M);
    copy(buffFrontLineStart, buffFrontLineStart + M, buffer[0]);
    copy(buffCurrentLine, buffCurrentLine + M, buffer[1]);
    copy(image[start + 1], image[start + 1] + M, buffer[2]);

    for (int i = start; i < end - 1; ++i)
    {
        for (int j = 0; j < M; ++j)
        {
            image[i][j] = oneKernel(buffer, j);
        }
        copy(buffer[1], buffer[1] + M, buffer[0]);
        copy(buffer[2], buffer[2] + M, buffer[1]);
        if (i + 2 == N || i + 2 == end)
            copy(buffFrontLineEnd, buffFrontLineEnd + M, buffer[2]);
        else
            copy(image[i + 2], image[i + 2] + M, buffer[2]);
    }

    for (int j = 0; j < M; ++j)
    {
        image[end - 1][j] = oneKernel(buffer, j);
    }

    deallocateMatrix(buffer, 3);
    delete[] buffFrontLineStart;
    delete[] buffCurrentLine;
    delete[] buffFrontLineEnd;
}

void startThreads(my_barrier &barrier)
{
    int start = 0, end;
    int quotient = N / P;
    int remainder = N % P;
    vector<thread> threads(P);
    for (int i = 0; i < P; ++i)
    {
        end = start + quotient + (i < remainder ? 1 : 0);
        threads[i] = thread(applyConvolution, start, end, ref(barrier));
        start = end;
    }
    for (int i = 0; i < P; ++i)
    {
        threads[i].join();
    }
}

int main(int argc, char *argv[])
{
    // P = stoi(argv[1]);

    my_barrier barrier(P);

    ifstream fin(kernelPath);
    fin >> K >> K;
    fin.close();
    kernel = allocateMatrix(K, K);

    ifstream fin2(imagePath);
    fin2 >> N >> M;
    fin2.close();

    image = allocateMatrix(N, M);

    readMatrixFromFile(kernelPath, kernel, K, K);
    readMatrixFromFile(imagePath, image, N, M);

    auto startTime = chrono::high_resolution_clock::now();

    startThreads(barrier);

    auto endTime = chrono::high_resolution_clock::now();

    cout << chrono::duration<double, milli>(endTime - startTime).count();

    if (verify(resultPath) == false) {
        std::cout<<"false";
    }

    deallocateMatrix(kernel, K);
    deallocateMatrix(image, N);

    return 0;
}