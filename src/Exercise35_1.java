import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Exercise35_1 extends Application {

    @Override   //
    public void start(Stage stage) {
        DBConnectionPane connectionPane = new DBConnectionPane();

        Scene scene = new Scene(connectionPane, 350, 200);
        stage.setTitle("Exercise 35_1");
        stage.setScene(scene);
        stage.show();
    }
}
