package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow  extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ...
	Controller _ctrl;
	ControlPanel controlPanel;
	StatusBar statusBar;
	BodiesTable bodiesTable;
	Viewer viewer;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout(5,5));
		
		// TODO complete this method to build the GUI
		addToMainPanel(mainPanel);
		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel,BoxLayout.Y_AXIS));
		addToCentralPanel(centralPanel);
		mainPanel.add(centralPanel); 
		setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	// other private/protected methods
	// ...
	
	private void addToMainPanel(JPanel mainPanel) {
		controlPanel = new ControlPanel(_ctrl);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		
		statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
	}
	
	private void addToCentralPanel(JPanel centralPanel) {
		
		
		bodiesTable=new BodiesTable(_ctrl);
		bodiesTable.setPreferredSize(new Dimension (500,250));
		centralPanel.add(bodiesTable);
		
		viewer=new Viewer(_ctrl);
		viewer.setPreferredSize(new Dimension (500,500));
		centralPanel.add(viewer);
	}
	
}


