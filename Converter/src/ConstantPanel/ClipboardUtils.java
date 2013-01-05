package ConstantPanel;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class provides easy access to the clipboard.
 * 
 * One can get and set the contents of the clipboard as a string.
 *
 * @author Troy Shaw
 */
public class ClipboardUtils {

	/**
	 * Sets the clipboard to the given string.
	 * @param string the string to set the clipboard to
	 */
	public static void setClipboardContents(String string) {
		StringSelection stringSelection = new StringSelection(string);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	/**
	 * Returns the contents of the clipboard. Returns null if the contents is not a string.
	 * @return the clipboard as a string, or null
	 */
	public static String getClipboardContents() {
		String result = "";
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		
		if (hasTransferableText) {
			try {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e){
				return null;
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		stripAndLoadSwitch();
	}
	
	private static void stripAndLoadSwitch() {
		String s = getClipboardContents();
		
		s = s.replaceAll(Pattern.quote("<td><abbr title="), "");
		s = s.replaceAll(Pattern.quote("</td>"), "");
		s = s.replaceAll(Pattern.quote("</tr>"), "");
		s = s.replaceAll(Pattern.quote("<tr>"), "");
		s = s.replaceAll("<td><strong>..</strong>", "");
		s = s.replaceAll("    ", "");
		s = s.replaceAll("   ", "");
		s = s.replaceAll("\n\n\n", "");
		s = s.replaceAll("^\n", "");
		s = s.replaceAll(Pattern.quote("</abbr>"), "");
		
		
		Scanner scan = new Scanner(s);
		
		StringBuilder b = new StringBuilder();
		for (int i = 0x00; i <= 0xFF; i++) {
			String num = Integer.toString(i, 16).toUpperCase();
			
			String[] line = scan.nextLine().split(Pattern.quote("\">"));
			String info = line[1] + "                   ".substring(8 - (10 - line[1].length())) + line[0].substring(1);
			
			num = num.length() == 1 ? "0" + num : num;
			b.append("case 0x" + num + ":\t\t//" + info + "\n//\nbreak;\n");
		}
		
		scan.close();
		
		setClipboardContents(b.toString());
		System.out.println("done");
	}
	
	public static void loadSwitch() {
		StringBuilder b = new StringBuilder();
		for (int i = 0x00; i <= 0xFF; i++) {
			String num = Integer.toString(i, 16).toUpperCase();
			num = num.length() == 1 ? "0" + num : num;
			b.append("case 0x" + num + ":\n//\nbreak;\n");
		}
		
		System.out.println("done");
		
		setClipboardContents(b.toString());
	}
} 