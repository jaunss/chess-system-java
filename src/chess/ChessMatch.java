package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	
	private Board tabuleiro;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<Piece>();
	private List<Piece> capturedPieces = new ArrayList<Piece>();
	
	public ChessMatch() {
		tabuleiro = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.branco;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position posicao = sourcePosition.toPosition();
		validateSourcePosition(posicao);
		return tabuleiro.peca(posicao).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition posicaoOrigem, ChessPosition posicaoDestino) {
		Position source = posicaoOrigem.toPosition();
		Position target = posicaoDestino.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = tabuleiro.removePiece(source);
		Piece capturedPiece = tabuleiro.removePiece(target);
		tabuleiro.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position posicao) {
		if (!tabuleiro.thereIsAPiece(posicao)) {
			throw new ChessException("Não existe peça na posição de origem.");
		}
		if (currentPlayer != ((ChessPiece) tabuleiro.peca(posicao)).getCor()) {
			throw new ChessException("A peça escolhida não é sua.");
		}
		if (!tabuleiro.peca(posicao).isThereAnyPossibleMove()) {
			throw new ChessException("Não existe movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!tabuleiro.peca(source).possibleMove(target)) {
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino.");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.branco) ? Color.preto : Color.branco;
	}

	private void placeNewPiece(char coluna, int linha, ChessPiece peca) {
		tabuleiro.placePiece(peca, new ChessPosition(coluna, linha).toPosition());
		piecesOnTheBoard.add(peca);
	}

	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(tabuleiro, Color.branco, turn));
		placeNewPiece('c', 2, new Rook(tabuleiro, Color.branco, turn));
		placeNewPiece('d', 2, new Rook(tabuleiro, Color.branco, turn));
		placeNewPiece('e', 2, new Rook(tabuleiro, Color.branco, turn));
		placeNewPiece('e', 1, new Rook(tabuleiro, Color.branco, turn));
		placeNewPiece('d', 1, new King(tabuleiro, Color.branco, turn));

		placeNewPiece('c', 7, new Rook(tabuleiro, Color.preto, turn));
		placeNewPiece('c', 8, new Rook(tabuleiro, Color.preto, turn));
		placeNewPiece('d', 7, new Rook(tabuleiro, Color.preto, turn));
		placeNewPiece('e', 7, new Rook(tabuleiro, Color.preto, turn));
		placeNewPiece('e', 8, new Rook(tabuleiro, Color.preto, turn));
		placeNewPiece('d', 8, new King(tabuleiro, Color.preto, turn));
	}
}