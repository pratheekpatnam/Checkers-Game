package sprint1_prod;

public class Board {

	private static int[][] board;
	private String turn = "Black";

	private GameState currentGameState;

	public enum GameState {
		PLAYING, DRAW, WHITE_WON, BLACK_WON
	}

	public int blackPieces = 12;
	public int whitePieces = 12;

	public Board() {
		board = new int[8][8];

		//Place all the pieces on the grid
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i == 0) {
					if (j % 2 == 1) {
						board[i][j] = 2;
					}
				} else if (i == 1) {
					if (j % 2 == 0) {
						board[i][j] = 2;
					}
				} else if (i == 2) {
					if (j % 2 == 1) {
						board[i][j] = 2;
					}
				} else if (i == 5) {
					if (j % 2 == 0) {
						board[i][j] = 1;
					}
				} else if (i == 6) {
					if (j % 2 == 1) {
						board[i][j] = 1;
					}
				} else if (i == 7) {
					if (j % 2 == 0) {
						board[i][j] = 1;
					}
				}
			}
		}
	}

	public int getCell(int row, int column) {
		if (row >= 0 && row < 8 && column >= 0 && column < 8)
			return board[row][column];
		else
			return -1;
	}

	public void setCellAfterOvertake(int row, int column) {
		board[row][column] = 0;
	}

	public String getTurn() {
		return turn;
	}

	public void ChangeTurn() {
		if (turn.equals("Black")) {
			this.turn = "White";
		} else {
			this.turn = "Black";
		}
	}

	public void makeMove(int oldrow, int oldcolumn, int row, int column, String turn) {
		if (row < 8 && column < 8 && row >= 0 && column >= 0) {
			if (board[row][column] == 0) {
				if (turn.equals("Black")) {
					board[oldrow][oldcolumn] = 0;
					board[row][column] = 1;
				} else {
					board[oldrow][oldcolumn] = 0;
					board[row][column] = 2;
				}
			}
		}
	}

	public void printBoard() {
		for (int[] x : board) {
			for (int y : x) {
				System.out.print(y + " ");
			}
			System.out.println();
		}
	}

	private boolean isWin(String turn) {
		if (whitePieces <= 0) {
			currentGameState = GameState.BLACK_WON;
			return true;
		} else if (blackPieces <= 0) {
			currentGameState = GameState.WHITE_WON;
			return true;
		}
		return false;
	}

	public void CheckGameState() {
		if (isWin(turn)) {
			currentGameState = (turn == "Black") ? GameState.BLACK_WON : GameState.WHITE_WON;
		} else {
			currentGameState = GameState.PLAYING;
		}
	}

	public GameState getGameState() {
		return currentGameState;
	}

	public void setCurrentGameState(GameState currentGameState) {
		this.currentGameState = currentGameState;
	}

}
