package bg.simuAccordeon.voiture;

public interface IVoiture {
	public static final int STATUS_VITESSE_UNIFORME=0;
	public static final int STATUS_DECELERATION=1;
	public static final int STATUS_VITESSE_TEMPS_REACTION=2;
	public static final int STATUS_ACCELERATION=3;
	public static final int STATUS_BIZARE=4;
	public static final int STATUS_STOPPED=5;

	public double getX();
	public double getV();
	public double getV_km_h();
	public double getAcceleration();
	public double getAcceleration_g();

	public void processX(IVoiture voiturePrecedent, int delta_t); 
	
	public void perturbation();
	public double getDistanceVoiturePrecedente(); 
	
	public int getStatus();
	public double getV_moyenne_km_h(); 

}
