package com.napnie.tfec.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.napnie.tfec.FareCalculator;
import com.napnie.tfec.Route;

import javafx.application.Platform;

/**
 * Info panel of accept infomation from user.
 * @author Nitith Chayakul
 *
 */
@SuppressWarnings("serial")
public class InfoPanel extends PlainPanel {
	/** Input for origin. */
	private JTextField origin;
	/** Input for destination. */
	private JTextField destination;
	/** Button for fare calcurate action. */
	private JButton estimate;
	
	/** Input for start fare rate. */
	private JTextField startFare;
	/** Input for running fare rate. */
	private JTextField runFare;
	/** Input for waiting fare rate. */
	private JTextField waitFare;
	
	/** Result Panel for TFEC GUI. */
	private final ResultPanel result;
	/** Visual map for TFEC GUI. */
	private final RouteMapGUI map;
	/** Estimator for estimate fare. */
	private final FareCalculator estimator;
	
	/** Announcer for announcer status or hint. */
	private JLabel announcer;
	
	/** Initialize InfoPanel */
	public InfoPanel(ResultPanel result, RouteMapGUI map, FareCalculator estimator) {
		this.result = result;
		this.map = map;
		this.estimator = estimator;
		initComponents();
	}
	
	public String getDestination() { return destination.getText(); }
	
	public String getOrigin() { return origin.getText(); }
	
	public void setStartFare(double startFare) { this.startFare.setText( formatField(startFare) ); }
	
	public void setRunFare(double runFare) { this.runFare.setText( formatField(runFare) ); }
	
	public void setWaitFare(double waitFare) { this.waitFare.setText( formatField(waitFare)); }
	
	public double getWaitFare() { return Double.parseDouble( waitFare.getText() ); }
	
	public double getRunFare() { return Double.parseDouble( runFare.getText() ); }
	
	public double getStartFare() { return Double.parseDouble( startFare.getText() ); }

	/**
	 * Initialize components.
	 */
	private void initComponents() {
		JPanel place = new JPanel();
		place.setLayout(new BoxLayout(place, BoxLayout.Y_AXIS) );
		initilizePlaceInput(10);
		estimate = new JButton("Estimate");
		
		JLabel originText = new JLabel("Origin:");
		JLabel destinationText = new JLabel("Destination:");
		
		place.add(originText);
		place.add(origin);
		place.add(destinationText);
		place.add(destination);
		place.add(estimate);
		
		JPanel fareRate = new JPanel();
		fareRate.setLayout(new BoxLayout(fareRate, BoxLayout.Y_AXIS) );
		fareRate.add(new JLabel("Fare Rate"));
		initilizeFareSet(10);
		
		JPanel fareInput = new JPanel();
		fareInput.setLayout(new GridLayout(3, 3) );
		initField(fareInput, "Start: ", startFare ,"Baht");
		initField(fareInput, "Fare per 1 km Run: ", runFare ,"Baht");
		initField(fareInput, "Fare per 1 hour Wating: ", waitFare ,"Baht");
		
		fareRate.add(fareInput);
		
		setLayout(new BorderLayout() );
		add(place, BorderLayout.NORTH);
		add(fareRate, BorderLayout.SOUTH);
//		setPreferredSize(new Dimension(410 , 450+75) );
//		place.setPreferredSize(new Dimension(getWidth(), 150) );
//		fareRate.setPreferredSize(new Dimension(getWidth(), 150));
		setAction();
	}
	
	/** Action for estimate fare. */
	private void estimateAction() {
		announce("Estimating");
		String origin = getOrigin();
		String destination = getDestination();
		
		estimator.setStartFare( Double.parseDouble( startFare.getText() ) );
		estimator.setRunFare( Double.parseDouble( runFare.getText() ) );
		estimator.setWaitFare( Double.parseDouble( waitFare.getText() ) );
		
		estimator.estimateRoute(origin, destination);
		if( !estimator.isRouteEstimated() ) {
			announce( estimator.getHint() );
			return;
		}
		
		result.setDistance( estimator.getDistance() );
		result.setDuration( estimator.getDuration() );
		result.setWaitTime( estimator.getWaitTime() );
		result.setFare( estimator.estimateFare() );
		
		announce("Plot Step");
		Route route = estimator.getRoute();
		map.setMap(route.getOriginLocation(), route.getDestinationLocation());
		
		result.setStep(route);
		announce("Done");
	}
	
	/** Set announcer. */
	public void setAnnouncer(JLabel announcer) { this.announcer = announcer; }
	
	/**
	 * Announce status of estimator to announcer.
	 * @param contact - contact of announcement
	 */
	private void announce(String contact) { 
		if(announcer != null) announcer.setText(contact); 
	}
	
	/** Add ActionListener for estimate button and TextField for origin and destination. */
	private void setAction() {
		ActionListener estimateAction = (event) -> {
			Platform.runLater(new Runnable() {  
				@Override
				public void run() {
					try {
						estimateAction();
					} catch(Exception e) {
						announce( "Error!:" + e.getMessage() );
					}
				}
			});
		};
		estimate.addActionListener( estimateAction );
		origin.addActionListener( estimateAction );
		destination.addActionListener( estimateAction );
	}

	/** Initialize input of Fare Rate panel. */
	private void initilizeFareSet(int width) {
		startFare = new JTextField(width);
		runFare = new JTextField(width);
		waitFare = new JTextField(width);
	}
	
	/** Initialize input of Place panel. */
	private void initilizePlaceInput(int width) {
		origin = new JTextField(width);
		destination = new JTextField(width);
	}

}
