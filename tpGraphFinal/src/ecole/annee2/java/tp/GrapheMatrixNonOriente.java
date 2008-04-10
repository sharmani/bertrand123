package ecole.annee2.java.tp;

public class GrapheMatrixNonOriente extends GrapheMatrix {
	
	public GrapheMatrixNonOriente(int n) {
		super(n);
	}

	@Override
	public void ajouterArc(int s1, int s2) {
		super.ajouterArc(s1, s2);
		super.ajouterArc(s2, s1);
	}

	@Override
	public void supprimerArc(int s1, int s2) {
		super.supprimerArc(s1, s2);
		super.supprimerArc(s2, s1);
	}
}