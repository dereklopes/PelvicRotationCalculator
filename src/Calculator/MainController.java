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
    @FXML
    public TableColumn<Result, Double> s2ToMFHTrueCol;
    @FXML
    public TableColumn<Result, Double> s2ToFilmAPCol;
    @FXML
    public TableColumn<Result, Double> MFHToFilmTrueCol;
    @FXML
    public TableColumn<Result, Double> s2ToMFHAPFilmCol;
    @FXML
    public TableColumn<Result, Double> s2ToMFHOffsetCol;
    @FXML
    public TableColumn<Result, Double> rotationalDegreeCol;

    ObservableList<Result> resultsTableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        focalFilmDistanceCol.setCellValueFactory(cellData -> cellData.getValue().focalFilmDistanceProperty().asObject());
        s2ToFilmLateralCol.setCellValueFactory(cellData -> cellData.getValue().s2ToFilmLateralProperty().asObject());
        s2ToMFHLateralFilmCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHLateralFilmProperty().asObject());
        s2ToFilmAPCol.setCellValueFactory(cellData -> cellData.getValue().s2ToFilmAPProperty().asObject());
        s2ToMFHAPFilmCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHAPFilmProperty().asObject());
        s2ToMFHTrueCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHTrueProperty().asObject());
        MFHToFilmTrueCol.setCellValueFactory(cellData -> cellData.getValue().MFHToFilmTrueProperty().asObject());
        s2ToMFHOffsetCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHOffsetProperty().asObject());
        rotationalDegreeCol.setCellValueFactory(cellData -> cellData.getValue().rotationDegreeProperty().asObject());
    }

    @FXML
    public void calculateButtonPressed() {
        resultsTableData.add(
                new Result(
                        new Double(focalFilmDistanceField.getText()),
                        new Double(s2ToFilmLateralField.getText()),
                        new Double(s2ToMFHLateralFilmField.getText()),
                        new Double(s2ToFilmAPField.getText()),
                        new Double(s2ToMFHAPFilmField.getText())
                )
        );
        resultsTable.setItems(resultsTableData);
    }
}
