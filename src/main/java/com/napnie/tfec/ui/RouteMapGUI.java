package com.napnie.tfec.ui;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RouteMapGUI extends JPanel implements MapUI {  

	private Stage stage;  
	private WebView browser;  
	private JFXPanel jfxPanel;  
	private JButton swingButton;  
	private WebEngine webEngine;  

	private String[] mapPoint;

	public RouteMapGUI(){
		initComponents();
		setMinimumSize(new Dimension(650, 450+75));
	}  

	public static void main(String[] args){  
		RouteMapGUI ui = new RouteMapGUI();
		// Run this later:
			SwingUtilities.invokeLater(new Runnable() {  
				@Override
				public void run() {  
					final JFrame frame = new JFrame();  

					frame.getContentPane().add( ui );  

					frame.setMinimumSize(new Dimension(650, 450+75));  
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
					frame.setVisible(true);  
				}  
			});   

			String prompt = "";
			Scanner scan = new Scanner(System.in);
			do {
				System.out.print("Origin ");
				String origin = scan.nextLine();
				System.out.print("Destination ");
				String destination = scan.nextLine();
				ui.setMap(origin, destination);
			} while( !prompt.equals("q") );
			scan.close();
	}  

	private void initComponents(){  

		jfxPanel = new JFXPanel();  
		createScene();  

		setLayout(new BorderLayout());  
		add(jfxPanel, BorderLayout.CENTER);  

		swingButton = new JButton();  
		swingButton.addActionListener(new ActionListener() {

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
		swingButton.setText("Reload");  

		add(swingButton, BorderLayout.SOUTH);  
	}     

	private void createScene() {  
		PlatformImpl.startup(new Runnable() {  
			@Override
			public void run() {  

				stage = new Stage();  

				stage.setTitle("Hello Java FX");  
				stage.setResizable(true);  

				Group root = new Group();  
				Scene scene = new Scene(root,80,20);  
				stage.setScene(scene);  

				// Set up the embedded browser:
				browser = new WebView();
				webEngine = browser.getEngine();

				if( mapPoint == null ) webEngine.loadContent( getDefualtPlace() );
				else webEngine.loadContent( getRouteMap(mapPoint[0], mapPoint[1]) );

				ObservableList<Node> children = root.getChildren();
				children.add(browser);                     

				jfxPanel.setScene(scene);  
			}  
		});  
	}

	@Override
	public void setMap(String origin, String destination) {
		if( mapPoint == null ) mapPoint = new String[2];
		mapPoint[0] = origin;
		mapPoint[1] = destination;
		createScene();
		swingButton.doClick();
	}
}