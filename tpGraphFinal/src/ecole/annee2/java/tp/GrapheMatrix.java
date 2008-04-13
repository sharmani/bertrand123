package ecole.annee2.java.tp;

public class GrapheMatrix implements Graphe {
	private boolean isOriented = false;

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

	public void ajouterArc(int s1, int s2, int cout) {
		if (isOriented) {
			ajouterArcOriented(s1, s2, cout);
		} else {
			ajouterArrete(s1, s2,cout);
		}
	}
	

	public void ajouterArcOriented(int s1, int s2,int cout) {
		if (s1 < 0 || s2 < 0) {
			System.out.println("Ces sommets n'appartiennent pas au graphe");
			return;
		}

		if (estArc(s1, s2) == true) {
			System.out.println("Cet arc existe déjà");
			return;
		}
		matrix[s1][s2] = cout;
	}

	public void ajouterArrete(int s1, int s2,int cout) {
		this.ajouterArcOriented(s1, s2, cout);
		this.ajouterArcOriented(s2, s1,cout);
	}


	public void supprimerArc(int s1, int s2) {
		if (isOriented) {
			supprimerArcOriented(s1, s2);
		} else {
			supprimerArrete(s1, s2);
		}
	}
	public void supprimerArcOriented(int s1, int s2) {
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
	public void supprimerArrete(int s1, int s2) {
		this.supprimerArcOriented(s1, s2);
		this.supprimerArcOriented(s2, s1);
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

	public boolean isOriented() {
		return isOriented;
	}

	public void setOriented(boolean isOriented) {
		this.isOriented = isOriented;
	}

	@Override
	public String toString() {
		String r = "GrapheMAtrix :";
		int n = matrix.length;
		for (int i = 0; i < n; i++) {
			r+="\n";
			for (int j = 0; j < n; j++) {
				r+=matrix[i][j]+" ";
			}
		}r+="\n";
		return r;
	}
}