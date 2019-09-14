package grundspiel;

import java.util.ArrayList;

public class Modell {

    private SudokuEintrag[][] spielfeld;
    private SudokuStandard sudoku;
    private Boolean subfeldGerade;
    public Modell() {

    }
    /**
     * erstellt ein neues Sudoku mit den eingegebenen Parametern.
     */
    public void erstelleSudokuFeld(int BREITE_SUBSPIELFELD, int HOEHE_SUBSPIELFELD){
        subfeldGerade =(HOEHE_SUBSPIELFELD %2==0);
        sudoku = new SudokuStandard(BREITE_SUBSPIELFELD,HOEHE_SUBSPIELFELD);
        sudoku.erzeugeSpielfeld();
        spielfeld = sudoku.getSudokuSpielfeld();
        //sudoku.loeseSudoku();
    }

    /**
     * setzt einen Eintrag in des Spielfeld
     * @param id die id des Feldes
     * @param neuerEintrag Der Eintrag, der gesetzt werden soll
     */
    public void setzeEintrag(String id,String neuerEintrag){
        sudoku.setzeEintrag(id,neuerEintrag);
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
//Getter

    public SudokuEintrag[][] getSpielfeld() {
        return spielfeld;
    }
    public boolean isSubfeldgerade(){ return subfeldGerade;}

}
