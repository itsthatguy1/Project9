/**
 *@author Sean Stock
 *@version 3.13.18
 * @todo some more specific exception handling
 * @todo create a system in which user can have multiple save slots
 * @todo make it so that total scores and player turn are only displayed upon the end of a turn or when loading the game
 * @todo do something after a player has won
 * @todo make it so that the dye is automatically rolled for the user when it becomes their turn
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Game {
    private File game;
    private Scanner reader;
    private Scanner input;
    private Random rand;
    private int playerOneTotalScore;
    private int playerTwoTotalScore;
    private int playerTurn;
    private int turnTotal;
    private final int WINNING_SCORE = 100;

    /**
     *
     */
    public static void main(String[] args)
    {
        Game game = new Game();
    }

    /**
     *
     */
    public Game() {
        turnTotal = 0;
        playerOneTotalScore = 0;
        playerTwoTotalScore = 0;
        playerTurn = 1;
        input = new Scanner(System.in);
        rand = new Random();
        try
        {
            game = new File("C:\\Java Projects\\Project9\\Data\\game.csv");
            reader = new Scanner(game);
            mainMenu();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Oops");
        }
    }

    /**
     *
     */
    public void readFile()
    {
        playerOneTotalScore = reader.nextInt();
        playerTwoTotalScore = reader.nextInt();
        playerTurn = reader.nextInt();
        turnTotal = reader.nextInt();
    }

    /**
     *
     */
    public void writeFile()
    {
        try
        {
            PrintWriter writer = new PrintWriter(game);
            writer.println(playerOneTotalScore);
            writer.println(playerTwoTotalScore);
            writer.println(playerTurn);
            writer.println(turnTotal);
            writer.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Oops");
        }
    }

    /**
     *
     */
    public void mainMenu()
    {
        System.out.println("Welcome to Pig!");
        System.out.println("Would you like to:");
        System.out.println("1. Start a new game");
        System.out.println("2. Load an old game");
        int menuChoice = input.nextInt();
        if (menuChoice == 2)
        {
            readFile();
        }
        while (playerOneTotalScore < WINNING_SCORE && playerTwoTotalScore < WINNING_SCORE)
        {
            printInfo();
            playerChoice();
            writeFile();
        }
        printWin();
    }

    /**
     *
     */
    public void printInfo()
    {
        System.out.println("Player One total score: " + playerOneTotalScore);
        System.out.println("Player Two total score: " + playerTwoTotalScore);
        System.out.println("It is Player" + playerTurn + "'s turn");
        System.out.println("Turn total: " + turnTotal);
    }

    /**
     *
     */
    public void playerChoice() {
        System.out.println("Would you like to:");
        System.out.println("1. Roll the Dice");
        System.out.println("2. Hold");
        int choice = input.nextInt();
        while (choice != 1 && choice != 2)
        {
            System.out.println("I'm sorry, you have entered an invalid choice. Please try again.");
            {
                choice = input.nextInt();
            }
        }
        switch (choice) {
            case 1:
                rollDye();
                break;
            case 2:
                boolean hold = true;
                endTurn(hold);
                break;
        }
    }

    /**
     *
     */
    public void rollDye() {
        final int MAX_ROLL = 6;
        int roll = rand.nextInt(MAX_ROLL) + 1;
        System.out.println("You rolled a " + roll);
        if (roll == 1)
        {
            boolean hold = false;
            endTurn(hold);
        }
        else
        {
            turnTotal += roll;
        }
    }

    /**
     *
     */
    public void endTurn(boolean hold)
    {
        if (hold)
        {
            if (playerTurn == 1)
            {
                playerOneTotalScore += turnTotal;
                playerTurn = 2;
            }
            else
            {
                playerTwoTotalScore += turnTotal;
                playerTurn = 1;
            }
        }
        if (!hold)
        {
            if (playerTurn == 1)
            {
                System.out.println("Your turn is over.");
                playerTurn = 2;
            }
            else
            {
                System.out.println("Your turn is over.");
                playerTurn = 1;
            }
        }
        turnTotal = 0;
    }

    /**
     * The Messages have to be reversed b/c endTurn always switches the player turn
     */
    public void printWin()
    {
        if (playerTurn == 2)
        {
            System.out.println("Player1 Won!");
        }
        if (playerTurn == 1)
        {
            System.out.println("Player2 Won!");
        }
    }
}
