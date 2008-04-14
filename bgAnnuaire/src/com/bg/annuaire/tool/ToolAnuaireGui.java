package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;

public class ToolAnuaireGui {

	final static String CHOICE_PANEL_DETAIL = "detail";

	final static String CHOICE_PANEL_PRO = "Pro";
	final static String CHOICE_PANEL_SICOVAL = "Sicoval";

	final static String CHOICE_LIST_COMPANIES = "list";

	final static String CHOICE_LOGIN = "login";

	private static ToolAnuaireGui instance;

	private PanelSaisie panelSaisie = new PanelSaisie();

	private PanelSaisieAnnuairePro panelSaisieAnnuairePro = new PanelSaisieAnnuairePro();
	private PanelSaisieAnnuaireSicoval panelSaisieSicoval = new PanelSaisieAnnuaireSicoval();

	private PanelListCompany panelListCompanies = new PanelListCompany();
	
	private PanelLogin panelLogin = new PanelLogin();

	private JPanel panelGlobal = new JPanel(new CardLayout());

	private JFrame frame = new JFrame();

	private JTextField textFieldInfos = new JTextField();

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
		JMenuItem menuUpdateFromBdd = new JMenuItem("update");
		menuUpdateFromBdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFromBdd();
			}
		});
		menuBar.add(menuSaisieDetail);
		menuBar.add(menuSaisieAnnuairePro);
		menuBar.add(menuSaisieAnnuaireSicoval);
		menuBar.add(menuItemToHtml);
		menuBar.add(menuList);
		menuBar.add(menuCleanBdd);
		menuBar.add(menuCommit);
		menuBar.add(menuUpdateFromBdd);
		frame.setJMenuBar(menuBar);
		JPanel panelGlobal2 = new JPanel(new BorderLayout());
		panelGlobal2.add(panelGlobal, BorderLayout.CENTER);
		panelGlobal2.add(textFieldInfos, BorderLayout.SOUTH);
		frame.add(panelGlobal2);
		frame.setTitle("annuaire");
		frame.pack();
		frame.setVisible(true);
	}

	private void toHtml() {
		CompanyFactory.getInstance().toHtml();
		this.log(" Generate html done");

	}

	private void displaySaisieDetail() {
		CardLayout cl = (CardLayout) (this.panelGlobal.getLayout());
		cl.show(panelGlobal, CHOICE_PANEL_DETAIL);
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
		System.out.println("ListCompanies size:"+CompanyFactory.getInstance().getList().size());
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
			this.log("Connecting bdd done "+CompanyFactory.getInstance().getListCompanies().size());			
		} catch (Throwable e) {
			this.log("Exception "+e.getMessage());
			e.printStackTrace();
		}
	}

}
