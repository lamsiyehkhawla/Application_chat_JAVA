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
    private TextArea chatArea = new TextArea();
    private TextField inputField = new TextField();
    private Button sendButton = new Button("Send");
    private PrintWriter pw;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", 14));
        inputField.setFont(new Font("Arial", 14));
        sendButton.setFont(new Font("Arial", 14));
        sendButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 8px 15px; -fx-background-radius: 5;");
        sendButton.setEffect(new DropShadow(5, Color.GRAY));

        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, chatArea, inputBox);
        root.setStyle("-fx-padding: 10px; -fx-background-color: #E3F2FD;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Client Chat");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(this::startClient).start();

        sendButton.setOnAction(event -> sendMessage());
        inputField.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        String msg = inputField.getText();
        if (pw != null && !msg.isEmpty()) {
            pw.println(msg);
            chatArea.appendText("Moi: " + msg + "\n");
            inputField.clear();
        }
    }

    private void startClient() {
        try (Socket socket = new Socket("localhost", 9090);
             InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            pw = new PrintWriter(new OutputStreamWriter(os), true);
            String msg;
            while ((msg = br.readLine()) != null) {
                chatArea.appendText("Lui: " + msg + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
