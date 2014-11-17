package davidgbe_CSCI201_Assignment5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Factory extends JFrame {
	
	private JPanel mainPanel;
	private JPanel factoryPanel;
	private JTable table;
	private JPanel resourcesPanel;
	private JPanel toolsPanel;
	private JPanel workAreasPanel;
	
	private TreeMap<String, Integer> tools;
	
	public Factory() {
		super("Factory");
		this.setSize(800, 600);
		JMenuBar mainMenuBar = new JMenuBar();
		JMenu openFileMenu = new JMenu("Open folder...");
		JMenuItem openFileItem = new JMenuItem("Open file");
		openFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				selectFile();
			}
		});
		openFileMenu.add(openFileItem);
		mainMenuBar.add(openFileMenu);
		this.setJMenuBar(mainMenuBar);
		
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.X_AXIS));
		this.setUpFactoryPanel();
		this.setUpTaskBoard();
		addResources();
//		addTools();
		addWorkAreas();
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void selectFile() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Factory RCP", "factory", "rcp");
	    chooser.setFileFilter(filter);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	System.out.println(chooser.getSelectedFile().getAbsolutePath());
	    	parseDirectory(chooser.getSelectedFile().getAbsolutePath());
	    }
	}
	
	private void parseDirectory(String path) {
		try {
			Files.walk(Paths.get(path)).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			        System.out.println(filePath);
			        String fileName = filePath.toString();
			        if(fileName.substring(fileName.length() - 3, fileName.length()).equals("rcp")) {
			        	Task.fromFile(filePath.toString());
			        } else{
			        	parseTools(filePath.toString());
			        	this.repaint();
			        }
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setUpFactoryPanel() {
		factoryPanel = new JPanel();
		factoryPanel.setPreferredSize(new Dimension(600, 600));
		factoryPanel.setLayout(new BorderLayout());
		resourcesPanel = new JPanel();
		toolsPanel = new JPanel();
		workAreasPanel = new JPanel();
		workAreasPanel.setLayout(new GridBagLayout());
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));
		factoryPanel.add(resourcesPanel, BorderLayout.NORTH);
		factoryPanel.add(toolsPanel, BorderLayout.WEST);
		factoryPanel.add(workAreasPanel, BorderLayout.CENTER);
		this.mainPanel.add(factoryPanel);
	}
	
	private void setUpTaskBoard() {
		String [] columnNames = {"Task Board"};
		Object [][] data = {};
		table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setBackground(Color.gray);
		scrollPane.setPreferredSize(new Dimension(200, 600));
		this.mainPanel.add(scrollPane);
	}
	
	public void addResources() {
		Resource wood = new Resource("Wood", "resources/wood.png", 10);
		Resource metal = new Resource("Metal", "resources/metal.png", 10);
		Resource plastic = new Resource("Plastic", "resources/plastic.png", 10);
		resourcesPanel.add(wood);
		resourcesPanel.add(metal);
		resourcesPanel.add(plastic);
		this.repaint();
		
	}
	
	public void addTools(TreeMap<String, Integer> map) {
		
		for(String key : map.keySet()) {
			String stripped = key.toLowerCase().replaceAll("\\s","");
			System.out.println("Stripped :" + stripped);
			Tool tool = new Tool(key, "resources/" + stripped + ".png", map.get(key));
			this.toolsPanel.add(tool);
		}
		System.out.println("Here");
		toolsPanel.revalidate();
	}
	
	public void addWorkAreas() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 30;
		gbc.ipady = 0;
		gbc.gridy = 1;
		String title = "";
		for(int i = 0; i < 2; i++) {
			gbc.gridx = i+1;
			WorkArea anvil = new WorkArea("resources/anvil.png", title);
			workAreasPanel.add(anvil, gbc);
			new Thread(anvil).start();
		}
		for(int i = 0; i < 3; i++) {
			gbc.gridx = i+3;
			WorkArea workBench = new WorkArea("resources/workbench.png", title);
			workAreasPanel.add(workBench, gbc);
			new Thread(workBench).start();
		}
		
		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		JLabel anvilsLabel = new JLabel("Anvils");
		workAreasPanel.add(anvilsLabel, gbc); 
		gbc.gridx = 3;
		gbc.gridwidth = 3;
		JLabel workBenchesLabel = new JLabel("Work Benches");
		workAreasPanel.add(workBenchesLabel, gbc); 
		
		gbc.gridy = 3;
		gbc.gridx = 1;
		gbc.gridwidth = 5;
		workAreasPanel.add(Box.createVerticalStrut(50), gbc);
		
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		for(int i = 0; i < 2; i++) {
			gbc.gridx = i+1;
			WorkArea furnace = new WorkArea("resources/furnace.png", title);
			workAreasPanel.add(furnace, gbc);
			new Thread(furnace).start();
		}
		for(int i = 0; i < 3; i++) {
			gbc.gridx = i+3;
			WorkArea tableSaw = new WorkArea("resources/tablesaw.png", title);
			workAreasPanel.add(tableSaw, gbc);
			new Thread(tableSaw).start();
		}
		
		gbc.gridy = 5;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		JLabel furnacesLabel = new JLabel("Furnaces");
		workAreasPanel.add(furnacesLabel, gbc); 
		gbc.gridx = 3;
		gbc.gridwidth = 3;
		JLabel tableSawsLabel = new JLabel("Table Saws");
		workAreasPanel.add(tableSawsLabel, gbc); 
		
		gbc.gridy = 6;
		gbc.gridx = 1;
		gbc.gridwidth = 5;
		workAreasPanel.add(Box.createVerticalStrut(50), gbc);
		
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		for(int i = 0; i < 4; i++) {
			gbc.gridx = i+1;
			WorkArea paintingStation = new WorkArea("resources/paintingstation.png", title);
			workAreasPanel.add(paintingStation, gbc);
			new Thread(paintingStation).start();
		}
		for(int i = 0; i < 1; i++) {
			gbc.gridx = i+5;
			WorkArea press = new WorkArea("resources/press.png", title);
			workAreasPanel.add(press, gbc);
			new Thread(press).start();
		}
		
		gbc.gridy = 8;
		gbc.gridx = 1;
		gbc.gridwidth = 4;
		JLabel paintingStationsLabel = new JLabel("Painting Stations");
		workAreasPanel.add(paintingStationsLabel, gbc); 
		gbc.gridx = 5;
		gbc.gridwidth = 1;
		JLabel pressLabel = new JLabel("Press");
		workAreasPanel.add(pressLabel, gbc); 
		
		gbc.gridy = 9;
		gbc.gridx = 1;
		gbc.gridwidth = 5;
		workAreasPanel.add(Box.createVerticalStrut(50), gbc);
	}
	
	private void parseTools(String fileName) {
		tools = new TreeMap<String, Integer>();
		
		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String line;
		while(true) {
			try {
				line = br.readLine();
				if(line == null) {
					break;
				}
				Stack<Character> parseStack = new Stack<Character>();
				String quantity = "";
				String toolName = "";
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
						toolName += currChar;
					} else if(parseStack.peek().charValue() == ':') {
						if(currChar >= '0' && currChar <= '9') {
							quantity += currChar;
						} else {
							break;
						}
					}
				}
				System.out.println(quantity);
				System.out.println(toolName);
				int q = Integer.parseInt(quantity);
				if(!toolName.equals("Workers")) {
					tools.put(toolName, new Integer(q));
				}
			}
			catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}
		addTools(tools);
	}

	public static void main(String[] args) {
		Factory fact = new Factory();
	}

}
