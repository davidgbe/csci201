
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Server {
	public Server(int port) {
		try {
			ServerSocket ss= new ServerSocket( port );
			
			while(true){
				
				Socket playerSocket = ss.accept();
				ServerThread st= new ServerThread(playerSocket);
				st.start();
			}

		} catch (IOException ioe) {
			System.out.println("IOException in Server constructor: " + ioe.getMessage());
		}
	}
	
	public static void main(String [] args) {
		if (args.length!= 1) {
			System.out.println("USAGE: java Server [port]");
			return;
		}
		Server server = new Server(Integer.parseInt(args[0]));
	}
}
class ServerThread extends Thread {
	
	private Socket playerSocket;
	
	public ServerThread(Socket s) {
		this.playerSocket = s;
	}
	
	public void run() {
		
		try {
			ObjectInputStream in  = new ObjectInputStream(playerSocket.getInputStream());
			
			// constantly receive messages from this client
			while(true){
			
				Object objectReceived = in.readObject();
				
				if(objectReceived instanceof Message){
					Message messageReceived = (Message)objectReceived;
					// do stuff with message
				}
			}
			
		} catch (Exception ioe) {
			System.out.println("IOException in ServerThread constructor: " + ioe.getMessage());
		}
	}
}
