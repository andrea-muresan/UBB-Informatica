#include <iostream>
#include <string>
#include <thread>
#include <chrono>
#include <fstream>
using namespace std;

int N = 0, M = 0, n, P;
int **mat, **kernel, **result;

ifstream finImage("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela si distributiva\\Tema1\\Tema1_Java\\data.txt");
ifstream finKernel("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela"" si distributiva\\Tema1\\Tema1_Java\\kernel.txt");
ifstream finResult("C:\\Users\\Lenovo\\Desktop\\Github\\UBB-Informatica\\Anul_3\\Semestrul_1\\Programare paralela"" si distributiva\\Tema1\\Tema1_Java\\result.txt");

/*
 * Read image
 */
void readImage()
{
	finImage >> N >> M;
	mat = new (nothrow) int*[N];
	result = new (nothrow) int*[N];
	if (mat == nullptr || result == nullptr) return;
	for (int i = 0; i < N; i++) {
		mat[i] = new (nothrow) int[M];
		result[i] = new (nothrow) int[M];
		if (mat[i] == nullptr || result[i] == nullptr) return;
		for (int j = 0; j < M; j++) {
			finImage >> mat[i][j];
		}
	}
}

/*
 * Read kernel
 */
void readKernel()
{
	finKernel >> n >> n;
	kernel = new (nothrow) int* [n];
	if (kernel == nullptr) return;
	for (int i = 0; i < n; i++) {
		kernel[i] = new (nothrow) int[n];
		if (kernel[i] == nullptr) return;
		for (int j = 0; j < n; j++) {
			finKernel >> kernel[i][j];
		}
	}
}

/*
 * Verify result
 */
bool verify()
{
	int N2, M2, el;
	finResult >> N2 >> M2;
	if (N != N2 && M != M2)
	{
		return false;
	}
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < M; j++)
		{
			finResult >> el;
			// std:cout<< result[i][j] << " ";

		}
		// std::cout<<"\n";
	}

	return true;
}

/**
 * Retrieves the matrix element at the specified position,
 * adjusting out-of-bounds indices to the nearest valid values for safe access.
 */
int matrixElemExtended(int i, int j) {
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
int oneKernel(int i, int j) {
	int newValue = 0;
	int offset = n / 2;
	// Apply the kernel to the current position
	for (int ki = -offset; ki <= offset; ki++) {
		for (int kj = -offset; kj <= offset; kj++) {
			int mi = i + ki;
			int mj = j + kj;
			newValue += matrixElemExtended(mi, mj) * kernel[ki + offset][kj + offset];
		}
	}

	return newValue;
}

/*
	Applies firter on an image using sequentially; image, result and filter are allocated dynamically
*/
void runSequentiallyDynamic() {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			result[i][j] = oneKernel(i, j);
		}
	}
}

/*
	Thread function - lines
*/
void workerLines(int start, int end) {
	for (int i = start; i <= end; i++)
	{
		for (int j = 0; j < M; j++) {
			result[i][j] = oneKernel(i, j);
		}
	}
}

/*
	Applies filter on an image using P threads; assumes image, result, and filter are statically allocated.
*/
void runParallelDynamicLines() {
	thread threads[16];

	int threadId = 0;

	int linesPerThread = N / P;
	int linesLeft = N % P;

	for (int k = 0; k < N; k += linesPerThread) {
		int start = k;
		if (linesLeft > 0) {
			linesLeft--;
			k++;
		}
		int end = k + linesPerThread - 1;

		threads[threadId] = thread(workerLines, start, end);
		threadId ++;
	}

	// Wait for all threads to complete
	for (int i = 0; i < threadId; i++) {
		threads[i].join();
	}
}

/*
	Thread function - colums
*/
void workerColumns(int start, int end) {
	for (int j = start; j < end; j++)
	{
		for (int i = 0; i < N; i++) {
			result[i][j] = oneKernel(i, j);
		}
	}
}

void runParallelDynamicColumns() {
	thread threads[16];

	int threadId = 0;

	int columnsPerThread = M / P;
	int columnsLeft = M % P;

	for (int k = 0; k < M; k += columnsPerThread) {
		int start = k;
		if (columnsLeft > 0) {
			columnsLeft--;
			k++;
		}
		int end = k + columnsPerThread - 1;

		threads[threadId] = thread(workerColumns, start, end);
		threadId ++;
	}

	// Wait for all threads to complete
	for (int i = 0; i < threadId; i++) {
		threads[i].join();
	}
}

struct Block
{
	int firstLine;
	int firstColumn;
	int lastLine;
	int lastColumn;
	Block(int firstLine, int firstColumn, int lastLine, int lastColumn) : firstLine(firstLine), firstColumn(firstColumn), lastLine(lastLine), lastColumn(lastColumn) {}
	Block() : firstLine(-1), firstColumn(-1), lastLine(-1), lastColumn(-1) {}
};

// Function to split the 2D space into blocks
// Static array 'blocks' will store the resulting blocks
// 'blockIndex' keeps track of where to add the next block
Block* slice2d(int i, int j, int n, int m, int p, Block blocks[], int& blockIndex) {
	if (p == 1) {
		blocks[blockIndex++] = Block(i, j, n, m);
		return blocks;
	}

	if (n - i > m - j) {
		slice2d(i, j, (n + m)/ 2, m, p / 2, blocks, blockIndex);
		slice2d(i + n / 2, j, n - n / 2, m, p / 2 + p % 2, blocks, blockIndex);
	} else {
		slice2d(i, j, n, (m+n) / 2, p / 2 + p % 2, blocks, blockIndex);
		slice2d(i, j + m / 2, n, m - m / 2, p / 2, blocks, blockIndex);
	}

	return blocks;
}

/*
	Thread function - colums
*/
void workerBlock(Block block) {
	for (int i = block.firstLine; i < block.lastLine; i++) {
		for (int j = block.firstColumn; j < block.lastColumn; j++) {
			result[i][j] = oneKernel(i, j);
		}
	}
}

void runParallelDynamicBlocks() {
	thread threads[16];

	int threadId = 0;
	int columnsPerThread = M / P;
	int columnsLeft = M % P;
	Block blocks[100];

	int blockIndex = 0;
	slice2d(0, 0, N, M, P, blocks, blockIndex);

	for (int t = 0; t < P; t++) {
		Block block = blocks[t];

		threads[threadId] = thread(workerBlock, block);
		threadId ++;
	}

	// Wait for all threads to complete
	for (int i = 0; i < P; i++) {
		threads[i].join();
	}
}

void workerLinear(int start, int end) {
	for (int t = start; t < end; t++) {
		int i = t / M;
		int j = t % M;
		result[i][j] = oneKernel(i, j);
	}
}

void runParallelDynamicLinear() {
	thread threads[16];

	int len = N * M;
	int lenPerThread = len / P;
	int lenLeft = len % P;

	int start = 0, end = lenPerThread;
	for (int threadId = 0; threadId < P; threadId++) {
		if (lenLeft > 0)
		{
			lenLeft--;
			end++;
		}

		threads[threadId] = thread(workerLinear, start, end);

		start = end;
		end = start + lenPerThread;
	}

	// Wait for all threads to complete
	for (int i = 0; i < P; i++) {
		threads[i].join();
	}
}

void workerCyclic(int start, int len) {
	for (int t = start; t < len; t += P) {
		int i = t / M;
		int j = t % M;
		result[i][j] = oneKernel(i, j);
	}
}

void runParallelDynamicCyclic() {
	thread threads[16];

	int len = N * M;

	for (int threadId = 0; threadId < P; threadId++) {
		threads[threadId] = thread(workerCyclic, threadId, len);
	}

	// Wait for all threads to complete
	for (int i = 0; i < P; i++) {
		threads[i].join();
	}
}

int main(int argc, char** argv) {
	auto startTime = chrono::high_resolution_clock::now();

	readImage();
	readKernel();

	// P = stoi(argv[1]);
	P = 16;
	if (P == 1) {
		runSequentiallyDynamic();
	}
	else {
		// runParallelDynamicLines();
		// runParallelDynamicColumns();
		runParallelDynamicBlocks();
		// runParallelDynamicLinear();
		// runParallelDynamicCyclic();
	}

	auto endTime = chrono::high_resolution_clock::now();
	cout << chrono::duration<double, milli>(endTime - startTime).count();
	return 0;
}