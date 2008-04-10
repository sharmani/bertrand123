public interface Graphe
{
	public boolean estVide () ;
	public boolean estArc (int s1 , int s2) ;
	public int degrePlus(int s) ;
	public int degreMoins(int s);
	public void ajouterArc(int s1 , int s2) ;
	public void supprimerArc(int s1, int s2) ;
	public int iemeSuccesseur(int s , int i ) ;
	public int iemePredecesseur(int s , int i ) ;
	public int premierSuccesseur(int s) ;
	public int successeurSuivant (int s1 , int s2) ;
	public int getNbSommet();
	public int[][] getMatrix();
}