package com.napnie.tfec.ui;

import com.napnie.tfec.PropertiesUtil;
import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RouteMapGUI extends JPanel {  

	private JFXPanel jfxPanel;  
	private JButton reload;  
	private WebEngine webEngine;  

	private String[] mapPoint;

	public RouteMapGUI(){
		initComponents();
		setPreferredSize(new Dimension(799, 626));
	}  

//	public static void main(String[] args){  
//		RouteMapGUI ui = new RouteMapGUI();
//		// Run this later:
//		SwingUtilities.invokeLater(new Runnable() {  
//			@Override
//			public void run() {  
//				final JFrame frame = new JFrame();  
//
//				frame.getContentPane().add( ui );  
//
//				frame.setMinimumSize(new Dimension(650, 450+75));  
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//				frame.setVisible(true);  
//			}  
//		});   
//
//		String prompt = "";
//		Scanner scan = new Scanner(System.in);
//		do {
//			System.out.print("Origin ");
//			String origin = scan.nextLine();
//			System.out.print("Destination ");
//			String destination = scan.nextLine();
//			ui.setMap(origin, destination);
//		} while( !prompt.equals("q") );
//		scan.close();
//	}  

	private void initComponents(){  

		jfxPanel = new JFXPanel();  
		createScene();  

		setLayout(new BorderLayout());  
		add(jfxPanel, BorderLayout.CENTER);  

		reload = new JButton();  
		reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						webEngine.reload();
					}
				});
			}
		});  
		reload.setText("Reload");  

		add(reload, BorderLayout.SOUTH);  
	}     

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

	public void setMap(String origin, String destination) {
		if( mapPoint == null ) mapPoint = new String[2];
		mapPoint[0] = origin;
		mapPoint[1] = destination;
		createScene();
		reload.doClick();
	}
	
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
	
	private String getAPIKey() { return PropertiesUtil.getAPIKey(); }
}