package grundspiel;

import java.util.ArrayList;

public class Modell {

    private SudokuEintrag[][] spielfeld;
    private SudokuStandard sudoku;
    private int MAX_WERT;
    public Modell() {

    }
    /**
     * erstellt ein leeres Sudoku mit den eingegebenen Parametern.
     */
    public void erstelleSudokuLeer(int BREITE_SUBSPIELFELD, int HOEHE_SUBSPIELFELD){
        this.MAX_WERT=BREITE_SUBSPIELFELD*HOEHE_SUBSPIELFELD;
        sudoku = new SudokuStandard(BREITE_SUBSPIELFELD,HOEHE_SUBSPIELFELD);
        sudoku.erzeugeSpielfeldLeer();
        spielfeld = sudoku.getSudokuSpielfeld();
    }

    /**
     * setzt einen Eintrag in des Spielfeld
     * @param id die id des Feldes
     * @param neuerEintrag Der Eintrag, der gesetzt werden soll
     */
    public void setzeEintrag(String id,String neuerEintrag){
        String[] position = id.split(",");
        if(istValide(id,neuerEintrag)) {
            sudoku.setzeEintrag(Integer.parseInt(position[0]), Integer.parseInt(position[1]), neuerEintrag);
        }
    }
    public void setzeEintrag(int i, int j, String neuerEintrag){
        if(istValide(i,j,neuerEintrag)){
            sudoku.setzeEintrag(i,j,neuerEintrag);
        }
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

    /**
     * gibt an, ob ein Eintrag auf das Feld gesetzt werden darf.
     * @param id id des Feldes, auf dem die Zahl gesetzt werden soll.
     * @param s die Zahl, die gesetzt werden soll.
     * @return s darf auf id gesetzt werden.
     */
    public boolean istValide(String id, String s){
        if(!s.equals(" ")) {
            ArrayList<int[]> korresFelder = berechneKorrespondierendeFelder(id);
            for (int[] feld : korresFelder
            ) {
                if (spielfeld[feld[0]][feld[1]].getEintrag().equals(s)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean istValide(int i, int j, String s){
        return istValide(i+","+j,s);
    }

    public Boolean loeseSudokuBacktracking(){
        int spalte=-1;
        int zeile=-1;
        boolean allesGeloest=true;
        for (int i = 0; i < MAX_WERT ; i++) {
            for (int j = 0; j < MAX_WERT; j++) {
                if(spielfeld[i][j].getEintrag().equals(" ")){
                    zeile = i;
                    spalte = j;
                    allesGeloest=false;
                    break;
                }
            }
            if(!allesGeloest){
                break;
            }
        }
        if(allesGeloest) {return true;}
        for (int i = 1; i <= MAX_WERT ; i++) {
            String tmp = Integer.toString(i);
            if(istValide(zeile,spalte,tmp)) {
                setzeEintrag(zeile, spalte, tmp);
                System.out.println(zeile+"|"+spalte+"|"+tmp);
                if (loeseSudokuBacktracking()) {
                    return true;
                } else {
                    setzeEintrag(zeile, spalte, " ");
                }
            }
        }
        return false;
    }

    public Boolean istgeloest(){
        return sudoku.getFreieFelder()==0;
    }

    //Getter
    public SudokuEintrag[][] getSpielfeld() {
        return spielfeld;
    }

}
