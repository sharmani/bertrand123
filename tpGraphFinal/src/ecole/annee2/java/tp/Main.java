package ecole.annee2.java.tp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	private HashMap<Integer, String> mesIndices = new HashMap<Integer, String>();
	public GrapheMatrix monGraph = new GrapheMatrix();
	public int s;
	public boolean[] marque;

	public Main() {

		parseFileGraph();
		calculCfc(monGraph);
		// parcoursProfondeur(monGraph,0);
		// cfc(monGraph);
		// afficherCfc(cfc(monGraph));
	}

	public void parseFileGraph() {

		try {
			// création du graph

			// selection et lecture du fichier

			File fGraph = new File("test.txt");
			System.out.println("fGraph :: " + fGraph.getAbsolutePath()
					+ "  exists: " + fGraph.exists());

			FileReader fReader = new FileReader(fGraph);
			BufferedReader bReader = new BufferedReader(fReader);
			String line = null;
			while ((line = bReader.readLine()) != null) {
				StringTokenizer stLine = new StringTokenizer(line, ":");
				String key = stLine.nextToken();
				String value = stLine.nextToken();
				if (key.equals("oriente")) {

					System.out.println("test oriente parse: " + value);
					if (value.equals("true")) {
						// setOriente(true);
					} else {
						// traitement a faire
					}

				}
				if (key.equals("sommet")) {

					getMesIndices().put(monGraph.getNbSommet(), value);
					monGraph.setNbSommet(monGraph.getNbSommet() + 1);
					// System.out.println("---check ajout correct:"+
					// getMesIndices().get(monGraph.getNbSommet()));
					// System.out.println("--- valeur nom du sommet:"+value);
					// System.out.println("---Nb de sommet:"+
					// monGraph.getNbSommet());
				}
				if (key.equals("fin")) {
					int sommets = monGraph.getNbSommet();
					int[][] matrix = new int[sommets][sommets];
					monGraph.setMatrix(matrix);
					System.out.println("---Matrice:" + monGraph.getMatrix());
				}
				if (key.equals("arc")) {
					int cout;
					StringTokenizer stArc = new StringTokenizer(value, "-");
					String nameSommet1 = stArc.nextToken();
					String nameSommet2 = stArc.nextToken();
					if (stArc.countTokens() == 1) {
						cout = Integer.parseInt(stArc.nextToken());
					} else {
						cout = 0;
					}
					int s1 = getKeyByValue(mesIndices, nameSommet1);
					int s2 = getKeyByValue(mesIndices, nameSommet2);
					monGraph.ajouterArc(s1, s2);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Mon main");
		new Main();

	}

	public HashMap<Integer, String> getMesIndices() {
		return mesIndices;
	}

	public void setMesIndices(HashMap<Integer, String> mesIndices) {
		this.mesIndices = mesIndices;
	}

	public int getKeyByValue(HashMap<Integer, String> mesIndices, String value) {
		if (mesIndices.containsValue(value)) {
			for (int i = 0; i < mesIndices.size(); i++) {
				if (mesIndices.get(i).equals(value)) {
					return i;
				}
			}
		}
		return -1;
	}

	public void parcoursLargeur(Graphe g, int s) {
		LinkedList<Integer> file = new LinkedList<Integer>();
		boolean[] marque = new boolean[g.getNbSommet()];
		for (int i = 0; i < g.getNbSommet(); i++) {
			marque[i] = false;
			System.out.println("Parcours Largeur---" + mesIndices.get(i));
		}

		file.addFirst(new Integer(s));
		marque[s] = true;
		while (!(file.isEmpty())) {
			s = ((Integer) file.removeLast()).intValue();
			System.out.println("test remove---" + mesIndices.get(s));
			for (int i = g.premierSuccesseur(s); i != 1; i = g
					.successeurSuivant(s, i)) {
				if (marque[i] == false) {
					marque[i] = true;
					file.addFirst(new Integer(i));
					System.out.println("Parcours Largeur---"
							+ mesIndices.get(i));
				}
			}
		}
	}

	private void parcoursProfondeur(Graphe g, int s, boolean[] marque,
			HashMap<Integer, String> mesIndices) {
		// System.out.println("Parcours Prof---"+g.premierSuccesseur(s));
		for (int i = 0; i < g.getNbSommet(); i = g.successeurSuivant(s, i)) {
			if (marque[i] == false) {
				marque[i] = true;
				System.out.println("Parcours Prof---" + mesIndices.get(i));
				parcoursProfondeur(g, i, marque, mesIndices);
			}
		}
	}

	public void parcoursProfondeur(Graphe g, int s) {
		boolean[] marque;
		marque = new boolean[g.getNbSommet()];
		for (int i = 0; i < g.getNbSommet(); i++) {
			marque[i] = false;
			// System.out.println("Parcours Prof---"+mesIndices.get( i));
		}

		marque[s] = true;
		System.out.println("Parcours Prof---" + mesIndices.get(s));
		parcoursProfondeur(g, s, marque, getMesIndices());
	}

	public void calculCfc(Graphe g) {
		int n = g.getNbSommet();
		boolean[] visite = new boolean[n];
		for (int i = 0; i < n; i++) {
			visite[i] = false;
		}
		LinkedList<Integer> pile = new LinkedList<Integer>();
		Matrice mDeBase = new Matrice(g.getMatrix());
		mDeBase.mettreDiagUn();
		Matrice tmp1 = new Matrice(mDeBase.maMatrice);
		Matrice tmp2 = new Matrice(g.getMatrix());
		tmp2.produitMatrice(mDeBase);
		tmp2.produitMatrice(mDeBase);

		while (!tmp1.matriceEgale(tmp2)) {
			tmp1 = new Matrice(tmp2.maMatrice);
			tmp2.produitMatrice(mDeBase);
			tmp1.convertUnite();
			tmp2.convertUnite();

		}
		tmp2.mettreSymetriquebyZero();
		boolean cfc = false;

		for (int i = 0; i < n; i++) {

			for (int j = 0; j < n; j++) {
				if (tmp2.maMatrice[i][j] == 1 && !visite[j]) {
					visite[j] = true;
					cfc = true;
					pile.add(j);
				}
			}
			if (cfc) {
				System.out.println("---debut cfc---");
			}
			while (!pile.isEmpty()) {
				System.out.println(mesIndices.get(pile.removeFirst()));
			}
			if (cfc) {
				System.out.println("---fin cfc---");
			}
			cfc = false;
		}

		for (int i = 0; i < n; i++) {
			if (!visite[i]) {
				System.out.println("---debut cfc---");
				System.out.println(mesIndices.get(i));
				System.out.println("---fin cfc---");
			}
		}

	}

}
