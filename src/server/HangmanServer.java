package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The HangmanServer class sets up a server to manage Hangman game sessions.
 */
public class HangmanServer {
    /**
     * The port number the server listens on.
     */
    private static final int PORT = 12345;

    /**
     * The main method that starts the Hangman server.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                // Start a new thread to handle the client
                new ClientHandler(socket).start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles client communication in a separate thread.
     */
    private static class ClientHandler extends Thread {
        private final Socket socket;

        /**
         * Constructs a ClientHandler with the specified socket.
         *
         * @param socket The client socket.
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Prepare to send a word challenge to the client
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                WordDB wordDB = new WordDB();
                String[] challenge = wordDB.loadChallenge();
                writer.println(challenge[0] + "," + challenge[1]);

                // Read messages from the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received from client: " + message); // Display message from client
                    if (message.equals("End communication")) {
                        System.out.println("Server: End communication");
                        break;
                    }
                }

                socket.close();
                System.out.println("Client disconnected"); // Indicate that the client has disconnected
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}