package Converter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.UIManager;

/**
 * @author Troy Shaw.
 *         Created May 29, 2012.
 */
public class ConverterFrame extends JFrame {
	private JMenuBar menuBar;
	private JToolBar toolbar;
	private MainPanel mainPanel;

	private ConverterFrame() {
		super("Converter");
		
		//some frame housekeeping
		setNativeLAF();
		setIcon();
		
		//create panels/ toolbar/ menubar
		initializeComponents();
		setLayout();
		
		//finally center/ pack/ display
		setFrameDefaults();	
	}

	
	
	private void initializeComponents() {
		mainPanel = new MainPanel(this);
		mainPanel.loadDefaults();
		
		toolbar = new ToolBar(mainPanel);
		
		menuBar = new MenuBar(mainPanel);
		setJMenuBar(menuBar);
	}

	private void setLayout() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(toolbar);
		panel.add(new JSeparator());
		panel.add(mainPanel);
		
		getContentPane().add(panel);
	}
	
	private void setFrameDefaults() {
		center();
		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void setNativeLAF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//empty
		}
	}

	private void setIcon() {
		String imgLocation = "pics/icon.png";
		try {
			setIconImage(ImageIO.read(ToolBar.class.getResource(imgLocation)));
		} catch (IOException e) {
			//empty
		}
	}
	
	/**
	 * Helper method used to center the panel for visual reasons.
	 */
	private void center() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		setBounds(x, y, w, h);
	}
	
	public void redisplay() {
		pack();
		center();
	}

	public static void main(String[] args) {
		new ConverterFrame();
	}
}