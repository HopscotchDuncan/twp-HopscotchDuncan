package edu.bsu.cs222.WikipediaProjectJavaFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {

    private ArrayList<String> users = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private WikipediaFileParser fileParser = new WikipediaFileParser();
    private WikipediaPageEditPeeker pageEditPeeker = new WikipediaPageEditPeeker();
    private TextField userInput = new TextField();
    private TextField systemOutput = new TextField();
    private StringBuilder output = new StringBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("Get Last Edit");
        button.setOnAction((event) -> {
            try {
                if (userInput.getText().isEmpty()) {
                    systemOutput.setText("No text inputted");
                    reset();
                } else {
                    users = fileParser.lastUsersWhoEdited(fileParser.getJSONfromURL(userInput.getText()));
                    dates = fileParser.dateOfEdit(fileParser.getJSONfromURL(userInput.getText()));
                    if (fileParser.isRedirected()) {
                        output.append("Redirected: ");
                    }
                    output.append(pageEditPeeker.formList(users, dates));
                    systemOutput.setText(output.toString());
                    reset();
                }
            } catch (NullPointerException e) {
                systemOutput.setText("Page not found");
                reset();
            }
        });
        VBox outerBox = new VBox();
        outerBox.getChildren().addAll(createInputBox(), button, createOutputBox());
        primaryStage.setScene(new Scene(outerBox));
        primaryStage.setWidth(200);
        primaryStage.show();
    }

    private HBox createInputBox() {
        HBox box = new HBox();
        box.getChildren().addAll(new Label("Input:"), userInput);
        return box;
    }

    private HBox createOutputBox() {
        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(new Label("Output:"), systemOutput);
        return bottomBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void reset(){
        output.delete(0, output.length());
        users.clear();
        dates.clear();
    }
}
