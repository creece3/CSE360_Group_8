import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class addDelete extends Application {

    Stage window;
    TableView<table> table;
    TextField valueInput;
    Button addButton;
    Button deleteButton;

    public static void main(String[] args) {
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Grade Analytics");

        //Value column
        TableColumn<table, Double> valueColumn = new TableColumn<>("Value");
        valueColumn.setMinWidth(100);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));

        //Value input
        valueInput = new TextField();
        valueInput.setPromptText("Value");

        //Buttons
        addButton = new Button("Add Value");
        addButton.setOnAction(e -> addButtonClicked());
        deleteButton = new Button("Delete Value");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(valueInput, addButton, deleteButton);

        table = new TableView<>();
        table.getColumns().addAll(valueColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

    //Add button clicked
    public void addButtonClicked(){
        table valTable = new table();
        valTable.setValue(Double.parseDouble(valueInput.getText()));
        table.getItems().add(valTable);
        valueInput.clear();
    }

    //Delete button clicked
    public void deleteButtonClicked(){
        ObservableList<table> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
    }

}
