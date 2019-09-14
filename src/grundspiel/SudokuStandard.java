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
    public void erzeugeSpielfeld(){
        freieFelder = MAX_WERT * MAX_WERT;
        sudokuSpielfeld = new SudokuEintrag[MAX_WERT][MAX_WERT];
        int quadrant;
        int[] offsets=new int[BREITE_SUBSPIELFELD];
        offsets[0]=0;
        offsets[1]=(HOEHE_SUBSPIELFELD-1);
        for (int i = 2; i < offsets.length ; i++) {
            offsets[i]=offsets[1]*i;
        }
        for (int i = 0; i < MAX_WERT; i++) {
            for (int j = 0; j < MAX_WERT; j++) {
                sudokuSpielfeld[j][i] = new SudokuEintrag(MAX_WERT);
                quadrant = (i/HOEHE_SUBSPIELFELD)+(j/BREITE_SUBSPIELFELD)+offsets[i/HOEHE_SUBSPIELFELD];
                sudokuSpielfeld[j][i].setQuadrant(quadrant);
                sudokuSpielfeld[j][i].setEintrag((" "));
                if(HOEHE_SUBSPIELFELD%2==0){
                    if(((int)quadrant/HOEHE_SUBSPIELFELD)%2==0){
                        if(quadrant%2==0){
                            sudokuSpielfeld[j][i].setFarbe("-fx-background-color: white");
                            sudokuSpielfeld[j][i].setFarbeHover("-fx-background-color: #E1F7E1");
                        }
                        else {
                            sudokuSpielfeld[j][i].setFarbe("-fx-background-color: #F3F3F3");
                            sudokuSpielfeld[j][i].setFarbeHover("-fx-background-color: #B1F7B1");
                        }
                    }
                    else{
                        if(quadrant%2==0){
                            sudokuSpielfeld[j][i].setFarbe("-fx-background-color: #F3F3F3");
                            sudokuSpielfeld[j][i].setFarbeHover("-fx-background-color: #B1F7B1");
                        }
                        else{
                            sudokuSpielfeld[j][i].setFarbe("-fx-background-color: white");
                            sudokuSpielfeld[j][i].setFarbeHover("-fx-background-color: #E1F7E1");
                        }
                    }

                } else {
                    if(quadrant%2==0){
                        sudokuSpielfeld[j][i].setFarbe("-fx-background-color: white");
                        sudokuSpielfeld[j][i].setFarbeHover("-fx-background-color: #E1F7E1");
                    }
                    else {
                        sudokuSpielfeld[j][i].setFarbe("-fx-background-color: #F3F3F3");
                        sudokuSpielfeld[j][i].setFarbeHover("-fx-background-color: #B1F7B1");
                        }
                }
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

    public void setzeZahlenAufDiagonale(){
        ArrayList<String> diagonalEinträge = new ArrayList<>();
        for (int i = 1; i <=MAX_WERT ; i++) {
            diagonalEinträge.add(i+"");
        }
        //TODO Subfelder auf den unkorrelierten Subfeldern (Diagonalen) setzen.
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
            x = (rd.nextInt(MAX_WERT));
            y = (rd.nextInt(MAX_WERT));
            if(sudokuSpielfeld[x][y].getEintrag().equals(" ")){
                int laenge = sudokuSpielfeld[x][y].getMoeglicheEintraege().size();
                String tmp = sudokuSpielfeld[x][y].getMoeglicheEintraege().get(rd.nextInt(laenge));//wenn nur eine Zahl geht
                setzeEintrag(x+","+y,tmp);
            }
        }

    }

    public SudokuEintrag[][] getSudokuSpielfeld() { return sudokuSpielfeld;}

}
