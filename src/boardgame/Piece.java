package boardgame;

public class Piece {

	/*
	 * Essa posi��o n�o � uma posi��o do Xadrez, � uma posi��o simples de matriz por
	 * esse motivo que colocamos como "protected"
	 */
	protected Position posicao;

	/* Composi��o - tem um */
	private Board tabuleiro;

	/* Construtor com argumentos */
	public Piece(Board tabuleiro) {
		this.tabuleiro = tabuleiro;
		/*
		 * Pra dizer que a posi��o dessa pe�a n�o foi colocada no tabuleiro ainda por�m
		 * n�o � obrigat�rio colocar pois o Java por padr�o j� coloca o valor null
		 */
		posicao = null;
	}

	/* Ser� acessado somente dentro do pacote boardgame */
	protected Board getTabuleiro() {
		return tabuleiro;
	}

}