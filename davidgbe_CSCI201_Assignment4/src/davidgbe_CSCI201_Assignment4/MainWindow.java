package davidgbe_CSCI201_Assignment4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.lang.model.element.Element;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MainWindow extends JFrame {
	private JPanel mainPanel;
	private GridPanel drawingPanel;
	private HashMap allTiles = new HashMap();
	private ArrayList<Car> allCars = new ArrayList<Car>();
	private String farthestWest = null; 
	private String farthestEast = null;
	public JTable tables;
	private int rowInc = 0;
	
	public MainWindow() {
		super("Roadway Simulator");
		this.setSize(800, 600);
		JMenuBar mainMenuBar = new JMenuBar();
		JMenu openFileMenu = new JMenu("Open file...");
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
		createDrawingPanel();
		createTable();
		this.add(this.mainPanel);
		this.setMinimumSize(new Dimension(480, 505));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void selectFile() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "XML", "xml");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	System.out.println(chooser.getSelectedFile().getAbsolutePath());
	    	parseFile(chooser.getSelectedFile().getAbsolutePath());
	    }
	}
	
	private void parseFile(String absPath) {
		File targetedFile = null;
		try {
			targetedFile = new File(absPath);
		}
		catch(Exception e) {
			System.out.println("Unable to find the specified file at the path provided");
			e.printStackTrace();
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("Unable to create a document builder: ");
			e.printStackTrace();
		}
		org.w3c.dom.Document doc = null;
		try {
			doc = db.parse(targetedFile);
		} catch (SAXException e) {
			System.out.println("SAX exception while attempting to parse file: ");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOE exception while attempting to parse file: ");
			e.printStackTrace();
		}
		parseRowData(doc);
		parseCarData(doc);
	}
	
	private void parseRowData(org.w3c.dom.Document doc) {
		NodeList rows = doc.getElementsByTagName("row");
		for(int r = 0; r < rows.getLength(); r++) {
			Node row = rows.item(r);
			NamedNodeMap nnmr = row.getAttributes();
			Tile[] rowList = new Tile[9];
			for(int i = 0; i < 9; i++) {
				rowList[i] = null;
			}
			allTiles.put(nnmr.getNamedItem("label").getTextContent(), rowList);
			parseTileData(row, rowList, nnmr.getNamedItem("label").getTextContent());
		}
	}
	
	public void parseTileData(Node row, Tile[] rowList, String rowName) {
		NodeList nd = row.getChildNodes();
		for(int t = 0; t < nd.getLength(); t++) {
			Node tile = nd.item(t);
			if(tile.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			if(this.farthestWest == null) {
				this.farthestWest = rowName;
			}
			this.farthestEast = rowName;
			NamedNodeMap tileData = tile.getAttributes();
			int col = Integer.parseInt(tileData.getNamedItem("column").getTextContent());
			String type = tileData.getNamedItem("type").getTextContent();
			String degree = tileData.getNamedItem("degree").getTextContent();
			rowList[col - 1] = new Tile(type, degree, rowName, col - 1);
			rowList[col - 1].print();
		}
	}
	
	public void parseCarData(org.w3c.dom.Document doc) {
		NodeList cars = doc.getElementsByTagName("car");
		for(int c = 0; c < cars.getLength(); c++) { 
			Node car = cars.item(c);
			if(car.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			NamedNodeMap cnmr = car.getAttributes();
			String color = cnmr.getNamedItem("color").getTextContent();
			String ai = cnmr.getNamedItem("ai").getTextContent();
			String speed = cnmr.getNamedItem("speed").getTextContent();
			System.out.println("Speed" + speed);
			NodeList locationList = car.getChildNodes();
			for(int i = 0; i < locationList.getLength(); i++) {
				Node locationNode = locationList.item(i);
				if(locationNode.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				NamedNodeMap lnmr = locationNode.getAttributes();
				String row = lnmr.getNamedItem("y").getTextContent();
				int col = Integer.parseInt(lnmr.getNamedItem("x").getTextContent());
				Tile currTile = ((Tile[])(allTiles.get(row)))[col - 1];
				Car newCar = null;
				if(ai.equals("1")) {
					newCar = new CarType1(color, ai, speed, row, col - 1, currTile, this, rowInc);
				} else if(ai.equals("2")) {
					newCar = new CarType2(color, ai, speed, row, col - 1, currTile, this, rowInc);
				} else if(ai.equals("3")) {
					newCar = new CarType3(color, ai, speed, row, col - 1, currTile, this, rowInc);
				} else if(ai.equals("4")) {
					newCar = new CarType4(color, ai, speed, row, col - 1, currTile, this, rowInc);
				}
				rowInc++;
				this.allCars.add(newCar);
				 
				newCar.print();
				newCar.start();
				break;
			}
		}
	}
	
	private void createTable() {
		String [] columnNames = {"Car #", "X", "Y"};
		Object [][] data = {};
		tables = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(tables);
		tables.setFillsViewportHeight(true);
		tables.setBackground(Color.gray);
		scrollPane.setPreferredSize(new Dimension(200, 600));
		this.mainPanel.add(scrollPane);
	}
	
	private void createDrawingPanel() {
		this.drawingPanel = new GridPanel(allTiles, allCars);
		this.mainPanel.add(drawingPanel);
	}
	
	public void moveCarInstance(Car c) {
		Tile tileForCar = c.getCurrentTile();
		String decision = c.nextLocation();
		if(decision.equals("up")) {
			String newRow = Character.toString(((char)(tileForCar.getRowAsInt() - 1 + 65)));
			Tile newTile = ((Tile[])(this.allTiles.get(newRow)))[tileForCar.getCol()];
			c.setCurrentTile(newTile);
		} else if(decision.equals("down")) {
			String newRow = Character.toString(((char)(tileForCar.getRowAsInt() + 1 + 65)));
			Tile newTile = ((Tile[])(this.allTiles.get(newRow)))[tileForCar.getCol()];
			c.setCurrentTile(newTile);
		} else if(decision.equals("right")) {
			Tile newTile = ((Tile[])(this.allTiles.get(tileForCar.getRow())))[tileForCar.getCol()+1];
			c.setCurrentTile(newTile);
		} else {
			Tile newTile = ((Tile[])(this.allTiles.get(tileForCar.getRow())))[tileForCar.getCol()-1];
			c.setCurrentTile(newTile);
		}
	}
	
	public HashMap getAllTiles() {
		return this.allTiles;
	}
	
	public String getFarthestWest() {
		return this.farthestWest;
	}
	
	public String getFarthestEast() {
		return this.farthestEast;
	}
	
	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		while(true) {
			mw.repaint();
		}
	}
}
