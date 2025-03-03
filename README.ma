the Multi-Threaded "Guess the Magic Number" Game
This application is a multi-threaded server-client game where multiple players can connect to a server and try to guess a randomly generated number. The game notifies all connected players when someone finds the correct number.

*** How It Works ***
1. Server (ServerMultiThread)
The server listens for incoming client connections on port 9090.
It generates a random number (the "magic number") between 0 and 99.
It maintains a list of connected players (sockets).
Each time a player connects, the server starts a new SocketThread to handle the player individually.
2. Client Handling (SocketThread)
Each client thread allows a player to:
Enter their name.
Guess the magic number (using input from the client).
Receive feedback:
If the guess is too high → "Entrez un nombre inférieur."
If the guess is too low → "Entrez un nombre supérieur."
If the guess is correct → "Bravo! Vous avez trouvé: " + nbMagic
When a player finds the correct number, a broadcast message is sent to all other players, informing them that the game has been won.
3. Testing with Telnet
We used Telnet to test the server by simulating multiple clients.
Players connected to the server using the following command in a terminal:
***telnet localhost 9090***
This allowed multiple users to join the game and play simultaneously from different terminals, verifying the server's ability to handle multiple connections.
Key Features
✅ Multi-threaded: Each player gets their own thread, allowing multiple clients to play simultaneously.
✅ Random Magic Number: The number to guess is different every time the server starts.
✅ Broadcasting System: When a player wins, all other players are notified.
✅ Telnet Testing: Easy to test the server without a dedicated client application.

Screenshot:
<img src="/captures/screenshot.png">