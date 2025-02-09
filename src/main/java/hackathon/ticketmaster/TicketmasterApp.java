package hackathon.ticketmaster;

import hackathon.ticketmaster.CustomHBox;
import hackathon.ticketmaster.ApiResponse;
import hackathon.ticketmaster.ApiResult;
import hackathon.ticketmaster.ApiEvent;
import hackathon.ticketmaster.ApiEventDate;
import hackathon.ticketmaster.ApiImage;
import hackathon.ticketmaster.EmbeddedResponse;
import hackathon.ticketmaster.EventDate;
import hackathon.ticketmaster.EventStatus;
import javafx.geometry.Insets;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    //HTTPClient
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();
    //GSON
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();


    Stage stage;
    Scene scene;
    Button prevButton;
    Button nextButton;
    Label search;
    Label search2;
    TextField searchBar;
    TextField searchBar2;
    HBox topBar;
    VBox root;
    ImageView imageView;
    Image backgroundImage;
    Button searchButton;
    ApiResponse responseObject;
    //Button eventDetails;


    public TicketmasterApp() {
        this.topBar = new HBox(5);
        this.prevButton = new Button("<");
        this.nextButton = new Button(">");
        this.root = new VBox();
        this.search = new Label("Search: ");
        this.searchBar = new TextField("Type..");
        this.search2 = new Label("State: ");
        this.searchBar2 = new TextField("State code");
        this.searchButton = new Button("Search");
        this.backgroundImage = new Image("file:Attachment-1.jpeg");
        this.imageView = new ImageView(this.backgroundImage);
    }

    @Override
    public void init() {
        System.out.println("init() called");
        topBar.getChildren().addAll(search, searchBar, search2, searchBar2, searchButton);
        root.getChildren().addAll(topBar, imageView);
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        topBar.setMaxWidth(Double.MAX_VALUE);
        search.setPadding(new Insets(4, 0, 0, 0));
        search2.setPadding(new Insets(4, 0, 0, 0));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);



        EventHandler<ActionEvent> searchForResult = (ActionEvent ae) -> {
            System.out.println("Running code clicked.");
            searchButton.setDisable(true);
            String keyword = searchBar.getCharacters().toString();
            String state = searchBar2.getCharacters().toString();
            createUrl(keyword, state);
            searchButton.setDisable(false);
        };

        searchButton.setOnAction(searchForResult);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

    }

    @Override
    public void start(Stage stage) {
        System.out.println("start() called");
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

    public void showAlert(Throwable cause) {

        TextArea textarea = new TextArea("URI: ");
        textarea.appendText("\n\nException: " + cause.toString());
        textarea.setEditable(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setResizable(true);
        alert.showAndWait();
    }


    public void createUrl(String keyword, String state) {
        System.out.println("createUrl() running");
        try {
            String baseUrl = "https://app.ticketmaster.com/discovery/v2/events.json?";
            System.out.println(baseUrl);
            String apiKey = "vPAEFFJwHsnJqoTOHwtBUeY0hjdiUrS0";
            keyword = keyword.replace(" ", "%20");
            String uri = baseUrl + "apikey=" + apiKey +
                "&keyword=" + keyword + "&city=" + state +
                "&radius=100" + "&unit=miles";
            URI location = URI.create(uri);
            HttpRequest req = HttpRequest.newBuilder().uri(location).build();
            HttpResponse<String> response = HTTP_CLIENT.send(req, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(uri + "ERROR:" + response.statusCode());
            }

            String jsonResponse = response.body();
            System.out.println("JSON response: " + jsonResponse);
            responseObject = GSON.<ApiResponse>fromJson(jsonResponse, ApiResponse.class);
            System.out.println("After GSON");
            extractName(responseObject);
            System.out.println("done with extract");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    } //create url
    private void  extractName(ApiResponse resp) {
        System.out.println("in extractName()");
        Set<String> nameSet = new HashSet<>();
        List<String> eventNames = new ArrayList<>();

        if (resp != null && resp.embedded != null) {

            for (ApiEvent event : resp.embedded.events) {  // Iterate over events
                if (event != null && event.name != null) {
                    if (nameSet.add(event.name)) {  // Ensure uniqueness
                        eventNames.add(event.name);

                    }
                }

            }
        }


        for (String events: eventNames) {
            System.out.println(events);
        }

        //CustomHBox customHBox = new CustomHBox(eventNames);
        //root.getChildren().add(customHBox);
    }//extractUri




}
