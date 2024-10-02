package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * The WordDB class manages the word database for the Hangman game.
 */
public class WordDB {
    /**
     * A map of word categories to their respective words.
     */
    private HashMap<String, String[]> wordList;

    /**
     * A list of categories for easy access.
     */
    private ArrayList<String> categories;

    /**
     * Constructs a WordDB and loads words and categories from a resource file.
     */
    public WordDB() {
        try {
            wordList = new HashMap<>();
            categories = new ArrayList<>();

            // Read words and categories from a file
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(CommonConstants.DATA_PATH)))) {
                if (reader == null) {
                    throw new IOException("Resource not found: " + CommonConstants.DATA_PATH);
                }

                // Process each line in the file
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String category = parts[0];
                    categories.add(category);

                    // Add words to the category
                    String[] values = Arrays.copyOfRange(parts, 1, parts.length);
                    wordList.put(category, values);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Loads a random word challenge.
     *
     * @return An array containing the category and the word.
     */
    public String[] loadChallenge() {
        Random rand = new Random();

        // Pick a random category
        String category = categories.get(rand.nextInt(categories.size()));

        // Pick a random word from the chosen category
        String[] categoryValues = wordList.get(category);
        String word = categoryValues[rand.nextInt(categoryValues.length)];

        // Return the category and word
        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }

    /**
     * Gets a random word challenge as a string.
     *
     * @return The category and word as a comma-separated string.
     */
    public String getChallengeAsString() {
        String[] challenge = loadChallenge();
        return challenge[0] + "," + challenge[1];
    }
}