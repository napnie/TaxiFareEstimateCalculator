package com.napnie.tfec.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.napnie.tfec.Route;

@SuppressWarnings("serial")
public class ResultPanel extends PlainPanel {
	/** TextField for show distance between origin and destination. */
	private JTextField distance;
	/** TextField for show duration from origin and destination. */
	private JTextField duration;
	/** TextField for show estimated wait time of traveling. */
	private JTextField waitTime;
	/** TextField for show estimated taxi fare from origin and destination. */
	private JTextField fare;
	
	private StepUI step; 

	public ResultPanel() {
		initComponents();
	}
	
	public void setStep(Route route) { step.setStep(route); }
	
	public void setDistance(double distance) { this.distance.setText( formatField(distance) ); }
	
	public void setDuration(double duration) { this.duration.setText( formatField(duration) ); }
	
	public void setWaitTime(double waitTime) { this.waitTime.setText( formatField(waitTime) ); }
	
	public void setFare(double fare) { this.fare.setText( formatField(fare) ); }

	private void initComponents() {
		step = new StepUI();
		
		// for resultPanel
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS) );
		resultPanel.add( new JLabel("Estimated Price") );
		
		JPanel result = new JPanel();
		initilizeResult(10);
		result.setLayout(new GridLayout(4, 3) );
		initField(result, "Distance: ", distance, "km");
		initField(result, "Duration: ", duration, "minute");
		initField(result, "Estimated Wating Time: ", waitTime, "minute");
		initField(result, "Overall Price", fare, "Baht");
		
		resultPanel.add(result);
		
		setLayout(new BorderLayout() );
		add(resultPanel, BorderLayout.NORTH);
		add(step, BorderLayout.CENTER);
	}
	
	/** Initialize TextField in Estimated Price panel. */
	private void initilizeResult(int width) {
		distance = new JTextField(width);
		duration = new JTextField(width);
		waitTime = new JTextField(width);
		fare = new JTextField(width);
		distance.setEditable(false);
		duration.setEditable(false);
		waitTime.setEditable(false);
		fare.setEditable(false);
		
		distance.setHorizontalAlignment(SwingConstants.RIGHT);
		duration.setHorizontalAlignment(SwingConstants.RIGHT);
		waitTime.setHorizontalAlignment(SwingConstants.RIGHT);
		fare.setHorizontalAlignment(SwingConstants.RIGHT);
	}
}
