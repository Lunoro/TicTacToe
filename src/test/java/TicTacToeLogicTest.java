import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import org.testng.annotations.Test;

import java.util.Arrays;

public class TicTacToeLogicTest {

    @Test
    public void isAHorizontalRow() {
        TicTacToeGame ticTacToe = new TicTacToeGame(3);
        ticTacToe.markPos(0, 0, Mark.X);
        ticTacToe.markPos(0, 1, Mark.X);
        ticTacToe.markPos(0, 2, Mark.X);
        System.out.println(Arrays.deepToString(ticTacToe.getGameBoard()));
    }

    @Test
    public void isAVerticalRow() {
        TicTacToeGame ticTacToe = new TicTacToeGame(3);
        ticTacToe.markPos(0, 0, Mark.X);
        ticTacToe.markPos(1, 0, Mark.X);
        ticTacToe.markPos(2, 0, Mark.X);
        System.out.println(Arrays.deepToString(ticTacToe.getGameBoard()));
    }

    @Test
    public void isADiagonalRow() {
        TicTacToeGame ticTacToe = new TicTacToeGame(3);
        ticTacToe.markPos(0, 0, Mark.X);
        ticTacToe.markPos(1, 1, Mark.X);
        ticTacToe.markPos(2, 2, Mark.X);
        System.out.println(Arrays.deepToString(ticTacToe.getGameBoard()));
    }

    @Test
    public void isAAntiDiagonalRow() {
        TicTacToeGame ticTacToe = new TicTacToeGame(3);
        ticTacToe.markPos(2, 0, Mark.Y);
        ticTacToe.markPos(1, 1, Mark.Y);
        ticTacToe.markPos(0, 2, Mark.Y);
        System.out.println(Arrays.deepToString(ticTacToe.getGameBoard()));
    }
}
