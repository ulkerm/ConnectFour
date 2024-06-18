package spw4.connectfour;

public class ConnectFourImpl implements ConnectFour {

    private Player currentPlayer;
    private Character[][] board;
    private boolean gameOver;
    private Player winner;
    private static final int rows = 6;
    private static final int cols = 7;

    public ConnectFourImpl(Player playerOnTurn) {
        currentPlayer = playerOnTurn;
        board = new Character[rows][cols];
        resetBoard();
                gameOver = false;
        winner = Player.none;
    }

    private void resetBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = '.';
            }
        }
    }

    public Player getPlayerAt(int row, int col) {
        return board[row][col] == 'R' ? Player.red : (board[row][col] == 'Y' ? Player.yellow : Player.none);
    }

    public Player getPlayerOnTurn() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            sb.append("|");
            for (int col = 0; col < cols; col++) {
                sb.append(" " + board[row][col] + " ");
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    public void reset(Player playerOnTurn) {
        resetBoard();
        gameOver = false;
        winner = Player.none;
        currentPlayer = playerOnTurn;
    }

    public void drop(int col) {
        if (gameOver) {
            System.out.println("Game is over. No more moves can be made.");
            return;
        }
        if (col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid column");
        }
        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][col] == '.') {
                board[row][col] = currentPlayer == Player.red ? 'R' : 'Y';
                if (checkWin(row, col)) {
                    gameOver = true;
                    winner = currentPlayer;
                    System.out.println("Game over! " + winner + " has won!");
                }
                currentPlayer = currentPlayer == Player.red ? Player.yellow : Player.red;
                return;
            }
        }
        throw new IllegalArgumentException("Column is full");
    }

    private boolean checkLine(int x1, int y1, int xDiff, int yDiff, char playerDisc) {
        for (int i = 0; i < 4; ++i) {
            int x = x1 + (xDiff * i);
            int y = y1 + (yDiff * i);
            if (x < 0 || x >= cols || y < 0 || y >= rows) {
                return false;
            }
            if (board[y][x] != playerDisc) {
                return false;
            }
        }
        return true;
    }

    private boolean checkWin(int row, int col) {
        char playerDisc = board[row][col];
        return checkVertical(col, playerDisc) ||
                checkHorizontal(row, playerDisc) ||
                checkDiagonal(row, col, playerDisc);
    }

    private boolean checkVertical(int col, char playerDisc) {
        for (int row = 0; row <= rows - 4; row++) {
            if (checkLine(col, row, 0, 1, playerDisc)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkHorizontal(int row, char playerDisc) {
        for (int col = 0; col <= cols - 4; col++) {
            if (checkLine(col, row, 1, 0, playerDisc)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal(int row, int col, char playerDisc) {
        // Check Leading Diagonal
        for (int offset = 0; offset <= cols - 4; offset++) {
            if (checkLine(col - offset, row - offset, 1, 1, playerDisc) ||
                    checkLine(col - offset, row + offset, 1, -1, playerDisc)) {
                return true;
            }
        }
        return false;
    }
}
