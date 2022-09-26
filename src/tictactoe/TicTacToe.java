package tictactoe;

public class TicTacToe
{
    public static void main(String[] args)
    {
        char[][] board = {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',},
                          {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',},
                          {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',}};

        MoveHandler.gameStarter(board);
    }
}