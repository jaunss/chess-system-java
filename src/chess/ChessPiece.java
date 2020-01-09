package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private Color cor;
	private int moveCount;
	
	public ChessPiece(Board tabuleiro, Color cor, int moveCount) {
		super(tabuleiro);
		this.cor = cor;
		this.moveCount = moveCount;
	}
	
	public Color getCor() {
		return cor;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(posicao);
	}
	
	protected boolean isThereOpponentPiece(Position posicao) {
		ChessPiece p = (ChessPiece) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
	protected void increaseMoveCount() {
		
	}
	
	protected void decreaseMoveCount() {
		
	}
}