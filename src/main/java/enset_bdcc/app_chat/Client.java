package enset_bdcc.app_chat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.geometry.Pos;

import java.io.*;
import java.net.Socket;

public class Client extends Application {
    private TextArea chatArea = new TextArea();  // Text area to display chat messages
    private TextField inputField = new TextField();  // Input field for the user to type messages
    private Button sendButton = new Button("Send");  // Button to send the message
    private PrintWriter pw;  // PrintWriter to send data to the server

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the chat area (non-editable) and input field (editable)
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", 14));
        inputField.setFont(new Font("Arial", 14));
        sendButton.setFont(new Font("Arial", 14));
        // Styling the send button
        sendButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 8px 15px; -fx-background-radius: 5;");
        sendButton.setEffect(new DropShadow(5, Color.GRAY));  // Add a drop shadow effect to the button

        // Organize the input field and send button horizontally
        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setAlignment(Pos.CENTER);  // Center the input box

        // Organize the chat area and input box vertically
        VBox root = new VBox(10, chatArea, inputBox);
        root.setStyle("-fx-padding: 10px; -fx-background-color: #E3F2FD;");  // Set background color and padding

        // Set up the scene and stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Client Chat");  // Set the window title
        primaryStage.setScene(scene);  // Set the scene
        primaryStage.show();  // Show the window

        // Start the client in a separate thread to avoid blocking the UI thread
        new Thread(this::startClient).start();

        // Set action events for sending the message when the button or Enter key is pressed
        sendButton.setOnAction(event -> sendMessage());
        inputField.setOnAction(event -> sendMessage());
    }

    // Method to send a message to the server
    private void sendMessage() {
        String msg = inputField.getText();  // Get the message from the input field
        if (pw != null && !msg.isEmpty()) {  // Check if the PrintWriter is initialized and the message is not empty
            pw.println(msg);  // Send the message to the server
            chatArea.appendText("Moi: " + msg + "\n");  // Append the message to the chat area
            inputField.clear();  // Clear the input field after sending the message
        }
    }

    // Method to start the client and connect to the server
    private void startClient() {
        try (Socket socket = new Socket("localhost", 9090);  // Connect to the server running on localhost at port 9090
             InputStream is = socket.getInputStream();  // Get the input stream from the server
             OutputStream os = socket.getOutputStream();  // Get the output stream to send data to the server
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {  // BufferedReader to read messages from the server

            pw = new PrintWriter(new OutputStreamWriter(os), true);  // Initialize PrintWriter to send data
            String msg;
            // Continuously read messages from the server and display them in the chat area
            while ((msg = br.readLine()) != null) {
                chatArea.appendText("Lui: " + msg + "\n");  // Append the received message to the chat area
            }
        } catch (IOException e) {
            e.printStackTrace();  // Print any IO exceptions to the console
        }
    }
}
