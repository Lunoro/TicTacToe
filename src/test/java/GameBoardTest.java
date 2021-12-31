import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class GameBoardTest {

    @Test
    public void setMark() {
        TicTacToeGame ticTacToe = new TicTacToeGame(3);
        Mark[][] before = new Mark[3][3];
        ticTacToe.markPos(3, Mark.Y);
        Assert.assertNotEquals(Arrays.deepToString(before), Arrays.deepToString(ticTacToe.getGameBoard()));
    }
}
