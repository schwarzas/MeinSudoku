package grundspiel;
//Der Controller ist noch ziemlicher Cancer

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

public class Controller implements Initializable {
    private Modell modell;
    private int BREITE_SUBSPIELFELD;
    private int HOEHE_SUBSPIELFELD;
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
    @FXML private Scene scene;
//FIXME für das Testen verschiedener Szenarien.
    Testsudokus testsudokus;
    int[][] testsudoku;
    public Controller(Stage primaryStage) {
        HOEHE_SUBSPIELFELD=3;
        BREITE_SUBSPIELFELD=3;
        modell = new Modell();
        modell.erstelleSudokuLeer(BREITE_SUBSPIELFELD,HOEHE_SUBSPIELFELD);
        //FIXME Testmethoden
        //testsudokus = new Testsudokus();
        //erstelleTestsudoku(testsudokus);
        //testsudokus.gibaus(modell.getSpielfeld());
        this.primaryStage = primaryStage;
    }

    //FIXME erstellt eines der Testsudokus
    private void erstelleTestsudoku(Testsudokus test){
        testsudoku = test.testsudoku1;
        BREITE_SUBSPIELFELD =3;
        HOEHE_SUBSPIELFELD =3;
        modell.erstelleSudokuLeer(BREITE_SUBSPIELFELD,HOEHE_SUBSPIELFELD);
        for (int i = 0; i < testsudoku.length ; i++) {
            for (int j = 0; j < testsudoku[0].length ; j++) {
                if(testsudoku[i][j]!=0){
                    modell.setzeEintrag(i,j,testsudoku[i][j]+"");
                }
            }

        }
    }
//buttons
    public void buttonParameterAendern(){
        gesagtes.setText("neue Parameter eingeben");
        Label l = new Label( "Felder dürfen sich maximal ums eins unterscheiden");
        Label felderNebeneinander = new Label("Felder nebeneinander");
        Label felderUebereinander = new Label("Felder übereinander");
        TextField felderNeben = new TextField();
        felderNeben.setPrefWidth(30);
        TextField felderUeber = new TextField();
        felderUeber.setPrefWidth(30);
        GridPane gp = new GridPane();
        gp.add(l,0,0);
        gp.add(felderNebeneinander,0,1);
        gp.add(felderNeben,1,1);
        gp.add(felderUebereinander,0,2);
        gp.add(felderUeber,1,2);
        Scene neueParameterScene = new Scene(gp,300,100);
        Stage neueParameterStage = new Stage();
        neueParameterStage.setOnCloseRequest(event ->{
            System.out.println("Mache was Spektakuläres");
            try {
                int tmpueber = Integer.parseInt(felderUeber.getText());
                int tmpneben = Integer.parseInt(felderNeben.getText());
                if(tmpneben==tmpueber || tmpneben+1==tmpueber || tmpneben-1==tmpueber){
                    if(1<tmpneben && tmpneben<5 && 1<tmpueber && tmpueber<5){
                        HOEHE_SUBSPIELFELD=tmpneben;
                        BREITE_SUBSPIELFELD=tmpueber;
                        modell.erstelleSudokuLeer(BREITE_SUBSPIELFELD,HOEHE_SUBSPIELFELD);
                        starte();
                    }
                    else {
                        gesagtes.setText("Eingabe ungültig");
                    }
                }
                else {
                    gesagtes.setText("Eingabe ungültig");
                }
            }
            catch (NumberFormatException e){
                gesagtes.setText("Eingabe ungültig");
            }
        });
        neueParameterStage.setTitle("neue Parameter eingeben");
        neueParameterStage.setScene(neueParameterScene);
        neueParameterStage.setX(primaryStage.getX()+100);
        neueParameterStage.setY(primaryStage.getY()+100);
        neueParameterStage.show();
    }
    public void buttonNeuesLeeresSpiel() {
        modell.erstelleSudokuLeer(BREITE_SUBSPIELFELD,HOEHE_SUBSPIELFELD);
        erzeugeSpielfeldLabelArray();
        gesagtes.setText("Spielfeld geleert");
    }
    public void buttonLoeseBacktracking() {
        gesagtes.setText("Löse mit Backtracking");
        if(!modell.loeseSudokuBacktracking()) {
            gesagtes.setText("Keine Lösung gefunden");
        }
        else{
            gesagtes.setText("Hier die Lösung");
        }
    };
    public void buttonNeuesSpiel() {
        gesagtes.setText("erstelle neues Spiel");
        modell.leereFeld();
        modell.erzeugeSpielbaresSudoku();
    }
    public void buttonHilfeEinstellungen(){
        System.out.println("noch nicht implementiert");
    }
    public void pause(){
        System.out.println("Pause noch nicht implementiert");
    }
    public void notizenModus(){
        System.out.println("Notizenmodus noch nicht implementiert");
    }

    /**
     * erzeugt die einzelnen Labels des Spielfeldes anhand des Spielfeldes im Moddel
     * @param i die Position des entsprechenden Eintrags im Modell und die Position in der das Label im Label[][]
     *          gesetzt wird.
     * @param j die Position des entsprechenden Eintrags im Modell und die Position in der das Label im Label[][]
     *          gesetzt wird.
     * @return das fertige Label.
     */
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
            //if (!modell.istgeloest()) {//Spielabfrage
                Popup popupMoeglicheEintraege = new Popup();
                GridPane gridPaneMoeglicheEintraege = new GridPane();
                int counter = 0;
                int tmp = 1;
                if (BREITE_SUBSPIELFELD < HOEHE_SUBSPIELFELD) {
                    moeglicheWerteAuswahl = new Label[HOEHE_SUBSPIELFELD][BREITE_SUBSPIELFELD];
                } else {
                    moeglicheWerteAuswahl = new Label[BREITE_SUBSPIELFELD][HOEHE_SUBSPIELFELD];
                }
                for (int k = 0; k < moeglicheWerteAuswahl[0].length; k++) {
                    for (int l = 0; l < moeglicheWerteAuswahl.length; l++) {
                        Label tmpLabel = new Label();
                        String wert = Integer.toString(tmp);
                        if (modell.istValide(label.getId(),wert)) {
                            tmpLabel.setText(wert);
                            tmpLabel.setOnMouseReleased(mouseEvent ->
                            {
                                modell.setzeEintrag(label.getId(), tmpLabel.getText());
                                popupMoeglicheEintraege.hide();
                                //if(modell.istgeloest()){
                                //    gewonnen();
                                //}
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
                        tmp++;
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
           // }
        });
        label.textProperty().bind(modell.getSpielfeld()[i][j].getEintragPoperty());
        return label;
    }
    private void gewonnen(){
        gesagtes.setText("Glückwunsch das Sudoku ist gelöst!");
    }

    /**
     * Erstellt ein neues Spielfeld anhand eines vorhandenen Modells.
     */
    private void erzeugeSpielfeldLabelArray(){
        sudokuLabelArray = new Label[modell.getSpielfeld().length][modell.getSpielfeld()[0].length];
        for (int i = 0; i < sudokuLabelArray.length; i++) {
            for (int j = 0; j < sudokuLabelArray[0].length; j++) {
                sudokuLabelArray[i][j] = erzeugeSpielEintragLabel(i, j);
                //.GridPane.add() moechte es natürlich andersrum haben.
                sudokuGridPane.add(sudokuLabelArray[i][j], j, i);

            }
        }
    }
    private void starte(){
        sudokuGridPane = new GridPane();
        sudokuGridPane.setHgap(2);
        sudokuGridPane.setVgap(2);
        sudokuGridPane.setGridLinesVisible(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(sudokuGridPane);
        erzeugeSpielfeldLabelArray();
        primaryStage.sizeToScene();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starte();
    }

}
