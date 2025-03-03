import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerMultiThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        Random random = new Random();
        int nbMagique = random.nextInt(100);

        while (true) {
            Socket socket = serverSocket.accept();
            SocketThread socketThread = new SocketThread(socket);
            socketThread.start();
        }


    }

}
