package grundspiel;


import java.util.ArrayList;
import java.util.Collections;
public class SudokuStandard {

    /**
     * Hält das Spielfeld
     */
    private SudokuEintrag[][] sudokuSpielfeld;
    /**
     * größter Wert im Spiel. Gleichzeitig Breite und Höhe des Spielfelds.
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
    public SudokuStandard(int BREITE_SUBSPIELFELD, int HOEHE_SUBSPIELFELD) {
        this.BREITE_SUBSPIELFELD=BREITE_SUBSPIELFELD;
        this.HOEHE_SUBSPIELFELD=HOEHE_SUBSPIELFELD;
        MAX_WERT=BREITE_SUBSPIELFELD*HOEHE_SUBSPIELFELD;


    }

    /**
     * Hier wird das Standardspielfeld erzeugt. Wichtig: Die Anzahl der Subfelder nebeneinander ist die SubfeldHÖHE.
     * Die Anzahl der Felder untereinander ist die SubfeldBREITE. offsets speichert die Verschiebung der jeweiligen
     * ID der einzelnen Felder, damit diese richtig zugeordnet werden.
     */
    public void erzeugeSpielfeldLeer(){
        freieFelder = MAX_WERT * MAX_WERT;
        sudokuSpielfeld = new SudokuEintrag[MAX_WERT][MAX_WERT];
        int quadrant;
        int[] offsets=new int[HOEHE_SUBSPIELFELD];
        offsets[0]=0;
        offsets[1]=(BREITE_SUBSPIELFELD-1);
        for (int i = 2; i < offsets.length ; i++) {
            offsets[i]=offsets[1]*i;
        }
        for (int i = 0; i < MAX_WERT; i++) {
            for (int j = 0; j < MAX_WERT; j++) {
                sudokuSpielfeld[i][j] = new SudokuEintrag(MAX_WERT);
                quadrant = (i/BREITE_SUBSPIELFELD)+(j/HOEHE_SUBSPIELFELD)+offsets[i/BREITE_SUBSPIELFELD];
                sudokuSpielfeld[i][j].setQuadrant(quadrant);
                sudokuSpielfeld[i][j].setEintrag((" "));
                if(BREITE_SUBSPIELFELD%2==0){
                    if(((int)quadrant/BREITE_SUBSPIELFELD)%2==0){
                        if(quadrant%2==0){
                            sudokuSpielfeld[i][j].setFarbe("-fx-background-color: white");
                            sudokuSpielfeld[i][j].setFarbeHover("-fx-background-color: #E1F7E1");
                        }
                        else {
                            sudokuSpielfeld[i][j].setFarbe("-fx-background-color: #F3F3F3");
                            sudokuSpielfeld[i][j].setFarbeHover("-fx-background-color: #B1F7B1");
                        }
                    }
                    else{
                        if(quadrant%2==0){
                            sudokuSpielfeld[i][j].setFarbe("-fx-background-color: #F3F3F3");
                            sudokuSpielfeld[i][j].setFarbeHover("-fx-background-color: #B1F7B1");
                        }
                        else{
                            sudokuSpielfeld[i][j].setFarbe("-fx-background-color: white");
                            sudokuSpielfeld[i][j].setFarbeHover("-fx-background-color: #E1F7E1");
                        }
                    }

                } else {
                    if(quadrant%2==0){
                        sudokuSpielfeld[i][j].setFarbe("-fx-background-color: white");
                        sudokuSpielfeld[i][j].setFarbeHover("-fx-background-color: #E1F7E1");
                    }
                    else {
                        sudokuSpielfeld[i][j].setFarbe("-fx-background-color: #F3F3F3");
                        sudokuSpielfeld[i][j].setFarbeHover("-fx-background-color: #B1F7B1");
                        }
                }
            }
        }
    }
    /**
     * Setzt den Eintrag in das Eintragsfeld mit der übergebenen id. Ebenfalls löscht die Methode den Eintrag aus den
     * moeglichen Einträgen der korespondierenden Eintragsfeldern.
     * @param neuerEintrag der neue Eintrag für das Feld.
     * @param i,j    die Position des Feldes.
     */
    public void setzeEintrag(int i, int j, String neuerEintrag){
        String alterEintrag = sudokuSpielfeld[i][j].getEintrag();
        sudokuSpielfeld[i][j].setEintrag(neuerEintrag);
        if(alterEintrag.equals(" ")){
            freieFelder--;
        }
        if (neuerEintrag.equals(" ")){
            freieFelder++;
        }
        for (int[] item:berechneKorrespondierendeFelder(i,j)) {
            if(!neuerEintrag.equals(" ")){
                sudokuSpielfeld[item[0]][item[1]].getMoeglicheEintraege().remove(neuerEintrag);
            }
            if(!alterEintrag.equals(" ")){
                sudokuSpielfeld[item[0]][item[1]].getMoeglicheEintraege().add(alterEintrag);
            }
        }
    }
//FIXME Ansatz ist Müll
    private void setzeZahlenAufDiagonale(){
        ArrayList<String> diagonalEintraege = new ArrayList<>();
        for (int i = 1; i <=MAX_WERT ; i++) {
            diagonalEintraege.add(i+"");
        }
        int subfeldHoeheStart=0;
        int subfeldBreiteStart=0;
        int faktor=1;
        int diagonalEintraegeIndex;
        while (subfeldBreiteStart<MAX_WERT && subfeldHoeheStart <MAX_WERT){
            //Fülle ein Diagonalsubfeld
            Collections.shuffle(diagonalEintraege);
            diagonalEintraegeIndex=0;
            for (int i = subfeldBreiteStart; i < BREITE_SUBSPIELFELD*faktor; i++) {
                for (int j = subfeldHoeheStart; j < HOEHE_SUBSPIELFELD*faktor ; j++) {
                    setzeEintrag(i,j,diagonalEintraege.get(diagonalEintraegeIndex));
                    diagonalEintraegeIndex++;
                }
            }
            faktor++;
            subfeldHoeheStart+=HOEHE_SUBSPIELFELD;
            subfeldBreiteStart+=BREITE_SUBSPIELFELD;
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
    public void erstelleSudoku(){
        setzeZahlenAufDiagonale();
    }

    //FIXME backtracing umsetzen
    public Boolean loeseSudokuBacktracking(){


        return false;
    }
//GETTER SETTER
    public SudokuEintrag[][] getSudokuSpielfeld() { return sudokuSpielfeld;}

    public int getFreieFelder(){ return freieFelder;}
}
