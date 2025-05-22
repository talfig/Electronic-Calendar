import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ElectronicCalendar extends Application {
    // Constants defining the dimensions of the calculator window
    private static final int CALENDAR_WIDTH_SIZE = 600;
    private static final int CALENDAR_HEIGHT_SIZE = 400;

    /**
     * The main entry point for all JavaFX applications.
     * This method is called after the application is launched.
     *
     * @param stage the primary stage for this application.
     * @throws Exception if loading the FXML file fails.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load the calculator layout from the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("calendar.fxml"));
        Scene scene = new Scene(root, CALENDAR_WIDTH_SIZE, CALENDAR_HEIGHT_SIZE);
        stage.setTitle("Electronic Calendar");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
        System.out.println();
    }
}
