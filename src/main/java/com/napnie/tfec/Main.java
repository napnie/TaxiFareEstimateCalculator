package com.napnie.tfec;

import javax.swing.SwingUtilities;

import com.napnie.tfec.ui.FareCalculatorUI;

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
