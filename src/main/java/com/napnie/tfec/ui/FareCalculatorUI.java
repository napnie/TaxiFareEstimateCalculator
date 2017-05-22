package com.napnie.tfec.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.napnie.tfec.FareCalculator;
import com.napnie.tfec.ui.RouteMapGUI;

/**
 * GUI for Taxi Fare Estimate Calculator (TFEC) program.
 * @author Nitith Chayakul
 * @since 7/05/2017
 *
 */
@SuppressWarnings("serial")
public class FareCalculatorUI extends JFrame {
	private RouteMapGUI centerPanel;
	
	public FareCalculatorUI(FareCalculator estimator) {
		initComponents(estimator);
	}
	
	/** Run this GUi. */
	public void run() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/** Initialize Components in this GUI. */
	private void initComponents(FareCalculator estimator) {
		setTitle("Taxi Fare Estimate Calculator");
		
		centerPanel = new RouteMapGUI();
		ResultPanel result = new ResultPanel();
		InfoPanel info = new InfoPanel(result, centerPanel, estimator);
		
		setLayout(new BorderLayout());
		add(info, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(result, BorderLayout.EAST);
		
		info.setStartFare( estimator.getStartFare() );
		info.setRunFare( estimator.getRunFare() );
		info.setWaitFare( estimator.getWaitFare() );
		
		pack();
	}
	
}
