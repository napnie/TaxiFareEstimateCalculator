package tfec;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * GUI for Taxi Fare Estimate Calculator (TFEC) program.
 * @author Nitith Chayakul
 * @since 7/05/2017
 *
 */
@SuppressWarnings("serial")
public class FareCalculatorUI extends JFrame {
	private FareCalculator estimator;
	
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
	/** Button for set new fare rate. */
//	private JButton setFare;
	
	/** TextField for show distance between origin and destination. */
	private JTextField distance;
	/** TextField for show duration from origin and destination. */
	private JTextField duration;
	/** TextField for show estimated wait time of traveling. */
	private JTextField waitTime;
	/** TextField for show estimated taxi fare from origin and destination. */
	private JTextField fare;
	
	public FareCalculatorUI(FareCalculator estimator) {
		this.estimator = estimator;
	}
	
	/** Run this GUi. */
	public void run() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}
	
	/** Initialize Components in this GUI. */
	private void initComponents() {
		setTitle("Taxi Fare Estimate Calculator");
		
		JPanel leftPanel = initilizeLeftPanel();
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = initilizeRightPanel();
		
		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		
		startFare.setText( String.valueOf( estimator.getStartFare() ) );
		runFare.setText( String.valueOf( estimator.getRunFare() ) );
		waitFare.setText( String.valueOf( estimator.getWaitFare() ) );
		
		pack();
	}
	
	/** Initialize right panel of this GUI. */
	private JPanel initilizeRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS) );
		rightPanel.add( new JLabel("Estimated Price") );
		
		JPanel result = new JPanel();
		initilizeResult(10);
		result.setLayout(new GridLayout(4, 3) );
		initField(result, "Distance: ", distance, "km");
		initField(result, "Duration: ", duration, "minute");
		initField(result, "Estimated Wating Time: ", waitTime, "minute");
		initField(result, "Overall Price", fare, "Baht");
		
		rightPanel.add(result);
		
		rightPanel.add(new JPanel());
		
		return rightPanel;
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
	
	/** Initialize left panel of this GUI. */
	private JPanel initilizeLeftPanel() {
		JPanel leftPanel = new JPanel();
		
		JPanel place = new JPanel();
		place.setLayout(new BoxLayout(place, BoxLayout.Y_AXIS) );
		initilizePlaceInput(10);
		estimate = new JButton("Estimate");
		estimate.addActionListener( (event) -> estimateAction() );
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
		initField(fareInput, "1km Run: ", runFare ,"Baht");
		initField(fareInput, "1hour Wating: ", waitFare ,"Baht");
		
		fareRate.add(fareInput);
//		setFare = new JButton("Set");
//		fareRate.add(setFare);
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS) );
		leftPanel.add(place);
		leftPanel.add(fareRate);
		
		leftPanel.add(new JPanel());
		
		return leftPanel;
	}
	
	private void estimateAction() {
		// TODO Auto-generated method stub
		String origin = this.origin.getText();
		String destination = this.destination.getText();
		estimator.estimateRoute(origin, destination);
		distance.setText( formatResult( estimator.getDistance() ) );
		duration.setText( formatResult( estimator.getDuration() ) );
		waitTime.setText( formatResult( estimator.getWaitTime() ) );
		fare.setText( formatResult( estimator.estimateFare() ) );
	}
	
	private String formatResult(double result) {
		return String.format("%.2f", result );
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
	
	/**
	 * Add TextField in panel
	 * @param panel - JPanel that want to add TextField.
	 * @param text - Text for describe TextField.
	 * @param field - TexField to add in panel.
	 * @param tailing - tailing unit of TextField.
	 */
	private void initField(JPanel panel ,String text, JTextField field, String tailing) {
		panel.add(new JLabel(text) );
		panel.add(field);
		panel.add(new JLabel(" "+tailing) );
	}
	

}
