package com.napnie.tfec.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Abstract class for inner panel of TFEC GUI.
 * @author Nitith Chayakul
 *
 */
@SuppressWarnings("serial")
public abstract class PlainPanel extends JPanel {
	
	/**
	 * Add TextField in panel
	 * @param panel - JPanel that want to add TextField.
	 * @param text - Text for describe TextField.
	 * @param field - TexField to add in panel.
	 * @param tailing - tailing unit of TextField.
	 */
	protected void initField(JPanel panel ,String text, JTextField field, String tailing) {
		panel.add(new JLabel(text) );
		panel.add(field);
		panel.add(new JLabel(" "+tailing) );
	}
	
	/**
	 * Format double into String.
	 * @param result - double that to format
	 * @return String of format result
	 */
	protected String formatField(double result) {
		return String.format("%.2f", result );
	}
}
