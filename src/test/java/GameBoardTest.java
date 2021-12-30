import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import org.testng.annotations.Test;

import java.util.Arrays;

public class GameBoardTest {

    @Test
    public void setMark() {
        TicTacToeGame ticTacToe = new TicTacToeGame(3);
        System.out.println(Arrays.deepToString(ticTacToe.getGameBoard()));
    }
}
