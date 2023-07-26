package sprint1_test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.scene.Node;
import sprint1_prod.Board;
import sprint1_prod.CheckersGUI;
import sprint1_prod.Board.GameState;

public class NewCheckersBoardTest {

	private Board board = new Board();
	int count = 0;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//5.1
	@Test
	public void testValidBlackPieceMove() {


		int rowFrom = 5;
		int colFrom = 4;
		int rowTo = 4;
		int colTo = 5;

		assertEquals("", board.getCell(rowFrom, colFrom), 1);
		assertEquals("", board.getCell(rowTo, colTo), 0);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFrom, colFrom, rowTo, colTo, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFrom, colFrom), 0);
		assertEquals("", board.getCell(rowTo, colTo), 1);
		assertEquals("", board.getTurn(), "White");

		//board.printBoard();
	}

	// 5.2
	@Test
	public void testInvalidNonEmptyCellBlackPieceMove() {
		//First Move for black piece
		int rowFrom = 5;
		int colFrom = 4;
		int rowTo = 4;
		int colTo = 5;

		assertEquals("", board.getCell(rowFrom, colFrom), 1);
		assertEquals("", board.getCell(rowTo, colTo), 0);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFrom, colFrom, rowTo, colTo, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFrom, colFrom), 0);
		assertEquals("", board.getCell(rowTo, colTo), 1);
		assertEquals("", board.getTurn(), "White");

		//Second move for white piece
		int rowFromW = 2;
		int colFromW = 3;
		int rowToW = 3;
		int colToW = 4;
		

		assertEquals("", board.getCell(rowFromW, colFromW), 2);
		assertEquals("", board.getCell(rowToW, colToW), 0);
		assertEquals("", board.getTurn(), "White");
		
		board.makeMove(rowFromW, colFromW, rowToW, colToW, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFromW, colFromW), 0);
		assertEquals("", board.getCell(rowToW, colToW), 2);
		assertEquals("", board.getTurn(), "Black");

		//Attempt to move and place a black piece on top of a white piece
		int rowFromB = 4;
		int colFromB = 5;
		int rowToB = 3;
		int colToB = 4;
		
		assertEquals("", board.getCell(rowFromB, colFromB), 1);
		assertEquals("", board.getCell(rowToB, colToB), 2);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFromB, colFromB, rowToB, colToB, board.getTurn());
		
		assertEquals("", board.getCell(rowFromB, colFromB), 1);
		assertEquals("", board.getCell(rowToB, colToB), 2);
		assertEquals("", board.getTurn(), "Black");
		
		//board.printBoard();
	}

	// 5.3
	@Test
	public void testInvalidOutOfBoundsCellBlackPieceMove() {
		//First Move for black piece
		int rowFrom = 5;
		int colFrom = 6;
		int rowTo = 4;
		int colTo = 7;

		assertEquals("", board.getCell(rowFrom, colFrom), 1);
		assertEquals("", board.getCell(rowTo, colTo), 0);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFrom, colFrom, rowTo, colTo, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFrom, colFrom), 0);
		assertEquals("", board.getCell(rowTo, colTo), 1);
		assertEquals("", board.getTurn(), "White");

		//Second move for white piece
		int rowFromW = 2;
		int colFromW = 3;
		int rowToW = 3;
		int colToW = 4;
		

		assertEquals("", board.getCell(rowFromW, colFromW), 2);
		assertEquals("", board.getCell(rowToW, colToW), 0);
		assertEquals("", board.getTurn(), "White");
		
		board.makeMove(rowFromW, colFromW, rowToW, colToW, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFromW, colFromW), 0);
		assertEquals("", board.getCell(rowToW, colToW), 2);
		assertEquals("", board.getTurn(), "Black");

		//Attempt to move and place a black piece out of bounds
		int rowFromB = 4;
		int colFromB = 7;
		int rowToB = 3;
		int colToB = 8; //out of bound column
		
		assertEquals("", board.getCell(rowFromB, colFromB), 1);
		assertEquals("", board.getCell(rowToB, colToB), -1);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFromB, colFromB, rowToB, colToB, board.getTurn());
		
		assertEquals("", board.getCell(rowFromB, colFromB), 1);
		assertEquals("", board.getCell(rowToB, colToB), -1);
		assertEquals("", board.getTurn(), "Black");
		
		//board.printBoard();
	}

	// 6.1
	@Test
	public void testValidWhitePieceMove() {
		
		int rowFrom = 5;
		int colFrom = 4;
		int rowTo = 4;
		int colTo = 5;
		
		board.makeMove(rowFrom, colFrom, rowTo, colTo, board.getTurn());
		board.ChangeTurn();
		
		//Black piece has to go first then white piece is next
		
		int rowFromW = 2;
		int colFromW = 3;
		int rowToW = 3;
		int colToW = 4;
		

		assertEquals("", board.getCell(rowFromW, colFromW), 2);
		assertEquals("", board.getCell(rowToW, colToW), 0);
		assertEquals("", board.getTurn(), "White");
		
		board.makeMove(rowFromW, colFromW, rowToW, colToW, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFromW, colFromW), 0);
		assertEquals("", board.getCell(rowToW, colToW), 2);
		assertEquals("", board.getTurn(), "Black");
		
	}

	// 6.2
	@Test
	public void testInvalidNonEmptyCellWhitePieceMove() {
		//First Move for black piece
		int rowFrom = 5;
		int colFrom = 4;
		int rowTo = 4;
		int colTo = 5;

		assertEquals("", board.getCell(rowFrom, colFrom), 1);
		assertEquals("", board.getCell(rowTo, colTo), 0);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFrom, colFrom, rowTo, colTo, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFrom, colFrom), 0);
		assertEquals("", board.getCell(rowTo, colTo), 1);
		assertEquals("", board.getTurn(), "White");


		//Second move for white piece
		int rowFromW = 2;
		int colFromW = 3;
		int rowToW = 3;
		int colToW = 4;
		

		assertEquals("", board.getCell(rowFromW, colFromW), 2);
		assertEquals("", board.getCell(rowToW, colToW), 0);
		assertEquals("", board.getTurn(), "White");
		
		board.makeMove(rowFromW, colFromW, rowToW, colToW, board.getTurn());
		board.ChangeTurn();

		assertEquals("", board.getCell(rowFromW, colFromW), 0);
		assertEquals("", board.getCell(rowToW, colToW), 2);
		assertEquals("", board.getTurn(), "Black");

		//black piece's turn
		int rowFromB = 5;
		int colFromB = 2;
		int rowToB = 4;
		int colToB = 3;
		
		assertEquals("", board.getCell(rowFromB, colFromB), 1);
		assertEquals("", board.getCell(rowToB, colToB), 0);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFromB, colFromB, rowToB, colToB, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFromB, colFromB), 0);
		assertEquals("", board.getCell(rowToB, colToB), 1);
		assertEquals("", board.getTurn(), "White");
		
		//Attempt to move and place a White piece on black piece
		int rowFromW2 = 3;
		int colFromW2 = 4;
		int rowToW2 = 4;
		int colToW2 = 5;
		
		assertEquals("", board.getCell(rowFromW2, colFromW2), 2);
		assertEquals("", board.getCell(rowToW2, colToW2), 1);
		assertEquals("", board.getTurn(), "White");
		
		board.makeMove(rowFromW2, colFromW2, rowToW2, colToW2, board.getTurn());
		
		assertEquals("", board.getCell(rowFromW2, colFromW2), 2);
		assertEquals("", board.getCell(rowToW2, colToW2), 1);
		assertEquals("", board.getTurn(), "White");
		
		//board.printBoard();

	}

	// 6.3
	@Test
	public void testInvalidOutOfBoundsCellWhitePieceMove() {

		//First Move for black piece
		int rowFrom = 5;
		int colFrom = 4;
		int rowTo = 4;
		int colTo = 5;

		assertEquals("", board.getCell(rowFrom, colFrom), 1);
		assertEquals("", board.getCell(rowTo, colTo), 0);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFrom, colFrom, rowTo, colTo, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFrom, colFrom), 0);
		assertEquals("", board.getCell(rowTo, colTo), 1);
		assertEquals("", board.getTurn(), "White");

		//Second move for white piece
		int rowFromW = 2;
		int colFromW = 1;
		int rowToW = 3;
		int colToW = 0;
		
		assertEquals("", board.getCell(rowFromW, colFromW), 2);
		assertEquals("", board.getCell(rowToW, colToW), 0);
		assertEquals("", board.getTurn(), "White");
		
		board.makeMove(rowFromW, colFromW, rowToW, colToW, board.getTurn());
		board.ChangeTurn();

		assertEquals("", board.getCell(rowFromW, colFromW), 0);
		assertEquals("", board.getCell(rowToW, colToW), 2);
		assertEquals("", board.getTurn(), "Black");

		//black piece's turn
		int rowFromB = 4;
		int colFromB = 5;
		int rowToB = 3;
		int colToB = 4;
		
		assertEquals("", board.getCell(rowFromB, colFromB), 1);
		assertEquals("", board.getCell(rowToB, colToB), 0);
		assertEquals("", board.getTurn(), "Black");
		
		board.makeMove(rowFromB, colFromB, rowToB, colToB, board.getTurn());
		board.ChangeTurn();
		
		assertEquals("", board.getCell(rowFromB, colFromB), 0);
		assertEquals("", board.getCell(rowToB, colToB), 1);
		assertEquals("", board.getTurn(), "White");
		
		//Attempt to move and place a White piece out of bounds
		int rowFromW2 = 3;
		int colFromW2 = 0;
		int rowToW2 = 4;
		int colToW2 = -1; //out of bound column
		
		assertEquals("", board.getCell(rowFromW2, colFromW2), 2);
		assertEquals("", board.getCell(rowToW2, colToW2), -1);
		assertEquals("", board.getTurn(), "White");
		
		board.makeMove(rowFromW2, colFromW2, rowToW2, colToW2, board.getTurn());
		
		assertEquals("", board.getCell(rowFromW2, colFromW2), 2);
		assertEquals("", board.getCell(rowToW2, colToW2), -1);
		assertEquals("", board.getTurn(), "White");
	
	}

	// 12.1
	@Test
	public void testBlackPieceWin() {
		
		board.whitePieces = 0;
		board.blackPieces = 1;
		
		board.CheckGameState();
		
		assertEquals("", board.getGameState(), GameState.BLACK_WON);
		
	}

	// 12.2
	@Test
	public void testWhitePieceWin() {

		board.ChangeTurn();
		board.whitePieces = 1;
		board.blackPieces = 0;
		
		board.CheckGameState();
		
		assertEquals("", board.getGameState(), GameState.WHITE_WON);
	}

}
