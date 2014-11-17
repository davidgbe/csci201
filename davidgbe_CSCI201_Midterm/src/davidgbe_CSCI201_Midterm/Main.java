package davidgbe_CSCI201_Midterm;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean invalidInput = true;
		while(invalidInput) {
			System.out.println("Image Editor [image]");
			System.out.println("Telephone [phone]");
			System.out.println("What application would you like to run?");
			String entry = in.nextLine();
			ParentGUI gui;
			if(entry.equals("image")) {
				invalidInput = false;
				gui = new ImageGUI("CSCI 201 Midter - Image Editor");
			} else if(entry.equals("phone")) {
				invalidInput = false;
				gui = new TelephoneGUI("CSCI 201 Midter - Telephone");
			}
		}
	}

}
