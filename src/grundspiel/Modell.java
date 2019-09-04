package grundspiel;
public class Modell {
    private SudokuEintrag[][] spielfeld;
    public Modell() {
        erzeugeLeeresSpielfeld();
    }
    private void erzeugeLeeresSpielfeld(){
        spielfeld = new SudokuEintrag[9][9];
        for (int i =0;i<spielfeld.length;i++){
            for (int j=0;j<spielfeld[i].length;j++){
                spielfeld[i][j] = new SudokuEintrag();
            }
        }
    }
    public void setzeEintrag(String id,String wert){
      String ident[]= id.split(",");
      int a = Integer.parseInt(ident[0]);
      int b = Integer.parseInt(ident[1]);
      spielfeld[a][b].setEintrag(wert);
      spielfeld[a][b].getMoeglicheEintraege().remove(wert);


    }

//Getter

    public SudokuEintrag[][] getSpielfeld() {
        return spielfeld;
    }
}
