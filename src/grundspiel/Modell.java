package grundspiel;

import java.util.ArrayList;

public class Modell {

    private SudokuEintrag[][] spielfeld;
    private SudokuStandard sudoku;
    public Modell() {

    }
    /**
     * erstellt ein neues Sudoku mit den eingegebenen Parametern.
     */
    public void erstelleSudokuFeld(int BREITE_SUBSPIELFELD, int HOEHE_SUBSPIELFELD){
        sudoku = new SudokuStandard(BREITE_SUBSPIELFELD,HOEHE_SUBSPIELFELD);
        sudoku.erzeugeSpielfeldLeer();
        spielfeld = sudoku.getSudokuSpielfeld();
        sudoku.erstelleSudoku();
        //sudoku.loeseSudoku();
    }

    /**
     * setzt einen Eintrag in des Spielfeld
     * @param id die id des Feldes
     * @param neuerEintrag Der Eintrag, der gesetzt werden soll
     */
    public void setzeEintrag(String id,String neuerEintrag){
        String[] position = id.split(",");
        sudoku.setzeEintrag(Integer.parseInt(position[0]),Integer.parseInt(position[1]),neuerEintrag);
    }
    public void setzeEintrag(int i, int j, String neuerEintrag){
        sudoku.setzeEintrag(i,j, neuerEintrag);
    }

    /**
     * Berechnet die Einträge, die mit dem übergebenen Eintrag korrespondieren.
     * @param id Id des übergebenen Eintrags.
     * @return Arraylist der Koordinaten der korrespondierenden Einträge als int[2].
     */
    public ArrayList<int[]> berechneKorrespondierendeFelder(String id){
        String[] position = id.split(",");
        return sudoku.berechneKorrespondierendeFelder(Integer.parseInt(position[0]),Integer.parseInt(position[1]));
    }

    public Boolean istgeloest(){
        return sudoku.getFreieFelder()==0;
    }
//Getter

    public SudokuEintrag[][] getSpielfeld() {
        return spielfeld;
    }

}
