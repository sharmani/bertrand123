package bg.simuAccordeon;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import bg.simuAccordeon.cadenceur.Cadenceur;
import bg.simuAccordeon.cadenceur.IAwakable;
import bg.simuAccordeon.voiture.IVoiture;
import bg.simuAccordeon.voiture.Voiture;

public class Moteur implements IAwakable {

	private volatile ArrayList<IVoiture> listVoitures = new ArrayList<IVoiture>();
	private boolean isSynchro=false;
	private int nbSynchro=0;
	private long timeTraitement =0;
	private long timeTraitement_1 =0;

	private int delta_t = 100;

	private static int X_MAX = 500;
    private GUIAccordeon gui ;
	private long timeTraitement_2;
	
	public Moteur() {
		gui = new GUIAccordeon(listVoitures);
		Cadenceur cadenceur = new Cadenceur(delta_t);
		cadenceur.setAwakable(this);
	}

	public void awake() {
		this.nbSynchro++;
		if (isSynchro){
			System.out.print("IsSynchro !!! "+this.nbSynchro);
			System.out.print("   timeTraitement: ");System.out.printf("%3d",this.timeTraitement);
			System.out.print("   timeTraitement1: ");System.out.printf("%3d",this.timeTraitement_1);
			System.out.print(" timeTraitement_2:");System.out.printf("%3d",this.timeTraitement_2);
			System.out.print("  deltaT:"+this.delta_t);
			System.out.println();
		}
		awakeSynchro();
		this.nbSynchro--;
	}
	public synchronized void awakeSynchro() {
		this.isSynchro=true;
		
		long timeStart = System.currentTimeMillis();
		//System.out.println(" Awake "+this.listVoitures.size());
		if (mustCreateVoiture()) {
			int i = this.listVoitures.size();
			Voiture v = new Voiture(i);
			this.listVoitures.add(v);
		}
		this.timeTraitement_2=System.currentTimeMillis()-timeStart;
		
		this.gui.displayNbTotalVoitures(this.listVoitures.size());
		this.gui.displayVoitureJustAfter(2*this.X_MAX,this.getVoitureJustAfter(2*this.X_MAX));
		long timeStart_1 = System.currentTimeMillis();
		
		this.processPostionVoitures(); 
		this.timeTraitement_1=System.currentTimeMillis()-timeStart_1;
		
		
		this.deleteVoiture();
		this.gui.repaintPanelRoute();
		
		this.isSynchro=false;
		
		this.timeTraitement= System.currentTimeMillis()-timeStart;
	}

	private void deleteVoiture() {
		Iterator<IVoiture> ite = listVoitures.iterator();
		while (ite.hasNext()) {
			IVoiture voiture = ite.next(); 			
			if (voiture.getX() > 3*X_MAX){
				ite.remove();
			}
		}
	}

	private void processPostionVoitures() {
		Iterator<IVoiture> ite = listVoitures.iterator();
		IVoiture voiturePrecedent = null;
		while (ite.hasNext()) {
			IVoiture voiture = ite.next(); 
			voiture.processX(voiturePrecedent, this.delta_t);
			
			
			if (voiture.getX() > X_MAX){
				voiture.perturbation();
			}
			voiturePrecedent = voiture;
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean mustCreateVoiture() {
		Iterator<IVoiture> ite = listVoitures.iterator();
		int d = Voiture.getDistanceMinimaleSecurité(Voiture.V_Init);
		while (ite.hasNext()) {
			IVoiture v = ite.next();
			if (v.getX() < d) {
				return false;
			}
		}
		return true;
	}
	
	private IVoiture getVoitureJustAfter(double distance){
		IVoiture voiture = null;
		Iterator<IVoiture> ite = listVoitures.iterator();		
		while (ite.hasNext()) {
			IVoiture v = ite.next();
			if (v.getX() > distance) {
				voiture = v;
			}else {
				return voiture;
			}
		}
		return null;
	}

}
