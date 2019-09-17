package grundspiel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

public class Controller implements Initializable {
    Modell modell;
    int BREITE_SUBSPIELFELD = 1;
    int HOEHE_SUBSPIELFELD = 2;
    /**
     * hält die möglichen Einträge auf dem Popup.
     */
    Label[][] moeglicheWerteAuswahl;
    @FXML
    private GridPane primaryPane;
    @FXML
    private Label gesagtes;
    @FXML
    private GridPane sudokuGridPane;
    @FXML
    private Label[][] sudokuLabelArray;
    @FXML
    private Stage primaryStage;
    @FXML
    private ScrollPane scrollPane;
//FIXME für das Testen verschiedener Szenarien.
    Testsudokus testsudokus;


    public Controller(Stage primaryStage) {
        modell = new Modell();
        modell.erstelleSudokuFeld(BREITE_SUBSPIELFELD, HOEHE_SUBSPIELFELD);
        //erstelleTestsudoku();
        //FIXME Testmethoden
        testsudokus = new Testsudokus();
        testsudokus.gibaus(modell.getSpielfeld());

        this.primaryStage = primaryStage;
    }
    //FIXME erstellt eines der Testsudokus
    private void erstelleTestsudoku(){
        Testsudokus test = new Testsudokus();
        int[][] testsudoku = test.testsudoku1;
        modell.erstelleSudokuFeld(3,3);
        for (int i = 0; i < testsudoku.length ; i++) {
            for (int j = 0; j < testsudoku[0].length ; j++) {
                //TODO Eintrag setzen

                modell.setzeEintrag(i,j,testsudoku[i][j]+"");
            }

        }
        test.gibaus(modell.getSpielfeld());

    }

    public void buttonNeuesSpiel() {
        System.out.println("neues Spiel gestartet");
        gesagtes.setText("neues Spiel gestartet");
    }

    private Label erzeugeSpielEintragLabel(int i, int j) {
        Label label = new Label();
        label.setId(i + "," + j);
        label.setMinHeight(40);
        label.setMinWidth(40);
        label.setAlignment(CENTER);
        label.setStyle(modell.getSpielfeld()[i][j].getFarbe());
//Färbe Felder ein, wenn die Maus darüber fährt.
        label.setOnMouseEntered(mausEnter -> {
            ArrayList<int[]> koresspondierendeFelder = modell.berechneKorrespondierendeFelder(label.getId());
            for (int[] item : koresspondierendeFelder
            ) {
                sudokuLabelArray[item[0]][item[1]].setStyle(modell.getSpielfeld()[item[0]][item[1]].getFarbeHover());
            }
        });
        label.setOnMouseExited(mausExit -> {
            ArrayList<int[]> koresspondierendeFelder = modell.berechneKorrespondierendeFelder(label.getId());
            for (int[] item : koresspondierendeFelder
            ) {
                sudokuLabelArray[item[0]][item[1]].setStyle(modell.getSpielfeld()[item[0]][item[1]].getFarbe());
            }
        });
        label.setOnMouseReleased(aufFeldgeklickt -> {
            //Ab hier Füllen des popups, dass die möglichen Einträge anzeigt.
            if (!modell.istgeloest()) {//Spielabfrage
                Popup popupMoeglicheEintraege = new Popup();
                GridPane gridPaneMoeglicheEintraege = new GridPane();
                ArrayList<String> moeglicheEintraege = modell.getSpielfeld()[i][j].getMoeglicheEintraege();
                int counter = 0;
                int wertEintragenEintrag = 1;
                if (BREITE_SUBSPIELFELD < HOEHE_SUBSPIELFELD) {
                    moeglicheWerteAuswahl = new Label[HOEHE_SUBSPIELFELD][BREITE_SUBSPIELFELD];
                } else {
                    moeglicheWerteAuswahl = new Label[BREITE_SUBSPIELFELD][HOEHE_SUBSPIELFELD];
                }
                for (int k = 0; k < moeglicheWerteAuswahl[0].length; k++) {
                    for (int l = 0; l < moeglicheWerteAuswahl.length; l++) {
                        Label tmpLabel = new Label();
                        String wert = Integer.toString(wertEintragenEintrag);
                        if (moeglicheEintraege.contains(wert)) {
                            tmpLabel.setText(wert);
                            tmpLabel.setOnMouseReleased(mouseEvent ->
                            {
                                modell.setzeEintrag(label.getId(), tmpLabel.getText());
                                popupMoeglicheEintraege.hide();
                                if(modell.istgeloest()){
                                    gewonnen();
                                }
                            });
                        } else {
                            tmpLabel.setText(" ");
                            tmpLabel.setOnMouseReleased(mouseEven2 -> popupMoeglicheEintraege.hide());
                        }
                        tmpLabel.setPrefSize(20, 20);
                        tmpLabel.setStyle("-fx-background-color: #0000FF60;-fx-border-color: black");
                        tmpLabel.setAlignment(CENTER);
                        moeglicheWerteAuswahl[l][k] = tmpLabel;
                        gridPaneMoeglicheEintraege.add(moeglicheWerteAuswahl[l][k], l, k);
                        wertEintragenEintrag++;
                    }
                    counter++;
                }
                Label leeren = new Label("leeren");
                leeren.setOnMouseReleased(mouseEventleeren ->
                {
                    modell.setzeEintrag(label.getId(), " ");
                    popupMoeglicheEintraege.hide();
                });
                leeren.setStyle("-fx-background-color: #0000FF60;-fx-border-color: black");
                gridPaneMoeglicheEintraege.add(leeren, 0, counter + 1, 1, BREITE_SUBSPIELFELD);
                gridPaneMoeglicheEintraege.setColumnSpan(leeren, BREITE_SUBSPIELFELD);
                leeren.setPrefSize(20 * BREITE_SUBSPIELFELD, 20);
                popupMoeglicheEintraege.getContent().add(gridPaneMoeglicheEintraege);
                popupMoeglicheEintraege.setAutoHide(true);
                Point2D position = label.localToScene(0, 0);
                popupMoeglicheEintraege.setAnchorX(primaryStage.getX() + position.getX() + 8);
                popupMoeglicheEintraege.setAnchorY(primaryStage.getY() + position.getY() + 29);
                if (!popupMoeglicheEintraege.isShowing()) {
                    popupMoeglicheEintraege.show(primaryStage);
                }
            }
        });
        label.textProperty().bind(modell.getSpielfeld()[i][j].getEintragPoperty());
        return label;
    }
    private void gewonnen(){
        gesagtes.setText("Glückwunsch das Sudoku ist gelöst!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sudokuGridPane = new GridPane();
        sudokuGridPane.setHgap(2);
        sudokuGridPane.setVgap(2);
        sudokuGridPane.setGridLinesVisible(true);
        sudokuLabelArray = new Label[modell.getSpielfeld().length][modell.getSpielfeld()[0].length];
        for (int i = 0; i < sudokuLabelArray.length; i++) {
            for (int j = 0; j < sudokuLabelArray[0].length; j++) {
                sudokuLabelArray[i][j] = erzeugeSpielEintragLabel(i, j);
                //.GridPane.add() moechte es natürlich andersrum haben.
                sudokuGridPane.add(sudokuLabelArray[i][j], j, i);

            }
        }
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(sudokuGridPane);
        primaryStage.sizeToScene();
    }

}
