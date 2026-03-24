package TicTacToe.model;
public class Board {
    private final int size;
    private final Symbol[][] grid;
    private int moveCount;
    public Board(int size) {
        this.size = size; this.grid = new Symbol[size][size]; this.moveCount = 0;
        for (int i = 0; i < size; i++) for (int j = 0; j < size; j++) grid[i][j] = Symbol.EMPTY;
    }
    public boolean makeMove(int row, int col, Symbol symbol) {
        if (row < 0 || row >= size || col < 0 || col >= size || grid[row][col] != Symbol.EMPTY) return false;
        grid[row][col] = symbol; moveCount++; return true;
    }
    public void undoMove(int row, int col) { grid[row][col] = Symbol.EMPTY; moveCount--; }
    public Symbol getCell(int r, int c) { return grid[r][c]; }
    public int getSize() { return size; }
    public boolean isFull() { return moveCount == size * size; }
    public Symbol checkWinner() {
        for (int i = 0; i < size; i++) {
            if (grid[i][0] != Symbol.EMPTY && allSame(grid[i])) return grid[i][0];
            Symbol first = grid[0][i];
            if (first != Symbol.EMPTY) { boolean col = true; for (int j = 1; j < size; j++) if (grid[j][i] != first) { col = false; break; } if (col) return first; }
        }
        Symbol d1 = grid[0][0]; boolean diag1 = d1 != Symbol.EMPTY;
        Symbol d2 = grid[0][size-1]; boolean diag2 = d2 != Symbol.EMPTY;
        for (int i = 1; i < size; i++) { if (grid[i][i] != d1) diag1 = false; if (grid[i][size-1-i] != d2) diag2 = false; }
        if (diag1) return d1; if (diag2) return d2;
        return null;
    }
    private boolean allSame(Symbol[] row) { for (int i = 1; i < row.length; i++) if (row[i] != row[0]) return false; return true; }
    public void print() {
        System.out.print("  "); for (int j = 0; j < size; j++) System.out.print(j + " "); System.out.println();
        for (int i = 0; i < size; i++) { System.out.print(i + " "); for (int j = 0; j < size; j++) System.out.print(grid[i][j] + " "); System.out.println(); }
    }
}
