package ticketmaster;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.layout.Pane;

public class TicketmasterApp extends Application {

    Stage stage;
    Scene scene;
    Pane root;
    Button prevButton;
    Button nextButton;
    Label search;
    TextField searchBar;
    HBox bottom;
    VBox all;



    public TicketmasterApp() {
        this.root = new HBox();
        this.prevButton = new Button("<");
        this.nextButton = new Button(">");
        this.all = new VBox();
        this.bottom = new HBox(5);

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
        bottom.getChildren().addAll(search, searchBar);
        all.getChildren().addAll(bottom);
        root.getChildren().add(all);
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        bottom.setMaxWidth(Double.MAX_VALUE);

    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(this.root,600,400);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.setTitle("ermmmm");
        this.stage.setScene(this.scene);
        this.stage.sizeToScene();
        Platform.runLater(() -> this.stage.setResizable(false));
        this.stage.show();
    }

    @Override
    public void stop() {
        System.out.println("stop() called");

    }

}
