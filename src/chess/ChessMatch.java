package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board tabuleiro;

	public ChessMatch() {
		tabuleiro = new Board(8, 8);
		initialSetup();
	}

	public ChessPiece[][] getPecas() {
		ChessPiece[][] mat = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunhas()];

		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunhas(); j++) {
				mat[i][j] = (ChessPiece) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public ChessPiece performChessMove(ChessPosition posicaoOrigem, ChessPosition posicaoDestino) {
		Position source = posicaoOrigem.toPosition();
		Position target = posicaoDestino.toPosition();
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = tabuleiro.removePiece(source);
		Piece capturedPiece = tabuleiro.removePiece(target);
		tabuleiro.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position posicao) {
		if (!tabuleiro.thereIsAPiece(posicao)) {
			throw new ChessException("Não existe peça na posição de origem.");
		}
		if (!tabuleiro.peca(posicao).isThereAnyPossibleMove()) {
			throw new ChessException("Não existe movimentos possíveis para a peça escolhida.");
		}
	}

	private void placeNewPiece(char coluna, int linha, ChessPiece peca) {
		tabuleiro.placePiece(peca, new ChessPosition(coluna, linha).toPosition());
	}

	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(tabuleiro, Color.branco));
		placeNewPiece('c', 2, new Rook(tabuleiro, Color.branco));
		placeNewPiece('d', 2, new Rook(tabuleiro, Color.branco));
		placeNewPiece('e', 2, new Rook(tabuleiro, Color.branco));
		placeNewPiece('e', 1, new Rook(tabuleiro, Color.branco));
		placeNewPiece('d', 1, new King(tabuleiro, Color.branco));

		placeNewPiece('c', 7, new Rook(tabuleiro, Color.preto));
		placeNewPiece('c', 8, new Rook(tabuleiro, Color.preto));
		placeNewPiece('d', 7, new Rook(tabuleiro, Color.preto));
		placeNewPiece('e', 7, new Rook(tabuleiro, Color.preto));
		placeNewPiece('e', 8, new Rook(tabuleiro, Color.preto));
		placeNewPiece('d', 8, new King(tabuleiro, Color.preto));
	}
}