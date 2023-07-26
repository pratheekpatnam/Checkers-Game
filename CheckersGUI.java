package sprint1_prod;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import sprint1_prod.Board.GameState;

public class CheckersGUI {

	private Board board;

	@FXML
	private GridPane CheckersBoard;

	@FXML
	private Label lblPlayer1;

	@FXML
	private Label lblPlayer1Perc;

	@FXML
	private Label lblPlayer2;

	@FXML
	private Label lblPlayer2Perc;

	@FXML
	private Label lblState;

	@FXML
	private Button btnStart;

	@FXML
	private Ellipse black;

	@FXML
	private Ellipse white;

	int[] possibleMoves;
	int[] jumpMoves;
	boolean jumpMovesFlag = false;
	boolean overtakeFlag = false;
	boolean kingPieceTrigger = false;

	@FXML
	private void InitializeBoard() {

		btnStart.setDisable(true);
		board = new Board();
		UpdateGame();

	}

	private void GameOver() {
		btnStart.setDisable(false);
	}

	private void UpdateGame() {

		CheckGameOver();
		for (Node piece : CheckersBoard.getChildren()) {
			if (piece instanceof Ellipse) {
				//Traverse all the pieces and look for the ones that belong to the current turn
				if (piece.getId().contains(board.getTurn().toLowerCase())) {
					//Create a listener for the pieces
					piece.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {

							if (piece.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {

								int fromRow = CheckRowIndex(piece);
								int fromCol = CheckColIndex(piece);
								
								System.out.println("Piece at " + fromRow + "-" + fromCol);
								System.out.println(board.getCell(fromRow, fromCol));
								//After getting the coordinates of the pressed button, DisplayPossibleMoves will
								//will store the potential possible moves for that piece.
								int[] possibleMoves = DisplayPossibleMoves(fromRow, fromCol, board.getTurn(),
										piece.getId());
								int row1, col1, row2, col2, row3, col3, row4, col4;
								if (possibleMoves.length == 4) {
									row1 = possibleMoves[0];
									col1 = possibleMoves[1];

									row2 = possibleMoves[2];
									col2 = possibleMoves[3];

									row3 = -1;
									col3 = -1;
									row4 = -1;
									col4 = -1;

									System.out.println("Possible move: " + row1 + "-" + col1);
									System.out.println("Possible move: " + row2 + "-" + col2);
								} else {
									row1 = possibleMoves[0];
									col1 = possibleMoves[1];
									row2 = possibleMoves[2];
									col2 = possibleMoves[3];
									row3 = possibleMoves[4];
									col3 = possibleMoves[5];
									row4 = possibleMoves[6];
									col4 = possibleMoves[7];

									System.out.println("Possible move: " + row1 + "-" + col1);
									System.out.println("Possible move: " + row2 + "-" + col2);
									System.out.println("Possible move: " + row3 + "-" + col3);
									System.out.println("Possible move: " + row4 + "-" + col4);
								}

								for (Node Tile : CheckersBoard.getChildren()) {
									if (Tile instanceof Rectangle) {
										//Traverse the checkers board squares
										Tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
											@Override
											public void handle(MouseEvent event) {
												//Creating the onClick listener.
												if (Tile.getBoundsInParent().contains(event.getSceneX(),
														event.getSceneY())) {

													int toRow = CheckRowIndex(Tile);
													int toCol = CheckColIndex(Tile);
													
													//If the coordinates of the square belong to the possible moves array
													//the square will contain the listener and can be clicked
													if (row1 == toRow && col1 == toCol || row2 == toRow && col2 == toCol
															|| row3 == toRow && col3 == toCol
															|| row4 == toRow && col4 == toCol) {

														System.out.println("Tile at " + toRow + "-" + toCol);
														System.out.println(board.getCell(toRow, toCol));
														// Make move when clicked on the corresponding square
														DoMove(fromRow, fromCol, toRow, toCol, piece);
														//if the move is an overtake we perform an overtake
														if (overtakeFlag) {
															overtakeFlag = false;
															PerfomOvertake(fromRow, fromCol, toRow, toCol, piece);
														}
														//After the overtake check if there is a possible jump move
														CheckJumpMove(toRow, toCol, board.getTurn(), piece.getId());
														board.printBoard();
														GridPane.setRowIndex(piece, toRow);
														GridPane.setColumnIndex(piece, toCol);

														//if jumpMovesFlag is true, remove listeners from only the squares
														//so that the player can select the piece again and continue
														//jumping.
														if (jumpMovesFlag) {
															RemoveListeners('R');
															System.out.println("You have to keep jumping");
														} else {
															//Change the shape of the piece if king piece
															if (kingPieceTrigger) {
																((Ellipse) piece).setStroke(Color.RED);
																((Ellipse) piece).setStrokeType(StrokeType.INSIDE);
																((Ellipse) piece).setStrokeWidth(5);
																piece.setId(board.getTurn().toLowerCase() + "King");
																kingPieceTrigger = false;
															}
															//Remove listeners from both pieces and squares
															RemoveListeners('B');
															System.out.println(board.getTurn());
															board.CheckGameState();
															board.ChangeTurn();
															System.out.println(board.getGameState().toString());
															if (board.getGameState() == GameState.PLAYING) {
																lblState.setText("Game is underway!");
																//restart this method but with the turn change to activate the
																//other player's pieces.
																UpdateGame();
															} else {
																if (board.getGameState() == GameState.BLACK_WON) {
																	lblState.setText("Black Wins!");
																} else if (board
																		.getGameState() == GameState.WHITE_WON) {
																	lblState.setText("White Wins!");
																}
																RemoveListeners('B');
																GameOver();
															}
														}
													}
												}
											}
										});
									}
								}
							}
						}
					});
				}
			}
		}
	}

	private void DoMove(int fromRow, int fromCol, int toRow, int toCol, Node piece) {

		//If the piece that is performing the move is moving 2 rows above or below, it means that it is an overtake move
		//whereas if the piece moves 1 row above or below it is a a normal move
		board.makeMove(fromRow, fromCol, toRow, toCol, board.getTurn());
		int rowDif = fromRow - toRow;
		rowDif = Math.abs(rowDif);
		System.out.println("Row Diff: " + rowDif);
		if (rowDif == 2) {
			jumpMovesFlag = true;
			overtakeFlag = true;
		}

		//if the piece reaches the end of the board whether it is black or white, kingPieceTrigger will be true
		if (toRow == 0 && board.getTurn().equals("Black") || toRow == 7 && board.getTurn().equals("White")) {
			kingPieceTrigger = true;
		}
	}

	private void PerfomOvertake(int fromRow, int fromCol, int toRow, int toCol, Node piece) {

		System.out.println("from Row: " + fromRow + " From col: " + fromCol);
		System.out.println("to Row: " + toRow + " to col: " + toCol);
		int piecetoRemoverow = 0;
		int piecetoRemoveCol = 0;

		if (!piece.getId().contains("King")) {
			if (board.getTurn().equals("Black")) {
				//Store the location of the piece that will be removed, one row above fromRow
				piecetoRemoverow = fromRow - 1;
			} else if (board.getTurn().equals("White")) {
				//Store the location of the piece that will be removed, one row below fromRow
				piecetoRemoverow = fromRow + 1;
			}
		} else {
			//checking if the overtake move is going from the top to the bottom or the opposite to determine
			//the position of the piece that will be captured
			if (fromRow < toRow) {
				piecetoRemoverow = fromRow + 1;
			} else {
				piecetoRemoverow = fromRow - 1;
			}
		}
		piecetoRemoveCol = 0;
		//Checking if the overtake move is going left to right or the opposite to determine the column position
		//of the piece that will be captured.
		if (fromCol > toCol) {
			piecetoRemoveCol = fromCol - 1;
		} else {
			piecetoRemoveCol = fromCol + 1;
		}
		System.out.println("piecetoRemoverow: " + piecetoRemoverow + " piecetoRemoveCol: " + piecetoRemoveCol);

		//We have the coordinates of the piece we want to remove so we traverse all the pieces on the board to remove it
		for (Node piecetoRemove : CheckersBoard.getChildren()) {
			if (piecetoRemove instanceof Ellipse) {
				int rowForRemoval = 0;
				int colForRemoval = 0;
				if (GridPane.getRowIndex(piecetoRemove) == null) {
					rowForRemoval = 0;
				} else {
					rowForRemoval = GridPane.getRowIndex(piecetoRemove);
				}
				if (GridPane.getColumnIndex(piecetoRemove) == null) {
					colForRemoval = 0;
				} else {
					colForRemoval = GridPane.getColumnIndex(piecetoRemove);
				}

				if (rowForRemoval == piecetoRemoverow && colForRemoval == piecetoRemoveCol) {

					System.out.println("rowForRemoval: " + rowForRemoval + " colForRemoval: " + colForRemoval);
					//Removing the piece
					CheckersBoard.getChildren().remove(piecetoRemove);
					if (board.getTurn().equals("Black")) {
						board.whitePieces--;
						System.out.println("Black pieces left: " + board.blackPieces);
						System.out.println("White pieces left: " + board.whitePieces);

					} else {
						board.blackPieces--;
						System.out.println("Black pieces left: " + board.blackPieces);
						System.out.println("White pieces left: " + board.whitePieces);
					}
					board.setCellAfterOvertake(rowForRemoval, colForRemoval);
					break;
				}
			}
		}
	}

	private int[] DisplayPossibleMoves(int fromRow, int fromCol, String turn, String id) {

		int[] possibleMovesMethod = null;

		if (id.contains("King")) {
			int pieceNumber = 0;
			if (turn.equals("Black"))
				pieceNumber = 2;
			else
				pieceNumber = 1;

			possibleMovesMethod = new int[8];

			if (board.getCell(fromRow - 1, fromCol + 1) == 0) { // Check one row above and a column to the right
				System.out.println("You can move top right");
				possibleMovesMethod[0] = fromRow - 1;
				possibleMovesMethod[1] = fromCol + 1;

			// Check one row above and a column to the right and if the space 2 rows above and 2 columns to the right is empty
			} else if (board.getCell(fromRow - 1, fromCol + 1) == pieceNumber && board.getCell(fromRow - 2, fromCol + 2) == 0) {
				System.out.println("You can overtake top right");
				possibleMovesMethod[0] = fromRow - 2;
				possibleMovesMethod[1] = fromCol + 2;

			} else {
				possibleMovesMethod[0] = -1;
				possibleMovesMethod[1] = -1;
			}

			if (board.getCell(fromRow - 1, fromCol - 1) == 0) { // Check one row above and a column to the left
				System.out.println("You can move top left");
				possibleMovesMethod[2] = fromRow - 1;
				possibleMovesMethod[3] = fromCol - 1;
				
			// Check one row above and a column to the left and if the space 2 rows above and 2 columns to the left is empty
			} else if (board.getCell(fromRow - 1, fromCol - 1) == pieceNumber && board.getCell(fromRow - 2, fromCol - 2) == 0) {
				System.out.println("You can overtake top left");
				possibleMovesMethod[2] = fromRow - 2;
				possibleMovesMethod[3] = fromCol - 2;

			} else {
				possibleMovesMethod[2] = -1;
				possibleMovesMethod[3] = -1;
			}

			if (board.getCell(fromRow + 1, fromCol + 1) == 0) { // Check one row below and a column to the right
				System.out.println("You can move bottom right");
				possibleMovesMethod[4] = fromRow + 1;
				possibleMovesMethod[5] = fromCol + 1;

			// Check one row below and a column to the right and if the space 2 rows below and 2 columns to the right is empty
			} else if (board.getCell(fromRow + 1, fromCol + 1) == pieceNumber && board.getCell(fromRow + 2, fromCol + 2) == 0) {
				System.out.println("You can overtake bottom right");
				possibleMovesMethod[4] = fromRow + 2;
				possibleMovesMethod[5] = fromCol + 2;

			} else {
				possibleMovesMethod[4] = -1;
				possibleMovesMethod[5] = -1;
			}

			if (board.getCell(fromRow + 1, fromCol - 1) == 0) { // Check one row below and a column to the left
				System.out.println("You can move bottom left");

				possibleMovesMethod[6] = fromRow + 1;
				possibleMovesMethod[7] = fromCol - 1;
				
			// Check one row below and a column to the left and if the space 2 rows below and 2 columns to the left is empty
			} else if (board.getCell(fromRow + 1, fromCol - 1) == pieceNumber && board.getCell(fromRow + 2, fromCol - 2) == 0) {
				System.out.println("You can overtake bottom left");
				possibleMovesMethod[6] = fromRow + 2;
				possibleMovesMethod[7] = fromCol - 2;

			} else {
				possibleMovesMethod[6] = -1;
				possibleMovesMethod[7] = -1;
			}

		} else {
			possibleMovesMethod = new int[4];
			if (turn.equals("Black")) {

				if (board.getCell(fromRow - 1, fromCol + 1) == 0) { // Check one row above and a column to the right
					System.out.println("You can move right");
					possibleMovesMethod[0] = fromRow - 1;
					possibleMovesMethod[1] = fromCol + 1;
					
				// Check one row above and a column to the right and if the space 2 rows above and 2 columns to the right is empty
				} else if (board.getCell(fromRow - 1, fromCol + 1) == 2 && board.getCell(fromRow - 2, fromCol + 2) == 0) {
					System.out.println("You can overtake right");
					possibleMovesMethod[0] = fromRow - 2;
					possibleMovesMethod[1] = fromCol + 2;

				} else {
					possibleMovesMethod[0] = -1;
					possibleMovesMethod[1] = -1;
				}

				if (board.getCell(fromRow - 1, fromCol - 1) == 0) { // Check one row above and a column to the left
					System.out.println("You can move left");
					possibleMovesMethod[2] = fromRow - 1;
					possibleMovesMethod[3] = fromCol - 1;

				// Check one row above and a column to the left and if the space 2 rows above and 2 columns to the left is empty
				} else if (board.getCell(fromRow - 1, fromCol - 1) == 2 && board.getCell(fromRow - 2, fromCol - 2) == 0) {
					System.out.println("You can overtake left");
					possibleMovesMethod[2] = fromRow - 2;
					possibleMovesMethod[3] = fromCol - 2;

				} else {
					possibleMovesMethod[2] = -1;
					possibleMovesMethod[3] = -1;
				}

			} else if (turn.equals("White")) {

				if (board.getCell(fromRow + 1, fromCol + 1) == 0) { // Check one row below and a column to the right
					System.out.println("You can move right");
					possibleMovesMethod[0] = fromRow + 1;
					possibleMovesMethod[1] = fromCol + 1;

				// Check one row below and a column to the right and if the space 2 rows below and 2 columns to the right is empty
				} else if (board.getCell(fromRow + 1, fromCol + 1) == 1 && board.getCell(fromRow + 2, fromCol + 2) == 0) {
					System.out.println("You can overtake right");
					possibleMovesMethod[0] = fromRow + 2;
					possibleMovesMethod[1] = fromCol + 2;

				} else {
					possibleMovesMethod[0] = -1;
					possibleMovesMethod[1] = -1;
				}

				if (board.getCell(fromRow + 1, fromCol - 1) == 0) { // Check one row below and a column to the left
					System.out.println("You can move left");

					possibleMovesMethod[2] = fromRow + 1;
					possibleMovesMethod[3] = fromCol - 1;
					
				// Check one row below and a column to the left and if the space 2 rows below and 2 columns to the left is empty
				} else if (board.getCell(fromRow + 1, fromCol - 1) == 1 && board.getCell(fromRow + 2, fromCol - 2) == 0) {
					System.out.println("You can overtake left");
					possibleMovesMethod[2] = fromRow + 2;
					possibleMovesMethod[3] = fromCol - 2;

				} else {
					possibleMovesMethod[2] = -1;
					possibleMovesMethod[3] = -1;
				}
			}
		}
		return possibleMovesMethod;
	}

	private void CheckJumpMove(int row, int column, String turn, String id) {

		jumpMoves = new int[8];
		boolean isKing = false;
		boolean blackPiece = false;
		boolean whitePiece = false;

		int pieceNumber = 0; //opponent piece number will be stored here

		//if it is Black pieces turn then the piece's we are going to set the white piece number as the opponent
		if (turn.equals("Black")) {
			blackPiece = true;
			pieceNumber = 2;
		} else {
			whitePiece = true;
			pieceNumber = 1;
		}

		if (id.contains("King")) {
			isKing = true;
		}

		//if it is a black piece we check if the space in the row above and to the right contains a white piece
		//then we check if the 2 rows above and 2 spaces to the right are empty
		//In case it is a king piece we check for the same condition.
		if (board.getCell(row - 1, column + 1) == pieceNumber && board.getCell(row - 2, column + 2) == 0 && blackPiece
				|| board.getCell(row - 1, column + 1) == pieceNumber && board.getCell(row - 2, column + 2) == 0
						&& isKing) {
			jumpMoves[0] = row - 2;
			jumpMoves[1] = column + 2;
		} else {
			jumpMoves[0] = -1;
			jumpMoves[1] = -1;
		}

		//if it is a black piece we check if the space in the row above and to the left contains a white piece
		//then we check if the 2 rows above and 2 spaces to the left are empty
		//In case it is a king piece we check for the same condition.
		if (board.getCell(row - 1, column - 1) == pieceNumber && board.getCell(row - 2, column - 2) == 0 && blackPiece
				|| board.getCell(row - 1, column - 1) == pieceNumber && board.getCell(row - 2, column - 2) == 0
						&& isKing) {
			jumpMoves[2] = row - 2;
			jumpMoves[3] = column - 2;
		} else {
			jumpMoves[2] = -1;
			jumpMoves[3] = -1;
		}

		//if it is a white piece we check if the space in the row above and to the right contains a black piece
		//then we check if the 2 rows above and 2 spaces to the right are empty
		//In case it is a king piece we check for the same condition.
		if (board.getCell(row + 1, column + 1) == pieceNumber && board.getCell(row + 2, column + 2) == 0 && whitePiece
				|| board.getCell(row + 1, column + 1) == pieceNumber && board.getCell(row + 2, column + 2) == 0
						&& isKing) {
			jumpMoves[4] = row + 2;
			jumpMoves[5] = column + 2;
		} else {
			jumpMoves[4] = -1;
			jumpMoves[5] = -1;
		}

		//if it is a white piece we check if the space in the row above and to the left contains a black piece
		//then we check if the 2 rows above and 2 spaces to the left are empty
		//In case it is a king piece we check for the same condition.
		if (board.getCell(row + 1, column - 1) == pieceNumber && board.getCell(row + 2, column - 2) == 0 && whitePiece
				|| board.getCell(row + 1, column - 1) == pieceNumber && board.getCell(row + 2, column - 2) == 0
						&& isKing) {
			jumpMoves[6] = row + 2;
			jumpMoves[7] = column - 2;
		} else {
			jumpMoves[6] = -1;
			jumpMoves[7] = -1;
		}

		//if all the jumpMoves array contains only the value -1 then there are no jumpMoves.
		if (jumpMoves[0] == -1 && jumpMoves[1] == -1 && jumpMoves[2] == -1 && jumpMoves[3] == -1 && jumpMoves[4] == -1
				&& jumpMoves[5] == -1 && jumpMoves[6] == -1 && jumpMoves[7] == -1 || jumpMoves == null) {
			jumpMovesFlag = false;
		}

		if (jumpMovesFlag) {
			System.out.println("Possible Jump move: " + jumpMoves[0] + "-" + jumpMoves[1]);
			System.out.println("Possible Jump move: " + jumpMoves[2] + "-" + jumpMoves[3]);
			System.out.println("Possible Jump move: " + jumpMoves[4] + "-" + jumpMoves[5]);
			System.out.println("Possible Jump move: " + jumpMoves[6] + "-" + jumpMoves[7]);
		}
		System.out.println(jumpMovesFlag);
	}

	private void RemoveListeners(char choice) {

		if (choice == 'R') { //Removing all listeners from the board squares
			for (Node piece : CheckersBoard.getChildren()) {
				if (piece instanceof Rectangle) {
					piece.setOnMouseClicked(null);
				}
			}
		} else {  //Removing all listeners from the both board squares and pieces
			for (Node piece : CheckersBoard.getChildren()) {
				if (piece instanceof Ellipse || piece instanceof Rectangle) {
					piece.setOnMouseClicked(null);
				}
			}
		}
	}

	private void GameState() {
		board.CheckGameState(); //Checking game state if there is a winner
		boolean isDraw = true;
		if (board.getGameState() == GameState.PLAYING) {
			for (Node piece : CheckersBoard.getChildren()) {
				if (piece instanceof Ellipse) {  //Traversing each piece on the board that belongs to the current turn player.
					if (piece.getId().contains(board.getTurn().toLowerCase())) { 
						
						int fromRow = CheckRowIndex(piece);
						int fromCol = CheckColIndex(piece);

						//Finding all the possible moves of this piece
						int[] possibleMoves = DisplayPossibleMoves(fromRow, fromCol, board.getTurn(), piece.getId());
						
						//Traversing along the possible moves, if one of them is not -1 then it is not a draw
						for (int i = 0; i < possibleMoves.length; i++) {
							if (possibleMoves[i] != -1) {
								isDraw = false;
							}
						}
					}
				}
			}

			if (isDraw) {
				if (board.getTurn().equals("Black")) {
					board.setCurrentGameState(GameState.WHITE_WON);
				} else {
					board.setCurrentGameState(GameState.BLACK_WON);
				}
			}
		}
	}

	private void CheckGameOver() {
		GameState();
		if (board.getGameState() == GameState.BLACK_WON) {
			lblState.setText("Black Wins!");
			RemoveListeners('B');
			GameOver();
		} else if (board.getGameState() == GameState.WHITE_WON) {
			lblState.setText("White Wins!");
			RemoveListeners('B');
			GameOver();
		}

	}
	
	private int CheckRowIndex(Node piece) {
		if (GridPane.getRowIndex(piece) == null) {
			return 0;
		} else {
			return GridPane.getRowIndex(piece);
		}
	}

	private int CheckColIndex(Node piece) {
		if (GridPane.getColumnIndex(piece) == null) {
			return 0;
		} else {
			return GridPane.getColumnIndex(piece);
		}
	}

}
