package sprint1_prod;

import javafx.scene.Node;

public class Piece {

	private Node node;
	private int row;
	private int col;
	private int[] possibleMoves;
	
	public Piece(Node node, int row, int col, int[] possibleMoves) {
		super();
		this.node = node;
		this.row = row;
		this.col = col;
		this.possibleMoves = possibleMoves;
	}

	public Node getNode() {
		return node;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public int[] getPossibleMoves() {
		return possibleMoves;
	}

}
