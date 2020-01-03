package boardgame;

public class Board {

	private int linhas, colunas;
	private Piece[][] pecas;

	/* Composição - tem vários */
//	private List<Piece> list = new ArrayList<Piece>();

	public Board(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Piece[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunhas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	public Piece peca(int linhas, int colunas) {
		return pecas[linhas][colunas];
	}
	
	public Piece peca(Position posicao) {
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void placePiece(Piece peca, Position posicao) {
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
}