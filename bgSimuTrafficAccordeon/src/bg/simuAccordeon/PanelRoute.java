package bg.simuAccordeon;

import java.awt.Color;
import java.awt.Graphics;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import bg.simuAccordeon.voiture.IVoiture;

public class PanelRoute extends JPanel {

	private NumberFormat numberFormatAccelration = new java.text.DecimalFormat("#.00");

	private NumberFormat numberFormatVitesse = new java.text.DecimalFormat("##");
	private NumberFormat numberFormatDistance = new java.text.DecimalFormat("##");

	ArrayList<IVoiture> listVoitures;

	public PanelRoute(ArrayList<IVoiture> listVoitures) {
		this.listVoitures = listVoitures;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<IVoiture> ite = this.listVoitures.iterator();
		int i = 0;
		while (ite.hasNext()) {

			i++;
			IVoiture voiture = ite.next();
			g.setColor(this.getColor(voiture.getStatus()));
			int x = (int) voiture.getX() / 1;
			if (x > this.getWidth()) {

			} else {
				this.paintVoiture(g, x);
				g.setColor(Color.WHITE);
				g.fillRect(x, 30,35, 40);
				g.setColor(Color.BLUE);
				g.drawLine(x, 0, x, 60);
				
				g.drawString("" + numberFormatDistance.format(voiture.getDistanceVoiturePrecedente()), x + 1, 40);
				g.drawString("v:" + numberFormatVitesse.format(voiture.getV_km_h()), x + 1, 50);
				g.drawString("g:" + numberFormatAccelration.format(voiture.getAcceleration_g()), x + 1, 60);
			}
		}
		int j = 0;
		int x = 0;
		g.setColor(Color.BLACK);
		while (x < this.getSize().getWidth()) {
			j++;
			x = j * 100;
			g.drawLine(x, 0, x, 10);
			g.drawString("" + x, x + 1, 10);
		}
	}

	private Color getColor(int status) {
		if (status == IVoiture.STATUS_VITESSE_UNIFORME) {
			return Color.BLUE;
		}
		if (status == IVoiture.STATUS_ACCELERATION) {
			return Color.GREEN;
		}
		if (status == IVoiture.STATUS_VITESSE_TEMPS_REACTION) {
			return Color.MAGENTA;
		}
		if (status == IVoiture.STATUS_DECELERATION) {
			return Color.RED;
		}
		if (status == IVoiture.STATUS_BIZARE) {
			return Color.CYAN;
		}
		if (status == IVoiture.STATUS_STOPPED) {
			return Color.YELLOW;
		}
		return Color.GRAY;
	}

	private void paintVoiture(Graphics g, int x) {
		int positionRoue = 20;
		int longueurVoiture = 10;
		int hauteurVoiture = 9;
		g.fillRect(x , positionRoue - ((2 * hauteurVoiture) / 3), longueurVoiture, (2 * hauteurVoiture) / 3);
		g.fillRect(x + ((longueurVoiture * 1) / 4), positionRoue - hauteurVoiture, longueurVoiture / 2, hauteurVoiture);
	}

}
