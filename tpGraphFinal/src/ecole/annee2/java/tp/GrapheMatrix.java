package ecole.annee2.java.tp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

import ecole.annee2.java.tp.ui.GrapheGUI;

public class GrapheMatrix implements Graphe {
	private boolean isOriented = false;

	private int nbSommet;

	private int ident;

	private int[][] matrix;

	private Sommet[] sommetTab;

	private HashMap<Integer, String> mesIndices = new HashMap<Integer, String>();

	private ILogger logger;
	public  GrapheMatrix(File fGraph) {
		this(fGraph,null);
	}
	
	
	public  GrapheMatrix(File fGraph,ILogger logger) {
		try {
			// création du graph
			// selection et lecture du fichier
			this.logger=logger;
			System.out.println("fGraph :: " + fGraph.getAbsolutePath() + "  exists: " + fGraph.exists());
			FileReader fReader = new FileReader(fGraph);
			BufferedReader bReader = new BufferedReader(fReader);
			String line = null;
			while ((line = bReader.readLine()) != null) {
				StringTokenizer stLine = new StringTokenizer(line, ":");
				String key = stLine.nextToken();
				String value = stLine.nextToken().trim();
				if (key.equalsIgnoreCase("oriente")) {

					System.out.println("test oriente parse: " + value);
					if (value.equalsIgnoreCase("true")) {
						this.setOriented(true);
					} else {
						this.setOriented(false);
					}

				}
				if (key.equalsIgnoreCase("sommet")) {

					this.getMesIndices().put(this.getNbSommet(), value);
					this.setNbSommet(this.getNbSommet() + 1);
					// System.out.println("---check ajout correct:"+
					// getMesIndices().get(monGraph.getNbSommet()));
					// System.out.println("--- valeur nom du sommet:"+value);
					// System.out.println("---Nb de sommet:"+
					// monGraph.getNbSommet());
				}
				if (key.equalsIgnoreCase("fin")) {
					int sommets = this.getNbSommet();
					int[][] matrix = new int[sommets][sommets];
					this.setMatrix(matrix);
					System.out.println("---Matrice:" + this.getMatrix());
				}
				if (key.equalsIgnoreCase("arc")) {
					int cout;
					StringTokenizer stArc = new StringTokenizer(value, "-");
					String nameSommet1 = stArc.nextToken().trim();
					String nameSommet2 = stArc.nextToken().trim();
					if (stArc.countTokens() == 1) {
						cout = Integer.parseInt(stArc.nextToken().trim());
					} else {
						cout = 1;
					}
					int s1 = this.getKeyByValue( nameSommet1);
					int s2 = this.getKeyByValue( nameSommet2);
					this.ajouterArc(s1, s2,cout);
				}
			}
			this.calculCfc();
			//calculCfc(monGraph);
			this.activate();
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}


	public void activate() {
		this.sommetTab = new Sommet[nbSommet];
		for (int i = 0; i < nbSommet; i++) {
			String nameSommet = mesIndices.get(i);
			sommetTab[i] = new Sommet();
			sommetTab[i].setI(i);
			sommetTab[i].setName(nameSommet);
		}
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
			ajouterArrete(s1, s2, cout);
		}
	}

	public void ajouterArcOriented(int s1, int s2, int cout) {
		if (s1 < 0 || s2 < 0) {
			log("Ces sommets n'appartiennent pas au graphe");
			return;
		}

		if (estArc(s1, s2) == true) {
			log("[orienté]Cet arc existe déjà  s1:" + s1 + "  s2:" + s2);
			return;
		}
		matrix[s1][s2] = cout;
	}

	public void ajouterArrete(int s1, int s2, int cout) {
		this.ajouterArcOriented(s1, s2, cout);
		this.ajouterArcOriented(s2, s1, cout);
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
			log("Ces sommets n'appartiennent pas au graphe");
			return;
		}
		if (estArc(s1, s2) == false) {
			log("L'arc n'appartient pas au graphe");
			return;
		}
		matrix[s1][s2] = 0;
	}

	public void supprimerArrete(int s1, int s2) {
		this.supprimerArcOriented(s1, s2);
		this.supprimerArcOriented(s2, s1);
	}

	public void calculCfc() {
		int n = this.getNbSommet();
		boolean[] visite = new boolean[n];
		for (int i = 0; i < n; i++) {
			visite[i] = false;
		}
		LinkedList<Integer> pile = new LinkedList<Integer>();
		Matrice mDeBase = new Matrice(this.getMatrix());
		mDeBase.mettreDiagUn();
		Matrice tmp1 = new Matrice(mDeBase.maMatrice);
		Matrice tmp2 = new Matrice(this.getMatrix());
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
				log("---debut composante---");
			}
			while (!pile.isEmpty()) {
				log(mesIndices.get(pile.removeFirst()));
			}
			if (cfc) {
				log("---fin composante---");
			}
			cfc = false;
		}

		for (int i = 0; i < n; i++) {
			if (!visite[i]) {
				log("---debut composante---");
				log(mesIndices.get(i));
				log("---fin composante---");
			}
		}

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
			log("Ces sommets n'appartiennent pas au graphe");
			return false;
		}

		return (matrix[s1][s2] != 0);
	}

	public int degrePlus(int s) {
		int cpt = 0;
		if (s < 0 || s > nbSommet) {
			log("Ces sommets n'appartiennent pas au graphe");
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
			log("Ces sommets n'appartiennent pas au graphe");
			return 1;
		}
		for (int i = 0; i < nbSommet; i = i + 1) {
			if (matrix[i][s] != 0)
				cpt = cpt + 1;
		}
		return cpt;
	}

	public int iemeSuccesseur(int s, int num) {
		int i, cpt = 0;
		if (s < 0 || s > nbSommet) {
			log("Ces sommets n'appartiennent pas au graphe");
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
			log("Ces sommets n'appartiennent pas au graphe");
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
			log("Ces sommets n'appartiennent pas au graphe");
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
			r += "\n";
			for (int j = 0; j < n; j++) {
				r += matrix[i][j] + " ";
			}
		}
		r += "\n";
		return r;
	}

	private Vector<Sommet> getVectorTousLesSommets() {
		Vector<Sommet> v = new Vector<Sommet>();
		for (int i = 0; i < nbSommet; i++) {
			v.add(sommetTab[i]);
		}
		return v;
	}

	private Sommet extraireMinDeF(Vector<Sommet> v) {
		Sommet sMin = null;
		for (Sommet s : v) {
			//System.out.println("sommet  du vecteur dans extraireMinDeF : " + s);
			if (s.getDistanceASource() >= 0) {
				if (sMin == null) {
					sMin = s;
				} else if (s.getDistanceASource() < sMin.getDistanceASource()) {
					sMin = s;
				}
			}
		}
		return sMin;
	}

	public void getPCC_dijkstra(String debut,String fin) {
        int iDebut = this.getKeyByValue( debut);
        int iFin = this.getKeyByValue( fin);
        this.getPCC_dijkstra(iDebut,iFin);
	}

	public void getPCC_dijkstra(int debut,int fin) {
		this.sommetTab[debut].setDistanceASource(0);
		int iteration = 0;
		Vector<Sommet> vToVisit = getVectorTousLesSommets();
		Vector<Sommet> vVisited = new Vector<Sommet>();
		while (!vToVisit.isEmpty()) {
			Sommet sMin = extraireMinDeF(vToVisit);
			vToVisit.remove(sMin);
			if (Main.debug){
				System.out.println("");
				System.out.println("iteration "+iteration+"PCC de "+mesIndices.get(debut)+ " à " + sMin);
			}
			if(fin==sMin.getI()){

				log("");
				log("PCC de "+mesIndices.get(debut)+ " à " + sMin);
				return;
			}
			vVisited.add(sMin);
			for (int i = 0; i < nbSommet; i++) {
				for (int j = 0; j < nbSommet; j++) {
					int poid_i_j = matrix[i][j];
					if (poid_i_j != 0) {
						Sommet s_u = sommetTab[i];
						Sommet s_v = sommetTab[j];
						if (vVisited.contains(s_v)) {
							// La distance est dejà calculée
						} else if (s_v.getDistanceASource() > s_u.getDistanceASource() + poid_i_j) {
							int d_v = s_u.getDistanceASource() + poid_i_j;
							s_v.setDistanceASource(d_v);
							s_v.setSommetPredecesseur(s_u);
						}
					}
				}
			}
			iteration++;
		}
	}
	
	
	public int getKeyByValue( String value) {
		if (mesIndices.containsValue(value)) {
			for (int i = 0; i < mesIndices.size(); i++) {
				if (mesIndices.get(i).equals(value)) {
					return i;
				}
			}
		}
		return -1;
	}

	public HashMap<Integer, String> getMesIndices() {
		return mesIndices;
	}
	
	private void log(String s){
		if (this.logger== null){
			
		}else {
			logger.log(s);
		}
		System.out.println(s);
	}


	public void setLogger(ILogger logger) {
		this.logger=logger;
	}
}