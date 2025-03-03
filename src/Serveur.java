import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {
    public static void main(String[] args) throws IOException {
        ServerSocket serveurSocket = new ServerSocket(9090);
        Socket socket = serveurSocket.accept();

        DataInputStream in = new DataInputStream(socket.getInputStream());
        OutputStream out = socket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out), true);
        Scanner sc = new Scanner(System.in);
        String msg;
        do {

            msg = br.readLine();
            System.out.println("lui : " + msg);
            System.out.println("Moi  : ");
            msg = sc.nextLine();
            pw.println(msg);
        }while(!msg.equals("bye"));




    }
}
