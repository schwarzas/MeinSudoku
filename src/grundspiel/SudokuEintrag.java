package grundspiel;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class SudokuEintrag {
   private String eintrag;
    /**
     * gibt an, ob eine Entitaet zur Aufgabenstellung gehört. True bedeutet, dass die Entität vom Spieler nicht geändert
     * werden darf.
     */
   private boolean konstant;
    /**
     * Einträge, die noch in SudokuEintrag eingegeben werden können.
     */
   private ArrayList<String> moeglicheEintraege;

    /**
     * Konstruktur, SpielEntität wird mit einem leeren Feld erzeugt und or
     */
    @Contract(pure = true)
    public SudokuEintrag(){
        eintrag ="0";
        konstant = false;
        moeglicheEintraege = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            moeglicheEintraege.add(Integer.toString(i));

        }
    }
//Getter und Setter
    public String getEintrag() {
        return eintrag;
    }

    public boolean isKonstant() {
        return konstant;
    }

    public void setKonstant(boolean konstant) {
        this.konstant = konstant;
    }

    public void setEintrag(String eintrag) {
        this.eintrag = eintrag;
    }
    public ArrayList<String> getMoeglicheEintraege() {
        return moeglicheEintraege;
    }

    public void setMoeglicheEintraege(ArrayList<String> moeglicheEintraege) {
        this.moeglicheEintraege = moeglicheEintraege;
    }
}
