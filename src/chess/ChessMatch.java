package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board tabuleiro;
	
	public ChessMatch() {
		tabuleiro = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPecas(){
		ChessPiece[][] mat = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunhas()];
		
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunhas(); j++) {
				mat[i][j] = (ChessPiece) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void placeNewPiece(char coluna, int linha, ChessPiece peca) {
		tabuleiro.placePiece(peca, new ChessPosition(coluna, linha).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(tabuleiro, Color.branco));
		placeNewPiece('e', 8, new King(tabuleiro, Color.preto));
		placeNewPiece('e', 1, new King(tabuleiro, Color.branco));
	}
}