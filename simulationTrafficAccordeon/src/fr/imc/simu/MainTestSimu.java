package fr.imc.simu;

import fr.imc.simu.moteur.MoteurSimu;
import fr.imc.simu.sequenceur.Sequenceur;

public class MainTestSimu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MoteurSimu moteur = new MoteurSimu();
		Sequenceur seq = new Sequenceur(moteur);
		
	}

}
