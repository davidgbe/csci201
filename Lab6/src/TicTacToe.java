import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class TicTacToe extends JFrame {
	
	private int currPlayer = 1;
	private String[] markers = {"X", "O"};
	private JLabel header;
	private ArrayList<BoardSquare> squares = new ArrayList<BoardSquare>();
	
	String[][] board = new String[3][3];
	
	public TicTacToe() {
		super("Tic-Tac-Toe");
		setSize(400, 500);
		add(createApp());
		setVisible(true);
	}

	public static void main(String[] args) {
		new TicTacToe();
	}
	
	private JPanel createApp() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		header = new JLabel("Current Player: 1");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPanel board = createBoard();
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				refresh();
			}
		});
		
		mainPanel.add(header, BorderLayout.NORTH);
		mainPanel.add(board, BorderLayout.CENTER);
		mainPanel.add(refresh, BorderLayout.SOUTH);
		
		return mainPanel;
	}
	
	private JPanel createBoard() {
		JPanel board = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				gbc.gridx = c;
				gbc.gridy = r;
				BoardSquare square = new BoardSquare(r, c);
				squares.add(square);
				square.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						square.setText(markers[currPlayer - 1]);
						square.setEnabled(false);
						squareClicked(square.getRow(), square.getCol());
					}
				});
				board.add(square, gbc);
			}
		}
		return board;
	}
	
	private void squareClicked(int r, int c) {
		this.board[r][c] = this.markers[currPlayer - 1];
		if(checkIfWon(this.currPlayer)) {
			header.setText("Player " + this.currPlayer + " wins!");
			endGame();
		}
		else {
			if(this.currPlayer == 1) {
				this.currPlayer++;
			}
			else {
				this.currPlayer--;
			}
			header.setText("Current Player: " + this.currPlayer);
		}
	}
	
	private void refresh() {
		for(int i = 0; i < squares.size(); i++) {
			squares.get(i).setEnabled(true);
			squares.get(i).setText("");
			for(int r = 0; r < 3; r++) {
				for(int c = 0; c < 3; c++) {
					this.board[r][c] = null;
				}
			}
		}
		header.setText("Current Player: 1");
	}
	
	private boolean checkIfWon(int player) {
		boolean won = false;
		for(int x = 0; x < 3; x++){
			if(this.board[x][0] == this.board[x][1] && this.board[x][1] == this.board[x][2] && this.board[x][2] == this.markers[player - 1]) {
				won = true;
			}
		}
		for(int y = 0; y < 3; y++){
			if(this.board[0][y] == this.board[1][y] && this.board[1][y] == this.board[2][y] && this.board[2][y] == this.markers[player - 1]) {
				won = true;
			}
		}
		if(this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2] && this.board[2][2] == this.markers[player - 1]) {
			won = true;
		}
		if(this.board[0][2] == this.board[1][1] && this.board[1][1] == this.board[2][0] && this.board[2][0] == this.markers[player - 1]) {
			won = true;
		}
		return won;
	}
	
	private void endGame() {
		for(int i = 0; i < squares.size(); i++) {
			squares.get(i).setEnabled(false);
		}
	}
}
