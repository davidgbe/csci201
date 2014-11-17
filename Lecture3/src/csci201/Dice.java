package csci201;

import java.util.Scanner;

public class Dice {
	
	private int numberOfRolls;
	private int numOccurrences[];
	public static final int NUM_FACES = 6;
	
	public Dice(int numberOfRolls) {
		this.numberOfRolls = numberOfRolls;
		this.numOccurrences = new int[NUM_FACES];
	}
	
	public void roll() {
		for(int i = 0; i < this.numberOfRolls; i++) {
			int num = (int)(Math.random() * Dice.NUM_FACES);
			this.numOccurrences[num]++;
		}
	}
	
	public void printStatistics() {
		for(int i = 0; i < Dice.NUM_FACES; i++) {
			System.out.print("The number " + (i+1) + " was rolled ");
			System.out.println(this.numOccurrences[i] + " times.");
		}
	}

	private static int getNumRolls() {
		System.out.print("How many rolls? ");
		Scanner scan = new Scanner(System.in);
		int numRolls = scan.nextInt();
		return numRolls;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numRolls = getNumRolls();
		Dice d = new Dice(numRolls);
		d.roll();
		d.printStatistics();
	}

}
