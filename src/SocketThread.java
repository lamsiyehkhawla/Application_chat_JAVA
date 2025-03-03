import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class SocketThread extends Thread {
    private Socket socket; // Client socket
    private int nbMagic; // The magic number to guess
    private String joueurName; // Player's name
    private List<Socket> sockets; // List of all connected clients

    // Constructor to initialize the client socket, magic number, and client list
    public SocketThread(Socket socket, int nbMagic, List<Socket> sockets) {
        this.socket = socket;
        this.nbMagic = nbMagic;
        this.sockets = sockets;
    }

    // Method to notify all other players when someone finds the magic number
    private void broadcastMessage() throws IOException {
        for (Socket s : sockets) {
            if (s != socket) { // Send message to all other players except the current one
                OutputStream os = s.getOutputStream();
                PrintWriter ps = new PrintWriter(new OutputStreamWriter(os), true);
                ps.println("Le joueur " + joueurName + " a trouvé le nombre magique : " + nbMagic);
            }
        }
    }

    @Override
    public void run() {
        try {
            // Setup input and output streams for communication
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter ps = new PrintWriter(new OutputStreamWriter(outputStream), true);
            Scanner sc = new Scanner(System.in);
            int nbJoueur;

            // Send welcome message to the player
            ps.println("Bienvenue dans 'GUESS THE MAGIC NUMBER' !!!");

            // Ask the player for their name
            ps.println("Please enter your name : ");
            joueurName = br.readLine();

            // Ask the player to start guessing the number
            ps.println("Enter a number: ");

            // Game loop - keep asking until the player finds the correct number
            do {
                nbJoueur = Integer.parseInt(br.readLine()); // Read player's guess

                if (nbJoueur > nbMagic) {
                    ps.println("Entrez un nombre inférieur."); // Hint to enter a smaller number
                } else if (nbJoueur < nbMagic) {
                    ps.println("Entrez un nombre supérieur."); // Hint to enter a larger number
                } else {
                    ps.println("Bravo! Vous avez trouvé: " + nbMagic); // Player guessed correctly
                    broadcastMessage(); // Notify other players
                }

            } while (nbJoueur != nbMagic); // Repeat until the correct number is guessed

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
