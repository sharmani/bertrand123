package imc.test.simu.gui;


import fr.imc.simu.moteur.MoteurSimu;
import fr.imc.simu.moteur.Voiture;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Administrateur
 *
 */
public class SimuGUI implements Runnable, ActionListener{
	private boolean isOn = true;
	private JFrame frame = new JFrame();
	JTextField numVoiture;
	private MoteurSimu moteur;
	private AccordeonPanel accordeonPanel;
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(isOn){
			this.accordeonPanel.repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public SimuGUI(MoteurSimu moteur) {
		this.moteur = moteur;
		accordeonPanel = new AccordeonPanel(moteur);
		
		Panel generalPanel = new Panel();
		BorderLayout myLayout = new BorderLayout();
		generalPanel.setLayout(myLayout);
		
		//boutons :
		JPanel panelButtons = new JPanel();
		JButton buttonStop = new JButton("Stopper");
		JButton buttonStart = new JButton("Reprendre");
		JButton buttonRalentir = new JButton("Ralentir");
		JLabel label = new JLabel("Numéro de la voiture : ");
		numVoiture = new JTextField("     ");
		//numVoiture.s
		
		panelButtons.add(label);
		panelButtons.add(numVoiture);
		panelButtons.add(buttonStop);
		panelButtons.add(buttonStart);
		panelButtons.add(buttonRalentir);
		
		buttonStart.addActionListener(this);
		buttonStop.addActionListener(this);
		buttonRalentir.addActionListener(this);
		
		generalPanel.add(panelButtons, BorderLayout.SOUTH);
		JLabel labelWest = new JLabel(" Routes :");
		labelWest.setBackground(Color.GRAY);
		generalPanel.add(labelWest, BorderLayout.WEST);
		generalPanel.add(accordeonPanel, BorderLayout.CENTER);
			
		frame.add(generalPanel);
		frame.pack();
		frame.setVisible(true);
		//frame.setSize(500, 300);
		frame.setTitle("Appli Accordeon");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Reprendre"){
			System.out.println("Reprendre");
		}
		if (e.getActionCommand() == "Stopper"){
			int numCar = 0;
			try {
				numCar = Integer.parseInt(this.numVoiture.getText().trim());
			} catch (NumberFormatException e1) {
			}
			ArrayList<Voiture> listVoiture;
			listVoiture = (ArrayList<Voiture>) this.moteur.getLVoiture();
			Voiture voiture;
			voiture = listVoiture.get(numCar);
			voiture.setVitesse(0);
			System.out.println("Stopper : " + numCar);
		}
		if (e.getActionCommand() == "Ralentir"){
			int numCar = 0;
			try {
				numCar = Integer.parseInt(this.numVoiture.getText().trim());
			} catch (NumberFormatException e1) {
			}
			ArrayList<Voiture> listVoiture;
			listVoiture = (ArrayList<Voiture>) this.moteur.getLVoiture();
			Voiture voiture;
			voiture = listVoiture.get(numCar);
			if (voiture.getVitesse() > 0.0077){
				voiture.setVitesse(voiture.getVitesse() - 0.0077);
			}
			System.out.println("Ralentir : " + numCar);
		}
		
		if (e.getActionCommand() == "Reprendre"){
			int numCar = 0;
			try {
				numCar = Integer.parseInt(this.numVoiture.getText().trim());
			} catch (NumberFormatException e1) {
			}
			ArrayList<Voiture> listVoiture;
			listVoiture = (ArrayList<Voiture>) this.moteur.getLVoiture();
			Voiture voiture;
			voiture = listVoiture.get(numCar);
			voiture.setAcceleration(0.0277/20000d);
			System.out.println("Reprendre : " + numCar);
		}
	}

}
