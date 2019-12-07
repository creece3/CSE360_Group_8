import java.io.File;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {

    Stage window;
    TableView<table> table;
    TextField valueInput;
    Button addButton;
    Button deleteButton;
    Data data = null;
    Text errors = new Text();
    Text dataSection = new Text();

    //Historgram Data
    List <Float> grades = new ArrayList<Float>();
    int DATA_SIZE = 1000;
    int hData[] = new int[DATA_SIZE];
    int group[] = new int[10];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
        VBox root = new VBox();
        SplitPane split_pane = new SplitPane();
        Scene scene = new Scene(root, 1000, 800); //setup of the scene inside the pane

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
        //File Chooser tool for importing files
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("Text Files", "*.txt"),
        		new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        //Buttons
        Button submitData = new Button("Submit Data");
        Button createReport = new Button("Create Report");
        createReport.setOnAction(e -> {
            createDataReport();
            createOperationReport();
        });
        Button importFile = new Button("Import File");
        importFile.setOnAction(e -> { File selectedFile = fileChooser.showOpenDialog(primaryStage); });
//        addButton.setOnAction(e -> addButtonClicked());

        boundaryPane.getChildren().addAll(minLabel, minBoundaryInput, maxLabel, maxBoundaryInput, submitData);
        reportButtonsPane.getChildren().addAll(createReport, importFile);

        //Value column
        TableColumn<table, Double> valueColumn = new TableColumn<>("Value");
        valueColumn.setMinWidth(100);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));

        //Value input
        valueInput = new TextField();
        valueInput.setPromptText("Value to Insert");

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
            submitData.setDisable(true);
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
        Button returnToMain = new Button("Return to Main Screen");
        returnToMain.setOnAction(e -> primaryStage.setScene(scene));
        reportButtonsPane.getChildren().addAll(histB);
        HistvBox.getChildren().addAll(returnToMain);
        //root.getChildren().addAll(histB);


        // display the Application
        primaryStage.setTitle("Grading Application");
        
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

        // Insert and print data
        data.insertData(valueInput.getText());
        dataSection.setText(data.printData());

        // check if the data is within bounds
        if (Double.parseDouble(valueInput.getText()) >= data.min && data.max >= Double.parseDouble(valueInput.getText())) {

        	table.getItems().clear();
        	
        	for(Float element : data.getData()) 
        	{
        		table valTable = new table();
                valTable.setValue(Double.parseDouble(element.toString()));
                table.getItems().add(valTable);
        	}
        }
        
        valueInput.clear();

        errors.setText(data.printErrors());

    }

    /**
     * Function to remove selected data from the Data Table
     */
    private void deleteButtonClicked(){
        // NEED TO CHECK DATA TYPE
        if (data == null)
            data = new Data();
        
        ObservableList<table> productSelected;
        productSelected = table.getSelectionModel().getSelectedItems();
        
        for(table tableElement : productSelected) 
        {
        	data.deleteElement(Double.toString((tableElement.getValue())));
        }
        
    	table.getItems().clear();
    	
    	for(Float element : data.getData()) 
    	{
    		table valTable = new table();
            valTable.setValue(Double.parseDouble(element.toString()));
            table.getItems().add(valTable);
    	}
    	
        dataSection.setText(data.printData());
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
    
     /**
     * Function to print the report for all data gathered and analyzed
     */
    private void createDataReport(){
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Data Analysis.txt"));

            buffWriter.write("=== Data ===\n" + data.printData());
            buffWriter.write("\n" + data.mean() + "\n" + data.median() + "\n" +  data.mode());

            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println((e));
        }
    }

     /**
     * Function to print the report for all operations done
     */
    private void createOperationReport(){
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Operations.txt"));

            buffWriter.write("=== Operations ===\n" + data.printHistory());

            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println((e));
        }
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
    private void groupData2()
    {
        //grades = data.getData();

        //grades.add(Data.data.get(i));
        Float min = data.min;
        Float max = data.max;
        
        Float split = (max - min)/10;
        
        for(int i=0; i<10; i++){
            group[i]=0;
        }
        for(int i=0; i<grades.size(); i++)
        {
            Float grade = grades.get(i);
            if(grade >= min && grade <= (min + split)){
                group[0]++;
            }else if(grade >= min + split && grade <= min + split * 2){
                group[1]++;
            }else if(grade >= min + split * 2 && grade <= min + split* 3){
                group[2]++;
            }else if(grade >= min + split * 3 && grade <= (min + split * 4)){
                group[3]++;
            }else if(grade >= min + split * 4 && grade <= (min + split * 5)){
                group[4]++;
            }else if(grade >= min + split * 5 && grade <= (min + split * 6)){
                group[5]++;
            }else if(grade >= min + split * 6 && grade <= (min + split * 7)){
                group[6]++;
            }else if(grade >= min + split * 7 && grade <= (min + split * 8)){
                group[7]++;
            }else if(grade >= min + split * 8 && grade <= (min + split * 9)){
                group[8]++;
            }else if(grade >= min + split * 9 && grade <= max){
                group[9]++;
            }
        }
    }
}
