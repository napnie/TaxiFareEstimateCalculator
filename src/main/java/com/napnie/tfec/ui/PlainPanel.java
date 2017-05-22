package com.napnie.tfec.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	
	protected String formatField(double result) {
		return String.format("%.2f", result );
	}
}
