import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        List<Integer> sizes = new ArrayList<>();
        sizes.add(100);
        sizes.add(1000);
        sizes.add(5000);

        List<Long> linearRes = new ArrayList<>();
        List<Long> FJPRes = new ArrayList<>();

        for (int size : sizes) {    //FJP
            Matrix matrixA = new Matrix();
            Matrix matrixB = new Matrix();
            Matrix matrixC = new Matrix();

            matrixA.initMatrixRandom(size);
            matrixB.initMatrixRandom(size);
            matrixC.initMatrixZero(size);

            matrixB.transpose();

            long start = System.currentTimeMillis();

            new ForkJoinPool().invoke(new MatrixSumCalc(matrixA, matrixB, matrixC, 0, 0, 0));

            long end = System.currentTimeMillis();

            FJPRes.add(end - start);

            matrixC.initMatrixZero(size);

            start = System.currentTimeMillis();

            LinearMatrixSum sumClass = new LinearMatrixSum(matrixA, matrixB, matrixC);
            sumClass.sum();

            end = System.currentTimeMillis();

            linearRes.add(end - start);
        }

        for (int i = 0; i < sizes.size(); i++) {
            System.out.println("Time elapsed for linear algorithm, size " + sizes.get(i) + " = " + linearRes.get(i) +  "ms");
        }

        for (int i = 0; i < sizes.size(); i++) {
            System.out.println("Time elapsed for FJP, size " + sizes.get(i) + " = " + FJPRes.get(i) +  "ms");
        }
    }
}
