package com.napnie.tfec.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
	private Font font = new Font( Font.SANS_SERIF, Font.PLAIN, 16 );
	
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
		
		JLabel status = new JLabel("Idle");
		status.setHorizontalAlignment(SwingConstants.CENTER);
		
		RouteMapGUI centerPanel = new RouteMapGUI();
		ResultPanel result = new ResultPanel();
		InfoPanel info = new InfoPanel(result, centerPanel, estimator);
		info.setAnnouncer(status);
		
		setLayout(new BorderLayout());
		add(status, BorderLayout.NORTH);
		add(info, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(result, BorderLayout.EAST);
		
		info.setStartFare( estimator.getStartFare() );
		info.setRunFare( estimator.getRunFare() );
		info.setWaitFare( estimator.getWaitFare() );
		
		setFont(this, font);
		pack();
	}
	
	private void setFont(Component component, Font font) {
		component.setFont(font);
		if( component instanceof Container) {
			Container container = (Container) component;
			for(Component inner : container.getComponents() ) {
				setFont(inner, font);
			}
		}
	}
	
}
