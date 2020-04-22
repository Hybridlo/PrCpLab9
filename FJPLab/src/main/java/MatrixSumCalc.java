import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MatrixSumCalc extends RecursiveAction {
    private Matrix matrixA;
    private Matrix matrixB;
    private Matrix matrixC;
    private int depth;
    int rowA;
    int columnB;

    MatrixSumCalc(Matrix matrixA, Matrix matrixB, Matrix matrixC, int depth, int rowA, int columnB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;
        this.depth = depth;
        this.rowA = rowA;
        this.columnB = columnB;
    }

    @Override
    protected void compute() {
        List<MatrixSumCalc> subTasks = new ArrayList<>();

        if (depth == 0) {       //give each subtask a row from matrix A
            for (int i = 0; i < matrixA.size(); i++) {
                MatrixSumCalc task = new MatrixSumCalc(matrixA, matrixB, matrixC, 1, i, 0);
                task.fork();
                subTasks.add(task);
            }
        }

        if (depth == 1) {       //give each subtask a column from matrix B
            for (int j = 0; j < matrixB.size(); j++) {
                int temp = 0;

                for (int k = 0; k < matrixA.size(); k++) {
                    temp += matrixA.get(rowA, k) * matrixB.get(j, k);     //B is transposed
                }

                matrixC.set(rowA, columnB, temp);
            }
        }

        for (MatrixSumCalc task : subTasks) {
            task.join();
        }
    }
}
