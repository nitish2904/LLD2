package TicTacToe.service;
import TicTacToe.model.*;
public class GameService {
    private final Board board;
    private final Player player1, player2;
    private Player currentPlayer;
    public GameService(int boardSize, Player p1, Player p2) {
        this.board = new Board(boardSize); this.player1 = p1; this.player2 = p2; this.currentPlayer = p1;
    }
    public Player play() {
        System.out.println("Game: " + player1 + " vs " + player2 + " on " + board.getSize() + "x" + board.getSize());
        while (true) {
            int[] move = currentPlayer.getMove(board);
            board.makeMove(move[0], move[1], currentPlayer.getSymbol());
            System.out.println(currentPlayer.getName() + " → (" + move[0] + "," + move[1] + ")");
            board.print();
            Symbol winner = board.checkWinner();
            if (winner != null) { System.out.println("🏆 " + currentPlayer.getName() + " WINS!\n"); return currentPlayer; }
            if (board.isFull()) { System.out.println("🤝 DRAW!\n"); return null; }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }
}
