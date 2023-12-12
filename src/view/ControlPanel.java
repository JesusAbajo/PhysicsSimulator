package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;




import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel
	implements SimulatorObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DialogoForces dialogoForces;
	private Controller _ctrl;
	private boolean _stopped;
	
	private int steps=10000;
	private double dt=2500;
	
	
	private JToolBar toolbar;
	private JSpinner spinnerSteps;
	private JTextField deltaTime_textField;
	private JFileChooser fileChooser;
	
	private JButton openButton, forceButton, runButton, stopButton, exitButton;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
	// TODO build the tool bar by adding buttons, etc.
		toolbar = new JToolBar();
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.PAGE_START);
		
		//Open file button
		fileChooser= new JFileChooser();
		fileChooser.setDialogTitle("Elige un archivo a cargar");
		openButton = new JButton();
		openButton.setActionCommand("load");
		openButton.setToolTipText("Carga el archivo a la aplicación");
		openButton.setIcon(new ImageIcon("./resources/icons/open.png"));
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				openButton();
			}
			
		});
		toolbar.add(openButton);
		toolbar.addSeparator();
		
		
		
		//Force button
		
		forceButton=new JButton();
		forceButton.setToolTipText("Selecciona la fuerza a aplicar");
		forceButton.setIcon(new ImageIcon("./resources/icons/physics.png"));
		forceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				forceLawsButton();	
			}	
		});
		toolbar.add(forceButton);
		toolbar.addSeparator();
		
		
		
		// Play button
		
		runButton=new JButton();
		runButton.setToolTipText("Enciende el simulador");
		runButton.setIcon(new ImageIcon("./resources/icons/run.png"));
		runButton.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					runButton();	
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null,"Something went wrong: "+ex.getMessage() ,"ERROR",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		toolbar.add(runButton);
		
		
		
		//Stop button
		stopButton=new JButton();
		stopButton.setToolTipText("Para el simulador");
		stopButton.setIcon(new ImageIcon("./resources/icons/stop.png"));
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				_stopped=true;
			}
			
		});
		toolbar.add(stopButton);
		
		
		
		//Steps
		
		JLabel stepsLabel=new JLabel("Steps: ");
		toolbar.add(stepsLabel);
		spinnerSteps = new JSpinner(new SpinnerNumberModel(10000, 1, 10000, 100));
		spinnerSteps.setMaximumSize(new Dimension(100,30));
		spinnerSteps.setPreferredSize(new Dimension(100, 30));
		spinnerSteps.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				steps = Integer.valueOf(spinnerSteps.getValue().toString());
			}
			
		});		
		toolbar.add(spinnerSteps);
		
		//Deltatime
		JLabel deltaLabel=new JLabel("Delta-Time: ");
		toolbar.add(deltaLabel);
		deltaTime_textField = new JTextField();
		deltaTime_textField.setText(Double.toString(dt));
		deltaTime_textField.setMaximumSize(new Dimension(100, 30));
		deltaTime_textField.setPreferredSize(new Dimension(100, 30));
		deltaTime_textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dt = Float.parseFloat(deltaTime_textField.getText());
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null,"Something went wrong: "+"\""+deltaTime_textField.getText()+"\" is not a number." ,"ERROR",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		toolbar.add(deltaTime_textField);
		
		
		//Exit button
		toolbar.addSeparator();
		toolbar.add(Box.createHorizontalGlue());
		exitButton=new JButton();
		exitButton.setAlignmentX(RIGHT_ALIGNMENT);
		exitButton.setToolTipText("Salir del simulador");
		exitButton.setIcon(new ImageIcon("./resources/icons/exit.png"));
		
		exitButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) { 
				 exitButton();
			}
		});
		toolbar.add(exitButton);
		
		
		
	}
	// other private/protected methods
	
	private void openButton() {
		
			int returnVal = fileChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				File file = fileChooser.getSelectedFile();
				InputStream inputStream;
				try {
					inputStream = new FileInputStream(file);
					_ctrl.reset();
					_ctrl.loadBodies(inputStream);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"Something went wrong: "+e1.getMessage() ,"ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
	}
	
	
	private void forceLawsButton() { 
		
		List<JSONObject> listaLaws =  _ctrl.getForceLawsInfo();
		
		JSONObject json;

		Window parentWindow = SwingUtilities.windowForComponent(this); 
		if(dialogoForces == null) {
			dialogoForces = new DialogoForces((Frame) parentWindow, listaLaws);			
		}
		int status = dialogoForces.open();
		if(status==1) {
			try {
				json=dialogoForces.getData();
				_ctrl.setForceLaws(json);
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Something went wrong: "+ex.getMessage(), "ERROR" ,JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	
	}
	
	private void runButton() {
	
				openButton.setEnabled(false);
				forceButton.setEnabled(false); 
				runButton.setEnabled(false);
				exitButton.setEnabled(false);
				
				_stopped=false;
				
				_ctrl.setDeltaTime(dt);
				
				run_sim(steps);
				
	}
	

	
	private void exitButton() {
		
		int select=JOptionPane.showOptionDialog(new JFrame(), "¿Seguro de que desea salir?", "Confirmar salida",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if(select==JOptionPane.YES_OPTION) System.exit(0);
				
	}
	// ...
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
		try {
		_ctrl.run(1, null, null, null);
		} catch (Exception e) {
		// TODO show the error in a dialog box
			JOptionPane.showMessageDialog(null, "Something went wrong: "+ e.getMessage(), "ERROR" ,JOptionPane.ERROR_MESSAGE);
			
		// TODO enable all buttons
			enableAllButtons();
		_stopped = true;
		return;
	}
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
			run_sim(n-1);
			}
		});
		} else {
			
		_stopped = true;
	
	// TODO enable all buttons
		enableAllButtons();
		
		}
	}
	private void enableAllButtons() {
		// TODO Auto-generated method stub
		openButton.setEnabled(true);
		forceButton.setEnabled(true); 
		runButton.setEnabled(true);
		stopButton.setEnabled(true);
		exitButton.setEnabled(true);
	}

	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {  
			
			@Override 
			public void run() { _ctrl.setDeltaTime(dt); }
			
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		 SwingUtilities.invokeLater( new Runnable() { 
				
				@Override 
				public void run() { _ctrl.setDeltaTime(dt); }
				
			});
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater( new Runnable() { 
			
			@Override 
			public void run() { _ctrl.setDeltaTime(dt); }
			
		});
		
	}
	
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}