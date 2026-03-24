package TicTacToe.strategy;
import TicTacToe.model.*;
import java.util.*;
public class RandomMoveStrategy implements MoveStrategy {
    private final Random rand = new Random();
    public int[] getMove(Board board, Symbol symbol) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) for (int j = 0; j < board.getSize(); j++) if (board.getCell(i,j) == Symbol.EMPTY) moves.add(new int[]{i,j});
        return moves.get(rand.nextInt(moves.size()));
    }
    public String getName() { return "Random"; }
}
