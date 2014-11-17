import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter size of array for the search object: ");
		int userInput = scan.nextInt();
		SearchObject searcher = new SearchObject(userInput);
		while(true) {
			System.out.print("Type 0 to binary search, 1 to bruteforce, 2 to print data, and 3 to exit: ");
			int input = scan.nextInt();
			if(input == 0) {
				System.out.print("Type values to search for: ");
				int target = scan.nextInt();
				System.out.println("Index is " + searcher.bruteForceSearch(target));
			}
			else if(input == 1) {
				System.out.print("Type values to search for: ");
				int target = scan.nextInt();
				System.out.println("Index is " + searcher.binarySearch(target));
			}
			else if(input == 2) {
				searcher.printData();
			}
			else if(input == 3) {
				break;
			}
			else {
				System.out.println("Try again");
				continue;
			}
		}
		scan.close();
	}

}
