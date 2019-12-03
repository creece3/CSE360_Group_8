import java.awt.Label;
import java.awt.TextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class createReport extends Application {

	Button reportButton;
	TextField report;
	Label mean, median, mode;
	Label high, low;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Tile
		primaryStage.setTitle("Grade Analytics");

        reportButton = new Button("Create Report");
        reportButton.setOnAction(e -> reportButtonClicked());
        
        HBox hbox = new HBox(reportButton);
        Scene scene = new Scene(hbox, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
	
	//Report button clicked
    public void reportButtonClicked() {
    	//FileWriter dataAnalysis;
    	//FileWriter errorLog;
    	BufferedWriter bw1;
    	BufferedWriter bw2;
        
        try {
        	//dataAnalysis = new FileWriter("Data Analysis.txt");
        	//errorLog = new FileWriter("Error Log.txt");
        	
        	bw1 = new BufferedWriter(new FileWriter("DataAnalysis.txt"));
        	bw2 = new BufferedWriter(new FileWriter("ErrorLog.txt"));
        	
        	bw1.write("Data Analysis: ");
        	bw2.write("Error Log: ");
        	
        	bw1.close();
        	bw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
