package grundspiel;

public class Testsudokus {

    public int[][] testsudoku1 = new int[][]
            {
                    {3, 0, 6, 5, 0, 8, 4, 0, 0},
                    {5, 2, 0, 0, 0, 0, 0, 0, 0},
                    {0, 8, 7, 0, 0, 0, 0, 3, 1},
                    {0, 0, 3, 0, 1, 0, 0, 8, 0},
                    {9, 0, 0, 8, 6, 3, 0, 0, 5},
                    {0, 5, 0, 0, 9, 0, 6, 0, 0},
                    {1, 3, 0, 0, 0, 0, 2, 5, 0},
                    {0, 0, 0, 0, 0, 0, 0, 7, 4},
                    {0, 0, 5, 2, 0, 6, 3, 0, 0}
            };

    public Testsudokus() {

    }

    public String getEintragAusTestsudoku(int[][] testsudoku, int i, int j) {

        if (testsudoku[i][j] == 0) {
            return " ";
        } else {
            return testsudoku[i][j] + "";
        }
    }

public void gibaus(SudokuEintrag[][] sudoku){
    System.out.println("Die interne Repräsentation:");
    for (int i = 0; i < sudoku.length; i++) {
        for (int j = 0; j < sudoku[0].length; j++) {
            if(sudoku[i][j].getEintrag().equals(" ")){
                System.out.print("0|");
            }
            else {
                System.out.print(sudoku[i][j].getEintrag()+"|");
            }

        }
        System.out.println();
        }
    }
}