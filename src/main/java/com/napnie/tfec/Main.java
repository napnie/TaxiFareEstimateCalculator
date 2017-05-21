package com.napnie.tfec;

import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Run TFEC program.
 * @author Nitith Chayakul
 * @since 7/05/2017
 *
 */
public class Main {
	
	/** Run TFEC program. */
	public static void main(String[] arg) {
		FareCalculator estimator = new FareCalculator();
		FareCalculatorUI ui = new FareCalculatorUI(estimator);
		SwingUtilities.invokeLater(new Runnable() {  
			@Override
			public void run() {
				ui.run();
			}  
		});   
		
	}

}
