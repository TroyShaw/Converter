package Converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * Class with various pop-up windows.
 *
 * @author Troy Shaw.
 *         Created May 29, 2012.
 */
public class Dialogs {

	/**
	 * Displays the save panel, as well as saves.
	 * If the save fails, an error message is displayed.
	 *
	 * @param info list of name, base, name, base, etc 
	 */
	public static void savePanels(List<String> info) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));  
		int returnVal = fileChooser.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile() != null) {
			File toSave = fileChooser.getSelectedFile(); 

			try {
				FileWriter fstream = new FileWriter(toSave);
				BufferedWriter out = new BufferedWriter(fstream);

				for (String s: info) {
					out.write(s + ",");
				}

				out.close();
			}catch (Exception e) {
				Dialogs.showFailureDialog("Saving failed");
			}
		}
	}

	/**
	 * Loads a file and returns an array containing its elements. Does not verify integrity of file.
	 * Format for file (according to save function) is "name,base,name,base...name,base," - note the leading comma
	 *
	 * @return
	 */
	public static String[] loadPanels() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		int value = fileChooser.showOpenDialog(null);

		if (value == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile() != null) {
			File toLoad = fileChooser.getSelectedFile(); 

			try {
				Scanner scan = new Scanner(toLoad);
				String[] toReturn =  scan.nextLine().split(",");
				scan.close();

				return toReturn;
			}catch (Exception e) {
				Dialogs.showFailureDialog("Loading failed");
			}
		}

		return null;
	}

	/**
	 * Displays two input panes for the user to create a new panel.
	 * The information is verified first to make sure it is valid.
	 *
	 * @return an array containing the name of the panel, and the base
	 */
	public static String[] getNewPanel() {
		String name = JOptionPane.showInputDialog("Input the name of the panel."); 
		if (name == null) return null;
		if (name.equals("")) {
			Dialogs.showFailureDialog("Name was invalid");
			return null;
		}

		String base = JOptionPane.showInputDialog("Input the base (2 - 36)."); 
		if (base == null) return null;
		if (!ConversionPanel.validBase(base)) {
			Dialogs.showFailureDialog(("Base was invalid."));
			return null;
		}

		return new String[]{name, base};

	}

	/**
	 * Displays an input pane to get the name of a panel to delete. <p>
	 * 
	 * Returns the name of the panel to delete, or null if the user cancels the dialog.
	 *
	 * @return the name of the panel to delete, or null
	 */
	public static String getDeletePanel() {
		return JOptionPane.showInputDialog("Name of panel to delete?"); 
	}

	/**
	 * Displays an error message.
	 * 
	 * @param error the error message to display
	 */
	public static void showFailureDialog(String error) {
		JOptionPane.showMessageDialog(null, error, "Error!", JOptionPane.ERROR_MESSAGE);	
	}

	/**
	 * Displays a yes/ no selection box to confirm the user wishes to delete all panels
	 *
	 * @return true if yes is selected
	 */
	public static boolean verifyDeleteAll() {
		int value = JOptionPane.showConfirmDialog(null, "Really remove all?", "Remove all", JOptionPane.YES_NO_OPTION);

		return value == JOptionPane.OK_OPTION;
	}

	/**
	 * Displays the programs help.
	 */
	public static void showHelp() {
		String help = 	"" +
				"This program can convert any base to any other base.\n\n" +
				"To convert, simply type in a number in the appropriate panel.\n" +
				"The number will then be converted in the other panels.\n" +
				"You may need to push enter depending on your version of Java.\n\n" +
				"Select \"File -> Add\" to add a new panel.\n" +
				"Add a panel by specifiying its name and base.\n\n" +
				"Select \"File -> Delete\" to remove a panel.\n" +
				"Remove a panel by specifying its name.\n\n" + 
				"One can also save and load their configurations for future use.\n\n" +
				"Thanks for using, and enjoy!" +
				"";

		JOptionPane.showMessageDialog(null, help);
	}

	/**
	 * Displays the programs 'about'.
	 */
	public static void showAbout() {
		String about = 	"Converter\nVersion 1.00\n\n" +
				"Troy Shaw\ntroyshw@gmail.com\n\n" +
				"Pictures contained in toolbox are the property of Sun Microsystems, Inc.\n\n" +
				"This software is free and open source.\n" +
				"Feel free to distribute it to your friends!";

		JOptionPane.showMessageDialog(null, about);
	}
}