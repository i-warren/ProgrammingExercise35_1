import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This program creates a connection interface to connect to a database using
 * the DBConnectionPane class. User can then update the database using a batch
 * update or non-batch update and see the efficiency difference.
 *
 * @author Isaac Warren
 */

public class Exercise35_1 extends Application {

    @Override   //Overrides start in Application class
    public void start(Stage stage) {
        // creates new DBConnectionPane
        DBConnectionPane connectionPane = new DBConnectionPane();

        // Create a new scene and set it in the stage
        Scene scene = new Scene(connectionPane, 350, 200);
        stage.setTitle("Exercise 35_1");        // set title
        stage.setScene(scene);      // place scene in stage
        stage.show();       // show stage
    }
}
