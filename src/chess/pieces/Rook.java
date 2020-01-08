package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board tabuleiro, Color cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] PossibleMoves() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunhas()];
		return mat;
	}
}