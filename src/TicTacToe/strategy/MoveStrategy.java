package TicTacToe.strategy;
import TicTacToe.model.Board;
import TicTacToe.model.Symbol;
public interface MoveStrategy {
    int[] getMove(Board board, Symbol symbol);
    String getName();
}
