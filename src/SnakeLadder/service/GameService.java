package SnakeLadder.service;
import SnakeLadder.model.*;
import SnakeLadder.strategy.DiceStrategy;
import java.util.*;
public class GameService {
    private final Board board;
    private final Queue<Player> players = new LinkedList<>();
    private final DiceStrategy dice;
    public GameService(Board board, DiceStrategy dice) { this.board = board; this.dice = dice; }
    public void addPlayer(Player p) { players.add(p); }
    public Player play() {
        System.out.println("Game started with " + players.size() + " players on " + board.getSize() + "-cell board");
        int maxTurns = 200;
        while (maxTurns-- > 0) {
            Player current = players.poll();
            int roll = dice.roll();
            int newPos = current.getPosition() + roll;
            System.out.println(current.getName() + " rolled " + roll + " (" + current.getPosition() + " → " + newPos + ")");
            if (newPos > board.getSize()) { System.out.println("  ⛔ Exceeds board, stays at " + current.getPosition()); players.add(current); continue; }
            if (newPos == board.getSize()) { current.setPosition(newPos); System.out.println("🏆 " + current.getName() + " WINS!\n"); return current; }
            newPos = board.getFinalPosition(newPos);
            current.setPosition(newPos);
            players.add(current);
        }
        System.out.println("Game ended after max turns\n");
        return null;
    }
}
