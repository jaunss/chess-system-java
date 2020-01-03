package boardgame;

public class Board {

	private int linhas, colunas;
	private Piece[][] pecas;

	/* Composição - tem vários */
//	private List<Piece> list = new ArrayList<Piece>();

	public Board(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException("Erro ao criar o tabuleiro: É necessário que haja pelo menos 1 linha e 1 coluna.");
		}
		
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Piece[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunhas() {
		return colunas;
	}
	
	public Piece peca(int linhas, int colunas) {
		if (!positionExists(linhas, colunas)) {
			throw new BoardException("Posição fora do tabuleiro.");
		}
		
		return pecas[linhas][colunas];
	}
	
	public Piece peca(Position posicao) {
		if (!positionExists(posicao)) {
			throw new BoardException("Posição fora do tabuleiro.");
		}
		
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void placePiece(Piece peca, Position posicao) {
		if (thereIsAPiece(posicao)) {
			throw new BoardException("Já existe uma peça nessa posição " + posicao);
		}
		
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean positionExists(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean positionExists(Position posicao) {
		return positionExists(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean thereIsAPiece(Position posicao) {
		if (!positionExists(posicao)) {
			throw new BoardException("Posição fora do tabuleiro.");
		}
		
		return peca(posicao) != null;
	}
}