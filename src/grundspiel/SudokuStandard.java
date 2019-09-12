package grundspiel;


import java.util.ArrayList;
import java.util.Random;

public class SudokuStandard {
    private Random rd = new Random();
    /**
     * Hält das Spielfeld
     */
    private SudokuEintrag[][] sudokuSpielfeld;

    /**
     * Spiel gelöst
     */
    private boolean geloest;
    /**
     * größter Wert im Spiel.
     */
    private int MAX_WERT;
    /**
     * Hoehe des einzelnen Quadranten
     */
    private int HOEHE_SUBSPIELFELD;
    /**
     * Breite des einzelnen Quadranten
     */
    private int BREITE_SUBSPIELFELD;
    /**
     * unbesetzte Felder im Spiel
     */
    private int freieFelder;

    /**
     * Konstruktor
     */
    public SudokuStandard(int MAX_WERT, int BREITE_SUBSPIELFELD, int HOEHE_SUBSPIELFELD) {
        this.MAX_WERT=MAX_WERT;
        this.BREITE_SUBSPIELFELD=BREITE_SUBSPIELFELD;
        this.HOEHE_SUBSPIELFELD=HOEHE_SUBSPIELFELD;

    }
    public void erzeugeSpielfeld(){
        freieFelder = MAX_WERT * MAX_WERT;
        sudokuSpielfeld = new SudokuEintrag[MAX_WERT][MAX_WERT];
        int quadrant;
        int offset=0;
        for (int i = 0; i < MAX_WERT; i++) {
            if(i<HOEHE_SUBSPIELFELD){
                offset=0;
            }
            else if (i>=HOEHE_SUBSPIELFELD && i<2*HOEHE_SUBSPIELFELD){
                offset=1;
            }
            else{
                offset=2;
            }

            for (int j = 0; j < MAX_WERT; j++) {
                sudokuSpielfeld[j][i] = new SudokuEintrag(MAX_WERT);
                quadrant = (i/HOEHE_SUBSPIELFELD)+(j/BREITE_SUBSPIELFELD)+offset;
                sudokuSpielfeld[j][i].setQuadrant(quadrant);
                sudokuSpielfeld[j][i].setEintrag((" "));
            }

        }
    }

    /**
     * Setzt den Eintrag in das Eintragsfeld mit der übergebenen id. Ebenfalls löscht die Methode den Eintrag aus den
     * moeglichen Einträgen der korespondierenden Eintragsfeldern.
     * @param neuerEintrag der neue Eintrag für das Feld.
     * @param id    die Position des Feldes anhand der LabelID.
     */
    public void setzeEintrag(String id, String neuerEintrag){
        String[] tmp = id.split(",");
        int zeile = Integer.parseInt(tmp[0]);
        int spalte = Integer.parseInt(tmp[1]);
        String alterEintrag = sudokuSpielfeld[zeile][spalte].getEintrag();
        sudokuSpielfeld[zeile][spalte].setEintrag(neuerEintrag);
        if(alterEintrag.equals(" ")){
            freieFelder--;
        }
        if (neuerEintrag.equals(" ")){
            freieFelder++;
        }

        for (int[] item:berechneKorrespondierendeFelder(zeile,spalte)) {
            if(!neuerEintrag.equals(" ")){
                sudokuSpielfeld[item[0]][item[1]].getMoeglicheEintraege().remove(neuerEintrag);
            }
            if(!alterEintrag.equals(" ")){
                sudokuSpielfeld[item[0]][item[1]].getMoeglicheEintraege().add(alterEintrag);
            }
        }
    }

    
    /**
     * Berechne korrespondierende Felder
     */
    public ArrayList<int[]> berechneKorrespondierendeFelder(int a, int b){
        ArrayList<int[]> felder = new ArrayList<>();
        for (int i = 0; i < MAX_WERT; i++) {
            int[] tmp = {a,i};
            felder.add(tmp);
            if(!(i==a)){
                int[] tmp2 = {i,b};
                felder.add(tmp2);
            }
        }
        int subSpielfeldZeilenStart= (a/BREITE_SUBSPIELFELD)*BREITE_SUBSPIELFELD;
        int subSpielfeldZeilenEnde= subSpielfeldZeilenStart+BREITE_SUBSPIELFELD-1;
        int subSpielfeldSpaltenStart =(b/HOEHE_SUBSPIELFELD)*HOEHE_SUBSPIELFELD;
        int subSpielfeldSpaltenEnde = subSpielfeldSpaltenStart+HOEHE_SUBSPIELFELD-1;

        for (int i = subSpielfeldZeilenStart; i <= subSpielfeldZeilenEnde; i++) {
            for (int j = subSpielfeldSpaltenStart; j <= subSpielfeldSpaltenEnde; j++) {
                if((j!=b)&&(i!=a)){
                        int[] tmp ={i,j};
                        felder.add(tmp);
                }
            }
        }
        return felder;
    }

    //FIXME backtracing umsetzen
    public void loeseSudoku(){
        int x;
        int y;
        while(freieFelder>=( MAX_WERT * MAX_WERT -10)){
            System.out.println(freieFelder);
            x = (rd.nextInt(MAX_WERT));
            y = (rd.nextInt(MAX_WERT));
            if(sudokuSpielfeld[x][y].getEintrag().equals(" ")){
                int laenge = sudokuSpielfeld[x][y].getMoeglicheEintraege().size();
                System.out.println(laenge);
                String tmp = sudokuSpielfeld[x][y].getMoeglicheEintraege().get(rd.nextInt(laenge));//wenn nur eine Zahl geht
                setzeEintrag(x+","+y,tmp);
            }
        }

    }

    public SudokuEintrag[][] getSudokuSpielfeld() {
        return sudokuSpielfeld;
    }
}
