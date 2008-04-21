package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;
import com.bg.annuaire.tool.fileFilter.FileFilterBg;

public class ToolAnuaireGui {

	final static String CHOICE_PANEL_DETAIL = "detail";

	final static String CHOICE_PANEL_PRO = "Pro";

	final static String CHOICE_PANEL_SICOVAL = "Sicoval";

	final static String CHOICE_LIST_COMPANIES = "list";

	final static String CHOICE_LOGIN = "login";

	private File fCurrentDirectory = new File(".");

	private static ToolAnuaireGui instance;

	private PanelSaisie panelSaisie = new PanelSaisie();

	private PanelSaisieAnnuairePro panelSaisieAnnuairePro = new PanelSaisieAnnuairePro();

	private PanelSaisieAnnuaireSicoval panelSaisieSicoval = new PanelSaisieAnnuaireSicoval();

	private PanelListCompany panelListCompanies = new PanelListCompany();

	private PanelLogin panelLogin = new PanelLogin();

	private JPanel panelGlobal = new JPanel(new CardLayout());

	private JFrame frame = new JFrame();

	private JTextField textFieldInfos = new JTextField();
	
	private JFileChooser fileChooser = new JFileChooser(fCurrentDirectory);
	private FileFilterBg filterXml = new FileFilterBg("xml");
	private FileFilterBg filterExcel = new FileFilterBg("CSV","Excell CSV");
	private FileFilterBg filterHtml = new FileFilterBg("html");
	

	public ToolAnuaireGui() {
		instance = this;
		frame.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				System.out.println("byby");
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
		panelGlobal.add(panelLogin, CHOICE_LOGIN);
		panelGlobal.add(panelSaisie, CHOICE_PANEL_DETAIL);
		panelGlobal.add(panelSaisieAnnuairePro, CHOICE_PANEL_PRO);
		panelGlobal.add(panelSaisieSicoval, CHOICE_PANEL_SICOVAL);
		panelGlobal.add(panelListCompanies, CHOICE_LIST_COMPANIES);
		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuItemToExcel = new JMenuItem("to Excel");
		menuItemToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toExcel();
			}
		});
		JMenuItem menuItemConnectBdd = new JMenuItem("connect Bdd");
		menuItemConnectBdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectBdd();
			}
		});

		JMenuItem menuItemToHtml = new JMenuItem("to Html");
		menuItemToHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toHtml();
			}
		});
		JMenuItem menuSaisieAnnuaireSicoval = new JMenuItem("saisie Sicoval");
		menuSaisieAnnuaireSicoval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displaySaisieAnnuaireSicoval();
			}
		});

		JMenuItem menuSaisieAnnuairePro = new JMenuItem("saisie Pro");
		menuSaisieAnnuairePro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displaySaisieAnnuairePro();
			}
		});
		JMenuItem menuSaisieDetail = new JMenuItem("saisie Detail");
		menuSaisieDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displaySaisieDetail();
			}
		});

		JMenuItem menuList = new JMenuItem("list");
		menuList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listCompanies();
			}

		});

		JMenuItem menuCleanBdd = new JMenuItem("clean Bdd");
		menuCleanBdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanBdd();
			}
		});
		JMenuItem menuCommit = new JMenuItem("commit");
		menuCommit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commit();
			}
		});
		JMenu menuFile = new JMenu("File");

		JMenuItem menuSave = new JMenuItem("save");
		menuSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		menuFile.add(menuSave);

		JMenuItem menuUpdateFromBdd = new JMenuItem("update");
		menuUpdateFromBdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFromBdd();
			}
		});
		menuBar.add(menuFile);
		menuBar.add(menuSaisieDetail);
		menuBar.add(menuSaisieAnnuairePro);
		menuBar.add(menuSaisieAnnuaireSicoval);
		menuBar.add(menuItemToHtml);
		menuBar.add(menuItemToExcel);
		menuBar.add(menuList);
		menuBar.add(menuCleanBdd);
		menuBar.add(menuCommit);
		menuBar.add(menuUpdateFromBdd);
		menuBar.add(menuItemConnectBdd);
		frame.setJMenuBar(menuBar);
		JPanel panelGlobal2 = new JPanel(new BorderLayout());
		panelGlobal2.add(panelGlobal, BorderLayout.CENTER);
		panelGlobal2.add(textFieldInfos, BorderLayout.SOUTH);
		frame.add(panelGlobal2);
		frame.setTitle("annuaire");
		frame.pack();
		frame.setVisible(true);
		initSaveFileChooser();
	}

	protected void connectBdd() {
		System.out.println("connectBdd");
		this.displayLogin();
	}


	private void toHtml() {
		CompanyFactory.getInstance().toHtml();
		this.log(" Generate html done");

	}

	private void toExcel() {
		File f = CompanyFactory.getInstance().toExcel();
		toExcel(f);
	}

	private void toExcel(File f) {
		CompanyFactory.getInstance().toExcel(f);
		this.log(" Generate exceldone ");
	}

	private void displaySaisieDetail() {
		CardLayout cl = (CardLayout) (this.panelGlobal.getLayout());
		cl.show(panelGlobal, CHOICE_PANEL_DETAIL);
		this.panelGlobal.repaint();
	}

	private void displayLogin() {
		CardLayout cl = (CardLayout) (this.panelGlobal.getLayout());
		cl.show(panelGlobal, CHOICE_LOGIN);
		this.panelGlobal.repaint();
	}

	private void displaySaisieAnnuairePro() {
		System.out.println("displaySaisieAnnuairePro");
		CardLayout cl = (CardLayout) (this.panelGlobal.getLayout());
		this.panelSaisieAnnuairePro.clean();
		cl.show(panelGlobal, CHOICE_PANEL_PRO);
		this.panelGlobal.repaint();
	}

	private void displaySaisieAnnuaireSicoval() {
		System.out.println("displaySicoval");
		CardLayout cl = (CardLayout) (this.panelGlobal.getLayout());
		this.panelSaisieSicoval.clean();
		cl.show(panelGlobal, CHOICE_PANEL_SICOVAL);
		this.panelGlobal.repaint();
	}

	public static ToolAnuaireGui getInstance() {
		return instance;
	}

	public void displayDetailForValidation(Company c) {
		this.panelSaisie.setCompanyCurrent(null);
		this.panelSaisie.displayCompany(c);
		this.displaySaisieDetail();

	}

	public JFrame getFrame() {
		return frame;
	}

	private void cleanBdd() {
		CompanyFactory.getInstance().cleanBdd();
		this.log(" nb companies : " + CompanyFactory.getInstance().getList().size());
	}

	private void commit() {
		CompanyFactory.getInstance().commit();
		this.log("commit nb companies : " + CompanyFactory.getInstance().getList().size());
	}

	public void log(String text) {
		this.textFieldInfos.setText(text);
	}

	public void listCompanies() {
		System.out.println("ListCompanies size:" + CompanyFactory.getInstance().getList().size());
		CardLayout cl = (CardLayout) (this.panelGlobal.getLayout());
		this.panelListCompanies.updateList();
		cl.show(panelGlobal, CHOICE_LIST_COMPANIES);
	}

	public void editCompany(Company c) {
		System.out.println("editCompany  " + c);
		this.panelSaisie.setCompanyCurrent(c);
		this.displaySaisieDetail();
	}

	private void updateFromBdd() {
		try {
			this.log("Connecting bdd");
			CompanyFactory.getInstance().updateListCompanyFromBdd();
			this.log("Connecting bdd done " + CompanyFactory.getInstance().getListCompanies().size());
		} catch (Throwable e) {
			this.log("Exception " + e.getMessage());
			e.printStackTrace();
		}
	}
		
	private void save() {
		int returnVal = fileChooser.showSaveDialog(this.frame);
		//int returnVal = fileChooser.showOpenDialog(this.frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			System.out.println("DileSelected: " + file + "  exists: " + file.exists());
			// This is where a real application would open the file.
			log("Opening: " + file.getName());
			if (filterExcel.accept(file)) {
				this.toExcel(file);
			} else if (filterXml.accept(file)) {
				CompanyFactory.getInstance().saveAsXml(file);
			} else if (filterHtml.accept(file)) {
				CompanyFactory.getInstance().toHtml(file);
			}
		} else {
			log("Open command cancelled by user.");
		}

	}
	
	
	private void  initSaveFileChooser(){
		long time0 = System.currentTimeMillis();
		System.out.println("save processing .");
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		long time1 = System.currentTimeMillis();
		System.out.println("save processing .." + (time1 - time0) + " ms");

		String fileName = "bddAnuaire." + filterExcel.getExtension();
		File fSelected = new File(fCurrentDirectory, fileName);
		System.out.println(" fc.getSelectedFile  AA:"+fileChooser.getSelectedFile());
		fileChooser.setDialogTitle("Save ");
		fileChooser.setName("nameZoooooorg");
		long time2 = System.currentTimeMillis();
		fileChooser.setMultiSelectionEnabled(false);
		System.out.println("save processing ..." + (time2 - time1) + " ms "+fileChooser.getSelectedFile());

		fileChooser.addChoosableFileFilter(filterXml);
		fileChooser.addChoosableFileFilter(filterHtml);
		fileChooser.addChoosableFileFilter(filterExcel);

		long time3 = System.currentTimeMillis();
		fileChooser.setSelectedFile(fSelected);
		
		System.out.println("save processing .... " + (time3 - time2) + " ms   "+fileChooser.getSelectedFile());
		fileChooser.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("FileChanged "+evt.getOldValue()+"  "+evt.getNewValue()+"  "+evt.getSource().getClass());
				if (evt.getNewValue()== null){
					if (evt.getOldValue()!= null){
						JFileChooser fc = (JFileChooser)evt.getSource();
						File fOld = (File) evt.getOldValue();
						File dir = fc.getCurrentDirectory();
						File f = new File(dir, fOld.getName());
						fc.setSelectedFile(f);
						//File fNew = new File(fc.getS)
					}
				}
			}
		});
			
		fileChooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				FileFilterBg ff = (FileFilterBg) evt.getNewValue();				
				ff.changeExtensionFileSelected(fileChooser);
			}
		});
		
	}

}
