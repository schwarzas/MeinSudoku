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
    public int[][] getTestsudoku1loesung = new int[][]
            {
                    {3, 1, 6, 5, 7, 8, 4, 9, 2},
                    {5, 2, 9, 1, 3, 4, 7, 6, 8},
                    {4, 8, 7, 6, 2, 9, 5, 3, 1},
                    {2, 6, 3, 4, 1, 5, 9, 8, 7},
                    {9, 7, 4, 8, 6, 3, 1, 2, 5},
                    {8, 5, 1, 7, 9, 2, 6, 4, 3},
                    {1, 3, 8, 9, 4, 7, 2, 5, 6},
                    {6, 9, 2, 3, 5, 1, 8, 7, 4},
                    {7, 4, 5, 2, 8, 6, 3, 1, 9}
            };
    public int[][] testsudoku2 =new int[][]
            {
                    {1,0,0,2},
                    {2,0,3,0},
                    {0,0,0,4},
                    {0,2,0,3}
            };
    public int[][] gettestsudoku2Loesung = new int[][]
            {
                    {1,3,4,2},
                    {2,4,3,1},
                    {3,1,2,4},
                    {4,2,1,3}
            };
public int[][] testsudoku3 = new int[][]
        {
                {0,5,0,0,0,1},
                {0,0,4,6,0,0},
                {4,0,0,0,5,0},
                {1,0,0,0,0,4},
                {0,4,3,0,0,0},
                {0,6,0,2,4,0}
    };
    public Testsudokus() {

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