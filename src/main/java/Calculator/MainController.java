package Calculator;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public TableView S2resultsTable;
    @FXML
    public TableView MFHresultsTable;
    @FXML
    public TableView inputTable;
    @FXML
    public TableColumn<Result, String> nameCol;
    @FXML
    public TableColumn<Result, Double> focalFilmDistanceCol;
    @FXML
    public TableColumn<Result, Double> s2ToFilmLateralCol;
    @FXML
    public TableColumn<Result, Double> s2ToMFHLateralFilmCol;
    @FXML
    public TableColumn<Result, String> unitsCol;
    @FXML
    public TableColumn<Result, Double> s2ToMFHTrueCol;
    @FXML
    public TableColumn<Result, Double> s2ToFilmAPCol;
    @FXML
    public TableColumn<Result, Double> MFHToFilmTrueCol;
    @FXML
    public TableColumn<Result, Double> s2ToMFHAPFilmCol;
    @FXML
    public TableColumn<Result, Double> S2s2ToMFHOffsetCol;
    @FXML
    public TableColumn<Result, Double> MFHs2ToMFHOffsetCol;
    @FXML
    public TableColumn<Result, Double> rotationalDegreeColS2;
    @FXML
    public TableColumn<Result, Double> rotationalDegreeColMFH;

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

    @FXML
    public MenuBar mainMenu;
    @FXML
    public MenuItem fileOpen;
    @FXML
    public MenuItem fileSave;
    @FXML
    public MenuItem fileSaveAs;
    @FXML
    public MenuItem fileClose;
    @FXML
    public MenuItem editDeleteSelectedRow;
    @FXML
    public MenuItem helpAbout;

    private ArrayList<Result> resultList;
    private ObservableList<Result> tableData = FXCollections.observableArrayList();
    private HostServices hostServices;
    private File currentFile;
    private Integer selectedRow = -1;

    @FXML
    private void initialize() {
        if (CalculatorApplication.os != null && CalculatorApplication.os.startsWith("Mac")) {
            this.mainMenu.useSystemMenuBarProperty().setValue(true);
        }

        // Populate choice boxes with unit types
        ChoiceBox[] unitChoiceBoxes = {
                focalFilmDistanceUnitsBox,
                s2ToFilmLateralUnitsBox,
                s2ToMFHLateralFilmUnitsBox,
                s2ToFilmAPUnitsBox,
                s2ToMFHAPFilmUnitsBox,
                resultUnitsBox,
        };
        for (ChoiceBox unitChoiceBox : unitChoiceBoxes) {
            unitChoiceBox.setItems(FXCollections.observableArrayList("in", "cm", "mm"));
        }

        // set context menus for tables
        ContextMenu tableContextMenu = new ContextMenu();
        MenuItem deleteRow = new MenuItem("Delete Row...");
        deleteRow.setOnAction(e -> deleteRow());
        tableContextMenu.getItems().add(deleteRow);
        inputTable.setContextMenu(tableContextMenu);
        resultsTable.setContextMenu(tableContextMenu);
        S2resultsTable.setContextMenu(tableContextMenu);
        MFHresultsTable.setContextMenu(tableContextMenu);

        resultList = new ArrayList<>();

        // Populate table data
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        focalFilmDistanceCol.setCellValueFactory(cellData -> cellData.getValue().focalFilmDistanceProperty().asObject());
        s2ToFilmLateralCol.setCellValueFactory(cellData -> cellData.getValue().s2ToFilmLateralProperty().asObject());
        s2ToMFHLateralFilmCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHLateralFilmProperty().asObject());
        s2ToFilmAPCol.setCellValueFactory(cellData -> cellData.getValue().s2ToFilmAPProperty().asObject());
        s2ToMFHAPFilmCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHAPFilmProperty().asObject());
        unitsCol.setCellValueFactory(cellData -> cellData.getValue().resultUnitsProperty());
        s2ToMFHTrueCol.setCellValueFactory(cellData -> cellData.getValue().s2ToMFHTrueProperty().asObject());
        MFHToFilmTrueCol.setCellValueFactory(cellData -> cellData.getValue().MFHToFilmTrueProperty().asObject());
        S2s2ToMFHOffsetCol.setCellValueFactory(cellData -> cellData.getValue().s2s2ToMFHOffsetProperty().asObject());
        MFHs2ToMFHOffsetCol.setCellValueFactory(cellData -> cellData.getValue().MFHs2ToMFHOffsetProperty().asObject());
        rotationalDegreeColS2.setCellValueFactory(cellData -> cellData.getValue().s2RotationDegreeProperty().asObject());
        rotationalDegreeColMFH.setCellValueFactory(cellData -> cellData.getValue().MFHRotationDegreeProperty().asObject());
    }

    @FXML
    public void calculateButtonPressed() {
        try {
            Result result = new Result(
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
            );
            this.resultList.add(result);
            updateTables(this.resultList);
        } catch (NumberFormatException e) {
            showErrorPopup("Provided values are invalid. Please try again.");
        } catch (Exception e) {
            showErrorPopup("An error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void save() {
        if (this.currentFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(currentFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(resultList);
                fos.close();
                oos.close();
            } catch (FileNotFoundException e) {
                showErrorPopup("Could not find file. Error: " + e.getMessage());
            } catch (IOException e) {
                showErrorPopup("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            saveAs();
        }
    }

    @FXML
    public void saveAs() {
        // Create and configure save dialogue
        final Stage saveWindow = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PRC files (*.prc)", "*.prc");
        fileChooser.getExtensionFilters().add(extFilter);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date curDateTime = new Date();
        fileChooser.setInitialFileName("results_" + dateFormat.format(curDateTime));

        // Show save dialogue and save file
        File saveFile = fileChooser.showSaveDialog(saveWindow);
        if (saveFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(saveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(resultList);
                fos.close();
                oos.close();
                this.currentFile = saveFile;
                CalculatorApplication.getApp().setOpenFileName(saveFile.getName());
            } catch (FileNotFoundException e) {
                showErrorPopup("Could not find file. Error: " + e.getMessage());
            } catch (IOException e) {
                showErrorPopup("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void open() {
        // Create and configure open dialogue
        final Stage openWindow = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PRC files (*.prc)", "*.prc");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open dialogue and load file
        File openFile = fileChooser.showOpenDialog(openWindow);
        if (openFile != null) {
            try {
                FileInputStream fis = new FileInputStream(openFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object readObject = ois.readObject();
                if (readObject instanceof ArrayList) {
                    ArrayList readArrayList = (ArrayList)readObject;
                    if (readArrayList.size() != 0)
                        this.resultList = (ArrayList<Result>)readObject;
                } else {
                    showErrorPopup("Selected file is not a valid PRC file. Cannot load results.");
                }
                fis.close();
                ois.close();

                updateTables(this.resultList);
            } catch (FileNotFoundException e) {
                showErrorPopup("Could not find file. Error: " + e.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                showErrorPopup("An error occurred: " + e.getMessage());
            }
        }
    }

    @FXML
    public void close() {
        Platform.exit();
    }

    @FXML
    public void selectRow() {
        int selectedInput = inputTable.getSelectionModel().getFocusedIndex();
        int selectedResult = resultsTable.getSelectionModel().getFocusedIndex();
        int selectedS2Result = S2resultsTable.getSelectionModel().getFocusedIndex();
        int selectedMFHResult = MFHresultsTable.getSelectionModel().getFocusedIndex();
        if (selectedInput != selectedRow) {
            selectedRow = selectedInput;
            resultsTable.getSelectionModel().clearAndSelect(selectedRow);
            S2resultsTable.getSelectionModel().clearAndSelect(selectedRow);
            MFHresultsTable.getSelectionModel().clearAndSelect(selectedRow);
        } else if (selectedResult != this.selectedRow) {
            selectedRow = selectedResult;
            inputTable.getSelectionModel().clearAndSelect(selectedRow);
            S2resultsTable.getSelectionModel().clearAndSelect(selectedRow);
            MFHresultsTable.getSelectionModel().clearAndSelect(selectedRow);
        } else if (selectedS2Result != this.selectedRow) {
            selectedRow = selectedS2Result;
            inputTable.getSelectionModel().clearAndSelect(selectedRow);
            resultsTable.getSelectionModel().clearAndSelect(selectedRow);
            MFHresultsTable.getSelectionModel().clearAndSelect(selectedRow);
        } else if (selectedMFHResult != this.selectedRow) {
            selectedRow = selectedMFHResult;
            inputTable.getSelectionModel().clearAndSelect(selectedRow);
            resultsTable.getSelectionModel().clearAndSelect(selectedRow);
            S2resultsTable.getSelectionModel().clearAndSelect(selectedRow);
        }
    }

    @FXML
    public void deleteRow() {
        if (resultList.isEmpty())
            return;
        // delete or backspace was pressed, show confirmation window and delete the row from each table if confirmed
        final Stage confirmDeleteWindow = new Stage();
        Integer readableSelectedRow = selectedRow + 1;
        Label confirmDialogue = new Label("Are you sure you want to delete row " +
                readableSelectedRow.toString() + " titled '" + resultList.get(selectedRow).nameProperty().getValue()
                + "'?");
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ff5454; -fx-text-fill: white");
        deleteButton.setOnAction(e -> {
            resultList.remove(selectedRow.intValue());
            updateTables(resultList);
            confirmDeleteWindow.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setDefaultButton(true);
        cancelButton.setOnAction(e -> confirmDeleteWindow.close());
		VBox confirmDeleteVBox = new VBox();
		confirmDeleteVBox.setAlignment(Pos.CENTER);
		confirmDeleteVBox.getChildren().add(confirmDialogue);
		confirmDeleteVBox.setPadding(new Insets(10));
		confirmDeleteVBox.setSpacing(5);
		HBox buttonHBox = new HBox();
		buttonHBox.getChildren().addAll(deleteButton, cancelButton);
		buttonHBox.setAlignment(Pos.CENTER);
		buttonHBox.setPadding(new Insets(10));
		buttonHBox.setSpacing(5);
		confirmDeleteVBox.getChildren().add(buttonHBox);
        Scene confirmDeleteScene = new Scene(confirmDeleteVBox);
        confirmDeleteWindow.setScene(confirmDeleteScene);
        confirmDeleteWindow.show();
    }

    @FXML
    public void showAbout() {
        final Stage aboutWindow = new Stage();
        Label instructions = new Label("Visit this site for information and instructions:");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> aboutWindow.close());
        String readmeLink = "https://github.com/dereklopes/PelvicRotationCalculator/blob/master/README.md";
        Hyperlink gitLink = new Hyperlink(readmeLink);
        gitLink.setOnAction(event -> hostServices.showDocument(gitLink.getText()));
        VBox aboutVBox = new VBox();
        aboutVBox.getChildren().addAll(instructions, gitLink, closeButton);
        aboutVBox.setAlignment(Pos.CENTER);
        aboutVBox.setPadding(new Insets(10));
        aboutVBox.setSpacing(5);
        Scene aboutScene = new Scene(aboutVBox);
        aboutWindow.setScene(aboutScene);
        aboutWindow.show();
    }

    void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    /**
     * Updates table values based on the
     */
    private void updateTables(ArrayList<Result> results) {
        this.tableData.setAll(results);
        this.inputTable.setItems(tableData);
        this.resultsTable.setItems(tableData);
        this.S2resultsTable.setItems(tableData);
        this.MFHresultsTable.setItems(tableData);
    }

    private void showErrorPopup(String message) {
        final Stage errorWindow = new Stage();
        errorWindow.initModality(Modality.WINDOW_MODAL);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> errorWindow.close());
        VBox errorVBox = new VBox();
        errorVBox.getChildren().addAll(new Text(message), closeButton);
        errorVBox.setAlignment(Pos.CENTER);
        errorVBox.setPadding(new Insets(10));
        errorVBox.setSpacing(5);
        Scene errorScene = new Scene(errorVBox);
        errorWindow.setScene(errorScene);
        errorWindow.show();
    }
}
