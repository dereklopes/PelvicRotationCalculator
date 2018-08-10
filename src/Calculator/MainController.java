package Calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;


public class MainController {
    @FXML
    public TextField focalFilmDistanceField;
    @FXML
    public TextField s2ToFilmLateralField;
    @FXML
    public TextField s2ToMFHLateralFilmField;
    @FXML
    public TextField s2ToFilmAPField;
    @FXML
    public TextField s2ToMFHAPFilmField;
    @FXML
    public Button calculateButton;

    @FXML
    public TableView resultsTable;
    @FXML
    public TableColumn<Result, Double> focalFilmDistanceCol;
    @FXML
    public TableColumn<Result, Double> s2ToFilmLateralCol;
    @FXML
    public TableColumn<Result, Double> s2ToMFHLateralFilmCol;

    ObservableList<Result> resultsTableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        focalFilmDistanceCol.setCellValueFactory(cellData -> cellData.getValue().focalFilmDistanceProperty().asObject());
        s2ToFilmLateralCol.setCellValueFactory(cellData -> cellData.getValue().s2ToFilmLateralProperty().asObject());
        s2ToMFHLateralFilmCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHLateralFilmProperty().asObject());
    }

    @FXML
    public void calculateButtonPressed() {
        // TODO: Populate new row in resultsTable
        System.out.println(focalFilmDistanceField.getText());
        resultsTableData.add(
                new Result(
                        new Double(focalFilmDistanceField.getText()),
                        new Double(s2ToFilmLateralField.getText()),
                        new Double(s2ToMFHLateralFilmField.getText())
                )
        );
        resultsTable.setItems(resultsTableData);
    }
}
