package bg.simuAccordeon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bg.simuAccordeon.voiture.IVoiture;
import bg.simuAccordeon.voiture.Voiture;

public class GUIAccordeon {

	
	private PanelRoute panelRoute ;
	private JLabel labelNbTotalVoitures = new JLabel();
	private JLabel labelVoitureTime = new JLabel();
	
	public GUIAccordeon(ArrayList<IVoiture> listVoitures){
		this.panelRoute = new PanelRoute(listVoitures);
		JFrame frame = new JFrame();
		frame.setLocation(20, 400);
		//frame.setSize(1200, 80);
		frame.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {System.exit(0);}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}			
		});
		
		JPanel panelGlobal = new JPanel();
		
		panelGlobal.setLayout(new BorderLayout());
		
		this.panelRoute.setBorder(BorderFactory.createLineBorder(Color.RED));
		Dimension dim = new Dimension(1200,80);
		this.panelRoute.setSize(dim);
		this.panelRoute.setPreferredSize(dim);
		JLabel labelTempsReactionFreinage = new JLabel();
		labelTempsReactionFreinage.setText("  Temps de réaction au freinage : "+Voiture.getTempsDeReactionFreinage_s()+" s ");
		JPanel panelNorth = new JPanel();
		
		panelNorth.add(this.labelNbTotalVoitures);
		panelNorth.add(this.labelVoitureTime);
		panelNorth.add(labelTempsReactionFreinage);
		panelGlobal.add(this.panelRoute,BorderLayout.CENTER);
		panelGlobal.add(panelNorth,BorderLayout.NORTH);
		frame.add(panelGlobal);
		frame.pack();
		frame.setVisible(true);
	}
	public void repaintPanelRoute() {
		this.panelRoute.repaint();
	}
	
	public void displayNbTotalVoitures(int nbTotalVoitures) {
		this.labelNbTotalVoitures.setText("Nb Total Voitures :"+nbTotalVoitures);
	}
	
	private DecimalFormat decimalFormat = new DecimalFormat("000");
	
	public void displayVoitureJustAfter(int x, IVoiture voitureJustAfter) {
		if (voitureJustAfter== null){
			this.labelVoitureTime.setText("Vitesse moyenne : **");
		}else {
			double vitesseMoyenne = voitureJustAfter.getV_moyenne_km_h();
			this.labelVoitureTime.setText("Vitesse moyenne : "+decimalFormat.format(vitesseMoyenne));
		}
	}

	
}
