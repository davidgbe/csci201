import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ChatThread extends Thread {
	private Socket s;
	private ChatServer cs;
	private PrintWriter pw;
	public ChatThread(Socket s, ChatServer cs) {
		this.s = s;
		this.cs = cs;
		try {
			this.pw = new PrintWriter(s.getOutputStream());
		} catch(IOException ioe) {
			System.out.println("ioe in ChatThread constructor: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true) {
				String line = br.readLine();
				this.cs.sendMessage(line, this);
			}
		}
		catch(IOException ioe) {
			System.out.println("ioe in ChatThread: " + ioe.getMessage());
			cs.removeChatThread(this);
		}
	}
	
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
}
