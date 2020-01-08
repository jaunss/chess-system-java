package boardgame;

public class Position {

	private int linha, coluna;

	/* Construtor com argumentos */
	public Position(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	/* Getters e Setters */
	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	public void setValues(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	/* toString */
	@Override
	public String toString() {
		return linha + ", " + coluna;
	}
}