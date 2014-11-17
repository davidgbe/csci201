import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class FFThread extends Thread {
	private Socket socket;
	private FFServer ffServer;
	private PrintWriter pw;
	
	public FFThread(Socket s, FFServer ffs) {
		this.socket = s;
		this.ffServer = ffs;
		try {
			this.pw = new PrintWriter(socket.getOutputStream());
		} catch(IOException ioe) {
			System.out.println("ioe in FFThread constructor: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				String line = br.readLine();
				System.out.println(line);
				processClientMessage(line);
			}
		} catch(IOException ioe) {
			System.out.println("ioe in FFClient.run: " + ioe.getMessage());
			ffServer.removeFFThread(this);
		}
	}
	
	private void processClientMessage(String msg) {
		String[] split = msg.split("\\s+");
		int player = Integer.parseInt(split[0]);
		String attackType = split[1];
		ffServer.processAttack(player, attackType);
	}
	
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
}
