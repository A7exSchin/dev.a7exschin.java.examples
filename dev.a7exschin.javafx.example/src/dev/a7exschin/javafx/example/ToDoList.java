package dev.a7exschin.javafx.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ToDoList extends Application {

    private ObservableList<String> toDoItems;
    private ListView<String> listView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To-Do List");

        // Create a list to store to-do items
        toDoItems = FXCollections.observableArrayList();

        // Create a ListView to display the to-do items
        listView = new ListView<>(toDoItems);

        // Create input fields
        TextField newItemField = new TextField();
        newItemField.setPromptText("Add a new item");
        newItemField.setPrefWidth(200);

        // Create buttons for adding and removing items
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");

        // Add functionality to add a new item
        addButton.setOnAction(e -> {
            String newItem = newItemField.getText();
            if (!newItem.isEmpty()) {
                toDoItems.add(newItem);
                newItemField.clear();
            }
        });

        // Add functionality to remove selected item
        removeButton.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                toDoItems.remove(selectedIndex);
            }
        });

        // Create layout
        HBox inputBox = new HBox(newItemField, addButton, removeButton);
        inputBox.setSpacing(10);

        VBox layout = new VBox(listView, inputBox);
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        // Set the layout in the scene
        Scene scene = new Scene(layout, 300, 400);

        // Set the scene in the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Save data or perform cleanup before exiting the application
    }
}
