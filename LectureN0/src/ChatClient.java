import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient extends Thread{
	private Socket s;
	public ChatClient(String hostname, int port, Scanner scan) {
		try {
			this.s = new Socket(hostname, port);
			this.start();
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			while(true) {
				String line = scan.nextLine();
				pw.println(line);
				pw.flush();
			}
		} catch(IOException ioe) {
			System.out.println("ioe in ChatClient: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true) {
				String line = br.readLine();
				System.out.println(line);
				
			}
		} catch(IOException ioe) {
			System.out.println("ioe in ChatClient.run: " + ioe.getMessage());
		}
	}
	
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the hostname/IP address: ");
		String hostname = scan.nextLine();
		System.out.print("Enter the port: ");
		int port = scan.nextInt();	
		ChatClient cc = new ChatClient(hostname, port, scan);
	}
}
