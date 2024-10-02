# Hangman
Course's project: Advanced Application Programming in JAVA

Description of How the Program Works:
The Hangman Game Application is a simple and interactive game that allows users to
guess a hidden word by selecting letters. The game starts with a randomly selected
word and category from a text database. The user has a limited number of incorrect
guesses before the game ends.

The main functionalities provided by the application are:
Connect to Server: The client connects to a server that provides the word challenge.
Word Challenge: The server sends a word challenge to the client, including a category
and a hidden word represented by asterisks.
User Interaction: User can guess letters by clicking buttons. Correct guesses show the
letters in the hidden word, while incorrect guesses update the hangman image.
Game Status: The game updates the user with messages indicating whether they have
guessed the word correctly or if they need to try again.
Reset and Quit Options: Users can reset the game to get a new word challenge or quit
the game.

Resources and Settings Needed to Run the Application:
To run the Hangman Game Application, you need the following:
Java Development Kit (JDK): Ensure that you have JDK installed on your system.
Server Configuration: The server should be running on localhost at port 12345.
Word Database: A text file containing word categories and corresponding words is
required.

Java Elements and Mechanisms Used:
The application utilizes various Java elements and mechanisms, including:
Swing for GUI: The application uses Java Swing components (JFrame, JLabel, JButton,
etc.) to create a graphical user interface.
Networking: Socket and ServerSocket classes are used for client-server
communication.
Multithreading: The server handles multiple clients using threads.
Streams: BufferedReader, InputStreamReader, PrintWriter, and InputStream are used
for reading from and writing to streams.
Image Handling: ImageIO is used to load and update images.
Custom Fonts: The Font class is used to load and apply custom fonts.
Additionally, the project uses custom classes like WordDB to manage word challenges
and CustomTools for utility functions such as loading images and creating fonts.
