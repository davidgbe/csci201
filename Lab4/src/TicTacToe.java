import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class TicTacToe {
	private String[][] board = new String[3][3];
	private String[] markers = {"x", "o"};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter a file name to use as input: ");
		String inputFileName = scan.nextLine();
		TicTacToe ttt = new TicTacToe();
		BufferedReader br = ttt.parseInputFile(inputFileName);
		boolean done = false;
		try {
			String line = br.readLine();
			int player = 0;
			while(line != null) {
				System.out.println("interation");
				String inputArr[] = line.split(",");
				int x = Integer.parseInt(inputArr[0]);
				int y = Integer.parseInt(inputArr[1]);
				try {
					if (!ttt.makeMove(x, y, player)) {
						done = true;
						break;
					}
				}
				catch(Exception e) {
					System.out.println("error within here");
				}
				if( ttt.checkIfWon(player) ) {
					if(player == 1) {
						System.out.print("done1");
						ttt.createOutput("Player 2");
					}
					else {
						System.out.print("done2");
						ttt.createOutput("Player 1");
					}
					done = true;
					break;
				}
				line = br.readLine();
				if(player == 1) {
					player--;
				}
				else { player++; };
			}
			if(!done) {
				System.out.print("done3");
				ttt.createOutput("Neither");
			}
		}
		catch(Exception e) {
			System.out.print("There was an error");
			System.out.println(e.getMessage());
		}
	}
	
	public BufferedReader parseInputFile(String inputFileName) {
		try {
			FileReader fr = new FileReader(inputFileName);
			BufferedReader br = new BufferedReader(fr);
			for(int x = 0; x < 3; x++) {
				for(int y = 0; y < 3; y++) {
					this.board[x][y] = "";
				}
			}
			return br;
		}
		catch (FileNotFoundException e) {
			System.out.println("Invalid file name: please try again");
			return null;
		}
	}
	
	
	public boolean makeMove(int x, int y, int player) {
		if((x < 0 || x >= 3) || ((y < 0) || y >= 3)) {
			System.out.print("OUT OF BOUNDS");
			try {
				System.out.print("done");
				createOutput("Out of Bounds");
			}
			catch (Exception e) {
				System.out.println("out of bounds error on 88");
				return false;
			}
			return false;
		}
		if(this.board[x][y] == "x" || this.board[x][y] == "o") {
			try {
				createOutput("Repeated move");
			}
			catch (Exception e) {
				System.out.println("repeated move error 96");
			}
			return false;
		}
		
		this.board[x][y] = markers[player];
		return true;
	}
	
	public boolean checkIfWon(int player) {
		boolean won = false;
		for(int x = 0; x < 3; x++){
			if(this.board[x][0] == this.board[x][1] && this.board[x][1] == this.board[x][2] && this.board[x][2] == this.markers[player]) {
				won = true;
			}
		}
		for(int y = 0; y < 3; y++){
			if(this.board[0][y] == this.board[1][y] && this.board[1][y] == this.board[2][y] && this.board[2][y] == this.markers[player]) {
				won = true;
			}
		}
		if(this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2] && this.board[2][2] == this.markers[player]) {
			won = true;
		}
		if(this.board[0][2] == this.board[1][1] && this.board[1][1] == this.board[2][0] && this.board[2][0] == this.markers[player]) {
			won = true;
		}
		return won;
	}
	
	public void createOutput(String result) {
		FileWriter fw;
		try {
			fw = new FileWriter("output.txt");
		}
		catch(Exception e) {
			System.out.print("this is the error");
			return;
		}
		PrintWriter pw = new PrintWriter(fw);
		for(int y = 0; y < 3; y++) {
			String line = "";
			for(int x = 0; x < 3; x++) {
				if(this.board[x][y] == "x" || this.board[x][y] == "o") {
					line += this.board[x][y];
				}
				else {
					line += "_";
				}
			}
			pw.println(line);
			System.out.println(line);
		}
		pw.println(result);
		pw.close();
	}
	
	

}
