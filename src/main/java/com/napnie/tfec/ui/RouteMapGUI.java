package com.napnie.tfec.ui;

import com.napnie.tfec.PropertiesUtil;
import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JPanel;

/**
 * RouteMapGUI is a panel for showing visual map.
 * @author Nitith Chayakul
 *
 */
@SuppressWarnings("serial")
public class RouteMapGUI extends JPanel {  
	private JFXPanel jfxPanel;
	private WebEngine webEngine;  

	/** Two point to generate map. */
	private String[] mapPoint;

	/** Initialize RouteMapGUI panel. */
	public RouteMapGUI(){
		initComponents();
		Dimension dimension = new Dimension(799, 600 + 100);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
	}

	/** Initialize components in RouteMapGUI. */
	private void initComponents(){ 
		jfxPanel = new JFXPanel();  
		createScene();  

		setLayout(new BorderLayout());  
		add(jfxPanel, BorderLayout.CENTER);
	}     

	/** Generate page with Google Map Embed Map API HTML page.  */
	private void createScene() {  
		PlatformImpl.startup(new Runnable() {  
			@Override
			public void run() {  

				Stage stage = new Stage();  

				stage.setTitle("Google Map");  
				stage.setResizable(true);  

				Group root = new Group();  
				Scene scene = new Scene(root,80,20);  
				stage.setScene(scene);  

				// Set up the embedded browser:
				WebView browser = new WebView();
				webEngine = browser.getEngine();

				if( mapPoint == null ) webEngine.loadContent( getDefualtPlace() );
				else webEngine.loadContent( getRouteMap(mapPoint[0], mapPoint[1]) );

				ObservableList<Node> children = root.getChildren();
				children.add(browser);                     

				jfxPanel.setScene(scene);  
			}  
		});  
	}

	/** Set origin and destination of map. */
	public void setMap(String origin, String destination) {
		if( mapPoint == null ) mapPoint = new String[2];
		mapPoint[0] = origin;
		mapPoint[1] = destination;
		createScene();
	}
	
	/**
	 * Generate HTML page in String.
	 * An overview of thailand as a default page.
	 * @return Thailand Map HTML page in String
	 */
	private String getDefualtPlace() {
    	String thai = "<html>"
    			+ "<body>"
    			+ "<iframe style=\"position:fixed; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:999999;\""
    			+ "src=\"https://www.google.com/maps/embed/v1/view?zoom=6&center=15.8700,100.9925"	
    			+ "&key=" + getAPIKey() + "\" allowfullscreen></iframe>"
    			+ "</body>"
    			+ "</html>";
  		return thai;
    }
    
	/**
	 * Generate HTML page in String.
	 * An overview of route between two point.
	 * @return Route Map HTML page in String
	 */
	private String getRouteMap(String origin, String destination) {
		String html = 	"<html>\n"
				+ "<body>\n"
    			+ "<iframe style=\"position:fixed; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:999999;\""
				+ " src=\"https://www.google.com/maps/embed/v1/directions?origin=" + origin
				+ "&destination=" + destination + "&key=" + getAPIKey() + "\" "
				+ "allowfullscreen></iframe>\n"
				+ "</body>\n"
				+ "</html>" ;
		return html;
	}
	
	/** Get API Key. */
	private String getAPIKey() { return PropertiesUtil.getAPIKey(); }
}