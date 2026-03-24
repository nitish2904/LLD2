package SnakeLadder.model;
import java.util.*;
public class Board {
    private final int size;
    private final Map<Integer, Integer> snakes = new HashMap<>();
    private final Map<Integer, Integer> ladders = new HashMap<>();
    public Board(int size) { this.size = size; }
    public void addSnake(int head, int tail) { snakes.put(head, tail); }
    public void addLadder(int bottom, int top) { ladders.put(bottom, top); }
    public int getSize() { return size; }
    public int getFinalPosition(int pos) {
        if (snakes.containsKey(pos)) { System.out.println("  🐍 Snake! " + pos + " → " + snakes.get(pos)); return snakes.get(pos); }
        if (ladders.containsKey(pos)) { System.out.println("  🪜 Ladder! " + pos + " → " + ladders.get(pos)); return ladders.get(pos); }
        return pos;
    }
}
