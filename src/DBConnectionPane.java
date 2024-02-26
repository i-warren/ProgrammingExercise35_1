import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DBConnectionPane extends BorderPane {
    // declare new Connection
    Connection connection;

    public static final String QUERY =
            "insert into Temp (num1, num2, num3) "
                    + " values (!, @, #);";

    public DBConnectionPane() {
        draw();
    }

    public Connection getConnection() {
        return connection;
    }

    public void draw() {
        getChildren().clear();
        connection = null;
        Label status = new Label("No connection");
        setTop(status);
        BorderPane.setAlignment(status, Pos.CENTER);

        // create center GridPane
        GridPane center = new GridPane();

        // Create Labels and Fields with preferred widths
        center.add(new Label("JDBC Drive "), 0, 0);
        TextField tfJDBC = new TextField();
        tfJDBC.setPrefColumnCount(20);
        center.add(tfJDBC, 1, 0);

        center.add(new Label("Database URL "), 0, 1);
        TextField tfURL = new TextField();
        tfURL.setPrefColumnCount(20);
        center.add(tfURL, 1, 1);

        center.add(new Label("Username "), 0, 2);
        TextField tfUsername = new TextField();
        tfUsername.setPrefColumnCount(20);
        center.add(tfUsername, 1, 2);

        center.add(new Label("Password "), 0, 3);
        TextField tfPassword = new PasswordField();
        tfPassword.setPrefColumnCount(20);
        center.add(tfPassword, 1, 3);

        // Set Center
        setCenter(center);

        // create Connect to database button
        Button btnConnectToDB = new Button("Connect to DB");
        BorderPane.setAlignment(btnConnectToDB, Pos.TOP_RIGHT);
        setBottom(btnConnectToDB);

        // set action for button
        btnConnectToDB.setOnAction(e ->
        {
            try {
                Class.forName(tfJDBC.getText().trim());
                String url = tfURL.getText().trim();
                String username = tfUsername.getText().trim();
                String password = tfPassword.getText().trim();

                connection = DriverManager.getConnection(url,
                        username, password);

                status.setText("Connected to " + url);
                drawTesting();
            } catch (Exception exception) {
                status.setText(exception.getMessage());
            }
        });
    }

    public void drawTesting() {

        getChildren().clear();
        Label status = new Label("");
        Button connectToDB = new Button("Connect to Database");
        HBox top = new HBox(5, status, connectToDB);
        setTop(top);

        // create text area
        TextArea center = new TextArea();
        setCenter(center);

        // create buttons for batch and non-batch updates
        Button batchUpdate = new Button("Batch Update");
        Button nonBatchUpdate = new Button("Non-Batch Update");
        HBox bottom = new HBox(10, batchUpdate, nonBatchUpdate);
        bottom.setAlignment(Pos.CENTER);
        setBottom(bottom);

        // set action for conncet to database button
        connectToDB.setOnAction(e -> draw());

        // set action for batch update button
        batchUpdate.setOnAction(e ->
        {
            status.setText("");
            long start = System.currentTimeMillis();
            try {
                // creates statement for batch
                Statement statement = connection.createStatement();
                // loop to add to batch
                for (int i = 0; i < 1_000; i++)
                {
                    String query = QUERY.replace("!", Math.random() + "");
                    query = query.replace("@", Math.random() + "");
                    query = query.replace("#", Math.random() + "");
                    statement.addBatch(query);
                }
                statement.executeBatch();   // perform batch update
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
            long end = System.currentTimeMillis();
            status.setText("Batch Update succeeded");
            center.appendText("Batch update completed\n");
            center.appendText("The elapsed Time is " + (end - start) + "\n\n");
        });


        // set action for non-batch update button
        nonBatchUpdate.setOnAction(e ->
        {
            status.setText("");
            long start = System.currentTimeMillis();
            // creates loop update
            for (int i = 0; i < 1_000; i++) {
                try {
                    // creates statement and updates
                    Statement statement = connection.createStatement();
                    String query = QUERY.replace("!", Math.random() + "");
                    query = query.replace("@", Math.random() + "");
                    query = query.replace("#", Math.random() + "");
                    statement.executeUpdate(query);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            status.setText("Non-Batch Update succeeded ");
            center.appendText("Non-Batch update completed\n ");
            center.appendText("The elapsed Time is " + (end - start) + "\n\n ");
        });
    }
}