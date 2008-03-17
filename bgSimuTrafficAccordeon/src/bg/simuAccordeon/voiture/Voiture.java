package bg.simuAccordeon.voiture;

import java.text.DecimalFormat;

public class Voiture implements IVoiture {
	
	private String trace ="";

	private static final double G = 9.81d; 
	
	private static DecimalFormat decimalFormat=new DecimalFormat("000.00");

	private double x = 0;

	private double x_Z_1 = 0;

	private double distanceVoiturePrecedente = 0;

	private double distanceVoiturePrecedente_Z_1 = 0;

	private static int tempsDeReactionFreinage = 2000;

	public static double V_Max = 130d * 1000d / 3600d;

	public static double V_Init = 100d * 1000d / 3600d;

	private double v = V_Init;

	private int evenement_initial_freinage = 0;

	private int temps;

	private int numeroVoiture = 0;

	private int status=0;
	
	private double acceleration =0d;

	public Voiture(int numero) {
		this.numeroVoiture = numero;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void processX(IVoiture voiturePrecedent, int deltaT_ms) {
		this.x_Z_1 = this.x;
		this.v += (this.acceleration*deltaT_ms)/1000;
		this.v = Math.min(this.v, V_Max);
		this.v = Math.max(this.v, 0d);
		int delta_x = (int) (this.v * deltaT_ms) / 1000;
		this.x += delta_x;
		this.temps += deltaT_ms;
		this.distanceVoiturePrecedente_Z_1 = this.distanceVoiturePrecedente;
		//trace +="Numero: "+this.numeroVoiture+" temps: "+this.temps+" x:"+decimalFormat.format(this.x)+" delta_x:"+decimalFormat.format(delta_x)+" deltaT_ms: "+deltaT_ms+" v:"+decimalFormat.format(this.v)+" v km/h "+decimalFormat.format(this.getV_km_h())+"\n";
		if (voiturePrecedent == null) {
			return;
		}
		
		

		this.distanceVoiturePrecedente = voiturePrecedent.getX() - x;
		int distanceMinimumSecurité = getDistanceMinimaleSecurité(this.v);
		// Je ne depasse jamais la voiture precedente !
		if (distanceVoiturePrecedente <= 5) {
			this.status=IVoiture.STATUS_DECELERATION;
			this.x = voiturePrecedent.getX() - 5;
			this.v = (int) (voiturePrecedent.getV()*0.8);
			this.acceleration=- this.getAccelerationFreinage_G(distanceVoiturePrecedente, distanceMinimumSecurité); 
			if (this.x==this.x_Z_1){
				this.status=STATUS_STOPPED;
				this.acceleration=0; 
				this.v=0;
			}else {
				this.status=STATUS_BIZARE;
			}
		}else if (this.distanceVoiturePrecedente < (distanceMinimumSecurité )) {
			if (evenement_initial_freinage == 0) {
				this.status=IVoiture.STATUS_VITESSE_TEMPS_REACTION;
				evenement_initial_freinage = temps;
			} else if ((temps - evenement_initial_freinage) > tempsDeReactionFreinage) {
				this.status=IVoiture.STATUS_DECELERATION;
				this.acceleration= - this.getAccelerationFreinage_G(distanceVoiturePrecedente, distanceMinimumSecurité); 
			}
		} else	if (distanceVoiturePrecedente > (distanceMinimumSecurité * 1.1)) {
			this.status=IVoiture.STATUS_ACCELERATION;
			this.acceleration=  (int)(0.2*G);
		} else {
			this.status=IVoiture.STATUS_VITESSE_UNIFORME;
			evenement_initial_freinage = 0;
		}
		if (v < 0) {
			v = 0;
		}
	}
	
	private double getAccelerationFreinage_G(double distanceVoiturePrecedente, double distanceSecurite){
		if (distanceVoiturePrecedente<distanceSecurite/3){
			return 0.7d;
		}else if (distanceVoiturePrecedente<distanceSecurite/2){
			return 0.5d;
		}else {
			return 0.3d;
		}
	}

	private static final double D_SECURITE_130_km_h = 100.0d;
	public static int getDistanceMinimaleSecurité(double v) {
		int d = (int) (D_SECURITE_130_km_h * v / V_Max);
		if (d < 10) {
			d = 10;
		}
		return d;
	}

	private double getVitesseMaximaleFromDistance(int d) {
		double v = d * V_Max / D_SECURITE_130_km_h;
		if (v < 1.0d) {
			v = 1d;
		}
		if (v > V_Max) {
			v = V_Max;
		}
		return v;
	}

	public double getX_Z_1() {
		return x_Z_1;
	}

	public void perturbation() {
		if (numeroVoiture == 0) {
			if (v > 10) {
				v = 10;
			}
		}

	}

	public double getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public double getDistanceVoiturePrecedente() {
		return distanceVoiturePrecedente;
	}

	public double getV_km_h() {
		return this.v * 3600 / 1000;
	}

	public static double convertVitesse_m_s_to_km_h(double v) {
		return v * 3600d / 1000d;
	}

	public int getStatus() { 
		
		return this.status;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public double getAcceleration_g() {
		// TODO Auto-generated method stub
		return acceleration/G;
	}

	public static int getTempsDeReactionFreinage() {
		return tempsDeReactionFreinage;
	}
	public static int getTempsDeReactionFreinage_s() {
		return tempsDeReactionFreinage/1000;
	}

	public static void setTempsDeReactionFreinage(int tempsDeReactionFreinage) {
		Voiture.tempsDeReactionFreinage = tempsDeReactionFreinage;
	}

	public double getV_moyenne_km_h() {
		// TODO Auto-generated method stub
		double v = this.x/this.temps*1000d;
		return convertVitesse_m_s_to_km_h(v);
	}
	
	public void finalize__() throws Throwable{
		super.finalize();
	}

}
