package client;

import server.CommonConstants;
import server.CustomTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import server.WordDB;

/**
 * The Hangman class creates the GUI and manages the game logic for the Hangman game.
 */
public class Hangman extends JFrame implements ActionListener {
    private int incorrectGuesses;
    private String[] wordChallenge;
    private final WordDB wordDB;
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel, resultLabel, wordLabel;
    private JButton[] letterButtons;
    private JDialog resultDialog;
    private Font customFont;

    /**
     * Constructs the Hangman game window and initializes the game.
     */
    public Hangman() {
        super("Hangman Game");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitGame();
            }
        });

        // Initialize variables
        wordDB = new WordDB();
        letterButtons = new JButton[26];

        // Get word challenge from server
        String challenge = HangmanClient.getChallenge();
        wordChallenge = challenge.split(",");

        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        createResultDialog();
        addGuiComponents();
    }

    /**
     * Adds GUI components to the frame.
     */
    private void addGuiComponents() {
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        if (hangmanImage != null) {
            hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);
        }

        categoryLabel = new JLabel(wordChallenge[0]);
        categoryLabel.setFont(customFont.deriveFont(30f));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(
                0,
                hangmanImage != null ? hangmanImage.getPreferredSize().height - 28 : 50,
                CommonConstants.FRAME_SIZE.width,
                categoryLabel.getPreferredSize().height
        );

        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallenge[1]));
        hiddenWordLabel.setFont(customFont.deriveFont(64f));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(
                0,
                categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                CommonConstants.FRAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );

        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width,
                CommonConstants.BUTTON_PANEL_SIZE.height
        );
        buttonPanel.setLayout(gridLayout);

        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(Character.toString(c));
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.setFont(customFont.deriveFont(22f));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);

            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(customFont.deriveFont(22f));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(customFont.deriveFont(22f));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        getContentPane().add(categoryLabel);
        if (hangmanImage != null) {
            getContentPane().add(hangmanImage);
        }
        getContentPane().add(hiddenWordLabel);
        getContentPane().add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Reset") || command.equals("Restart")) {
            resetGame();

            if (command.equals("Restart")) {
                resultDialog.setVisible(false);
            }
        } else if (command.equals("Quit")) {
            quitGame();
        } else {
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);

            if (wordChallenge[1].contains(command)) {
                button.setBackground(Color.GREEN);

                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                for (int i = 0; i < wordChallenge[1].length(); i++) {
                    if (wordChallenge[1].charAt(i) == command.charAt(0)) {
                        hiddenWord[i] = command.charAt(0);
                    }
                }

                hiddenWordLabel.setText(String.valueOf(hiddenWord));

                if (!hiddenWordLabel.getText().contains("*")) {
                    resultLabel.setText("You got it right!");
                    resultDialog.setVisible(true);
                }

            } else {
                button.setBackground(Color.RED);
                ++incorrectGuesses;
                CustomTools.updateImage(hangmanImage, "/resources/" + (incorrectGuesses + 1) + ".png");

                if (incorrectGuesses >= 6) {
                    resultLabel.setText("Too Bad, Try Again?");
                    resultDialog.setVisible(true);
                }
            }
            wordLabel.setText("Word: " + wordChallenge[1]);
        }
    }

    /**
     * Creates the result dialog for the game.
     */
    private void createResultDialog() {
        resultDialog = new JDialog();
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wordLabel = new JLabel();
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(CommonConstants.SECONDARY_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
    }

    /**
     * Resets the game with a new word challenge.
     */
    private void resetGame() {
        // Get a new word challenge
        String challenge = wordDB.getChallengeAsString();
        wordChallenge = challenge.split(",");

        incorrectGuesses = 0;
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);
        categoryLabel.setText(wordChallenge[0]);
        String hiddenWord = CustomTools.hideWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);

        for (int i = 0; i < letterButtons.length; i++) {
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(CommonConstants.PRIMARY_COLOR);
        }
    }

    /**
     * Quits the game and ends communication with the server.
     */
    private void quitGame() {
        HangmanClient.sendEndCommunication();
        System.out.println("Client: End communication");
        System.exit(0);
    }
}