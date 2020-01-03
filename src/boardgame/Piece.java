package boardgame;

public class Piece {

	/*
	 * Essa posição não é uma posição do Xadrez, é uma posição simples de matriz por
	 * esse motivo que colocamos como "protected"
	 */
	protected Position posicao;

	/* Composição - tem um */
	private Board tabuleiro;

	/* Construtor com argumentos */
	public Piece(Board tabuleiro) {
		this.tabuleiro = tabuleiro;
		/*
		 * Pra dizer que a posição dessa peça não foi colocada no tabuleiro ainda porém
		 * não é obrigatório colocar pois o Java por padrão já coloca o valor null
		 */
		posicao = null;
	}

	/* Será acessado somente dentro do pacote boardgame */
	protected Board getTabuleiro() {
		return tabuleiro;
	}

}