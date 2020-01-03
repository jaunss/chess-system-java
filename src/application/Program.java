package application;

import boardgame.Board;
import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		
		ChessMatch partida = new ChessMatch();
		
		UI.printBoard(partida.getPecas());
	}
}