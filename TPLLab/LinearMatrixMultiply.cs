using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;

namespace TPLLab
{
    class LinearMatrixMultiply : IMatrixMultiply
    {
        int[] A;
        int[] B;
        int[] C;
        int size;

        public LinearMatrixMultiply(int[] A, int[] B, int[] C, int size)
        {
            this.A = A;
            this.B = B;
            this.C = C;
            this.size = size;
        }

        void IMatrixMultiply.multiply()
        {
            Stopwatch sw = Stopwatch.StartNew();

            int temp = 0;
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {

                    for (int k = 0; k < size; k++)
                        temp += A[i * size + k] * B[j * size + k];

                    C[i * size + j] = temp;
                    temp = 0;
                }
            }

            sw.Stop();

            Console.WriteLine("Time linear for size = " + size + " is " + sw.ElapsedMilliseconds.ToString() + "ms");
        }
    }
}
