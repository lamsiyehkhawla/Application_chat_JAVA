import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerMultiThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        Random random = new Random();
        int nbMagique = random.nextInt(100);
        List<Socket> sockets = new ArrayList<Socket>();
        while (true) {
            Socket socket = serverSocket.accept();
            sockets.add(socket);
            SocketThread socketThread = new SocketThread(socket, nbMagique,sockets);
            socketThread.start();
        }


    }

}
