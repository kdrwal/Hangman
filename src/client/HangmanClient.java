package client;

import java.io.*;
import java.net.Socket;
import javax.swing.SwingUtilities;

/**
 * The HangmanClient class manages the client-side communication with the Hangman server.
 */
public class HangmanClient {
    public static final String SERVER_ADDRESS = "localhost"; // Server address
    public static final int SERVER_PORT = 12345; // Server port
    private static Socket socket;
    private static PrintWriter writer;
    private static BufferedReader reader;

    /**
     * Connects to the Hangman server.
     */
    public static void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("Client is running");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the word challenge from the server.
     *
     * @return The word challenge as a string.
     */
    public static String getChallenge() {
        String challenge = "";
        try {
            if (reader != null) {
                challenge = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return challenge;
    }

    /**
     * Sends a message to the server to end communication and closes the connection.
     */
    public static void sendEndCommunication() {
        if (writer != null) {
            writer.println("End communication");
            try {
                writer.close();
                reader.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Starts the Hangman client application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                connectToServer();
                new Hangman().setVisible(true); // Show the Hangman game UI
            }
        });
    }
}