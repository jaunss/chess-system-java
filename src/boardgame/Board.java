package boardgame;


public class Board {
	
	private int linhas, colunas;
	private Piece[][] pecas;
	
	/* Composi��o - tem v�rios */
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
}