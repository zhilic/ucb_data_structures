package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        // Generate random string of letters of length n
        String s = "";
        for (int i = 0; i < n; i++) {
            s += CHARACTERS[rand.nextInt(CHARACTERS.length)];
        }
        return s;
    }

    public void drawFrame(String s) {
        // Take the string and display it in the center of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 2, s);
        // If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20));
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            if (playerTurn) {
                StdDraw.text(width / 2, height - 1, "Type!");
            } else {
                StdDraw.text(width / 2, height - 1, "Watch!");
            }
            StdDraw.textRight(width - 1, height - 1,
                    ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
            StdDraw.line(0, height - 2, width, height - 2);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        // Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(Character.toString(letters.charAt(i)));
            StdDraw.pause(1000);  // each letter lasts for 1 second
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);   // break for 0.5 second
        }
    }

    public String solicitNCharsInput(int n) {
        // Read n letters of player input
        String input = "";
        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            input += Character.toString(StdDraw.nextKeyTyped());
        }
        return input.toLowerCase();
    }

    public void startGame() {
        // Set any relevant variables before the game starts
        round = 1;
        gameOver = false;

        // Establish Game loop
        while (!gameOver) {
            playerTurn = false;
            String target = generateRandomString(round);
            flashSequence(target);
            playerTurn = true;
            drawFrame("Please type your answer.");
            String input = solicitNCharsInput(round);
            if (!input.equals(target)) {
                gameOver = true;
                drawFrame("Game Over! You made it to round: " + round);
            } else {
                round += 1;
                drawFrame("Round: " + round + "! Good Luck!");
                StdDraw.pause(1000);
            }
        }
    }

}
