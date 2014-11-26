package davidgbe_CSCI201_Assignment5;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

public class Instruction {
	private int time;
	private TreeMap<String, Integer> tools;
	private String location;
	int index = 0;
	boolean needsReturn = false;
	boolean complete = false;
	
	public Instruction(int time, TreeMap<String, Integer> tools, String location) {
		this.time = time;
		this.tools = tools;
		this.location = location;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public String location() {
		System.out.println("LOCATION CALLED");
		needsReturn = true;
		index = 0;
		return this.location;
	}
	
	public boolean needToReturnTools() {
		return this.needsReturn;
	}
	
	public String nextTool() {
		if(needsTool()) {
			String toReturn = (String) tools.keySet().toArray()[index];
			index++;
			return toReturn;
		}
		return null;
	}
	
	public int quantityForTool(String tool) {
		for(String key : this.tools.keySet()) {
			System.out.println("QuantityTool: '" + tool + "'");
			System.out.println("QUANT: " + tools.containsKey(tool));
		}
		if(!tools.containsKey(tool)) {
			System.out.println("FUCK: '" + tool + "'");
		}
		return tools.get(tool).intValue();
	}
	
	public boolean needsTool() {
		if(index < tools.size()) {
			return true;
		} else if(needsReturn) {
			complete = true;
		}
		return false;
	}
	
	public static void parseInstruction(String line, ArrayList<Task> tasks) {
		TreeMap<String, Integer> toolsMap = new TreeMap<String, Integer>();
		Stack<Character> parseStack = new Stack<Character>();
		String quantityString = "";
		String toolName = "";
		String location = "";
		String duration = "";
		char initial = line.charAt(5);
		if(initial >= '0' && initial <= '9') {
			parseStack.push('t');
			parseStack.push('#');
		} else {
			parseStack.push('l');
		}
		for(int i = 4; i < line.length(); i++) {
			char currChar = line.charAt(i);
			if(parseStack.isEmpty()) {
				if(currChar == 'd') {
					parseStack.push('t');
					parseStack.push('#');
				} else if(currChar == 't') {
					parseStack.push('l');
				} else if(currChar == 'r') {
					parseStack.push('s');
				}
			} else if(parseStack.peek().charValue() == '#') {
				if(currChar == 'x') {
					parseStack.pop();
				} else if(currChar == ' ') {
					
				} else {
					quantityString += currChar;
				}
			} else if(parseStack.peek().charValue() == 't') {
				if(currChar == ' ')  {
					parseStack.push('n');
				} 
			} else if(parseStack.peek().charValue() == 'n') {
				if(currChar == ' ')  {
					parseStack.pop();
					parseStack.pop();
					toolsMap.put(toolName, new Integer(Integer.parseInt(quantityString)));
					toolName = "";
					quantityString = "";
				} else {
					toolName += currChar;
				}	
			} else if(parseStack.peek().charValue() == 'l') {
				if(currChar == ' ')  {
					parseStack.push('m');
				} 
			} else if(parseStack.peek().charValue() == 'm') {
				if(currChar == ' ' && !Character.isUpperCase(line.charAt(i+1))) {
					parseStack.pop();
					parseStack.pop();
				} else {
					location += currChar;
				}	
			} else if(parseStack.peek().charValue() == 's') {
				if(currChar == ' ') {
					
				} else if(currChar == 's') {
					break;
				} else {
					duration += currChar;
				}
			} 
		}
		for(int i = 0; i < tasks.size(); i++) {
			TreeMap<String, Integer> newToolsMap = new TreeMap<String, Integer>();
			for(String name : toolsMap.keySet()) {
				System.out.println("Preface: " + name);
				System.out.println(toolsMap.get(name));
				System.out.println("Name: " + name + " Quantity: " + toolsMap.get(name));
				newToolsMap.put(name, toolsMap.get(name).intValue());
			}
			System.out.println("Duration: " + duration + " Location: " + location);
			tasks.get(i).addInstuction(Integer.parseInt(duration), newToolsMap, location);
		}
	}
}
