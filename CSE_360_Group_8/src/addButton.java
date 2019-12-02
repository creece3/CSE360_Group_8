import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class addButton extends Application {

	Stage window;
	Scene scene;
	Button addButton;

	public static void main(String[] args) {
		launch(args);
	}

  @Override
  public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Grade Analytics");

		//Form
		TextField addInput = new TextField();

		addButton = new Button("Add Value");
		addButton.setOnAction(e -> isInt(addInput, addInput.getText()));

		//Layout
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(addInput, addButton);

		scene = new Scene(layout, 300, 250);
		window.setScene(scene);
		window.show();
	}

	//Validate age
	private boolean isInt(TextField input, String message){
		try {
			int value = Integer.parseInt(input.getText());
			System.out.println("Added Value: " + value);
			return true;
		} catch(NumberFormatException e) {
			System.out.println("Error: " + message + " is not a number");
			return false;
		}
	}
}
