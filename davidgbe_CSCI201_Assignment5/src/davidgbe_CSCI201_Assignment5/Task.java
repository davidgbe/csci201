package davidgbe_CSCI201_Assignment5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;

public class Task {
	private HashMap<String, Integer> materials;
	private ArrayList<Instruction> instructions;
	private int instructionsIndex = 0;
	private String taskName;
	private int id;
	private static int classId = 0;
	
	public Task(String taskName) {
		this.materials = new HashMap<String, Integer>();
		this.instructions = new ArrayList<Instruction>();
		this.id = classId;
		classId++;
		this.taskName = taskName;
	}
	
	public HashMap<String, Integer> getMaterials() {
		return this.materials;
	}
	
	public Instruction getNextInstruction() {
		if(hasNextInstruction()) {
			return instructions.get(instructionsIndex);
		}
		return null;
	}
	
	public boolean hasNextInstruction() {
		if(instructionsIndex < instructions.size()) {
			return true;
		}
		return true;
	}
	
	public void addMaterial(String materialName, int quantity) {
		this.materials.put(materialName, quantity);
	}
	
	public void addInstuction(int time, TreeMap<String, Integer> tools, String location) {
		this.instructions.add(new Instruction(time, tools, location));
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public int getId() {
		return this.id;
	}
	
	private static void parseResource(String line, ArrayList<Task> tasks) {
		Stack<Character> parseStack = new Stack<Character>();
		String quantity = "";
		String resourceName = "";
		for(int i = 0; i < line.length(); i++) {
			char currChar = line.charAt(i);
			if(currChar == '[' ) {
				parseStack.push(new Character('['));
			} else if(currChar == ']') {
				parseStack.pop();
				break;
			} else if(currChar == ':') {
				parseStack.push(new Character(':'));
			} else if(parseStack.isEmpty()) {
				continue;
			} else if(parseStack.peek().charValue() == '[') {
				resourceName += currChar;
			} else if(parseStack.peek().charValue() == ':') {
				if(currChar >= '0' && currChar <= '9') {
					quantity += currChar;
				} else {
					break;
				}
			}
		}
		System.out.println(quantity);
		System.out.println(resourceName);
		int q = Integer.parseInt(quantity);
		for(int i = 0; i < tasks.size(); i++) {
			tasks.get(i).getMaterials().put(resourceName, new Integer(q));
		}
	}
	
	public static ArrayList<Task> fromFile(String fileName) {
		FileReader fr;
		try {
			fr = new FileReader(fileName);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		ArrayList<Task> newTaskList = new ArrayList<Task>();
		BufferedReader br = new BufferedReader(fr);
		String line;
		while(true) {
			try {
				line = br.readLine();
				if(line == null) {
					break;
				}
				if(newTaskList.isEmpty()) {
					Stack<Character> parseStack = new Stack<Character>();
					String quantity = "";
					String taskName = "";
					for(int i = 0; i < line.length(); i++) {
						char currChar = line.charAt(i);
						if(currChar == '[' ) {
							parseStack.push(new Character('['));
						} else if(currChar == ']') {
							parseStack.pop();
						} else if(currChar == 'x') {
							parseStack.push(new Character('x'));
						} else if(parseStack.isEmpty()) {
							continue;
						} else if(parseStack.peek().charValue() == '[') {
							taskName += currChar;
						} else if(parseStack.peek().charValue() == 'x') {
							if(currChar >= '0' && currChar <= '9') {
								quantity += currChar;
							} else {
								break;
							}
						}
					}
					System.out.println(quantity);
					System.out.println(taskName);
					int q = Integer.parseInt(quantity);
					for(int i = 0; i < q; i++) {
						newTaskList.add(new Task(taskName));
					}
				} else if(line.charAt(1) == 'U') {
					System.out.println("Getting here");
					Instruction.parseInstruction(line, newTaskList);
					
				} else {
					Task.parseResource(line, newTaskList);
				}
			}
			catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}
		return newTaskList;
	}
	
}
