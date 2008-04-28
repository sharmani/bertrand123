package bg.imc.web;

import java.util.Vector;

//bg.imc.web.Params
public class Params {

	
	private static Params instance;
	
	  
	private Vector<String> vAlertDestinataires = new Vector();
	
	public Params() {
		instance=this;
	}

	public static Params getInstance() {
		return instance;
	} 
	
	public void setAlertDestinataire(String s) {
		this.vAlertDestinataires.add(s);
	}

	public Vector<String> getVAlertDestinataires() {
		return vAlertDestinataires;
	}

	public void setVAlertDestinataires(Vector<String> alertDestinataires) {
		vAlertDestinataires = alertDestinataires;
	}

}
