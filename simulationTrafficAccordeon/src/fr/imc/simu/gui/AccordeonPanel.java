package fr.imc.simu.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.imc.simu.moteur.MoteurSimu;
import fr.imc.simu.moteur.Voiture;

/**
 * @author Administrateur
 *
 */
public class AccordeonPanel extends JPanel {
	private MoteurSimu moteur;
	
	/**
	 * 
	 */
	public AccordeonPanel(MoteurSimu moteur) {
		super();
		this.moteur = moteur;
		Dimension dim = new Dimension(500,50);
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setBackground(Color.BLACK);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.red);
		double position;
		//System.out.print("----------------paint");
		if (moteur == null){
			return;
		}else{
			for(Voiture voiture : moteur.getLVoiture()){
				position = (voiture.getPosition()*500)/1000.0d;
				g.drawLine((int)position, 0,(int)position, 50);				
			}
				
		}
		
		
	}

	/**
	 * @return the moteur
	 */
	public MoteurSimu getMoteur() {
		return moteur;
	}

	

}
