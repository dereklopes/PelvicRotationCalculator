package Calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorApplication extends Application {
    static String os;
    private Stage primaryStage;

    private static final String baseTitle = "Pelvic Rotation Calculator";

    // Make a singleton so primaryStage can be accessed in the controller
    private static CalculatorApplication app;
    static CalculatorApplication getApp() {
        return app;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        app = this;
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();

        MainController mc = loader.getController();
        mc.setHostServices(getHostServices());
        this.primaryStage.setTitle(baseTitle);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public void setOpenFileName(String fileName) {
        this.primaryStage.setTitle(fileName + " - " + baseTitle);
    }

    public static void main(String[] args) {
        os = System.getProperty("os.name");
        launch(args);
    }
}
