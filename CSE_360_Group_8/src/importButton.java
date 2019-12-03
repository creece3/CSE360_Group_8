import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class importButton extends Application {
	
	Button importButton;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grade Analytics");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("Text Files", "*.txt"),
        		new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        importButton = new Button("Import");
        importButton.setOnAction(e -> { File selectedFile = fileChooser.showOpenDialog(primaryStage); });

        VBox vBox = new VBox(importButton);
        Scene scene = new Scene(vBox, 300, 100);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
