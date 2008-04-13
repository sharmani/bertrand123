package ecole.annee2.java.tp;

public class Sommet {
	
	private int i =0;
	private String name;
	private Sommet  sommetPredecesseur = null;
	private int distanceASource=10000000;
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public Sommet getSommetPredecesseur() {
		return sommetPredecesseur;
	}
	public void setSommetPredecesseur(Sommet sommetPredecesseur) {
		this.sommetPredecesseur = sommetPredecesseur;
	}
	public int getDistanceASource() {
		return distanceASource;
	}
	public void setDistanceASource(int distanceASource) {
		this.distanceASource = distanceASource;
	}
	@Override
	public String toString() {
		
		return name+" distance "+distanceASource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
