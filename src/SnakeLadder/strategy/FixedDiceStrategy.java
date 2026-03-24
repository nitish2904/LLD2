package SnakeLadder.strategy;
public class FixedDiceStrategy implements DiceStrategy {
    private final int[] rolls;
    private int idx = 0;
    public FixedDiceStrategy(int... rolls) { this.rolls = rolls; }
    public int roll() { return rolls[idx++ % rolls.length]; }
    public String getName() { return "Fixed"; }
}
