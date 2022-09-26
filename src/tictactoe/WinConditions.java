package tictactoe;

public class WinConditions
{
    private final static int BOARD_HEIGHT = 3;
    private final static int BOARD_LENGTH = 9;
    private final static int POSITION_NUMS = 3;              // positions in each quadrant (ABC) = (123)

    private final static int TOP_ROW_INDEX = 0;
    private final static int CTR_ROW_INDEX = 1;
    private final static int BOT_ROW_INDEX = 2;

    private final static int FIRST_COL_INDEX = 0;
    private final static int CTR_COL_INDEX = 4;              // the center position in a given row
    private static boolean XWins = true;

    public static boolean isGameFinished(char[][] board)
    {
        if (WinConditions.meetsWinConditions(board, 'O')) {
            XWins = false;
            return true;
        }

        if (WinConditions.meetsWinConditions(board, 'X')) {
            XWins = true;
            return true;
        }
        return false;
    }

    //
    // Determines if a win condition in either the
    // horizontal, vertical, or diagonal axes has been met,
    //
    public static boolean meetsWinConditions(char[][] board, char symbol)
    {
        return (horizontalWinConditions(board, symbol)||
                verticalWinConditions(board, symbol)||
                diagonalWinConditions(board, symbol));
    }

    //
    // Logic for win condition in the horizontal axis
    //
    public static boolean horizontalWinConditions(char[][] board, char symbol)
    {
        // Start at top row and iterate to bottom row
        for (int row = TOP_ROW_INDEX; row < BOARD_HEIGHT; row++)
        {
            // Win condition #1a: Fill up a quadrant with all 3 positions
            //
            //      (ABC|___|___)
            //
            for (int col = FIRST_COL_INDEX; col < BOARD_LENGTH; col = col + POSITION_NUMS)
            {
                if (board[row][col] == symbol &&
                    board[row][col + 1] == symbol &&
                    board[row][col + 1 + 1] == symbol)
                {
                   // System.out.print("Same Quadrant Win");
                    return true;
                }
            }

            // Win condition #2a): Horizontal connection with same position
            //
            //      (_B_|_B_|_B_)
            //
            for (int col = FIRST_COL_INDEX; col < POSITION_NUMS; col++)
            {
                if (board[row][col] == symbol &&
                    board[row][col + (1 * POSITION_NUMS)] == symbol &&
                    board[row][col + (2 * POSITION_NUMS)] == symbol)
                {
                   // System.out.println("Horizontal Straight Win");
                    return true;
                }
            }

            // Win condition #2a): Horizontal connection with ascending/descending positions
            //
            //      Middle index always stays the same (centerPositionIndex)
            //      Distance b/t the center and outer positions remains the same
            //
            //		ascending:		(A__|_B_|__C) [distance b/t middle = 4]
            //      descending:     (__C|_B_|A__) [distance b/t middle = 2]
            //
            if (board[row][CTR_COL_INDEX] == symbol)
            {
                //  check for descending win, than check for ascending win
                for (int colCenterDist = 2; colCenterDist <= 4; colCenterDist = colCenterDist + 2)
                {
                    if (board[row][CTR_COL_INDEX - colCenterDist] == symbol &&
                        board[row][CTR_COL_INDEX + colCenterDist] == symbol)
                    {
                       // System.out.println("Horizontal Ascend/Descend Win");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //
    // Logic for win condition in the vertical axis
    //
    public static boolean verticalWinConditions(char[][] board, char symbol) {

        // Start at first column (index 0) and work down to bottom col (col < boardLength)
        for (int col = FIRST_COL_INDEX; col < BOARD_LENGTH; col++)
        {
            // Check for vertical win with same position
            //
            //  (_A_|___)
            //  (_A_|___)
            //  (_A_|___)
            //
            if (board[TOP_ROW_INDEX][col] == symbol &&
                board[CTR_ROW_INDEX][col] == symbol &&
                board[BOT_ROW_INDEX][col] == symbol)
            {
                //System.out.println("Vertical Straight Win");
                return true; // True: win condition found.
            }
        }

        // Check for ascending/descending wins in the vertical axes
        //
        //  Ascending:     Descending:
        //  (A__|___)      (__A|___)
        //  (_A_|___) and  (_A_|___)
        //  (__A|___)      (A__|___)
        //
        for (int col = FIRST_COL_INDEX + 1; col < BOARD_LENGTH; col = col + POSITION_NUMS)
        {
            // Only possible with a center position on the center row
            if (board[CTR_ROW_INDEX][col] == symbol)
            {
                // test for ascending win, than test for descending win
                for (int colCenterDist = 1; colCenterDist >= -1; colCenterDist = colCenterDist - 2)
                {
                    if (board[TOP_ROW_INDEX][col - colCenterDist] == symbol &&
                        board[BOT_ROW_INDEX][col + colCenterDist] == symbol)
                    {
                        //System.out.println("Vertical Ascend/Descend Win");
                        return true; // True: win condition found.
                    }
                }
            }
        }
        return false; // False: No win conditions found.
    }

    //
    // Logic for win condition in the diagonal axes
    //
    public static boolean diagonalWinConditions(char[][] board, char symbol)
    {
		/* Check for same position diagonals

		    Row0      (___|___|A__)     (_B_|___|___)
		    Row1      (___|A__|___) and (___|_B_|___)
		    Row2      (A__|___|___)     (___|___|_B_)

		**/

        // find position of piece within first columns/quadrants
        for (int col = FIRST_COL_INDEX; col < POSITION_NUMS; col++)
        {
            // check for right to left diagonal, than left to right diagonal
            for (int rowCenterDist = 1; rowCenterDist >= -1; rowCenterDist = rowCenterDist - 2)
            {
                // find if position of piece is similar to other two diagonal quadrants
                if (board[CTR_ROW_INDEX - rowCenterDist][col] == symbol &&
                    board[CTR_ROW_INDEX][col + (1 * POSITION_NUMS)] == symbol &&
                    board[CTR_ROW_INDEX + rowCenterDist][col + (2 * POSITION_NUMS)] == symbol)
                {
                    //System.out.println("Diagonal Straight Win!");
                    return true; // True: win condition found.
                }
            }
        }

		/* Check for ascending/descending diagonals

		    Row0     (___|___|__C)     (__C|___|___)
	        Row1     (___|_B_|___) and (___|_B_|___)
		    Row2     (A__|___|___)     (___|___|A__)

		**/

        // Check for ascending/descending win conditions ONLY if there is a center row+position piece
        if (board[CTR_ROW_INDEX][CTR_COL_INDEX] == symbol)
        {
            //  check for descending win, than check for ascending win
            for (int colCenterDist = 2; colCenterDist <= 4; colCenterDist = colCenterDist + 2)
            {
                // check for right to left diagonal, than left to right diagonal
                for (int rowCenterDist = 1; rowCenterDist >= -1; rowCenterDist = rowCenterDist - 2)
                {
                    if (board[CTR_ROW_INDEX - rowCenterDist][CTR_COL_INDEX - colCenterDist] == symbol &&
                            board[CTR_ROW_INDEX + rowCenterDist][CTR_COL_INDEX + colCenterDist] == symbol)
                    {
                        //System.out.println("Diagonal Ascend/Descend Win!");
                        return true; // True: win condition found.
                    }
                }
            }
        }
        return false; // False: No win conditions found.
    }
}