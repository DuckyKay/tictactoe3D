package tictactoe;

import java.util.Random;
import java.util.Scanner;

import static tictactoe.WinConditions.isGameFinished;

public class MoveHandler
{
    private final static int TOP_ROW_INDEX = 0;
    private final static int CTR_ROW_INDEX = 1;
    private final static int BOT_ROW_INDEX = 2;

    public static void gameStarter(char[][] board) {
        System.out.println(
"                                                                                                              \n"+
"   _/_/_/            _/_/_/          _/    _/            _/                          _/                       \n"+
"        _/  _/  _/        _/    _/_/_/_/      _/_/   _/_/_/_/  _/_/_/     _/_/   _/_/_/_/   _/_/      _/_/    \n"+
"    _/_/      _/      _/_/        _/    _/  _/         _/    _/    _/   _/         _/    _/    _/  _/_/_/_/   \n"+
"       _/  _/  _/        _/      _/    _/  _/         _/    _/   _/_/  _/         _/    _/    _/  _/_/        \n"+
" _/_/_/           _/_/_/        _/    _/   _/_/      _/     _/_/   _/  _/_/      _/      _/_/      _/_/_/     \n"+
"                                                                                                              \n");
        boolean gameStarted = false;
        Scanner a = new Scanner(System.in);
        while(!gameStarted) {
            System.out.println("Select Opponent: CPU or Human    ");
            String result = a.nextLine();

            if (result.equals("Human")) {
                playerVsHuman(board);
                gameStarted = true;
            } else if (result.equals("CPU")) {
                playerVsCPU(board);
                gameStarted = true;
            } else {
                System.out.println("Error~! Enter command properly ('Human/CPU') ");
            }
        }
    }

    // handles messages when a player vs. computer
    private static void playerVsCPU(char[][] board)
    {
        while (true)
        {
            /*
            bestComputerTurn(board);
            BoardGenerator.printBoard(board);
            if (isGameFinished(board))
            {
                System.out.println("Computer wins!");
                break;
            }
            playerTurn(board);
            if (isGameFinished(board))
            {
                System.out.println("You win!");
                break;
            }
            */
            BoardGenerator.printBoard(board);
            if (isGameFinished(board))
            {
                System.out.println("Computer wins!");
                break;
            }
            playerTurn(board);
            if (isGameFinished(board))
            {
                System.out.println("You win!");
                break;
            }
            bestComputerTurn(board);
        }
    }
    // handles messages when a player vs. another player
    private static void playerVsHuman(char[][] board)
    {
        BoardGenerator.printBoard(board);
        while (true) {
            if (isGameFinished(board)) {
                System.out.println("O wins!");
                break;
            }
            playerTurn(board, 'X');
            if (isGameFinished(board)) {
                System.out.println("X wins!");
                break;
            }
            playerTurn(board, 'O');
        }
    }

    // Accepts player input and places piece accordingly
    private static void playerTurn(char[][] board)
    {
        Scanner scanner = new Scanner(System.in);
        String quadrantInput;
        String positionInput;
        while(true)
        {
            System.out.println("Player X: " + "Choose Quadrant (1-9) and Position (1,2,3)");
            String input = scanner.nextLine();

            // Only accept inputs equal or longer than 2 characters; anything less is invalid
            if (input.length() >= 2)
            {
                quadrantInput = input.substring(0, 1);
                positionInput = input.substring(1, 2);
                if (isValidMove(board, quadrantInput, positionInput))
                {
                    break;
                }
            }
            System.out.println("Try again; Not a Valid Move");
        }
        placePiece(board, quadrantInput, positionInput, 'X');
        BoardGenerator.printBoard(board);
    }

    // method overload of playerTurn() method that accepts additional symbol (X, O) parameter
    private static void playerTurn(char[][] board, char symbol)
    {
        Scanner scanner = new Scanner(System.in);
        String quadrantInput;
        String positionInput;
        while(true)
        {
            System.out.println(symbol + " Player: " + "Choose Quadrant (1-9) and Position (A,B,C)");
            String input = scanner.nextLine();

            // Only accept inputs equal or longer than 2 characters; anything less is invalid
            if (input.length() >= 2)
            {
                quadrantInput = input.substring(0, 1);
                positionInput = input.substring(1, 2);
                if (isValidMove(board, quadrantInput, positionInput))
                {
                    break;
                }
            }
            System.out.println("Try again; Not a Valid Move");
        }
        placePiece(board, quadrantInput, positionInput, symbol);
        BoardGenerator.printBoard(board);
    }

    private static void bestComputerTurn(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int bestComputerQuadrant = 0;
        int bestComputerPosition = 0;
        for(int i=1; i<=9; i++) {             // 9 quadrants
            for (int j = 1; j <= 3; j++) {    // 3 positions
                if (isValidMove(board, String.valueOf(i), String.valueOf(j))) {
                    placePiece(board, String.valueOf(i), String.valueOf(j), 'O');
                    int score = minimax(board, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    placePiece(board, String.valueOf(i), String.valueOf(j), ' ');
                    if (score > bestScore) {
                        bestScore = score;
                        bestComputerQuadrant = i;
                        bestComputerPosition = j;
                    }
                }
            }
        }
        System.out.println("Computer placed O at: " + bestComputerQuadrant + bestComputerPosition);
        placePiece(board, String.valueOf(bestComputerQuadrant), String.valueOf(bestComputerPosition), 'O');
    }

    // minimax using alpha-beta pruning
    private static int minimax(char[][] board, int depth, int a, int b, boolean isMaximizing) {
        if (depth == 0 || isGameFinished(board)) {
            if (WinConditions.meetsWinConditions(board, 'X')) {
                return -1; // X wins: advantageous position
            }
            return 1;    // O wins: disadvantageous
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for(int i=1; i<=9; i++) {       // 9 quadrants
                for(int j=1; j<=3; j++) {    // 3 positions
                    if (isValidMove(board, String.valueOf(i), String.valueOf(j))) {
                        placePiece(board, String.valueOf(i), String.valueOf(j), 'O');
                        int score = minimax(board, depth - 1, a, b, false);
                        placePiece(board, String.valueOf(i), String.valueOf(j), ' ');
                        bestScore = Integer.max(score, bestScore);
                        if(bestScore >= b) {
                            break;
                        }
                        a = Integer.max(a, bestScore);
                    }
                }
            }
            return bestScore;
        }
        else {
            int bestScore = Integer.MAX_VALUE;
            for(int i=1; i<=9; i++) {       // 9 quadrants
                for(int j=1; j<=3; j++) {    // 3 positions
                    if (isValidMove(board, String.valueOf(i), String.valueOf(j))) {
                        placePiece(board, String.valueOf(i), String.valueOf(j), 'X');
                        int score = minimax(board, depth - 1, a, b, true);
                        placePiece(board, String.valueOf(i), String.valueOf(j), ' ');
                        bestScore = Integer.min(score, bestScore);
                        if (bestScore <= a) {
                            break;
                        }
                        b = Integer.min(b, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    // Determines if player input is valid
    private static boolean isValidMove (char[][] board, String quadrant, String position)
    {
        int row = switch (quadrant) {
            case "1", "2", "3" -> TOP_ROW_INDEX;
            case "4", "5", "6" -> CTR_ROW_INDEX;
            case "7", "8", "9" -> BOT_ROW_INDEX;
            default -> 0;
        };

        // If board is empty where player inputs, the move is valid
        return switch (position) {
            case "1" -> (board[row][3 * (Integer.valueOf(quadrant) - (3 * row)) - 3] == ' ');
            case "2" -> (board[row][3 * (Integer.valueOf(quadrant) - (3 * row)) - 2] == ' ');
            case "3" -> (board[row][3 * (Integer.valueOf(quadrant) - (3 * row)) - 1] == ' ');
            default -> false;
        };
    }

    //
    // Places the pieces on the board based on
    // the quadrant and position that the player chooses
    //
    private static void placePiece(char[][] board, String quadrantInput, String positionInput,
                                   char symbol) {

        int row = 0; // initialize row variable
        // Set row based on chosen quadrant
        switch (quadrantInput)
        {
            case "1", "2", "3" -> row = TOP_ROW_INDEX;
            case "4", "5", "6" -> row = CTR_ROW_INDEX;
            case "7", "8", "9" -> row = BOT_ROW_INDEX;
            default -> System.out.print("Error!!");
        }

        // Set position based on chosen quadrant + row
        final int determineQuadrant = 3 * (Integer.parseInt(quadrantInput) - (3 * row));
        switch (positionInput) {
            case "1" -> board[row][determineQuadrant - 3] = symbol;
            case "2" -> board[row][determineQuadrant - 2] = symbol;
            case "3" -> board[row][determineQuadrant - 1] = symbol;
            default -> System.out.print("Error!!!!");
        }
    }
}