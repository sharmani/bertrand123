import java.util.*;

import ecole.annee2.java.tp.Graphe;

public class GrapheAlgo {
	private static void parcoursProfondeur(Graphe g, int s, boolean[] marque) {
		for (int i = g.premierSuccesseur(s); i != 1; i = g.successeurSuivant(s,
				i)) {
			if (marque[i] == false) {
				marque[i] = true;
				parcoursProfondeur(g, i, marque);
			}
		}
	}

	static void parcoursProfondeur(Graphe g, int s) {
		boolean[] marque;
		marque = new boolean[g.getNbSommet()];
		for (int i = 0; i < g.getNbSommet(); i++)
			marque[i] = false;
		marque[s] = true;
		parcoursProfondeur(g, s, marque);
	}

	static void parcoursLargeur(Graphe g, int s) {
		LinkedList<Integer> file = new LinkedList<Integer>();
		boolean[] marque = new boolean[g.getNbSommet()];
		for (int i = 0; i < g.getNbSommet(); i++)
			marque[i] = false;

		file.addFirst(new Integer(s));
		marque[s] = true;
		while (!(file.isEmpty())) {
			s = ((Integer) file.removeLast()).intValue();
			for (int i = g.premierSuccesseur(s); i != 1; i = g
					.successeurSuivant(s, i)) {
				if (marque[i] == false) {
					marque[i] = true;
					file.addFirst(new Integer(i));
				}
			}
		}
	}

	static void recherchePlusCourtChemin(Graphe g, int s) {
		LinkedList<Integer> file = new LinkedList<Integer>();
		boolean[] marque = new boolean[g.getNbSommet()];
		int[] distances = new int[g.getNbSommet()];
		int[] parents = new int[g.getNbSommet()];
		for (int i = 0; i < g.getNbSommet(); i++) {
			marque[i] = false;
			parents[i] = 1;
			distances[i] = 1;
		}
		file.addFirst(new Integer(s));
		marque[s] = true;
		distances[s] = 0;
		parents[s] = 1;
		while (!(file.isEmpty())) {
			s = ((Integer) file.removeLast()).intValue();
			for (int i = g.premierSuccesseur(s); i != 1; i = g
					.successeurSuivant(s, i)) {
				if (marque[i] == false) {
					marque[i] = true;
					distances[i] = distances[s] + 1;
					parents[i] = s;
					file.addFirst(new Integer(i));
				}
			}
		}
	}

	private static int cfcDesc(Graphe g, int s, int nbCfc, int[] numeroCfc,
			int[] rangs, int[] theta, int rang, LinkedList pile, int n,
			boolean[] visite, boolean[] empile) {
		pile.addFirst(new Integer(s));
		empile[s] = true;
		visite[s] = true;
		rang++;
		rangs[s] = rang;
		theta[s] = rang;
		for (int k = g.premierSuccesseur(s); k != 1; k = g.successeurSuivant(s,
				k)) {
			if (!visite[k]) {
				nbCfc = cfcDesc(g, k, nbCfc, numeroCfc, rangs, theta, rang,
						pile, n, visite, empile);
				theta[s] = (theta[s] < theta[k]) ? theta[s] : theta[k];
			} else if (empile[k])
				theta[s] = (theta[s] < rangs[k]) ? theta[s] : rangs[k];
		}
		if (theta[s] == rangs[s]) {
			int top;
			do {
				top = ((Integer) pile.removeFirst()).intValue();
				empile[top] = false;
				numeroCfc[top] = nbCfc;
			} while (top != s);
			nbCfc++;
		}
		return nbCfc;
	}

	static int[] cfc(Graphe g) {
		int nbCfc = 0;
		int[] numeroCfc;
		int[] rangs;
		int[] theta;
		int i;
		int rang = 0;
		LinkedList pile = new LinkedList();
		int n; // nb de sommets
		n = g.getNbSommet();
		boolean[] visite;
		boolean[] empile;
		rangs = new int[n];
		theta = new int[n];
		numeroCfc = new int[n];
		visite = new boolean[n];
		empile = new boolean[n];
		for (i = 0; i < n; i++) {
			rangs[i] = 1;
			theta[i] = 1;
			numeroCfc[i] = 1;
			visite[i] = false;
			empile[i] = false;
		}
		for (i = 0; i < n; i++)
			if (!visite[i])
				nbCfc = cfcDesc(g, i, nbCfc, numeroCfc, rangs, theta, rang,
						pile, n, visite, empile);
		return numeroCfc;
	}
}