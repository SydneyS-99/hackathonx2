package hackathon.ticketmaster;

import hackathon.ticketmaster.ApiResponse;
import hackathon.ticketmaster.ApiResult;
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
        this.backgroundImage = new Image("file:rocknroll.jpg");
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
            System.out.println("yeehaw");
            searchButton.setDisable(true);
            String keyword = searchBar.getCharacters().toString();
            String state = searchBar2.getCharacters().toString();
            String url = createUrl(keyword, state);
            System.out.println(url);
            ApiResponse newResponse =  makeRequest(url);
            List<String> eventNames = extractName(newResponse);
            CustomHBox customHBox = new CustomHBox(eventNames);
            root.getChildren().add(customHBox);
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


    public String createUrl(String keyword, String state) {
        String baseUrl = "https://app.ticketmaster.com/discovery/v2/events.json";
        System.out.println(baseUrl);
        String apiKey = "vPAEFFJwHsnJqoTOHwtBUeY0hjdiUrS0";
        keyword = keyword.replace(" ", "%20");
        String finalUrl = baseUrl + "?apikey=" + apiKey +
            "&keyword=" + keyword + "&stateCode=" + state +
            "&classificationName=music" + "&size =4"; //change size to adjust number of results
        return finalUrl;
    } //createurl

    public ApiResponse makeRequest(String uri) { //createUrl should be parameter
        try {
            URI location = URI.create(uri);
            HttpRequest req = HttpRequest.newBuilder().uri(location).GET().build();
            HttpResponse<String> response = HTTP_CLIENT.send(req, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(uri + "ERROR:" + response.statusCode());
            }
            System.out.println("Raw JSON Response: " + response.body());
            return GSON.fromJson(response.body(), ApiResponse.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            return null;
            }
    } //makerequest
    private List<String> extractName(ApiResponse resp) {
        Set<String> nameSet = new HashSet<>();
        List<String> eventNames = new ArrayList<>();

        if (resp != null && resp._embedded != null) {
            for (EmbeddedResponse embedded : resp._embedded) {  // Iterate over the array
                if (embedded != null && embedded.events != null) {
                    for (ApiEvent event : embedded.events) {  // Iterate over events
                        if (event != null && event.name != null) {
                            if (nameSet.add(event.name)) {  // Ensure uniqueness
                                eventNames.add(event.name);
                            }
                        }
                    }
                }
            }
        }
        return eventNames;

    }//extractUri




}
