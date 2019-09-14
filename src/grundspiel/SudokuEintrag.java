package grundspiel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class SudokuEintrag {
    /**
     * Eintrag, der auf das Feld gesetzt ist.
     */
    private StringProperty eintrag = new SimpleStringProperty( " ");

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
     * Farbe, in der das Feld angezeigt wird.
     */
    private String farbe;
    /**
     * Farbe, in der das Feld angezeigt wird, wenn die Maus darüber hovert.
     */
    private String farbeHover;
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
        return eintrag.get();
    }

    public void setEintrag(final String eintrag) {
        this.eintrag.set(eintrag);
    }

    public boolean isEintragKonstant() {
        return eintragKonstant;
    }

    public void setEintragKonstant(boolean eintragKonstant) {
        this.eintragKonstant = eintragKonstant;
    }

    public StringProperty getEintragPoperty(){
        return eintrag;
    }

    public void setEintragProperty(final StringProperty eintrag){
        this.eintrag=eintrag;
    }

    public ArrayList<String> getMoeglicheEintraege() {
        return moeglicheEintraege;
    }

    public String getFarbe(){return farbe;}

    public void setFarbe (String farbe){
        this.farbe=farbe;
    }

    public String getFarbeHover() {return farbeHover;}

    public void setFarbeHover(String farbeHover) {this.farbeHover = farbeHover;}
}

