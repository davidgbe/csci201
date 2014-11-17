import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class YahooParser {
	
	public void retrieveInfo(String info) {
		try {
			String currUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" + info + "%22)&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
			URL url = new URL(currUrl);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(url.openStream());
			NodeList nd = doc.getDocumentElement().getChildNodes();
			System.out.println("Name: " + doc.getElementsByTagName("Name").item(0).getTextContent());
			System.out.println("Year High: " + doc.getElementsByTagName("YearHigh").item(0).getTextContent());
			System.out.println("Year Low: " + doc.getElementsByTagName("YearLow").item(0).getTextContent());
			System.out.println("Market Capitalization: " + doc.getElementsByTagName("MarketCapitalization").item(0).getTextContent());
		}
		catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("There was an issue pulling the file");
		}
		catch(ParserConfigurationException pce) {
			System.out.println("There was an issue configuring the parser");
		}
		catch(SAXException se) {
			System.out.println("The xml to be parsed was incorrectly formatted");
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the code of a company whose stock you would like to check:");
		String info = scan.nextLine();
		YahooParser yp = new YahooParser();
		yp.retrieveInfo(info);
	}

}
