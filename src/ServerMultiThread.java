import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerMultiThread {
    public static void main(String[] args) throws IOException {
        // Create a server socket that listens on port 9090
        ServerSocket serverSocket = new ServerSocket(9090);

        // Generate a random number between 0 and 99 (the "magic number")
        Random random = new Random();
        int nbMagique = random.nextInt(100);

        // List to store all connected clients
        List<Socket> sockets = new ArrayList<>();

        System.out.println("Server is running on port 9090...");

        // Infinite loop to accept multiple client connections
        while (true) {
            // Accept an incoming client connection
            Socket socket = serverSocket.accept();
            System.out.println("New client connected: " + socket.getInetAddress());

            // Add the new client's socket to the list
            sockets.add(socket);

            // Start a new thread to handle this client
            SocketThread socketThread = new SocketThread(socket, nbMagique, sockets);
            socketThread.start();
        }
    }
}
