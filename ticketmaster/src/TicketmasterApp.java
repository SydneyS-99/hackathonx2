package ticketmaster;

import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.Stage;
import java.io.IOException;
import java.io.InputStream;


public class TicketmasterApp {

    Stage stage;
    Scene scene;
    Pane root;
    Button prevButton;
    Button nextButton;
    Label search;
    TextField searchBar;


    public TicketmasterApp() {
        this.stage = null;
        this.scene = null;
        this.root = new HBox();
        this.prevButton = new Button("<");
        this.nextButton = new Button(">");

        this.search = new Label("Search: ");
        this.searchBar = new TextField("Type..");
    }


    public void showAlert(Throwable cause) {

        TextArea textarea = new TextArea("URI: ");
        textarea.appendText("\n\nException: " + cause.toString());
        textarea.setEditable(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(true);
        alert.showAndWait();
    }

    @Override
    public void init() {

    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(this.root);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.setTitle("ermmmm");
        this.stage.setScene(this.scene);
        this.stage.sizeToScene();
        this.stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));

    }

    @Override
    public void stop() {
        System.out.println("stop() called");

    }

}
