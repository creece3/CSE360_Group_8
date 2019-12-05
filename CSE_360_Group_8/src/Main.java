
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    TableView<table> table;
    TextField valueInput;
    Button addButton;
    Button deleteButton;
    Data data = null;
    Text errors = new Text();
    
    //Historgram Data

    List <Float> grades = new ArrayList<Float>();
    int DATA_SIZE = 1000;
    int hData[] = new int[DATA_SIZE];
    int group[] = new int[10];

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

        // Don't allow input of data without bounds
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

        errors.setStyle("-fx-text-inner-color: red; -fx-font-size: 16px;");
//        errors.setDisable(true);

        errorBox.getChildren().addAll(errorHeader, errors);

        split_pane.getItems().addAll(errorBox);

        // Data Side
        VBox dataBox = new VBox();
        dataBox.setPadding(new Insets(15, 100, 500, 10));
        dataBox.setSpacing(10);
        dataBox.setStyle("-fx-background-color: #3b9dff;");

        Text dataHeader = new Text("DATA:");
        dataHeader.setFont(font);

        Text dataDisplay = new Text();

        dataBox.getChildren().addAll(dataHeader, dataDisplay);

        split_pane.getItems().addAll(dataBox);

        root.getChildren().addAll(split_pane);
        
        // Historgram
        
        /*
            Random floats for Data data
            FOR TESTING
        */
        //***********************
        Data gradeList = new Data();

        Random r = new Random();

        for (int i = 0; i < DATA_SIZE; i++) {
            Float random = 0 + r.nextFloat() * (100 - 0);
            grades.add(random);
            gradeList.insertData(random.toString());
        }
        groupData();
        //***********************
        Label labelInfo = new Label();
        labelInfo.setText(
                "Historgram: "  + "\n"
                );

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart
                = new BarChart<>(xAxis, yAxis);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        xAxis.setLabel("Grades");
        yAxis.setLabel("Frequency");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Histogram");
        series1.getData().add(new XYChart.Data("0-10", group[0]));
        series1.getData().add(new XYChart.Data("10-20", group[1]));
        series1.getData().add(new XYChart.Data("20-30", group[2]));
        series1.getData().add(new XYChart.Data("30-40", group[3]));
        series1.getData().add(new XYChart.Data("40-50", group[4]));

        series1.getData().add(new XYChart.Data("50-60", group[5]));
        series1.getData().add(new XYChart.Data("60-70", group[6]));
        series1.getData().add(new XYChart.Data("70-80", group[7]));
        series1.getData().add(new XYChart.Data("80-90", group[8]));
        series1.getData().add(new XYChart.Data("90-100", group[9]));

        barChart.getData().addAll(series1);

        VBox HistvBox = new VBox();
        HistvBox.getChildren().addAll(labelInfo, barChart);        

        Scene histScene = new Scene(HistvBox, 800, 450);
        Button histB = new Button("Make Historgram!");
        
        histB.setOnAction(e -> 
        {
            //groupData();
            primaryStage.setScene(histScene);
        });
        root.getChildren().addAll(histB);
        
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
        if (data == null)
            data = new Data();
        data.setMinMax(minBound.getText(), maxBound.getText());

        errors.setText(data.printErrors());
    }
    // Readies histogram
    private void groupData()
    {
        //grades = data.getData();
       
        //grades.add(Data.data.get(i));
        for(int i=0; i<10; i++){
            group[i]=0;
        }
        for(int i=0; i<DATA_SIZE; i++)
        {
            if(grades.get(i)<=10){
                group[0]++;
            }else if(grades.get(i)<=20){
                group[1]++;
            }else if(grades.get(i)<=30){
                group[2]++;
            }else if(grades.get(i)<=40){
                group[3]++;
            }else if(grades.get(i)<=50){
                group[4]++;
            }else if(grades.get(i)<=60){
                group[5]++;
            }else if(grades.get(i)<=70){
                group[6]++;
            }else if(grades.get(i)<=80){
                group[7]++;
            }else if(grades.get(i)<=90){
                group[8]++;
            }else if(grades.get(i)<=100){
                group[9]++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
