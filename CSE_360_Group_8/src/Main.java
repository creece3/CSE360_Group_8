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
    Data data = null;
    Text errors = new Text();
    Text dataSection = new Text();

    public static void main(String[] args) {
        launch(args);
    }

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
        VBox inputDataPane = new VBox();
        inputDataPane.setPadding(new Insets(15, 12, 15, 12));
        inputDataPane.setSpacing(10);
        inputDataPane.setStyle("-fx-background-color: #336699;");
        HBox boundaryPane = new HBox();
        boundaryPane.setPadding(new Insets(15, 12, 15, 12));
        boundaryPane.setSpacing(10);
        boundaryPane.setStyle("-fx-background-color: #336699;");
        HBox reportButtonsPane = new HBox();
        reportButtonsPane.setPadding(new Insets(15, 12, 15, 12));
        reportButtonsPane.setSpacing(10);
        reportButtonsPane.setStyle("-fx-background-color: #336699;");

        inputDataPane.getChildren().addAll(boundaryPane, reportButtonsPane);

        //Value input
        Text minLabel = new Text("Min: ");
        TextField minBoundaryInput = new TextField();
        minBoundaryInput.setText("0");
        Text maxLabel = new Text("Max: ");
        TextField maxBoundaryInput = new TextField();
        maxBoundaryInput.setText("100");

        //Buttons
        Button submitData = new Button("Submit Data");
        Button createReport = new Button("Create Report");
        Button importFile = new Button("Import File");
//        addButton.setOnAction(e -> addButtonClicked());

        boundaryPane.getChildren().addAll(minLabel, minBoundaryInput, maxLabel, maxBoundaryInput, submitData);
        reportButtonsPane.getChildren().addAll(createReport, importFile);

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

        // Don't allow input of data without bounds
        submitData.setOnAction(e -> {
            getBoundaries(minBoundaryInput, maxBoundaryInput);
            addButton.setDisable(false);
            deleteButton.setDisable(false);
        });

        // Add the panes to the dataEntry section
        dateEntryPane.getChildren().addAll(inputDataPane, tablePane);
        root.getChildren().addAll(dateEntryPane);

        // Error Side
        VBox errorBox = new VBox();
        errorBox.setPadding(new Insets(15, 100, 500, 10));
        errorBox.setSpacing(10);
        errorBox.setStyle("-fx-background-color: #ebd834;");

        Text errorHeader = new Text("ERROR:");
        errorHeader.setFont(font);

        errorBox.getChildren().addAll(errorHeader, errors);

        split_pane.getItems().addAll(errorBox);

        // Data Side
        VBox dataBox = new VBox();
        dataBox.setPadding(new Insets(15, 100, 500, 10));
        dataBox.setSpacing(10);
        dataBox.setStyle("-fx-background-color: #3b9dff;");

        Text dataHeader = new Text("DATA:");
        dataHeader.setFont(font);

        dataBox.getChildren().addAll(dataHeader, dataSection);

        split_pane.getItems().addAll(dataBox);

        root.getChildren().addAll(split_pane);

        // display the Application
        primaryStage.setTitle("Grading Application");
        Scene scene = new Scene(root, 1000, 800); //setup of the scene inside the pane
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Function to add inputted data to the Data Table
     */
    private void addButtonClicked(){
        // NEED TO CHECK DATA TYPE
        if (data == null)
            data = new Data();

        table valTable = new table();
        valTable.setValue(Double.parseDouble(valueInput.getText()));
        table.getItems().add(valTable);

        // Insert and print data
        data.insertData(valueInput.getText());
        dataSection.setText(data.printData());

        valueInput.clear();

        errors.setText(data.printErrors());

    }

    /**
     * Function to remove selected data from the Data Table
     */
    private void deleteButtonClicked(){
        ObservableList<table> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
    }

    /**
     * Function to set the min and max variables that bound the data that
     * analysis is done on. If the min and max values are not floats, an
     * error is called and displayed to the Error TextArea
     *
     * @param minBound The min value to bound the data that analysis is done on.
     * @param maxBound The max value to bound the data that analysis is done on.
     */
    private void getBoundaries(TextField minBound, TextField maxBound){
        if (data == null)
            data = new Data();
        data.setMinMax(minBound.getText(), maxBound.getText());

        errors.setText(data.printErrors());
    }
}
