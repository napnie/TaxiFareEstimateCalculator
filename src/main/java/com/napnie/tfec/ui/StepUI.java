package com.napnie.tfec.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.napnie.tfec.Route;
import com.napnie.tfec.Step;
import com.sun.javafx.application.PlatformImpl;

import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Inner panel of ResultPanel for showing step in route.
 * @author Nitith Chayakul
 *
 */
@SuppressWarnings("serial")
public class StepUI extends JPanel {
	private Route route;

	private JFXPanel jfxPanel;
	private WebEngine webEngine;
	private WebView browser;

	/** Initialize StepUI */
	public StepUI() {
		initComponents();
		this.setPreferredSize(new Dimension(100, 100) );
	}

	/** Initialize components. */
	private void initComponents() {
		jfxPanel = new JFXPanel();
		createScene();
		setLayout(new BorderLayout());
		
		add(jfxPanel, BorderLayout.CENTER);
	}
	
	/** Generate page with Step instruction with HTML tag.  */
	private void createScene() {
		PlatformImpl.startup(new Runnable() {
			@Override
			public void run() {

				Stage stage = new Stage();
				stage.setTitle("Step UI");
				stage.setResizable(true);

				Group root = new Group();
				Scene scene = new Scene(root,80,20);
				stage.setScene(scene);

				browser = new WebView();
				webEngine = browser.getEngine();

				if( route == null ) webEngine.loadContent( "<html><body bgcolor=\"#EEEEEE\">"
						+ "<font size=\"5\" color=\"black\">"
						+ "No Step</font></body><html>" );
				else webEngine.loadContent( initStep() );
				
				ObservableList<Node> children = root.getChildren();
				children.add(browser);                     

				jfxPanel.setScene(scene);  
			}
		});
	}

	/** Set route to show step. */
	public void setStep(Route route) {
		this.route = route;
		createScene();
	}

	/** Generate HTML page with step instruction with HTML tag from list of step in route. */
	private String initStep() {
		StringBuilder stepPage = new StringBuilder();
		stepPage.append("<html>"
				+ "<head>" );
		stepPage.append("   <script language=\"javascript\" type=\"text/javascript\">");  
		stepPage.append("       function toBottom(){");  
		stepPage.append("           window.scrollTo(0, document.body.scrollHeight);");  
		stepPage.append("       }");  
		stepPage.append("   </script>"); 
		stepPage.append("<style>"
				+ ".point { font-size: 170%; }"
				+ ".step { font-szie: 90%; }"
				+ "</style></head>"
				+ "<body bgcolor=\"#EEEEEE\">"
				+ "<font class=\"point\" color=\"red\"> Origin: " + route.getOrigin() + "<br></font>"
				+ "<font class=\"step\" color=\"black\">" );
		stepPage.append("<ol>");
		for(Step step : route) stepPage.append( "<li>" + step.getHTMLInstruction() + "</li>");
		stepPage.append("</ol>");
		stepPage.append("</font><font class=\"point\" color=\"green\"> Destination: " + route.getDestination() + "</font>" );
		return stepPage.toString();
	}
	

}
