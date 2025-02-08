package hackathon.ticketmaster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.Button;

import java.util.List;


public class CustomHBox extends HBox {

    private final List<TextField> textFields = new ArrayList<>();
    public CustomHBox(List<String> eventNames) {
        this.setSpacing(10); // Set spacing between inner HBoxes

        for (int i = 0; i < 4; i++) {
            String eventName = (i < eventNames.size()) ? eventNames.get(i) : "";
            HBox innerBox = createInnerHBox(eventName);
            this.getChildren().add(innerBox);
        }
    }
    private HBox createInnerHBox(String eventName) {
        TextField textField = new TextField(eventName);
        Button button = new Button("Get Event Details");

        textFields.add(textField); // Store reference for later updates

        return new HBox(5, textField, button); // Inner HBox with spacing
    }
    public void setTextField(int index, String text) {
        if (index >= 0 && index < textFields.size()) {
            textFields.get(index).setText(text);
        }
    }
    public String getTextField(int index) {
        if (index >= 0 && index < textFields.size()) {
            return textFields.get(index).getText();
        }
        return null;
    }
}
