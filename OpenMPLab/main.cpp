#include <chrono>
#include <stdlib.h>
#include <iostream>
#include <vector>

using namespace std;

void transpose(int*& matrix, int size)
{
	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j < size; j++)
		{
			int tmp = matrix[i * size + j];
			matrix[i * size + j] = matrix[j * size + i];
			matrix[j * size + i] = tmp;
		}
	}
}

void linearMatrixMultiplication(int*& A, int*& B, int*& C, int size)
{
	int temp = 0;
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {

			for (int k = 0; k < size; k++)
				temp += A[i * size + k] * B[j * size + k];

			C[i * size + j] = temp;
			temp = 0;
		}
	}
}

void parallelMatrixMultiplication(int*& A, int*& B, int*& C, int size)
{
	#pragma omp parallel
	{
		int temp = 0;
		#pragma omp for
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				for (int k = 0; k < size; k++)
					temp += A[i * size + k] * B[j * size + k];

				C[i * size + j] = temp;
				temp = 0;
			}
		}
	}
}

int main()
{
	srand(time(0));		//init random

	vector<int> sizes = { 100, 1000, 5000 };

	for (int size : sizes)
	{
		int* A;
		int* B;
		int* C;

		chrono::time_point<std::chrono::high_resolution_clock> start, end;

		A = new int[size * size];
		B = new int[size * size];
		C = new int[size * size];

		for (int i = 0; i < size * size; i++) {			//init matrices
			A[i] = rand() % 10 + 1;
			B[i] = rand() % 10 + 1;
		}

		transpose(B, size);

		start = chrono::high_resolution_clock::now();	//get start time point of multiplication

		linearMatrixMultiplication(A, B, C, size);

		end = chrono::high_resolution_clock::now();

		int millisec = chrono::duration_cast<chrono::milliseconds>(end - start).count();

		cout << "Time linear for size = " << size << " is " << millisec << "ms\n";

		start = chrono::high_resolution_clock::now();

		parallelMatrixMultiplication(A, B, C, size);

		end = chrono::high_resolution_clock::now();

		millisec = chrono::duration_cast<chrono::milliseconds>(end - start).count();

		cout << "Time parallel for size = " << size << " is " << millisec << "ms\n";
	}

	return 0;
}