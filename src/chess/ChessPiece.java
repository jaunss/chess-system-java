package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private Color cor;
	
	public ChessPiece(Board tabuleiro, Color cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	public Color getCor() {
		return cor;
	}
	
	protected boolean isThereOpponentPiece(Position posicao) {
		ChessPiece p = (ChessPiece) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
}