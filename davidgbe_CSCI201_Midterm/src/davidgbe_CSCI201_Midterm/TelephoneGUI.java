package davidgbe_CSCI201_Midterm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelephoneGUI extends ParentGUI {
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu menuPhone;
	private JMenuItem callItem;
	private JPanel mainPanel;
	private JTextField result;
	
	public TelephoneGUI(String title) {
		super(title);
		setVisible(true);
	}
	
	public void createMenuBar() {
		this.menuBar = new JMenuBar();
		setJMenuBar( menuBar );
		
		this.menuPhone = new JMenu( "Phone" );
		menuPhone.setMnemonic( 'P' );
		this.menuBar.add(this.menuPhone);

		this.callItem = new JMenuItem("Call");
		this.callItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clear();
			}
		});
		this.menuPhone.add(this.callItem);
	}
	
	public JPanel createGUI() {
		this.mainPanel = new JPanel();
		createMenuBar();
		createCustomGUI();
		return mainPanel;
	}
	
	private void createCustomGUI() {
		JPanel phoneScreen = new JPanel();
		phoneScreen.setLayout(new BoxLayout(phoneScreen, BoxLayout.Y_AXIS));
		phoneScreen.setPreferredSize(new Dimension(110, 150));
		this.result = new JTextField("", 100);
		JPanel keys = new JPanel();
		keys.setLayout(new GridLayout(4,3));
		createKeys(keys);
		phoneScreen.add(this.result);
		phoneScreen.add(keys);	
		this.mainPanel.add(phoneScreen);
	}
	
	private void createKeys(JPanel keys) {
		String[] allKeys = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "*", "0", "#"};
		for(int i = 0; i < allKeys.length; i++) {
			String keyChar = allKeys[i];
			JButton key = new JButton(keyChar);
			key.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					numberClicked(keyChar);
				}
			});
			keys.add(key);
		}
	}
	
	private void numberClicked(String i) {
		this.result.setText(this.result.getText() + i);
	}
	
	private void clear() {
		this.result.setText("");
	}
}
