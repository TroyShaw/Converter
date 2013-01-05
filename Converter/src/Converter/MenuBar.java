package Converter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menu bar for converter program
 * Has 2 bars File and Help
 * File contains Add/Delete panels and Exit
 * Help contains Help and About
 * 
 * 
 */
public class MenuBar extends JMenuBar implements ActionListener{
	private JMenuItem add, delete, deleteAll, save, load, exit, help, about;
	private MainPanel parent;
	
	public MenuBar(MainPanel parent) {
		super();

		this.parent = parent;
		
		JMenu gameMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");

		add = new JMenuItem("Add");
		delete = new JMenuItem("Delete");
		deleteAll = new JMenuItem("Delete all");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		exit = new JMenuItem("Exit");
		help = new JMenuItem("Help");
		about = new JMenuItem("About");

		add.addActionListener(this);
		delete.addActionListener(this);
		deleteAll.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		exit.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);

		gameMenu.add(add);
		gameMenu.add(delete);
		gameMenu.addSeparator();
		gameMenu.add(deleteAll);
		gameMenu.addSeparator();
		gameMenu.add(save);
		gameMenu.add(load);
		gameMenu.addSeparator();
		gameMenu.add(exit);

		helpMenu.add(help);
		helpMenu.addSeparator();
		helpMenu.add(about);

		add(gameMenu);
		add(helpMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if (o == about) {
			Dialogs.showAbout();
		} else if (o == help) {
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
		} else if (o == exit) {
			System.exit(0);
		}
	}
}