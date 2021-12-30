package de.lunoro.tictactoe.game.tictactoe;

import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;

public class TicTacToe {

    @Getter
    private final Mark[][] gameBoard;
    @Getter
    private boolean isCompleted;
    private final int size;
    private HashMap<Integer, String> boardMapping;

    public TicTacToe(int size) {
        this.size = size;
        this.gameBoard = new Mark[size][size];
        this.boardMapping = new HashMap<>();
        isCompleted = false;
        setBoardMapping();
    }

    private void setBoardMapping() {
        int first = 0;
        int second = 0;
        int index = 1;
        for (Mark[] marks : gameBoard) {
            for (Mark mark : marks) {
                boardMapping.put(index, first + "," + second);
                index++;
                second++;
            }
            second = 0;
            first++;
        }
    }

    public boolean markPos(int xPos, int yPos, Mark mark) {
        System.out.println(Arrays.deepToString(gameBoard));
        if (gameBoard[xPos][yPos] == null) {
            gameBoard[xPos][yPos] = mark;
            return true;
        }
        return false;
    }

    public boolean markPos(int slot, Mark mark) {
        String[] positions = boardMapping.get(slot).split(",");
        return markPos(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]), mark);
    }

    public boolean positionIsValid(int slot) {
        System.out.println(boardMapping);
        String[] positions = boardMapping.get(slot).split(",");
        return gameBoard[Integer.parseInt(positions[0])][Integer.parseInt(positions[1])] == null;
    }

    public Mark getWinnerIfHas(int xPos, int yPos, Mark mark) {
        for (int i = 0; i < size; i++) {
            if (gameBoard[xPos][i] != mark) {
                break;
            }
            if (i == size - 1) {
                isCompleted = true;
                return mark;
            }
        }

        for (int i = 0; i < size; i++) {
            if (gameBoard[i][yPos] != mark) {
                break;
            }
            if (i == size - 1) {
                isCompleted = true;
                return mark;
            }
        }

        if (xPos == yPos) {
            for (int i = 0; i < size; i++) {
                if (gameBoard[i][i] != mark) {
                    break;
                }
                if (i == size - 1) {
                    isCompleted = true;
                    return mark;
                }
            }
        }

        if (xPos + yPos == size - 1) {
            for (int i = 0; i < size; i++) {
                if (gameBoard[i][(size - 1) - i] != mark) {
                    break;
                }
                if (i == size - 1) {
                    isCompleted = true;
                    return mark;
                }
            }
        }
        return null;
    }
}
