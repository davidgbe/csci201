package csci201.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class concatenateGUI extends JFrame {
	private JTextField input1TF, input2TF, resultTF;
	
	
	public concatenateGUI() {
		super("Concatenate GUI");
		this.setSize(400, 400);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		JLabel text1Label = new JLabel("Text 1");
		input1TF = new JTextField("", 15);
		topPanel.add(text1Label);
		topPanel.add(input1TF);
		mainPanel.add(topPanel);
		
		JPanel secondPanel = new JPanel();
		JLabel text2Label = new JLabel("Text 2");
		input2TF = new JTextField("", 15);
		secondPanel.add(text2Label);
		secondPanel.add(input2TF);
		mainPanel.add(secondPanel);
		
		JPanel bottomPanel = new JPanel();
		JLabel resultLabel = new JLabel("result");
		resultTF = new JTextField("", 15);
		bottomPanel.add(resultLabel);
		bottomPanel.add(resultTF);
		
		JPanel thirdPanel = new JPanel();
		JButton concatenateButton = new JButton("Concatenate");
		concatenateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				resultTF.setText(input1TF.getText() + input2TF.getText());
			}
		});
		thirdPanel.add(concatenateButton);
		
		mainPanel.add(topPanel);
		mainPanel.add(secondPanel);
		mainPanel.add(thirdPanel);
		mainPanel.add(bottomPanel);
		
		add(mainPanel);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new concatenateGUI();
	}
}

class ConcatenateAdapter implements ActionListener {
	private JTextField jtf1, jtf2, resultTF;
	public ConcatenateAdapter(JTextField jtf1, JTextField jtf2, JTextField resultTF) {
		this.jtf1 = jtf1;
		this.jtf2 = jtf2;
		this.resultTF = resultTF;
	}
	public void actionPerformed(ActionEvent ae) {
		this.resultTF.setText(this.jtf1.getText() + this.jtf2.getText());
	}
}
