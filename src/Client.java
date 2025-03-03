import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",9090);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out), true);
        Scanner sc = new Scanner(System.in);
        String msg;
        do {
            System.out.println("Moi : ");
            msg = sc.nextLine();
            pw.println(msg);
            msg = br.readLine();
            System.out.println("Lui : "+msg);
        }while(!msg.equals("bye"));

    }
}
