import java.util.Random;


public class SearchObject {
	
	private int[] localArray;
	private int bruteForceTrials = 0;
	private int binarySearchTrials = 0;
	private int sessionBruteForceTrials;
	private int sessionBinarySearchTrials;
	private int bruteSessions = 0;
	private int binarySessions = 0;
	
	public SearchObject(int n) {
		localArray = new int[n];;
		for(int i = 0; i < n; i++) {
			localArray[i] = i + 1;
		}
		for(int i = 0; i < n; i++) {
			System.out.print(localArray[i]);
			if(i != n - 1) {
				System.out.print(", ");
			}
		}
		System.out.println("");
	}
	
	public int bruteForceSearch(int target) {
		bruteSessions++;
		sessionBruteForceTrials = 0;
		int index = -1;
		for(int i = 0; i < localArray.length; i++) {
			sessionBruteForceTrials++;
			if(localArray[i] == target) {
				index = i;
				break;
			}
		}
		bruteForceTrials += sessionBruteForceTrials;
		return index;
	}
	
	public int binarySearch(int target) {
		binarySessions++;
		sessionBinarySearchTrials = 0;
		return binarySearchRec(0, localArray.length, target);
	}
	
	private int binarySearchRec(int start, int end, int target) {
		sessionBinarySearchTrials++;
		int searchPos = (int)((start + end)/2);
		if( localArray[searchPos] == target ) {
			binarySearchTrials += sessionBinarySearchTrials;
			return searchPos;
		}
		else if(start == end) {
			binarySearchTrials += sessionBinarySearchTrials;
			return -1;
		}
		else if( localArray[searchPos] > target) {
			return binarySearchRec(start, searchPos, target);
		}
		else {
			return binarySearchRec(searchPos, end, target);
		}
	}
	
	public void printData() {
		System.out.println("Brute force data is:");
		System.out.print("Avg trials per session: ");
		System.out.println((double)(bruteForceTrials) / bruteSessions);
		System.out.println("Binary search data is:");
		System.out.print("Avg trials per session: ");
		System.out.println((double)(binarySearchTrials) / binarySessions);
	}
	
	
}
