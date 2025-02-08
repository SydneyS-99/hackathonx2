package ticketmaster;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
import javafx.geometry.Pos;
import javafx.geometry.Insets;

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
    ImageView imageView;
    Image backgroundImage;
    Label search2;
    TextField searchBar2;
    Button searchButton;


    public TicketmasterApp() {
        this.root = new HBox();
        this.prevButton = new Button("<");
        this.nextButton = new Button(">");
        this.search2 = new Label("State: ");
        this.searchBar2 = new TextField("State code ");
        this.searchButton = new Button("Search");
        this.all = new VBox();
        this.backgroundImage = new Image("file:rocknroll.jpg");
        this.imageView = new ImageView(this.backgroundImage);
        this.bottom = new HBox(5);
        this.search = new Label("Artist/Genre: ");
        this.searchBar = new TextField("Ex: Eagles");
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
        search.setPadding(new Insets(4, 0, 0, 0));
        search2.setPadding(new Insets(4, 0, 0, 0));
        bottom.getChildren().addAll(search, searchBar, search2, searchBar2, searchButton);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        all.getChildren().addAll(bottom,imageView);
        //        all.setAlignment(Pos.CENTER);
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
