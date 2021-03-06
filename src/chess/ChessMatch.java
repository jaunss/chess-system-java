package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private boolean check, checkMate;

	private Board tabuleiro;
	private ChessPiece enPassantVulnerable, promoted;

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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
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

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("Voc� n�o pode se colocar em check.");
		}

		ChessPiece movedPiece = (ChessPiece) tabuleiro.peca(target);
		
		/* Movimento Especial Promoted */
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getCor() == Color.branco && target.getLinha() == 0) || (movedPiece.getCor() == Color.preto && target.getLinha() == 7)) {
				promoted = (ChessPiece) tabuleiro.peca(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testeCheckMate(currentPlayer)) {
			checkMate = true;
		} else {
			nextTurn();
		}

		/* Movimento Especial EnPassant */
		if (movedPiece instanceof Pawn
				&& (target.getColuna() == source.getLinha() - 2 || target.getLinha() == source.getLinha() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("N�o h� pe�a para ser promovida.");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			throw new InvalidParameterException("Tipo inv�lido para promo��o.");
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = tabuleiro.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getCor());
		tabuleiro.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color cor) {
		if (type.equals("B")) return new Bishop(tabuleiro, cor);
		if (type.equals("N")) return new Knight(tabuleiro, cor);
		if (type.equals("Q")) return new Queen(tabuleiro, cor);
		return new Rook(tabuleiro, cor);
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece piece = (ChessPiece) tabuleiro.removePiece(source);
		piece.increaseMoveCount();

		Piece p = tabuleiro.removePiece(source);
		Piece capturedPiece = tabuleiro.removePiece(target);
		tabuleiro.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		/* Movimento Especial Roque Pequeno */
		if (p instanceof King && target.getColuna() == source.getColuna() + 2) {
			Position sourceT = new Position(source.getLinha(), source.getColuna() + 3);
			Position targetT = new Position(target.getLinha(), target.getLinha() + 1);

			ChessPiece rook = (ChessPiece) tabuleiro.removePiece(sourceT);
			tabuleiro.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		/* Movimento Especial Roque Grande */
		if (p instanceof King && target.getColuna() == source.getColuna() - 2) {
			Position sourceT = new Position(source.getLinha(), source.getColuna() - 4);
			Position targetT = new Position(target.getLinha(), target.getLinha() - 1);

			ChessPiece rook = (ChessPiece) tabuleiro.removePiece(sourceT);
			tabuleiro.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		/* Movimento Especial EnPassant */
		if (p instanceof Pawn) {
			if (source.getColuna() != target.getColuna() && capturedPiece == null) {
				Position pawnPosition;
				if (((Pawn) p).getCor() == Color.branco) {
					pawnPosition = new Position(target.getLinha() + 1, target.getColuna());
				} else {
					pawnPosition = new Position(target.getLinha() - 1, target.getColuna());
				}
				capturedPiece = tabuleiro.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece piece = (ChessPiece) tabuleiro.removePiece(target);
		piece.decreaseMoveCount();

		Piece p = tabuleiro.removePiece(target);
		tabuleiro.placePiece(p, source);

		if (capturedPiece != null) {
			tabuleiro.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		/* Movimento Especial Roque Pequeno */
		if (p instanceof King && target.getColuna() == source.getColuna() + 2) {
			Position sourceT = new Position(source.getLinha(), source.getColuna() + 3);
			Position targetT = new Position(target.getLinha(), target.getLinha() + 1);

			ChessPiece rook = (ChessPiece) tabuleiro.removePiece(targetT);
			tabuleiro.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		/* Movimento Especial Roque Grande */
		if (p instanceof King && target.getColuna() == source.getColuna() - 2) {
			Position sourceT = new Position(source.getLinha(), source.getColuna() - 4);
			Position targetT = new Position(target.getLinha(), target.getLinha() - 1);

			ChessPiece rook = (ChessPiece) tabuleiro.removePiece(targetT);
			tabuleiro.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		/* Movimento Especial EnPassant */
		if (p instanceof Pawn) {
			if (source.getColuna() != target.getColuna() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece) tabuleiro.removePiece(target);
				
				Position pawnPosition;
				if (((Pawn) p).getCor() == Color.branco) {
					pawnPosition = new Position(3, target.getColuna());
				} else {
					pawnPosition = new Position(4, target.getColuna());
				}
				tabuleiro.placePiece(pawn, pawnPosition);
			}
		}
	}

	private void validateSourcePosition(Position posicao) {
		if (!tabuleiro.thereIsAPiece(posicao)) {
			throw new ChessException("N�o existe pe�a na posi��o de origem.");
		}
		if (currentPlayer != ((ChessPiece) tabuleiro.peca(posicao)).getCor()) {
			throw new ChessException("A pe�a escolhida n�o � sua.");
		}
		if (!tabuleiro.peca(posicao).isThereAnyPossibleMove()) {
			throw new ChessException("N�o existe movimentos poss�veis para a pe�a escolhida.");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!tabuleiro.peca(source).possibleMove(target)) {
			throw new ChessException("A pe�a escolhida n�o pode se mover para a posi��o de destino.");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.branco) ? Color.preto : Color.branco;
	}

	private Color opponent(Color cor) {
		return (cor == Color.branco) ? Color.preto : Color.branco;
	}

	private ChessPiece king(Color cor) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getCor() == cor)
				.collect(Collectors.toList());

		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("N�o existe o rei da cor " + cor + " no tabuleiro.");
	}

	private boolean testCheck(Color cor) {
		Position kingPosition = king(cor).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getCor() == opponent(cor))
				.collect(Collectors.toList());

		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();

			if (mat[kingPosition.getLinha()][kingPosition.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Color cor) {
		if (!testCheck(cor)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getCor() == cor)
				.collect(Collectors.toList());

		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunhas(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(cor);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char coluna, int linha, ChessPiece peca) {
		tabuleiro.placePiece(peca, new ChessPosition(coluna, linha).toPosition());
		piecesOnTheBoard.add(peca);
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(tabuleiro, Color.branco));
		placeNewPiece('b', 1, new Knight(tabuleiro, Color.branco));
		placeNewPiece('c', 1, new Bishop(tabuleiro, Color.branco));
		placeNewPiece('d', 1, new Queen(tabuleiro, Color.branco));
		placeNewPiece('e', 1, new King(tabuleiro, Color.branco, this));
		placeNewPiece('f', 1, new Bishop(tabuleiro, Color.branco));
		placeNewPiece('g', 1, new Knight(tabuleiro, Color.branco));
		placeNewPiece('h', 1, new Rook(tabuleiro, Color.branco));
		placeNewPiece('a', 2, new Pawn(tabuleiro, Color.branco, this));
		placeNewPiece('b', 2, new Pawn(tabuleiro, Color.branco, this));
		placeNewPiece('c', 2, new Pawn(tabuleiro, Color.branco, this));
		placeNewPiece('d', 2, new Pawn(tabuleiro, Color.branco, this));
		placeNewPiece('e', 2, new Pawn(tabuleiro, Color.branco, this));
		placeNewPiece('f', 2, new Pawn(tabuleiro, Color.branco, this));
		placeNewPiece('g', 2, new Pawn(tabuleiro, Color.branco, this));
		placeNewPiece('h', 2, new Pawn(tabuleiro, Color.branco, this));

		placeNewPiece('a', 8, new Rook(tabuleiro, Color.preto));
		placeNewPiece('b', 8, new Knight(tabuleiro, Color.preto));
		placeNewPiece('c', 8, new Bishop(tabuleiro, Color.preto));
		placeNewPiece('d', 8, new Queen(tabuleiro, Color.preto));
		placeNewPiece('e', 8, new King(tabuleiro, Color.preto, this));
		placeNewPiece('f', 8, new Bishop(tabuleiro, Color.preto));
		placeNewPiece('g', 8, new Knight(tabuleiro, Color.preto));
		placeNewPiece('h', 8, new Rook(tabuleiro, Color.preto));
		placeNewPiece('a', 7, new Pawn(tabuleiro, Color.preto, this));
		placeNewPiece('b', 7, new Pawn(tabuleiro, Color.preto, this));
		placeNewPiece('c', 7, new Pawn(tabuleiro, Color.preto, this));
		placeNewPiece('d', 7, new Pawn(tabuleiro, Color.preto, this));
		placeNewPiece('e', 7, new Pawn(tabuleiro, Color.preto, this));
		placeNewPiece('f', 7, new Pawn(tabuleiro, Color.preto, this));
		placeNewPiece('g', 7, new Pawn(tabuleiro, Color.preto, this));
		placeNewPiece('h', 7, new Pawn(tabuleiro, Color.preto, this));
	}
}