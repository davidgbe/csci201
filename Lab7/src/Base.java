import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Base extends JFrame {
	
	private JButton clickOnce;
	private JPanel thisPanel;
	
	public Base() {
		super("Base");
		this.setSize(500,500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.clickOnce = new JButton("Click Once");
		this.clickOnce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				swapForDontClick();
			}
		});
		this.thisPanel = new polygonJPanel(this.clickOnce);
		this.thisPanel.add(this.clickOnce);
		this.add(this.thisPanel);
		setVisible(true);
	}
	
	private void swapForDontClick() {
		this.clickOnce.hide();
		this.thisPanel.remove(this.clickOnce);
		this.clickOnce = new dontClickButton();
		this.clickOnce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(null, "WHY DID YOU CLICK ME AGAIN", "Message", DISPOSE_ON_CLOSE);
				dispose();
			}
		});
		((polygonJPanel) this.thisPanel).setButton(this.clickOnce);
		this.thisPanel.add(this.clickOnce);
		this.thisPanel.revalidate();
	}
	
	

	public static void main(String[] args) {
		new Base();
	}

}
