package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board tabuleiro, Color cor) {
		super(tabuleiro, cor);

	}
	
	private boolean canMove(Position posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().peca(posicao);
		return p == null && p.getCor() != getCor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunhas()];

		Position p = new Position(0, 0);

		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if (getTabuleiro().positionExists(p) && canMove(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		return null;
	}

	@Override
	public String toString() {
		return "C";
	}
}