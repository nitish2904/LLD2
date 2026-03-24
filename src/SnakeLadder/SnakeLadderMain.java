package SnakeLadder;
import SnakeLadder.model.*;
import SnakeLadder.service.GameService;
import SnakeLadder.strategy.*;
public class SnakeLadderMain {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   🐍🪜 SNAKE & LADDER DEMO      ║");
        System.out.println("╚══════════════════════════════════╝\n");

        System.out.println("═══ GAME 1: Fixed dice (deterministic) ═══\n");
        Board b1 = new Board(30);
        b1.addSnake(27, 5); b1.addSnake(21, 9); b1.addSnake(17, 7);
        b1.addLadder(3, 22); b1.addLadder(5, 8); b1.addLadder(11, 26); b1.addLadder(20, 29);
        GameService g1 = new GameService(b1, new FixedDiceStrategy(3, 4, 5, 6, 2, 1, 4, 3, 1, 5));
        g1.addPlayer(new Player("Alice")); g1.addPlayer(new Player("Bob"));
        g1.play();

        System.out.println("═══ GAME 2: Random dice ═══\n");
        Board b2 = new Board(100);
        b2.addSnake(99, 2); b2.addSnake(70, 30); b2.addSnake(52, 11); b2.addSnake(25, 6);
        b2.addLadder(4, 56); b2.addLadder(12, 50); b2.addLadder(14, 55); b2.addLadder(22, 58); b2.addLadder(41, 79); b2.addLadder(54, 88);
        GameService g2 = new GameService(b2, new SingleDiceStrategy());
        g2.addPlayer(new Player("Charlie")); g2.addPlayer(new Player("Diana")); g2.addPlayer(new Player("Eve"));
        g2.play();

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   ✅ ALL GAMES COMPLETE           ║");
        System.out.println("╚══════════════════════════════════╝");
    }
}
