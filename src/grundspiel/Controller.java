package grundspiel;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

public class Controller implements Initializable {
    Modell modell;
    @FXML private GridPane primaryPane;
    @FXML private Label gesagtes;
    @FXML private GridPane sudokuGridPane;
    @FXML private Label[][] sudokuLabelArray;
    @FXML private Stage primaryStage;



    public Controller(Stage primaryStage) {
        modell = new Modell();
        this.primaryStage = primaryStage;

    }

    public void buttonNeuesSpiel(){
        System.out.println("neues Spiel gestartet");
        gesagtes.setText("neues Spiel gestartet");
    }

//Spielfeld auf Kommandozeile ausgeben
    private void gibSpielfeldAus(){
        SudokuEintrag[][] spielfeld =modell.getSpielfeld();
        for (SudokuEintrag[] sudokuEintrags : spielfeld) {
            for (SudokuEintrag sudokuEintrag : sudokuEintrags) {
                System.out.print(sudokuEintrag.getEintrag());
            }
            System.out.println("");
        }
    }


    private Label erzeugeSpielEintragLabel(int i, int j){
        Label label = new Label(modell.getSpielfeld()[i][j].getEintrag());
        label.setMinHeight(60);
        label.setMinWidth(60);
        label.setAlignment(CENTER);
        label.setStyle("-fx-background-color: white");
        label.setOnMouseEntered(e -> label.setStyle("-fx-background-color: grey;"));
        label.setOnMouseExited(e -> label.setStyle("-fx-background-color: white"));
        label.setOnMouseReleased(e -> {
            ArrayList<String> moeglicheEintraege = modell.getSpielfeld()[i][j].getMoeglicheEintraege();
            Popup popupMoeglicheEintraege = new Popup();
            GridPane gridPaneMoeglicheEintraege = new GridPane();
            int counter=0;
            if(!moeglicheEintraege.isEmpty()){

                while (counter<moeglicheEintraege.size())
                {
                    Label label1 = new Label(moeglicheEintraege.get(counter));
                    label1.setPrefSize(20,20);
                    label1.setAlignment(CENTER);
                    gridPaneMoeglicheEintraege.add(label1, (int) ( counter / 3), counter % 3);
                    counter++;
                }
            }
            gridPaneMoeglicheEintraege.setGridLinesVisible(true);
            Label ruecksetzen = new Label("Rücksetzen");
            gridPaneMoeglicheEintraege.add(ruecksetzen,0,counter+1);
            gridPaneMoeglicheEintraege.setColumnSpan(ruecksetzen,3);
            ruecksetzen.setPrefSize(60,20);
            popupMoeglicheEintraege.getContent().add(gridPaneMoeglicheEintraege);
            popupMoeglicheEintraege.setAutoHide(true);
            Point2D position = label.localToScene(0,0);
            popupMoeglicheEintraege.setAnchorX(primaryStage.getX()+position.getX()+8);
            popupMoeglicheEintraege.setAnchorY(primaryStage.getY()+position.getY()+29);
            if(!popupMoeglicheEintraege.isShowing()){
                popupMoeglicheEintraege.show(primaryStage);
            }


        });

        return label;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sudokuGridPane = new GridPane();
        sudokuGridPane.setHgap(2);
        sudokuGridPane.setVgap(2);
        sudokuGridPane.setGridLinesVisible(true);
        sudokuLabelArray = new Label[modell.getSpielfeld().length][modell.getSpielfeld()[0].length];
        for (int i = 0; i <sudokuLabelArray.length ; i++) {
            for (int j = 0; j < sudokuLabelArray[i].length; j++) {
                sudokuLabelArray[i][j]= erzeugeSpielEintragLabel(i,j);
                sudokuLabelArray[i][j].setId(i+","+j);
                sudokuGridPane.add(sudokuLabelArray[i][j],i,j);

            }
        }
        primaryPane.add(sudokuGridPane,0,2);



    }
}
