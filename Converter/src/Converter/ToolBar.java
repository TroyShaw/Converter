package Converter;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;


/**
 * Toolbar for the main Converter frame.
 * 
 * @author Troy Shaw.
 *         Created May 29, 2012.
 */
public class ToolBar extends JToolBar implements ActionListener{
	private MainPanel parent;
	private JButton save, load, add, delete, deleteAll, help;

	public ToolBar(MainPanel parent) {
		this.parent = parent;
		setAlignmentX(Component.LEFT_ALIGNMENT);

		setFloatable(false);

		initializeButtons();
	}

	private void initializeButtons() {
		save = NavButton("Save24", "Lol", "Save your configuration", "Save");
		load = NavButton("Open24", "Lol", "Load a configuration", "Load");
		add = NavButton("New24", "Lol", "Create a new panel", "New");
		delete = NavButton("Delete24", "Lol", "Delete a panel", "Delete");
		deleteAll = NavButton("Cut24", "Lol", "Delete all panels", "Delete All");
		help = NavButton("Help24", "Lol", "Help", "Help");

		add(save);
		add(load);
		addSeparator();
		add(add);
		add(delete);
		addSeparator();
		add(deleteAll);
		addSeparator();
		add(help);
	}

	private JButton NavButton(String imageName, String actionCommand, String toolTipText, String altText) {
		//Look for the image.
		String imgLocation = "pics/" + imageName + ".gif";
		URL imageURL = ToolBar.class.getResource(imgLocation);

		//Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(this);

		button.setIcon(new ImageIcon(imageURL, altText));

		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if (o == help) {
			Dialogs.showHelp();
		} else if (o == save) {
			parent.saveConversionPanel();
		} else if (o == load) {
			parent.loadConversionPanel();
		} else if (o == add) {
			parent.addConversionPanel();
		} else if (o == delete) {
			parent.deleteConversionPanel();
		} else if (o == deleteAll) {
			parent.clearAllConversionPanels();
		}
	}
}
