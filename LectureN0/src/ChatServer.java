import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;


public class ChatServer {
	private Vector<ChatThread> ctVector = new Vector<ChatThread>();
	public ChatServer(int port) {
		try{
			ServerSocket ss = new ServerSocket(port);
			while(true) {
				Socket s = ss.accept();
				ChatThread ct = new ChatThread(s, this);
				ctVector.add(ct);
				ct.start();
			}
		}
		catch(IOException ioe) {
			System.out.println("ioe in ChatServer: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(String message, ChatThread ct) {
		for(ChatThread c : ctVector) {
			if(!c.equals(ct)) {
				c.sendMessage(message);
			}
		}
	}
	
	public void removeChatThread(ChatThread ct) {
		ctVector.remove(ct);
	}
	
	public static void main(String[] args) {
		System.out.println("What port?");
		Scanner scan = new Scanner(System.in);
		ChatServer cs = new ChatServer(scan.nextInt());
	}
}
