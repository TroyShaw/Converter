package Converter;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * @author Troy Shaw.
 *         Created May 29, 2012.
 */
public class ConversionPanel {
	
	private JLabel name;
	private JTextField field;
	private MainPanel parent;
	private int base;
	private Robot robot;

	/**
	 * Create a new conversion panel.
	 * Note this is not actually a panel (JPanel, Panel, etc), more just an object that wraps the text field and label.
	 * ConversionPanel objects maintain their own conversion, and all numbers given/ received should be in base 10.
	 * If Robot is supported, conversion takes place as the user types, otherwise when they push "enter"
	 *
	 * @param parent the Converter object parent
	 * @param name the name to display
	 * @param base the base
	 */
	public ConversionPanel(MainPanel parent, String name, String base) {
		//Parameters have been checked prior to object creation
		this.base = Integer.parseInt(base);
		this.parent = parent;
		this.field = new JTextField(30);
		this.name = new JLabel(name, SwingConstants.LEFT);
		this.name.setLabelFor(field);
		
	
		//since text fields are silly and don't have the latest text value when a key event is sent
		//I use the Robot class to 'push' enter after each key the user pushes.
		//this then updates the text-field to have the latest value (which is then broad-cast)
		try {
			robot = new Robot();
		} catch (AWTException exception) {
			robot = null;
		}
		
		//every key press, the robot pushes enter, which makes the text-field have the correct data
		//once enter is pushed (either from the user or by the robot), the value is broadcast.
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					broadCastValue();
				} else {
					if (robot != null) {
						robot.keyPress(KeyEvent.VK_ENTER);
					}
				}
			}});

		//this highlights all text if a user clicks on a text-field.
		//this makes for a more user friendly experience.
		field.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!field.hasFocus()) field.selectAll();
			}});
	}

	/**
	 * Verifies the base is valid and within the limits for radix.
	 * 
	 * @param base the base
	 * @return true if it is valid
	 */
	public static boolean validBase(String base) {
		try {
			int num = Integer.parseInt(base);

			if (num >= Character.MIN_RADIX && num <= Character.MAX_RADIX) return true;
		} catch(NumberFormatException e) {}
		return false;
	}

	/**
	 * Method called by convert panel to indicate to display the value as whatever base this panel represents.
	 * @param value
	 */
	public void displayConverted(String value) {
		field.setText(convertTo(value));
	}

	private void broadCastValue() {
		String value = field.getText();

		if (value == null) value = "";

		if (isValidValue(value)) {
			parent.displayOnPanels(this, convertFrom(value));
		}
	}

	/**
	 * Converts from base 10 to this panels base
	 * @param value
	 * @return
	 */
	private String convertTo(String value) {		
		if (value.equals("")) return value;
		try {
			return Integer.toString(Integer.parseInt(value), base);
		} catch (Exception e) {
			return "Error";
		}
	}

	/**
	 * Converts from the current base into base 10.
	 * @param value base 10 value
	 * @return the value in this components base
	 */
	private String convertFrom(String value) {
		if (value.equals("")) return value;
		else return Integer.toString(Integer.parseInt(value, base));
	}

	/**
	 * Verifies the input string is valid in the base of this ConversionPanel. <p>
	 * 
	 * The string is valid if it contains only the characters in the base<br>
	 * The string "" is a special case, and is valid.
	 * 
	 * @param value
	 * @return true if the string is valid.
	 */
	private boolean isValidValue(String value) {
		//"" is considered valid so that when a user deletes all the text in a field, the others display "" too.
		if (value.equals("")) return true;

		try {
			Integer.toString(Integer.parseInt(value, base));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns the name of this ConversionPanel.
	 * @return
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * Retuns a JLabel containing the name of this ConversionPanel.
	 * @return
	 */
	public JLabel getNameLabel() {
		return name;
	}
	
	/**
	 * Returns a String representation of the base of this ConverionPanel.
	 * @return
	 */
	public String getBase() {
		return Integer.toString(base);
	}
	
	/**
	 * Returns the textfield associated with this ConversionPanel.
	 * @return
	 */
	public JTextField getTextField() {
		return field;
	}
}