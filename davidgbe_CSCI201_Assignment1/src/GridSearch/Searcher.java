package GridSearch;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Searcher {
	
	private int rows;
	private int cols;
	private boolean admin;
	private char[][] grid;
	
	public Searcher(int rows, int cols, String content, boolean admin) {
		this.rows = rows;
		this.cols = cols;
		this.admin = admin;
		this.grid = new char[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				this.grid[i][j] = content.charAt(cols*i + j);
				if(admin) {
					System.out.print(content.charAt(cols*i+j) + " ");
				}
			}
			if(admin) {
				System.out.println();
			}
		}
	}
	 
	public String search() {
		int row = 0;
		boolean up = true;
		String result = "";
		for(int col = 0; col < cols; col++) {
			result += this.grid[row][col];
			if(this.admin) {
				System.out.println("R: " + row + " C: " + col + " L: " + this.grid[row][col]);
			}
			if(up) { row++; }
			else { row--; }
			if(row == 0) { up = true; }
			if(row == this.rows - 1) { up = false; }
		}
		return result;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to the Maze Game!");
		System.out.println("Are you in an admin?");
		String answer = scan.next();
		boolean admin = false;
		if(answer != null && answer.charAt(0) == 'A') {
			admin = true;
		}
		scan.nextLine();
		int rows;
		int cols;
		while(true) {
			System.out.println("How many rows are in the grid?");
			try {
				rows = scan.nextInt();
				break;
			}
			catch(NoSuchElementException|IllegalStateException e) {
				System.out.println("Input must be an int");
				scan.nextLine();
				continue;
			}
		}
		while(true) {
			System.out.println("How many columns are in the grid?");
			try {
				cols = scan.nextInt();
				break;
			}
			catch(NoSuchElementException|IllegalStateException e) {
				System.out.println("Input must be an int");
				scan.nextLine();
				continue;
			}
		}
		scan.nextLine();
		String letters;
		while(true) {
			System.out.println("Enter " + rows * cols + " letters separated by spaces: ");
			letters = scan.nextLine();
			System.out.println(letters);
			letters = letters.replaceAll("\\s+", "");
			if(letters.length() == rows*cols) {
				break;
			}
			else {
				System.out.println("Error: That is not " + rows * cols + " letters. Try again!");
			}
		}
		if(admin) {
			System.out.println("Here is the grid just for you admin!");
		}
		Searcher se = new Searcher(rows, cols, letters, admin);
		System.out.println("Here is the step by step, just for you admin!");
		String result = se.search();
		if(result != null) {
			System.out.println("Result: " + result);
		}
		else {
			System.out.println("Unable to find a result");
		}
		System.out.println("Thanks for using the program!");
	}
	
}
