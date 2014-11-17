import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class FFClient extends Thread {
	private Socket socket;
	private PrintWriter pw;
	private FFClientWindow ffcw;
	private int playerNum;
	
	public FFClient(String hostname, int port) {
		try {
			this.socket = new Socket(hostname, port);
			this.start();
			System.out.println("Started client...");
			this.pw = new PrintWriter(socket.getOutputStream());
		} catch(IOException ioe) {
			System.out.println("ioe in ChatClient: " + ioe.getMessage());
		}
	}
	
	public void sendInfo(String line) {
		line = playerNum + " " + line;
		pw.println(line);
		pw.flush();
		ffcw.inactivate();
	}
	
	public void setWindow(FFClientWindow ffcw) {
		this.ffcw = ffcw;
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				String line = br.readLine();
				processServerResponse(line);
			}
		} catch(IOException ioe) {
			System.out.println("ioe in ChatClient.run: " + ioe.getMessage());
		}
	}
	
	public void processServerResponse(String line) {
		System.out.println(line);
		String[] split = line.split("\\s+");
		String type = split[0];
		if(type.equals("Start")) {
			playerNum = Integer.parseInt(split[1]);
			ffcw.start();
		} else if(type.equals("Normal")) {
			if(playerNum == Integer.parseInt(split[1])) {
				this.ffcw.updateValues(Integer.parseInt(split[2]), Integer.parseInt(split[4]));
			} else {
				this.ffcw.updateValues(Integer.parseInt(split[4]), Integer.parseInt(split[2]));
			}
			if(playerNum == Integer.parseInt(split[5])) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ffcw.activate();
			}
		} else if(type.equals("Winner")) {
			ffcw.inactivate();
			if(playerNum == Integer.parseInt(split[1])) {
				ffcw.won();
			} else {
				ffcw.lost();
			}
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the hostname/IP address: ");
		String hostname = scan.nextLine();
		System.out.print("Enter the port: ");
		int port = scan.nextInt();	
		FFClient ffc = new FFClient(hostname, port);
		FFClientWindow win = new FFClientWindow(ffc);
		ffc.setWindow(win);
	}

}
