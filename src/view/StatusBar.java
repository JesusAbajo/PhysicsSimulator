package view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	
	private JLabel timeLabel, lawsLabel, bodiesLabel;
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		createLabels();
		addLabels();
	}
	// other private/protected methods
	// ...
	private void createLabels() {
		timeLabel = new JLabel("Time: ");
		lawsLabel = new JLabel("Law: ");
		bodiesLabel = new JLabel("Bodies: ");
		_currTime = new JLabel();
		_numOfBodies = new JLabel();
		_currLaws = new JLabel();
	}
	
	private void addLabels() {
		JSeparator sep =  new JSeparator(SwingConstants.VERTICAL);
		
		this.add(timeLabel);
		this.add(_currTime);
		this.add(sep);
		this.add(lawsLabel);
		this.add(_currLaws);
		this.add(sep);
		this.add(bodiesLabel);
		this.add(_numOfBodies);
	}
	
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_currTime.setText(String.valueOf(time));
				_currLaws.setText(fLawsDesc);
				_numOfBodies.setText(String.valueOf(bodies.size()));
			} 
		});
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_currTime.setText(String.valueOf(time));
				_currLaws.setText(fLawsDesc);
				_numOfBodies.setText(String.valueOf(bodies.size()));
				
				
			} 
		});
		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_numOfBodies.setText(String.valueOf(bodies.size()));
			} 
		});
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_currTime.setText(String.valueOf(time));
				_numOfBodies.setText(String.valueOf(bodies.size()));

			} 
		});
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				_currLaws.setText(fLawsDesc);
			} 
		});
	}
	
}
