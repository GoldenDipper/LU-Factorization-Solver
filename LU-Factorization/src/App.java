public class App {
    static int[][] positions = { { 1, 0 }, { 2, 0 }, { 2, 1 } };

    public static void main(String[] args) throws Exception {

        int[][] MatrixU = { { 1, 0, 1, 3 }, { -2, 1, -2, -1 }, { 5, 3, 1, 2 } };
        int[][] MatrixL = new int[3][4];
        int[] MatrixLResults = { 1, 1, 1 };
        int[] MatrixUResults = { 1, 1, 1 };
        int[] equalizingValues = new int[3];

        populateMatrixL(MatrixL, MatrixU);

        display2DMatrix(MatrixU);

        clearValueCalculator(MatrixU, equalizingValues);

        inputValuesToMatrixL(MatrixL, equalizingValues);

        linerEquationMatrixL(MatrixL, MatrixLResults);

        for (int i = 0; i < (MatrixL[0].length - 1); i++) {

            MatrixU[i][3] = MatrixLResults[i];

        }
        
        linerEquationMatrixU(MatrixU, MatrixUResults);

    }

    public static void display2DMatrix(int[][] Matrix) {
        for (int i = 0; i < Matrix.length; i++) {
            for (int j = 0; j < Matrix[0].length; j++) {
                if (Matrix[i][j] >= 0) {
                    System.out.print("  " + Matrix[i][j]);
                } else {
                    System.out.print(" " + Matrix[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("\n-------------");
    }

    public static void display1DMatrix(int[] Matrix) {
        for (int i = 0; i < Matrix.length; i++) {
            System.out.print(Matrix[i] + " ");
        }
        System.out.println("\n-------------");
    }

    public static int[][] populateMatrixL(int[][] MatrixL, int[][] MatrixU) {

        for (int i = 0; i < MatrixL.length; i++) {
            for (int j = 0; j < MatrixL[0].length; j++) {
                if (j == (MatrixL[0].length - 1)) {
                    MatrixL[i][j] = MatrixU[i][j];
                } else if (i == j) {
                    //diagonal elements
                    MatrixL[i][j] = 1;
                } else if (i < j) {
                    //upper triangle 
                    MatrixL[i][j] = 0;
                } else if (i > j) {
                    //lower triangle
                    MatrixL[i][j] = -1;
                }
            }
        }

        return MatrixL;
    }

    public static int[] clearValueCalculator(int[][] MatrixU, int[] equalizingValues) {

        // find the values to clear the positions
        for (int i = 0; i < positions.length; i++) {
            if (i == 2) {
                //finding equalizingvalue for the last row 2nd column
                equalizingValues[i] = -1 * (MatrixU[positions[i][0]][positions[i][1]] / MatrixU[1][1]);
            } else {
                //finding equalizingvalue for the 2nd and 3rd row 1st column
                equalizingValues[i] = -1 * (MatrixU[positions[i][0]][positions[i][1]] / MatrixU[0][0]);
            }

            for (int j = 0; j < positions.length; j++) {
                if (i != 2) {
                    //multiplying 2nd row by the equalizingValues to make lower triangle zero
                    MatrixU[i + 1][j] += equalizingValues[i] * MatrixU[0][j];
                } else {
                    //this is for the last row 2nd column
                    MatrixU[2][j] += equalizingValues[i] * MatrixU[1][j];
                }
            }
        }

        display2DMatrix(MatrixU);

        return equalizingValues;
    }

    public static void inputValuesToMatrixL(int[][] MatrixL, int[] equalizingValues) {

        for (int i = 0; i < positions.length; i++) {
            MatrixL[positions[i][0]][positions[i][1]] = -1 * equalizingValues[i];
        }
    }

    public static void linerEquationMatrixL(int[][] MatrixL, int[] MatrixLResults) {
        int constant = 0;
        int coefficient = 0;

        for (int i = 0; i < MatrixL.length; i++) {
            for (int j = 0; j < (MatrixL[0].length - 1); j++) {

                // all numbers except variable with coefficient will add up to the constant
                if (i != j) {
                    constant += (MatrixLResults[j] * MatrixL[i][j]);
                } else if (j == i) {
                    coefficient = (MatrixLResults[j] * MatrixL[i][j]);
                }

            }
            // solving the equation
            MatrixLResults[i] = (MatrixL[i][3] - constant) / coefficient;
            // clearing variables for next iteration
            constant = 0;
            coefficient = 0;
        }

        display2DMatrix(MatrixL);
        display1DMatrix(MatrixLResults);
    }

    public static void linerEquationMatrixU(int[][] MatrixU, int[] MatrixUResults) {
        int constant = 0;
        int coefficient = 0;

        for (int i = (MatrixU.length - 1); i >= 0; i--) {
            for (int j = 0; j < (MatrixU[0].length - 1); j++) {

                // all numbers except variable with coefficient will add up to the constant
                if (i == j) {
                    coefficient = (MatrixUResults[j] * MatrixU[i][j]);
                } else {
                    constant += (MatrixUResults[j] * MatrixU[i][j]);
                }
                // System.out.println("constant: " + constant);
            }
            // System.out.println("coefficient is: " + coefficient);

            // solving the equation
            MatrixUResults[i] = (MatrixU[i][3] - constant) / coefficient;

            // clearing variables for next iteration
            constant = 0;
            coefficient = 0;

        }
        display1DMatrix(MatrixUResults);
    }
}
