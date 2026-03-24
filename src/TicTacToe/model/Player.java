package TicTacToe.model;
import TicTacToe.strategy.MoveStrategy;
public class Player {
    private final String name;
    private final Symbol symbol;
    private final MoveStrategy strategy;
    public Player(String name, Symbol symbol, MoveStrategy strategy) { this.name = name; this.symbol = symbol; this.strategy = strategy; }
    public int[] getMove(Board board) { return strategy.getMove(board, symbol); }
    public String getName() { return name; }
    public Symbol getSymbol() { return symbol; }
    @Override public String toString() { return name + "(" + symbol + ")"; }
}
