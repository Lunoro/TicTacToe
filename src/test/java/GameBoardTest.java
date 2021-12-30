import de.lunoro.tictactoe.game.tictactoe.TicTacToe;
import org.testng.annotations.Test;

import java.util.Arrays;

public class GameBoardTest {

    @Test
    public void setMark() {
        TicTacToe ticTacToe = new TicTacToe(3);
        System.out.println(Arrays.deepToString(ticTacToe.getGameBoard()));
    }
}
