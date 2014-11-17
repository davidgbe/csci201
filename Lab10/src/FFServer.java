import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;


public class FFServer {
	ArrayList<FFThread> allThreads = new ArrayList<FFThread>();
	int[] playerHealth = new int[2];
	boolean notWon = true;
	int playerWhoWon;
	Random rn = new Random();
	
	public FFServer(int port) {
		try{
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Started server...");
			while(allThreads.size() != 2) {
				Socket s = ss.accept();
				FFThread fft = new FFThread(s, this);
				allThreads.add(fft);	
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startBattle();
		}
		catch(IOException ioe) {
			System.out.println("ioe in FFServer: " + ioe.getMessage());
		}
	}
	
	private void startBattle() {
		playerHealth[0] = 10;
		playerHealth[1] = 10;
		for(int i = 0; i < allThreads.size(); i++) {
			allThreads.get(i).start();
			allThreads.get(i).sendMessage("Start " + i);
		}
	}
	
	public void processAttack(int player, String attack) {
		int playerToAttack;
		if(player == 0) { playerToAttack = 1; }
		else { playerToAttack = 0; }
		if(attack.equals("Sword")) {
			playerHealth[playerToAttack] -= 2;
		} else {
			int dmg = rn.nextInt() % 5 ;
			playerHealth[playerToAttack] -= (Math.abs(dmg) + 1);
		}
		sendMessage("Normal 0 " + playerHealth[0] + " 1 " + playerHealth[1] + " " + player);
		if(playerHealth[playerToAttack] <= 0) {
			notWon = false;
			playerWhoWon = player;
			notifyPlayersOfVictory();
		}
	}
	
	public void sendMessage(String message) {
		for(FFThread fft : allThreads) {
			fft.sendMessage(message);
		}
	}
	
	public void removeFFThread(FFThread fft) {
		for(int i = 0; i < allThreads.size(); i++) {
			if(allThreads.get(i) == fft) {
				if(i == 1) {
					playerWhoWon = 0;
					break;
				}
				playerWhoWon = 1;
				break;
			}
		}
		this.allThreads.remove(fft);
		this.notifyPlayersOfVictory();
	}
	
	public void notifyPlayersOfVictory() {
		sendMessage("Winner " + playerWhoWon);
	}
	
	public static void main(String[] args) {
		System.out.println("What port?");
		Scanner scan = new Scanner(System.in);
		FFServer fs = new FFServer(scan.nextInt());
	}

}
