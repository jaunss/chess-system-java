package boardgame;

public abstract class Piece {

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
	
	public abstract boolean[][] PossibleMoves ();
	
	public boolean possibleMove(Position posicao) {
		return PossibleMoves()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = PossibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}