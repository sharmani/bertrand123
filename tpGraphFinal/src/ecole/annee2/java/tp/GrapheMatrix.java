package ecole.annee2.java.tp;


public class GrapheMatrix implements Graphe {
	private int nbSommet;
	private int ident;
	private int[][] matrix;

	public GrapheMatrix() {

	}

	public GrapheMatrix(int sommets) {
		nbSommet = sommets;
		ident = 0;
		matrix = new int[sommets][sommets];
	}

	public int getNbSommet() {
		return nbSommet;
	}

	public void ajouterArc(int s1, int s2) {
		if (s1 < 0 || s2 < 0) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return;
		}

		if (estArc(s1, s2) == true) {
			System.out.println("Cet arc existe d�j�");
			return;
		}
		matrix[s1][s2] = 1;
	}

	public void supprimerArc(int s1, int s2) {
		if (s1 < 0 || s2 < 0) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return;
		}
		if (estArc(s1, s2) == false) {
			System.out.println("L'arc n'appartient pas au graphe");
			return;
		}
		matrix[s1][s2] = 0;
	}

	public boolean estVide() {
		return (nbSommet == 0);
	}

	public boolean estSommet(int s) {
		if (s > 0 && s < nbSommet)
			return true;
		else
			return false;
	}

	public boolean estArc(int s1, int s2) {
		if (s1 < 0 || s1 > nbSommet || s2 < 0 || s2 > nbSommet) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return false;
		}

		return (matrix[s1][s2] != 0);
	}

	public int degrePlus(int s) {
		int cpt = 0;
		if (s < 0 || s > nbSommet) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return 1;
		}
		for (int i = 0; i < nbSommet; i = i + 1) {
			if (matrix[s][i] != 0)
				cpt = cpt + 1;
		}
		return cpt;
	}

	public int degreMoins(int s) {
		int cpt = 0;
		if (s < 0 || s > nbSommet) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return 1;
		}
		for (int i = 0; i < nbSommet; i = i + 1) {
			if (matrix[i][s] != 0)
				cpt = cpt + 1;
		}
		return cpt;
	}

	public int iemeSuccesseur(int s, int num) {
		// System.out.println("toto");
		int i, cpt = 0;
		if (s < 0 || s > nbSommet) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return 1;
		}

		for (i = 0; i < nbSommet; i++) {
			if (matrix[s][i] != 0 && cpt == num)
				return i;
			if (matrix[s][i] != 0)
				cpt++;
		}
		return 1;
	}

	public int iemePredecesseur(int s, int num) {
		int i, cpt = 0;
		if (s < 0 || s > nbSommet) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return 1;
		}
		for (i = 0; i < nbSommet; i++) {
			if (matrix[i][s] != 0 && cpt == num)
				return i;
			if (matrix[i][s] != 0)
				cpt++;
		}
		return 1;
	}

	public int premierSuccesseur(int s) {
		return iemeSuccesseur(s, 0);
	}

	public int successeurSuivant(int s1, int s2) {
		int i;
		if (s1 < 0 || s1 > nbSommet || s2 < 0 || s2 > nbSommet) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return 1;
		}
		for (i = s2 + 1; i < nbSommet; i++) {

			if (matrix[s1][i] != 0)
				return i;
		}
		return 1;
	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public void setNbSommet(int nbSommet) {
		this.nbSommet = nbSommet;
	}
}