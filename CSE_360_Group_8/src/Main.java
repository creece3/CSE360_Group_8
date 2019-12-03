package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = new VBox();
        SplitPane split_pane = new SplitPane();

        // HBOX
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonProjected);
        root.getChildren().addAll(hbox);

        // Error Side
        VBox errorBox = new VBox();
        errorBox.setPadding(new Insets(15, 100, 500, 10));
        errorBox.setSpacing(10);
        errorBox.setStyle("-fx-background-color: #ebd834;");

        Text errorHeader = new Text("ERROR:");

        errorBox.getChildren().addAll(errorHeader);

        split_pane.getItems().addAll(errorBox);

        // Data Side
        VBox dataBox = new VBox();
        dataBox.setPadding(new Insets(15, 100, 500, 10));
        dataBox.setSpacing(10);
        dataBox.setStyle("-fx-background-color: #3b9dff;");

        Text dataHeader = new Text("DATA:");

        dataBox.getChildren().addAll(dataHeader);

        split_pane.getItems().addAll(dataBox);

        root.getChildren().addAll(split_pane);

        // display the Application
        primaryStage.setTitle("Grading Application");
        Scene scene = new Scene(root, 500, 400); //setup of the scene inside the pane
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
