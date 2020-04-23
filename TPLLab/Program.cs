using System;
using System.Collections.Generic;

namespace TPLLab
{
    class Program
    {
        static void Main(string[] args)
        {
            Random random = new Random();

            IList<int> sizes = new List<int>() { 100, 1000, 5000 };

            foreach (int size in sizes)
            {
                int[] A = new int[size * size];
                int[] B = new int[size * size];
                int[] C = new int[size * size];

                for (int i = 0; i < size * size; i++)
                {
                    A[i] = random.Next(10) + 1;
                    B[i] = random.Next(10) + 1;
                }

                transpose(B, size);

                IMatrixMultiply multiplicator = new LinearMatrixMultiply(A, B, C, size);

                multiplicator.multiply();

                multiplicator = new ParallelMatrixMultiply(A, B, C, size);

                multiplicator.multiply();
            }
        }

        static void transpose(int[] matrix, int size)
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
    }
}
