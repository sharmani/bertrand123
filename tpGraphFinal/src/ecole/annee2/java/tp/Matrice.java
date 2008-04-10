package ecole.annee2.java.tp;

public class Matrice {
	public int[][] maMatrice;

	public Matrice(int[][] maMatriceOld) {
		int n = maMatriceOld.length;
		maMatrice = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				maMatrice[i][j] = maMatriceOld[i][j];
			}
		}
	}

	public Matrice(int taille, int valeur) {
		maMatrice = new int[taille][taille];
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				maMatrice[i][j] = valeur;
			}
		}
	}

	public Matrice(int taille) {
		maMatrice = new int[taille][taille];
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				if (i == j) {
					maMatrice[i][j] = 1;
				} else {
					maMatrice[i][j] = 0;
				}
			}
		}
	}

	public void afficherMatrice() {
		int n = maMatrice.length;
		for (int i = 0; i < n; i++) {
			System.out.println("");
			for (int j = 0; j < n; j++) {
				System.out.print(maMatrice[i][j]);
			}
		}
		System.out.println("");
	}

	public void afficherMatrice(String nomMatrice) {
		System.out.println("----" + nomMatrice + "----");
		int n = maMatrice.length;
		for (int i = 0; i < n; i++) {
			System.out.println("");
			for (int j = 0; j < n; j++) {
				System.out.print(maMatrice[i][j]);
			}
		}
		System.out.println("");
	}

	public void produitMatrice(Matrice mPourProduit) {
		int n = maMatrice.length;
		// System.out.println("taille----"+n);
		Matrice tmp = new Matrice(n, 0);
		// tmp.afficherMatrice("tmp");
		// this.afficherMatrice("actuel");
		// mPourProduit.afficherMatrice("celle que jenvoi");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {

					tmp.maMatrice[i][j] = tmp.maMatrice[i][j] + maMatrice[i][k]
							* mPourProduit.maMatrice[k][j];

				}
			}
		}
		maMatrice = tmp.maMatrice;

	}

	public void sommeMatrice(Matrice mPourSomme) {
		int n = maMatrice.length;
		// System.out.println("taille----"+n);
		Matrice tmp = new Matrice(n, 0);
		// tmp.afficherMatrice("tmp");
		// this.afficherMatrice("actuel");
		// mPourProduit.afficherMatrice("celle que jenvoi");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tmp.maMatrice[i][j] = tmp.maMatrice[i][j] + maMatrice[i][j]
						+ mPourSomme.maMatrice[i][j];
			}
		}
		maMatrice = tmp.maMatrice;

	}

	public boolean matriceEgale(Matrice mAComparee) {
		int n = maMatrice.length;
		if (n == mAComparee.maMatrice.length) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (maMatrice[i][j] != mAComparee.maMatrice[i][j]) {
						return false;
					}
				}
			}
		} else {
			System.out.println("Matrice de taille differente");
			return false;
		}

		return true;
	}

	public void convertUnite() {
		int n = maMatrice.length;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (maMatrice[i][j] != 0)
					maMatrice[i][j] = 1;
			}
		}
	}

	public void mettreDiagUn() {
		int n = maMatrice.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					maMatrice[i][j] = 1;
			}
		}
	}

	public void mettreDiagZero() {
		int n = maMatrice.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					maMatrice[i][j] = 0;
			}
		}
	}

	public void mettreSymetriquebyZero() {
		int n = maMatrice.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (maMatrice[i][j] == 0)
					maMatrice[j][i] = 0;
			}
		}
	}

}