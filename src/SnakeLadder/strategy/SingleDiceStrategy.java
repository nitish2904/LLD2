package SnakeLadder.strategy;
import java.util.Random;
public class SingleDiceStrategy implements DiceStrategy {
    private final Random rand = new Random();
    public int roll() { return rand.nextInt(6) + 1; }
    public String getName() { return "SingleDice"; }
}
