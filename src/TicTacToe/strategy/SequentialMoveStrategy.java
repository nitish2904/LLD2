package TicTacToe.strategy;
import TicTacToe.model.*;
public class SequentialMoveStrategy implements MoveStrategy {
    public int[] getMove(Board board, Symbol symbol) {
        for (int i = 0; i < board.getSize(); i++) for (int j = 0; j < board.getSize(); j++) if (board.getCell(i,j) == Symbol.EMPTY) return new int[]{i,j};
        return null;
    }
    public String getName() { return "Sequential"; }
}
