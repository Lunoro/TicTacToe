package de.lunoro.tictactoe.game.tictactoe;

import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.Getter;

import java.util.HashMap;

public class TicTacToeGame {

    @Getter
    private final Mark[][] gameBoard;
    @Getter
    private boolean isCompleted;
    @Getter
    private Mark winner;
    private final int size;
    private final HashMap<Integer, String> boardMapping;

    public TicTacToeGame(int size) {
        this.size = size;
        this.gameBoard = new Mark[size][size];
        this.boardMapping = new HashMap<>();
        this.winner = null;
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

    public void markPos(int xPos, int yPos, Mark mark) {
        if (gameBoard[xPos][yPos] == null) {
            gameBoard[xPos][yPos] = mark;
            winner = getWinnerIfHas(xPos, yPos, mark);
        }
    }

    public void markPos(int index, Mark mark) {
        String[] positions = boardMapping.get(index).split(",");
        markPos(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]), mark);
    }

    public boolean positionIsValid(int index) {
        if (boardMapping.get(index) == null) {
            return false;
        }
        String[] positions = boardMapping.get(index).split(",");
        return gameBoard[Integer.parseInt(positions[0])][Integer.parseInt(positions[1])] == null;
    }

    public boolean isDraw() {
        for (Mark[] marks : gameBoard) {
            for (Mark mark : marks) {
                if (mark == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private Mark getWinnerIfHas(int xPos, int yPos, Mark mark) {
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
