package com.napnie.tfec.ui;

import java.awt.Color;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.napnie.tfec.Route;
import com.napnie.tfec.Step;

@SuppressWarnings("serial")
public class StepUI extends JFrame {
	private Route route ;
	private JTextPane inst;
	
	public StepUI(Route route) {
		this.route = route;
		setTitle("Route");
	}
	
	public void run() {
		initComponents();
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		System.out.println("Hello?");
		setVisible(true);
	}

	private void initComponents() {
		inst = new JTextPane();
		
		final String lineCutter = "---------------------------\n";
		final int HEADSIZE = 20;
		
		inst.setText("");
		appendInstruction("Origin: " + route.getOrigin() + "\n", Color.RED, HEADSIZE);
		appendInstruction(lineCutter, null);
		int stepIndex = 1;
		Iterator<Step> step = route.iterator();
		while(step.hasNext() ) {
			appendInstruction(stepIndex++ + ". "+ step.next().getInstruction() +"\n" + lineCutter, null);
		}
		
//		for(Step step : route) {
//			appendInstruction(stepIndex++ + ". "+ step.getInstruction() +"\n" + lineCutter, null);
//		}
		appendInstruction("Destination: " + route.getDestination(), Color.GREEN, HEADSIZE);
		
		add(inst);
		pack();
	}
	
	private void appendInstruction(String text, Color color) {
		appendInstruction(text, color, null);
	}
	
	private void appendInstruction(String text, Color color, Integer size) {
		StyledDocument doc = inst.getStyledDocument();
		
		System.out.println( "Try to append!: "+text );
		
        Style style = inst.addStyle("I'm a Style", null);
        if(color != null) StyleConstants.setForeground(style, color);
        if( size != null) StyleConstants.setFontSize(style, size);

        try { doc.insertString(doc.getLength(), text,style); }
        catch (BadLocationException e){ System.out.println("Och!"); }
	}
}
