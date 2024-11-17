#include <iostream>
#include <fstream>
#include <chrono>
#include <string>
using namespace std;

const string OUTPUT_PATH = "..\\..\\Outputs\\sequential.txt";

string imagePath = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\data.txt";
string kernelPath = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\filter.txt";
string resultPath = "C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema2\\Tema2_Java\\result.txt";


int **allocateMatrix(int n, int m)
{
    int **matrix = new int *[n];
    for (int i = 0; i < n; i++)
        matrix[i] = new int[m];
    return matrix;
}

void deallocateMatrix(int **matrix, int n)
{
    for (int i = 0; i < n; i++)
        delete[] matrix[i];
    delete[] matrix;
}

void readMatrixFromFile(const string &filePath, int **matrix, int n, int m)
{
    ifstream fin(filePath);
    int nothing;
    fin >> nothing >> nothing;
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
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
int oneKernel(int i, int j, int **image, int **kernel, int N, int M, int K) {
    int newValue = 0;
    int offset = K / 2;
    // Apply the kernel to the current position
    for (int ki = -offset; ki <= offset; ki++) {
        for (int kj = -offset; kj <= offset; kj++) {
            int mi = i + ki;
            int mj = j + kj;
            newValue += matrixElemExtended(mi, mj, image, N, M) * kernel[ki + offset][kj + offset];
        }
    }

    return newValue;
}


/*
 * Copy one row from a matrix
 */
void copyRowFromVectorToMatrix(int **matrix, int *vector, int row, int M)
{
    for (int i = 0; i < M; i++)
    {
        matrix[row][i] = vector[i];
    }
}

/*
 * Copy a vector
 */
void copyVectorToVector(int *vector1, int *vector2, int M)
{
    for (int i = 0; i < M; i++)
    {
        vector2[i] = vector1[i];
    }
}

/*
 * Verify result
 */
bool verify(int **matrix, string filePath, int N, int M) {
    ifstream fin(filePath);
    int value;
    fin >> value >> value;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fin >> value;
            if (matrix[i][j] != value) {
                return false;
            }
        }
    }
            
    fin.close();

    return true;
}

int main(int argc, char *argv[])
{
    int **image;
    int **kernel;
    int N, M, K;

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

    int *buffer1 = new int[M];
    int *buffer2 = new int[M];

    for (int j = 0; j < M; ++j)
    {
        buffer1[j] = oneKernel(0, j, image, kernel, N, M, K);
    }

    for (int i = 1; i < N; ++i)
    {

        for (int j = 0; j < M; ++j)
        {
            buffer2[j] = oneKernel(i, j, image, kernel, N, M, K);
        }

        copyRowFromVectorToMatrix(image, buffer1, i - 1, M);
        copyVectorToVector(buffer2, buffer1, M);
    }
    copyRowFromVectorToMatrix(image, buffer2, N - 1, M);

    auto endTime = chrono::high_resolution_clock::now();


    cout << chrono::duration<double, milli>(endTime - startTime).count();


    if (verify(image, resultPath, N, M) == false) {
        std::cout<< "false";
    }

    deallocateMatrix(kernel, K);
    deallocateMatrix(image, N);

    return 0;
}