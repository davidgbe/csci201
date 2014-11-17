package davidgbe_CSCI201_Midterm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageGUI extends ParentGUI {
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu menuImage;
	private JMenuItem openImageItem;
	private JPanel mainPanel;
	private JLabel image;
	
	public ImageGUI(String title) {
		super(title);
		setVisible(true);
	}

	public void createMenuBar() {
		this.menuBar = new JMenuBar();
		setJMenuBar( menuBar );
		
		this.menuImage = new JMenu( "Image" );
		menuImage.setMnemonic( 'I' );
		this.menuBar.add(this.menuImage);

		this.openImageItem = new JMenuItem("Open Image");
		this.openImageItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				openImage();
			}
		});
		this.menuImage.add(this.openImageItem);
	}
	
	public JPanel createGUI() {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		createMenuBar();
		this.image = new JLabel("");
		this.mainPanel.add(this.image);
		return mainPanel;
	}
	
	private void openImage() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JPG, GIF, & PNG Images", "jpg", "gif", "png");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	System.out.println(chooser.getSelectedFile().getAbsolutePath());
	    	ImageIcon newImage = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
	    	this.mainPanel.remove(this.image);
	    	this.image = new JLabel(newImage);
	    	this.image.setHorizontalAlignment(BoxLayout.X_AXIS);
	    	this.mainPanel.add(this.image);
	    	//this.mainPanel.add(new JLabel(newImage));
	    	this.image.revalidate();
	    	this.mainPanel.revalidate();
	    }
	}
	

}
