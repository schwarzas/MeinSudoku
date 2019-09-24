package grundspiel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Spielframe.fxml"));
        Controller controller = new Controller(primaryStage);
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("SudokuStandard");
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
