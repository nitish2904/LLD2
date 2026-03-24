package TicTacToe;
import TicTacToe.model.*; import TicTacToe.strategy.*; import TicTacToe.service.GameService;
public class TicTacToeMain {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    ❌⭕ TIC-TAC-TOE DEMO        ║");
        System.out.println("╚══════════════════════════════════╝\n");

        System.out.println("═══ GAME 1: Random vs Random ═══\n");
        new GameService(3, new Player("Alice", Symbol.X, new RandomMoveStrategy()), new Player("Bob", Symbol.O, new RandomMoveStrategy())).play();

        System.out.println("═══ GAME 2: Sequential vs Minimax (AI never loses) ═══\n");
        new GameService(3, new Player("Human", Symbol.X, new SequentialMoveStrategy()), new Player("AI", Symbol.O, new MinimaxStrategy())).play();

        System.out.println("═══ GAME 3: Minimax(X) vs Sequential ═══\n");
        new GameService(3, new Player("AI-X", Symbol.X, new MinimaxStrategy()), new Player("Seq-O", Symbol.O, new SequentialMoveStrategy())).play();

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    ✅ ALL GAMES COMPLETE          ║");
        System.out.println("╚══════════════════════════════════╝");
    }
}
