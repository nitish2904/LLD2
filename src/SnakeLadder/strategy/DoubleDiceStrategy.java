package SnakeLadder.strategy;
import java.util.Random;
public class DoubleDiceStrategy implements DiceStrategy {
    private final Random rand = new Random();
    public int roll() { return rand.nextInt(6) + 1 + rand.nextInt(6) + 1; }
    public String getName() { return "DoubleDice"; }
}
