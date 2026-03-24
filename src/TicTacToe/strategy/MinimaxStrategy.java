package TicTacToe.strategy;
import TicTacToe.model.*;
public class MinimaxStrategy implements MoveStrategy {
    public int[] getMove(Board board, Symbol symbol) {
        int bestScore = Integer.MIN_VALUE; int[] bestMove = null;
        Symbol opponent = symbol == Symbol.X ? Symbol.O : Symbol.X;
        for (int i = 0; i < board.getSize(); i++) for (int j = 0; j < board.getSize(); j++) {
            if (board.getCell(i,j) == Symbol.EMPTY) {
                board.makeMove(i, j, symbol);
                int score = minimax(board, 0, false, symbol, opponent);
                board.undoMove(i, j);
                if (score > bestScore) { bestScore = score; bestMove = new int[]{i,j}; }
            }
        }
        return bestMove;
    }
    private int minimax(Board board, int depth, boolean isMax, Symbol me, Symbol opp) {
        Symbol winner = board.checkWinner();
        if (winner == me) return 10 - depth;
        if (winner == opp) return depth - 10;
        if (board.isFull()) return 0;
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < board.getSize(); i++) for (int j = 0; j < board.getSize(); j++) if (board.getCell(i,j) == Symbol.EMPTY) { board.makeMove(i,j,me); best = Math.max(best, minimax(board, depth+1, false, me, opp)); board.undoMove(i,j); }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < board.getSize(); i++) for (int j = 0; j < board.getSize(); j++) if (board.getCell(i,j) == Symbol.EMPTY) { board.makeMove(i,j,opp); best = Math.min(best, minimax(board, depth+1, true, me, opp)); board.undoMove(i,j); }
            return best;
        }
    }
    public String getName() { return "Minimax"; }
}
