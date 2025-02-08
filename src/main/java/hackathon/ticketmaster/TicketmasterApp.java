package hackathon.ticketmaster;

import hackathon.ticketmaster.ApiResponse;
import hackathon.ticketmaster.ApiResult;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class TicketmasterApp extends Application{

    Stage stage;
    Scene scene;
    Pane root;
    Button prevButton;
    Button nextButton;
    Label search;
    TextField searchBar;
    VBox bottom;
    VBox all;

    public TicketmasterApp() {
        this.stage = null;
        this.scene = null;
        this.root = new HBox();
        this.bottom = new VBox(5);
        this.prevButton = new Button("<");
        this.nextButton = new Button(">");
        this.all = new VBox();
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


    //HTTPClient
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();
    //GSON
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    public String createUrl(String keyword, String state) {
        String baseUrl = "https://app.ticketmaster.com/discovery/v2/events.json";
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
            return GSON.fromJson(response.body(), ApiResponse.class);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            return null;
            }
    } //makerequest
    private List<String> extractUri(ApiResponse resp) {
        Set<String> uriSet = new HashSet<>();
        List<String> uris = new ArrayList<>();
        if (resp != null && resp.getResults() != null) {
            for (ApiResult result : resp.getResults()) {
                if (result != null && result.getName() != null) { //need to make getName
                    if (uriSet.add(result.getName())) {
                        uris.add(result.getName());
                    }
                }
            }
        }
        return uris;
    }//extractUri



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
