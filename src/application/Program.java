package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch partida = new ChessMatch();

		List<ChessPiece> capturado = new ArrayList<ChessPiece>();

		while (!partida.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(partida, capturado);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				boolean[][] possibleMoves = partida.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(partida.getPecas(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = partida.performChessMove(source, target);

				if (capturedPiece != null) {
					capturado.add(capturedPiece);
				}
				if (partida.getPromoted() != null) {
					System.out.print("Insira a peça promovida (B/N/R/Q): ");
					String type = sc.nextLine();
					partida.replacePromotedPiece(type);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(partida, capturado);
	}
}