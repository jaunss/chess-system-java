package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board tabuleiro, Color cor, ChessMatch chessMatch) {
		super(tabuleiro, cor);
		this.chessMatch = chessMatch;
	}
	
	private boolean testRookCastling(Position posicao) {
		ChessPiece p = (ChessPiece) getTabuleiro().peca(posicao);
		
		return p != null && p instanceof Rook && p.getCor() == getCor() && p.getMoveCount() == 0;
	}
	
	private boolean canMove(Position posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().peca(posicao);
		return p == null && p.getCor() != getCor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunhas()];
		
		Position p = new Position(0, 0);
		
		/* Acima */
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Abaixo */
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Esquerda */
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Direita */
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Noroeste */
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Nordeste */
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Sudoeste */
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Sudeste */
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Movimento Especial Roque */
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			
			/* Movimento Especial Roque Pequeno */
			Position pos1 = new Position(posicao.getLinha(), posicao.getColuna() + 3);
			if (testRookCastling(pos1)) {
				Position p1 = new Position(posicao.getLinha(), posicao.getColuna() + 1);
				Position p2 = new Position(posicao.getLinha(), posicao.getColuna() + 2);
				
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			
			/* Movimento Especial Roque Grande */
			Position pos2 = new Position(posicao.getLinha(), posicao.getColuna() - 4);
			if (testRookCastling(pos2)) {
				Position p1 = new Position(posicao.getLinha(), posicao.getColuna() - 1);
				Position p2 = new Position(posicao.getLinha(), posicao.getColuna() - 2);
				Position p3 = new Position(posicao.getLinha(), posicao.getColuna() - 3);
				
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "K";
	}
}