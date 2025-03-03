import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class SocketThread extends Thread {
    private Socket socket;
    private int nbMagic;
    private String joueurName;
    private List<Socket> sockets;
    public SocketThread(Socket socket, int nbMagic, List<Socket> sockets) {

        this.socket = socket;
        this.nbMagic = nbMagic;
        this.sockets = sockets;
    }
    private void broadcastMessage() throws IOException {
        for (Socket s : sockets) {
            if (s != socket) { // Send message to all other players
                OutputStream os = s.getOutputStream();
                PrintWriter ps = new PrintWriter(new OutputStreamWriter(os), true);
                ps.println("Le joueur " + joueurName + " a trouvé le nombre magique : " + nbMagic);
            }
        }
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

            ps.println("Bienvenue dans 'GUESS THE MAGIC NUMBER' !!!");
            ps.println("Please enter your name : !!!");
            joueurName = br.readLine();
            ps.println("Enter a number : !!!");


            do {
                nbJoueur = Integer.parseInt(br.readLine());
                if (nbJoueur > nbMagic) {
                    ps.println("Entrez un nombre inférieur.");
                }else if (nbJoueur < nbMagic) {
                    ps.println("Entrez un nombre supérieur.");
                }else {
                    ps.println("Bravo! Vous avez trouvé: " + nbMagic);
                    broadcastMessage();

                }

            }while(nbJoueur !=nbMagic);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
