using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;

namespace TPLLab
{
    class ParallelMatrixMultiply : IMatrixMultiply
    {
        int[] A;
        int[] B;
        int[] C;
        int size;

        public ParallelMatrixMultiply(int[] A, int[] B, int[] C, int size)
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
            Parallel.For(0, size, i =>
            {
                for (int j = 0; j < size; j++)
                {

                    for (int k = 0; k < size; k++)
                        temp += A[i * size + k] * B[j * size + k];

                    C[i * size + j] = temp;
                    temp = 0;
                }
            });

            sw.Stop();

            Console.WriteLine("Time parallel for size = " + size + " is " + sw.ElapsedMilliseconds.ToString() + "ms");
        }
    }
}
