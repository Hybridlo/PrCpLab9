public class LinearMatrixSum {
    private Matrix matrixA;
    private Matrix matrixB;
    private Matrix matrixC;

    LinearMatrixSum(Matrix matrixA, Matrix matrixB, Matrix matrixC) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;
    }

    void sum() {
        for (int i = 0; i < matrixA.size(); i++) {
            for (int j = 0; j < matrixA.size(); j++) {
                int temp = 0;

                for (int k = 0; k < matrixA.size(); k++) {
                    temp += matrixA.get(i, k) * matrixB.get(j, k);  //B is transposed
                }

                matrixC.set(i, j, temp);
            }
        }
    }
}
