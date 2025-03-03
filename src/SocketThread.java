import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketThread extends Thread {
    private Socket socket;
    private int nbMagic;
    public SocketThread(Socket socket, int nbMagic) {

        this.socket = socket;
        this.nbMagic = nbMagic;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter ps = new PrintWriter(new OutputStreamWriter(outputStream), true);
            Scanner sc = new Scanner(System.in);
            int nbJoueur;
            ps.println("Bienvenue !!!");
            do {
                nbJoueur = Integer.parseInt(sc.nextLine());
                if (nbJoueur > nbMagic) {
                    ps.println("ENtrer un nombe inf");
                }else if (nbJoueur < nbMagic) {
                    ps.println("Entrer un nombre superie");
                }else {
                    ps.println("Bravo you got it"+nbMagic);
                }

            }while(nbJoueur !=nbMagic);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
