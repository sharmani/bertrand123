package ecole.annee2.java.tp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ecole.annee2.java.tp.GrapheMatrix;
import ecole.annee2.java.tp.ILogger;

public class GrapheGUI implements ILogger{

	private JFrame frame = new JFrame();

	private JTextArea labelFile = new JTextArea(30, 20);

	private JTextArea labelResult = new JTextArea(30,20);

	private File fileGraph;

	private JTextField textFieldSommetArrivee = new JTextField(5);

	private JTextField textFieldSommetDepart = new JTextField(5);

	public GrapheGUI() {
		super();
		this.labelFile.setEditable(false);
		this.labelResult.setEditable(false);
		frame.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}

		});
		frame.setTitle("Graphe Test");
		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuItemFile = new JMenuItem("file");
		menuItemFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processFile();
			}
		});
		JMenuItem menuItemStart = new JMenuItem("start");
		menuItemStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		menuBar.add(menuItemFile);
		menuBar.add(menuItemStart);
		frame.setJMenuBar(menuBar);
		Dimension dimLabel = new Dimension(100, 200);
		labelFile.setPreferredSize(dimLabel);
		labelResult.setPreferredSize(dimLabel);

		JPanel panelCentral = new JPanel(new GridLayout(4, 1));
		JPanel panelPointArrivee = new JPanel();
		panelPointArrivee.add(new JLabel("Arrivée: "));
		panelPointArrivee.add(this.textFieldSommetArrivee);
		JPanel panelPointDepart = new JPanel();
		panelPointDepart.add(new JLabel("Depart: "));
		panelPointDepart.add(this.textFieldSommetDepart);
		panelCentral.add(panelPointDepart);
		panelCentral.add(panelPointArrivee);
		JButton buttonPCC = new JButton("Dijkstra");
		buttonPCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchDijkstra();

			}

		});
		JButton buttonStart = new JButton("Connexité");
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();

			}

		});
		panelCentral.add(buttonPCC);
		panelCentral.add(buttonStart);
		JPanel panelGlobal = new JPanel(new BorderLayout());
		panelGlobal.add(labelFile, BorderLayout.WEST);
		panelGlobal.add(panelCentral, BorderLayout.CENTER);
		panelGlobal.add(labelResult, BorderLayout.EAST);
		frame.add(panelGlobal);
		frame.pack();
		frame.setVisible(true);
	}

	protected void launchDijkstra() {
		this.labelResult.setText("");
		String sDebut=this.textFieldSommetDepart.getText();
		String sFin=this.textFieldSommetArrivee.getText();
		log("Depart "+sDebut);
		log("Arrivés "+sFin);
		GrapheMatrix gm = new GrapheMatrix(this.fileGraph,null);
		gm.setLogger(this);
		gm.getPCC_dijkstra(sDebut, sFin);
	}

	protected void start() {
		System.out.println("start");
		this.labelResult.setText("");
		GrapheMatrix gm = new GrapheMatrix(this.fileGraph,this);
		
	}

	private void processFile() {
		System.out.println("processFile");
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this.frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.fileGraph = fc.getSelectedFile();
			System.out.println("File " + fileGraph.getAbsolutePath());
		} else {
		}
		displayFile();
	}

	private void displayFile() {
		try {
			FileReader fr = new FileReader(this.fileGraph);
			int i = 0;
			StringBuffer sb = new StringBuffer();
			while ((i = fr.read()) >= 0) {
				sb.append("" + (char) i);
			}
			this.labelFile.setText(sb.toString());
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void log(String s) {
		this.labelResult.append(s+"\n");
		System.out.println("log   :"+s);
	}

}
