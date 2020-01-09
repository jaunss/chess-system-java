package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board tabuleiro, Color cor, int moveCount) {
		super(tabuleiro, cor, moveCount);
		
	}

	@Override
	public boolean[][] possibleMoves() {
		return null;
	}
	
}