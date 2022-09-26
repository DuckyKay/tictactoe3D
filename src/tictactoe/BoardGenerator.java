package tictactoe;

public class BoardGenerator
{
    private final static int BOARD_HEIGHT = 3;
    private final static int BOARD_LENGTH = 9;
    private final static int POSITION_NUMS = 3;              // positions in each quadrant (ABC) = (123)


    //
    // Prints out an empty 3x3 tic-tac-toe board.
    // Each quadrant holds three possible positions (A,B,C)
    //
    public static void printBoard(char[][] board)
    {
        // For each row, print out the respective columns
        // than print out dividers until last section
        for(int row = 1; row <= BOARD_HEIGHT; row++)                        // repeat this process for each row
        {
            int dividerCount = 0;                       		         	// keep count of mid dividers
            for (int col = 1; col <= (BOARD_LENGTH + dividerCount); col++)
            {
                if (col % (POSITION_NUMS + 1) == 0)                         // dividers go once every 3 #'s
                {
                    dividerCount++;
                    System.out.print("|");
                }
                else {
                    System.out.print(board[row - 1][col - dividerCount - 1]);  // print out respective col #
                }
            }
            System.out.println("");
            if (row % BOARD_HEIGHT != 0) {
                System.out.println("---+---+---");  // Print out dividers until last section
            }
        }
    }
}

