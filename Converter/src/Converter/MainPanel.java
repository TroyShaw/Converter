package Converter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Represents the panel that holds all the ConversionPanel objects.
 * Performs various 
 */
public class MainPanel extends JPanel{
	private List<ConversionPanel> panels;
	private JPanel mainPanel;
	private ConverterFrame parent;

	public MainPanel(final ConverterFrame parent) {
		this.parent = parent;

		panels = new ArrayList<ConversionPanel>();

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		add(mainPanel);
	}

	/*
	 * Public methods follow
	 */

	public void displayOnPanels(ConversionPanel panel, String value) {
		for (ConversionPanel p: panels) {
			if (p != panel) p.displayConverted(value);
		}
	}

	public void addConversionPanel() {
		String[] newPanel = Dialogs.getNewPanel();
		
		if (newPanel != null) addConverter(newPanel[0], newPanel[1]);
	}

	public void deleteConversionPanel() {
		String toDelete = Dialogs.getDeletePanel();

		//if they cancel the dialog or enter nothing, return
		if (toDelete == null) return;
		
		//actual deletion happens in deleteConverter(toDelete)... it returns true if it was deleted
		if (!deleteConverter(toDelete)) {
			Dialogs.showFailureDialog("Panel " + toDelete + " does not exist.");
		}
	}

	public void clearAllConversionPanels() {
		if (Dialogs.verifyDeleteAll()) deleteAll();
	}

	public void saveConversionPanel() {
		List<String> toSave = new ArrayList<String>();

		for (ConversionPanel p: panels) {
			toSave.add(p.getName());
			toSave.add(p.getBase());
		}

		Dialogs.savePanels(toSave);
	}

	public void loadConversionPanel() {
		String[] toLoad = Dialogs.loadPanels();

		if (toLoad == null) return;
		if (toLoad.length < 2) {
			Dialogs.showFailureDialog("File has invalid format");
			return;
		}

		//now check loaded file is valid
		for (int i = 1; i < toLoad.length; i += 2) {
			if (!ConversionPanel.validBase(toLoad[i])) {
				Dialogs.showFailureDialog("File has invalid format");
				return;
			}
		}

		deleteAll();

		for (int i = 0; i < toLoad.length - 1; i +=2) {
			addConverter(toLoad[i], toLoad[i + 1]);
		}
	}

	/**
	 * Called by parent Converter frame at startup to load some initial panels onto the screen
	 */
	public void loadDefaults() {
		addConverter("Decimal", "10");
		addConverter("Binary", "2");
		addConverter("Hexidecimal", "16");
	}

	/*
	 * Private methods follow
	 */

	private boolean addConverter(String name, String base) {
		ConversionPanel toAdd = getPanelByName(name);

		//checks it has valid base, valid name, and not already in the panel
		if (!ConversionPanel.validBase(base) || toAdd != null) return false;	

		toAdd = new ConversionPanel(this, name, base);

		panels.add(toAdd);
		mainPanel.add(toAdd.getNameLabel());
		mainPanel.add(toAdd.getTextField());

		parent.redisplay();

		return true;
	}

	private boolean deleteConverter(String name) {
		ConversionPanel toDelete = getPanelByName(name);

		if (toDelete == null) return false;

		panels.remove(toDelete);
		mainPanel.remove(toDelete.getNameLabel());
		mainPanel.remove(toDelete.getTextField());

		parent.redisplay();

		return true;
	}

	private void deleteAll() {
		panels.clear();
		mainPanel.removeAll();
		mainPanel.removeAll();

		parent.redisplay();
	}

	private ConversionPanel getPanelByName(String name) {
		if (name == null) return null;

		for (ConversionPanel p: panels) {
			if (p.getName().equalsIgnoreCase(name)) return p;
		}

		return null;
	}
}