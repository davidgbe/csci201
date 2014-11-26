
import java.net.Socket;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;


public class GameClient implements Runnable{
	
	
	Socket mySocket; // connection to server

	
	public GameClient(String hostname, int port) {
		
		
		
		try {
			
			mySocket = new Socket(hostname, port);
			
		} catch (IOException ioe) {
			System.out.println("IOException in Client constructor: " + ioe.getMessage());
		}
		
		
	}
	
	public static void main(String [] args) {
		if (args.length != 2) {
			System.out.println("USAGE: java Client [hostname] [port]");
			return;
		}
		
		GameClient client = new GameClient(args[0], Integer.parseInt(args[1]));
		Thread clientThread = new Thread(client);
		clientThread.start();
		
	}
	
	@Override
	public void run() {
		System.out.println("Top of run in client");
		try{
			ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
			
			// send a message every second
			while(true){
				
				Message testMessageToSend = new Message();
				out.writeObject(testMessageToSend);
				Thread.sleep(1000);
					
			}
		}
		
		catch(IOException | InterruptedException e){
		
		}		
		
	}
}
