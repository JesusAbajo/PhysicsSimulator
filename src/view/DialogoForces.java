package view;



import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


import org.json.JSONObject;




public class DialogoForces extends JDialog {

	
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBox;
	private JPanel panelAbajo;
	private JTable table;
	private int indexComboBox=0;
	public int estado=0;
	private ForceLawsTableModel lawsTableModel;
	private List<JSONObject> listaForces;

	
	public DialogoForces(Frame parent, List<JSONObject>  listaForces) {
		super(parent,true); // el true hace que no podamos usar la aplicacion hasta cerrar el dialogo
		this.listaForces=listaForces;
		
		this.initGUI();
		
		
	}
	


	private void initGUI() {
		
		this.setTitle("Force Laws Selection");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(600,400));
		setContentPane(mainPanel);
		
		
		JLabel help = new JLabel(
				 "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
		 
		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		//--------------------------------------------------------------------------------------------------------------
		
		//PANEL DE LA TABLA
		
		//Ponemos una fuerza predeterminada. Así aparece esa fuerza al abrir la ventana
		
		lawsTableModel=new ForceLawsTableModel();
		table = new JTable(lawsTableModel);
		
		
		mainPanel.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		//--------------------------------------------------------------------------------------------------------------------------------------------
		//PANEL COMBOBOX
		
		
		panelAbajo = new JPanel();
		panelAbajo.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panelAbajo);
		
		comboBox=new JComboBox<>();
		
		
			for(int i = 0; i< listaForces.size() ; i++) {
				comboBox.addItem(listaForces.get(i).getString("desc"));
			}
			
			comboBox.setSelectedIndex(0);
			
			
			lawsTableModel.setData(listaForces.get(indexComboBox).getJSONObject("data"),listaForces.get(indexComboBox).getString("type"));
			
			this.comboBox.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e){
					
					indexComboBox = comboBox.getSelectedIndex();
					lawsTableModel.setData(listaForces.get(indexComboBox).getJSONObject("data"), listaForces.get(indexComboBox).getString("type"));	
				}
			});
			
			panelAbajo.add(new JLabel("Force Law:"));
			panelAbajo.add(this.comboBox);	
			 mainPanel.add(panelAbajo);
			
			
		
			
			
			 
			
			
		//--------------------------------------------------------------------------------------------------------------
			//BOTONES
			
			
			JPanel panelBotones = new JPanel(new FlowLayout());
			 
			 JButton ok = new JButton("OK");
			 ok.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e ) {
					if (comboBox.getSelectedItem() != null) {
						estado = 1;
						DialogoForces.this.setVisible(false);
					}
				}
				  
			  });
			 panelBotones.add(ok);
			 
			 JButton cancel = new JButton("Cancel");
			 cancel.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
					   estado = 0;
					   DialogoForces.this.setVisible(false);
						
					}
					  
				  });
			 panelBotones.add(cancel);

		     mainPanel.add(panelBotones);
				
		    
		
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open() {
		pack();
		setVisible(true);
		return estado;
	}



	public JSONObject getData() throws Exception {
		return lawsTableModel.getData();
		
		
	}

}