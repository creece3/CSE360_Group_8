import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.text.*;

public class Main extends Application {

    Stage window;
    TableView<table> table;
    TextField valueInput;
    Button addButton;
    Button deleteButton;
    Data data;
    TextField errors = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
        VBox root = new VBox();
        SplitPane split_pane = new SplitPane();

        // Data Entry
        HBox dateEntryPane = new HBox();
        dateEntryPane.setPadding(new Insets(15, 12, 15, 12));
        dateEntryPane.setSpacing(10);
        dateEntryPane.setStyle("-fx-background-color: #336699;");

        // Boundaries for dataset
        HBox boundaryPane = new HBox();
        boundaryPane.setPadding(new Insets(15, 12, 15, 12));
        boundaryPane.setSpacing(10);
        boundaryPane.setStyle("-fx-background-color: #336699;");

        //Value input
        TextField minBoundaryInput = new TextField();
        minBoundaryInput.setPromptText("Min");
        TextField maxBoundaryInput = new TextField();
        maxBoundaryInput.setPromptText("Max");

        //Buttons
        Button submitData = new Button("Submit Data");
        Button createReport = new Button("Create Report");
//        addButton.setOnAction(e -> addButtonClicked());

        boundaryPane.getChildren().addAll(minBoundaryInput,maxBoundaryInput,submitData, createReport);

        //Value column
        TableColumn<table, Double> valueColumn = new TableColumn<>("Value");
        valueColumn.setMinWidth(100);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));

        //Value input
        valueInput = new TextField();
        valueInput.setPromptText("Value");

        //Buttons
        addButton = new Button("Add Value");
        addButton.setDisable(true);
        addButton.setOnAction(e -> addButtonClicked());
        deleteButton = new Button("Delete Value");
        deleteButton.setDisable(true);
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(valueInput, addButton, deleteButton);

        table = new TableView<>();
        table.getColumns().addAll(valueColumn);

        VBox tablePane = new VBox();
        tablePane.getChildren().addAll(table, hBox);

        // Dont allow input of data without bounds
        submitData.setOnAction(e -> {
            getBoundaries(minBoundaryInput, maxBoundaryInput);
            addButton.setDisable(false);
            deleteButton.setDisable(false);
        });

        // Add the panes to the dataEntry section
        dateEntryPane.getChildren().addAll(boundaryPane, tablePane);
        root.getChildren().addAll(dateEntryPane);

        // Error Side
        VBox errorBox = new VBox();
        errorBox.setPadding(new Insets(15, 100, 500, 10));
        errorBox.setSpacing(10);
        errorBox.setStyle("-fx-background-color: #ebd834;");

        Text errorHeader = new Text("ERROR:");
        errorHeader.setFont(font);

        errors.setDisable(true);

        errorBox.getChildren().addAll(errorHeader, errors);

        split_pane.getItems().addAll(errorBox);

        // Data Side
        VBox dataBox = new VBox();
        dataBox.setPadding(new Insets(15, 100, 500, 10));
        dataBox.setSpacing(10);
        dataBox.setStyle("-fx-background-color: #3b9dff;");

        Text dataHeader = new Text("DATA:");
        dataHeader.setFont(font);

        TextField dataDisplay = new TextField();
        dataDisplay.setDisable(true);

        dataBox.getChildren().addAll(dataHeader, dataDisplay);

        split_pane.getItems().addAll(dataBox);

        root.getChildren().addAll(split_pane);

        // display the Application
        primaryStage.setTitle("Grading Application");
        Scene scene = new Scene(root, 1000, 800); //setup of the scene inside the pane
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Add button clicked
    private void addButtonClicked(){
        table valTable = new table();
        valTable.setValue(Double.parseDouble(valueInput.getText()));
        table.getItems().add(valTable);
        valueInput.clear();
    }

    //Delete button clicked
    private void deleteButtonClicked(){
        ObservableList<table> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
    }

    // Boundaries for Data
    private void getBoundaries(TextField minBound, TextField maxBound){
        data = new Data();
        data.setMinMax(minBound.getText(), maxBound.getText());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
