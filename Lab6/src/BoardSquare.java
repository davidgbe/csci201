import javax.swing.JButton;

public class BoardSquare extends JButton {
	
	private int row;
	private int col;
	
	public BoardSquare(int r, int c) {
		super("");
		this.row = r;
		this.col = c;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}

}
