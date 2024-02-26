public class Main {
    private static final int SIZE = 3;
    private static final int[] RANGE = {-10, 10};

    public static double[][] initializeMatrix() {
        double[][] matrix = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = (Math.random()*(RANGE[1]-RANGE[0]));
            }
        }
        return matrix;
    }


    public static double[] getVector() {
        double[] f = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                f[i] += Math.random()*RANGE[1];
            }
        }
        return f;
    }

    public static void printMatrix(double[][] matrix, double[] f) {
        for (int i = 0; i < SIZE; i++) {
            for (double v : matrix[i]) {
                System.out.printf("%7.2f", v);
            }
            System.out.println(" | " + String.format("%3.0f", f[i]));
        }
    }

    public static void printMatrix(double[] f) {
            for (double v : f) {
                System.out.println(v);
        }
    }

    public static double[] gaussMethod(double[][] matrix, double[] f) {
        double[][] clonedMatrix = matrix.clone();
        double[] clonedF = f.clone();
        for (int i = 0; i < SIZE; i++) {
            clonedMatrix[i] = matrix[i].clone();
        }

        //Для метода Гауса в матрице не должно быть нулевых столбцов
        //Прямой ход
        if (noZeroColumns(clonedMatrix)) {
            for (int i = 0; i < SIZE; i++) {
                if (getIndexOfMaxInColumn(clonedMatrix, i) == i) {
                    for (int j = i + 1; j < SIZE; j++) {
                        clonedMatrix[i][j] /= clonedMatrix[i][i];
                    }
                    clonedF[i] /= clonedMatrix[i][i];
                    clonedMatrix[i][i] = 1;

                    for (int j = i + 1; j < SIZE; j++) {
                        for (int k = i + 1; k < SIZE; k++) {
                            clonedMatrix[j][k] -= clonedMatrix[i][k] * clonedMatrix[j][i];
                        }
                        clonedF[j] -= clonedMatrix[j][i] * clonedF[i];
                        clonedMatrix[j][i] = 0;
                    }
                } else {
                    int index = getIndexOfMaxInColumn(clonedMatrix, i);
                    swap(clonedMatrix, index, i);
                    swap(clonedF, index, i);
                    i--;
                }
            }

            //Обратный ход
            for (int i = SIZE - 2; i >= 0; i--) {
                for (int j = SIZE - 1; j > i; j--) {
                    clonedF[i] -= clonedMatrix[i][j] * clonedF[j];
                }
            }
        } else {
            System.out.println("В матрице есть нулевой столбец");
            return null;
        }
        return clonedF;
    }

    public static boolean noZeroColumns(double[][] matrix) {
        for (int j = 0; j < SIZE; j++) {
            int counter = 0;
            for (double[] doubles : matrix) {
                if (doubles[j] == 0) {
                    counter++;
                }
            }
            if (counter == SIZE) {
                return false;
            }
        }
        return true;
    }

    public static int getIndexOfMaxInColumn(double[][] matrix, int column) {
        int maxIndex = 0;
        for (int i = 1; i < SIZE; i++) {
            if (Math.abs(matrix[i][column]) > Math.abs(matrix[maxIndex][column])) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void swap(double[][] matrix, int firstIndex, int secondIndex) {
        double[] temp = matrix[firstIndex];
        matrix[firstIndex] = matrix[secondIndex];
        matrix[secondIndex] = temp;
    }

    public static void swap(double[] f, int firstIndex, int secondIndex) {
        double temp = f[firstIndex];
        f[firstIndex] = f[secondIndex];
        f[secondIndex] = temp;
    }

    public static void main(String[] args) {
        double[][] matrix = initializeMatrix();
        double[] f = getVector();
        System.out.println("Матрица:");
        printMatrix(matrix, f);
        double[] calculatedX = gaussMethod(matrix, f);
        System.out.println("Ответ:");
        printMatrix(calculatedX);
    }
}