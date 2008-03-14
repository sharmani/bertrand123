package fr.imc.simu.moteur;

public class Voiture {
	public Voiture() {
	}
	public static double XMIN=0;
	public static double VMAX=100.0d * 1000.0d/3600.0d;
	public static double AMAX=20;
	
	private double x=0;
	private double v=VMAX;
	private double a=0;
	
	public void updatePosition(long dt_ms,Voiture vPrec) {
		double dt = dt_ms/1000.0d;
		v=a*dt+v;
		v = Math.min(VMAX,v);
		x=x+v*dt+0.5*a*dt*dt;
	}

	public double getPosition() {
		return x;
	}

	public void setPosition(double x) {
		this.x = x;
	}

	public double getVitesse() {
		return v;
	}

	public void setVitesse(double v) {
		this.v = v;
	}

	public double getAcceleration() {
		return a;
	}

	public void setAcceleration(double a) {
		this.a = a;
	}
	
}

