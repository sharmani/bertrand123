package fr.imc.simu.moteur;

import fr.imc.simu.gui.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.imc.simu.sequenceur.Reveil;

public class MoteurSimu implements Reveil {
	
	SimuGUI simuGUI;
	private List<Voiture> lVoiture = new ArrayList<Voiture>();

	public MoteurSimu() {
		simuGUI=new SimuGUI(this);
	}

	public List<Voiture> getLVoiture() {
		return lVoiture;
	}

	public void setLVoiture(List<Voiture> v) {
		lVoiture = v;
	}

	public void reveil() {
		processPosition();
	}

	public void processPosition() {
		System.out.println(" nb de Voiture "+this.lVoiture.size());
		if (mustCreateCar()) {
			createCar();
		}
		Iterator<Voiture> ite = this.lVoiture.iterator();
		Voiture vToRemove = null;
		Voiture vPrec = null;
		while (ite.hasNext()) {
			Voiture v = ite.next();
			v.updatePosition(500,vPrec);
			if(v.getPosition()>1000){
				vToRemove=v;
			}
			vPrec = v;
		}
		if (vToRemove!= null){
			lVoiture.remove(vToRemove);
		}
	}

	private boolean mustCreateCar() {
		if (lVoiture.size()==0){
			return true;
		}
		else{
			double dMin =1000.0d;
			Iterator<Voiture> ite = this.lVoiture.iterator();
			while (ite.hasNext()) {
				Voiture v = ite.next();
				dMin = Math.min(v.getPosition(), dMin);
				//System.out.println("dMin = "+dMin);
			}
		return dMin > 100;
		}
	}

	private void createCar() {
		System.out.println("createCar");
		Voiture v = new Voiture();
		v.setPosition(Voiture.XMIN);
		lVoiture.add(v);
	}
	
}
