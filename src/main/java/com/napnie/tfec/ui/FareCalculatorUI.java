package com.napnie.tfec.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.napnie.tfec.FareCalculator;
import com.napnie.tfec.Route;
import com.napnie.tfec.ui.RouteMapGUI;
import com.napnie.tfec.ui.StepUI;

/**
 * GUI for Taxi Fare Estimate Calculator (TFEC) program.
 * @author Nitith Chayakul
 * @since 7/05/2017
 *
 */
@SuppressWarnings("serial")
public class FareCalculatorUI extends JFrame {
	
	private JLabel status;
	
	private StepUI stepUI;
	
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
		
		stepUI = new StepUI();
		centerPanel = new RouteMapGUI();
		ResultPanel result = new ResultPanel();
		InfoPanel info = new InfoPanel(result, centerPanel, estimator);
		
		status = new JLabel();
		status.setBackground( Color.BLACK);
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setText("Idle");
		
		setLayout(new BorderLayout());
		add(info, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(result, BorderLayout.EAST);
		add(status, BorderLayout.NORTH);
		
		info.setStartFare( estimator.getStartFare() );
		info.setRunFare( estimator.getRunFare() );
		info.setWaitFare( estimator.getWaitFare() );
		
		pack();
	}
	
	private String formatResult(double result) {
		return String.format("%.2f", result );
	}
	
}
