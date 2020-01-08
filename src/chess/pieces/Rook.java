package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunhas()];

		Position p = new Position(0, 0);

		/* Acima */
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getLinha() - 1][p.getColuna()] = true;
		}

		/* Esquerda */
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Direita */
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		/* Abaixo */
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		return mat;
	}
}