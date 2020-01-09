package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board tabuleiro, Color cor, int moveCount) {
		super(tabuleiro, cor, moveCount);
	}

	@Override
	public boolean[][] possibleMoves() {
		return null;
	}
	
}