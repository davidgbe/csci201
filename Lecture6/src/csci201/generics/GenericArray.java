package csci201.generics;

import java.util.ArrayList;
import java.util.Scanner;

public class GenericArray {

	public GenericArray() {
		
	}
	
	public ArrayList<Integer> getIntegerArray() {
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++) {
			arrList.add((int) (Math.random() * 100));
		}
		return arrList;
	}
	
	public ArrayList<Double> getDoubleArray() {
		ArrayList<Double> arrList = new ArrayList<Double>();
		for(int i = 0; i < 10; i++) {
			arrList.add((double) (Math.random() * 100));
		}
		return arrList;
	}
	
	public static void main() {
		Scanner scan = new Scanner(System.in);
		System.out.println("(i) Integer");
		System.out.println("(d) Double");
		System.out.print("Which type of array would you like to create?");
		String arrayType = scan.nextLine();
		GenericArray ga = new GenericArray();
		ArrayList<Integer> intArrayList = ga.getIntegerArray();
	}
}
