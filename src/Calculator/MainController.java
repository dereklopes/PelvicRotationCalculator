package Calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MainController {
    @FXML
    public TextField nameField;
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
    public TableColumn<Result, String> nameCol;
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

    @FXML
    public ChoiceBox focalFilmDistanceUnitsBox;
    @FXML
    public ChoiceBox s2ToFilmLateralUnitsBox;
    @FXML
    public ChoiceBox s2ToMFHLateralFilmUnitsBox;
    @FXML
    public ChoiceBox s2ToFilmAPUnitsBox;
    @FXML
    public ChoiceBox s2ToMFHAPFilmUnitsBox;
    @FXML
    public ChoiceBox resultUnitsBox;

    ObservableList<Result> resultsTableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Populate choice boxes with unit types
        ChoiceBox[] unitChoiceBoxes = {
                focalFilmDistanceUnitsBox,
                s2ToFilmLateralUnitsBox,
                s2ToMFHLateralFilmUnitsBox,
                s2ToFilmAPUnitsBox,
                s2ToMFHAPFilmUnitsBox,
                resultUnitsBox,
        };
        for (ChoiceBox unitChoiceBox: unitChoiceBoxes) {
            unitChoiceBox.setItems(FXCollections.observableArrayList("cm", "in"));
        }

        // Populate table data
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
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
        try {
            resultsTableData.add(
                    new Result(
                            nameField.getText(),
                            new Double(focalFilmDistanceField.getText()),
                            new Double(s2ToFilmLateralField.getText()),
                            new Double(s2ToMFHLateralFilmField.getText()),
                            new Double(s2ToFilmAPField.getText()),
                            new Double(s2ToMFHAPFilmField.getText()),
                            focalFilmDistanceUnitsBox.getValue().toString(),
                            s2ToFilmLateralUnitsBox.getValue().toString(),
                            s2ToMFHLateralFilmUnitsBox.getValue().toString(),
                            s2ToFilmAPUnitsBox.getValue().toString(),
                            s2ToMFHAPFilmUnitsBox.getValue().toString(),
                            resultUnitsBox.getValue().toString()
                    )
            );
            resultsTable.setItems(resultsTableData);
        } catch (NumberFormatException e) {
            showErrorPopup("Provided values are invalid. Please try again.");
        } catch (Exception e) {
            showErrorPopup("An error occurred: " + e.getMessage());
        }
    }

    private void showErrorPopup(String message) {
        final Stage errorWindow = new Stage();
        errorWindow.initModality(Modality.WINDOW_MODAL);
        Button okButton = new Button("Close");
        okButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                errorWindow.close();
            }
        });
        Scene myDialogScene = new Scene(VBoxBuilder.create()
                .children(new Text(message), okButton)
                .alignment(Pos.CENTER)
                .padding(new Insets(10))
                .spacing(5)
                .build());

        errorWindow.setScene(myDialogScene);
        errorWindow.show();
    }
}
