package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board tabuleiro, Color cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] possibleMoves() {
		return null;
	}
	
}