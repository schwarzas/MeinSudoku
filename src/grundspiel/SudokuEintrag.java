package grundspiel;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class SudokuEintrag {
    /**
     * Eintrag, der auf das Feld gesetzt ist.
     */
    private String eintrag = " ";
    /**
     * gibt an, ob eine Entitaet zur Aufgabenstellung gehört. True bedeutet, dass die Entität vom Spieler nicht geändert
     * werden darf.
     */
    private boolean eintragKonstant;

    /**
     * Gibt an, in welchem Quadranten des Spielfelds sich der Eintrag befindet.
     */
    private int quadrant;
    /**
     * enthält die Einträge, die das Feld regelkonform zu diesem Zeitpunkt annehmen kann.
     */
    private ArrayList<String> moeglicheEintraege;

    /**
     * Konstruktur, SpielEntität wird mit einem leeren Feld erzeugt und or
     */
    @Contract(pure = true)
    public SudokuEintrag(int groesterWert) {
        eintragKonstant = false;
        moeglicheEintraege = new ArrayList<>();
        for (int i = 1; i <= groesterWert; i++) {
            moeglicheEintraege.add(Integer.toString(i));

        }
    }

    //Getter und Setter

    public int getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(int quadrant) {
        this.quadrant = quadrant;
    }

    public String getEintrag() {
        return eintrag;
    }

    public boolean isEintragKonstant() {
        return eintragKonstant;
    }

    public void setEintragKonstant(boolean eintragKonstant) {
        this.eintragKonstant = eintragKonstant;
    }

    public void setEintrag(String eintrag) {
        this.eintrag = eintrag;
    }

    public ArrayList<String> getMoeglicheEintraege() {
        return moeglicheEintraege;
    }
}

