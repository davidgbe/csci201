import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class FFClientWindow extends JFrame {
	private JPanel mainPanel;
	private JButton swordButton;
	private JButton magicButton;
	private JLabel thisPlayerHealth;
	private JLabel otherPlayerHealth;
	private FFClient ffc;
	
	public FFClientWindow(FFClient ffc) {
		super("FF7");
		this.ffc = ffc;
		this.setSize(150, 150);
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		thisPlayerHealth = new JLabel("10/10 Health");
		thisPlayerHealth.setAlignmentX(CENTER_ALIGNMENT);
		this.mainPanel.add(thisPlayerHealth);
		otherPlayerHealth = new JLabel("Waiting for other player");
		otherPlayerHealth.setAlignmentX(CENTER_ALIGNMENT);
		this.mainPanel.add(otherPlayerHealth);
		swordButton = new JButton("Sword"); 
		swordButton.setAlignmentX(CENTER_ALIGNMENT);
		swordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				buttonClick("Sword");
			}
		});
		this.mainPanel.add(swordButton);
		magicButton = new JButton("Magic");
		magicButton.setAlignmentX(CENTER_ALIGNMENT);
		magicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				buttonClick("Magic");
			}
		});
		this.mainPanel.add(magicButton);
		inactivate();
		this.add(this.mainPanel);
		this.setVisible(true);
	}
	
	public void inactivate() {
		this.swordButton.setEnabled(false);
		this.magicButton.setEnabled(false);
	}
	
	public void activate() {
		this.swordButton.setEnabled(true);
		this.magicButton.setEnabled(true);
	}
	
	public void start() {
		this.otherPlayerHealth.setText("10/10  Health");
		JLabel count = new JLabel();
		count.setAlignmentX(CENTER_ALIGNMENT);
		this.mainPanel.add(count);
		this.mainPanel.revalidate();
		for(int i = 0; i < 3; i++) {
			count.setText((3 - i) + "");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count.setText("GO!");
		activate();
	}
	
	public void buttonClick(String type) {
		this.ffc.sendInfo(type);
	}
	
	public void updateValues(int thisHealth, int otherHealth) {
		this.thisPlayerHealth.setText(thisHealth + "/10 Health");
		this.otherPlayerHealth.setText(otherHealth + "/10 Health");
		
	}
	
	public void won() {
		JOptionPane.showMessageDialog(this,
			    "You have won!",
			    "Victory!",
			    JOptionPane.PLAIN_MESSAGE);
	}
	
	public void lost() {
		JOptionPane.showMessageDialog(this,
			    "You have lost!",
			    "Defeat",
			    JOptionPane.PLAIN_MESSAGE);
	}
}
