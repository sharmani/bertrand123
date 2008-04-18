package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;

public class PanelSaisie extends JPanel {

	private JTextField textFieldNAme = new JTextField(12);

	private JTextField textFieldSiret = new JTextField(12);

	private JTextField textFieldNaf = new JTextField(12);

	private JTextField textFieldTelephone = new JTextField(12);

	private JTextField textFieldFAx = new JTextField(12);

	private JTextField textFieldEffectif = new JTextField(12);

	private JTextField textFieldEmail = new JTextField(12);

	private JTextField textFieldSite = new JTextField(12);

	private JTextArea textAreaAdresse = new JTextArea(4, 12);

	private JTextArea textAreaComment = new JTextArea(1, 12);

	private Company companyCurrent;

	public PanelSaisie() {
		super();
		this.setLayout(new BorderLayout());
		JButton buttonBack = new JButton("<");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		JButton buttonNext = new JButton(">");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		JButton buttonNew = new JButton("new");
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTextField();
			}
		});
		JPanel panelNavigation = new JPanel();
		panelNavigation.setLayout(new GridLayout(1, 0));
		panelNavigation.add(buttonBack);
		panelNavigation.add(buttonNew);
		panelNavigation.add(buttonNext);

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

		p.add(this.createPanel("Nom", this.textFieldNAme));
		p.add(this.createPanel("Commentaire", this.textAreaComment));
		p.add(this.createPanel("Siret", this.textFieldSiret));
		p.add(this.createPanel("Naf", this.textFieldNaf));
		p.add(this.createPanel("Tel", this.textFieldTelephone));
		p.add(this.createPanel("FAx", this.textFieldFAx));
		p.add(this.createPanel("eMail", this.textFieldEmail));
		p.add(this.createPanel("Site", this.textFieldSite));
		p.add(this.createPanel("Effectifs", this.textFieldEffectif));
		p.add(this.createPanel("Adresse", this.textAreaAdresse));

		JButton buttonSave = new JButton("save");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		JButton buttonDelete = new JButton("Delete");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		JPanel panelSave = new JPanel();
		panelSave.add(buttonSave);
		panelSave.add(buttonDelete);
		p.add(panelSave);
		this.add(p, BorderLayout.CENTER);
		this.add(panelNavigation, BorderLayout.NORTH);
	}

	private Component createPanel(String name, JComponent c) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(name);
		Dimension dimLabel = new Dimension(80, 20);
		Dimension dimC = new Dimension(120, 20);
		label.setPreferredSize(dimLabel);
		c.setPreferredSize(dimC);
		panel.setLayout(new BorderLayout());
		panel.add(label, BorderLayout.WEST);
		panel.add(c, BorderLayout.CENTER);
		return panel;
	}

	private void initTextField() {
		this.companyCurrent = null;
		this.textAreaAdresse.setText("");
		this.textFieldEffectif.setText("");
		this.textFieldEmail.setText("");
		this.textFieldFAx.setText("");
		this.textFieldNaf.setText("");
		this.textFieldNAme.setText("");
		this.textFieldSiret.setText("");
		this.textFieldSite.setText("");
		this.textFieldTelephone.setText("");
		this.textAreaComment.setText("");
	}

	private boolean checkBeforeCreateNewCompany() {
		String name = this.textFieldNAme.getText();
		String siret = this.textFieldSiret.getText();
		String telephone = this.textFieldTelephone.getText();
		List<Company> cNAmes = CompanyFactory.getInstance().getCompaniesByName(name);
		List<Company> cSirets = CompanyFactory.getInstance().getCompaniesBySiret(siret);
		List<Company> cTelephones = CompanyFactory.getInstance().getCompaniesByTelephone(telephone);
		boolean r = true;
		if (cNAmes.size() > 0) {
			r = false;
		}
		if (cSirets.size() > 0) {
			if (this.textFieldSiret.getText().trim().length() > 0) {
				r = false;
			}
		}
		if (cTelephones.size() > 0) {
			if (this.textFieldTelephone.getText().trim().length() > 0) {
				r = false;
			}
		}
		if (!r) {
			Object[] o = { "Save", "Annul", "Compare" };
			String warning = "Des sociétes ont les même caracterisiques:";
			warning += "\n même nom : " + cNAmes.size();
			warning += "\n même téléphone: " + cTelephones.size();
			warning += "\n même No Siret: " + cSirets.size();
			int i = JOptionPane.showOptionDialog(this, warning, "zorg", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, o, null);
			System.out.println("option : i" + i);
			if (i == 0) {
				r = true;
			} else if (i == 1) {
				r = false;
			} else if (i == 2) {
				System.out.println("!Compare No Implemented yet!");
				r = false;
			}
		}
		return r;
	}

	private void save() {

		if (this.companyCurrent == null) {
			this.checkBeforeCreateNewCompany();
			this.companyCurrent = new Company();
			CompanyFactory.getInstance().add(this.companyCurrent);

		}
		companyCurrent.setAdresse(this.textAreaAdresse.getText());
		companyCurrent.setEMail(this.textFieldEmail.getText());
		companyCurrent.setFax(this.textFieldFAx.getText());
		companyCurrent.setNaf(this.textFieldNaf.getText());
		companyCurrent.setName(this.textFieldNAme.getText());
		companyCurrent.setSiret(this.textFieldSiret.getText());
		companyCurrent.setSite(this.textFieldSite.getText());
		companyCurrent.setTelephone(this.textFieldTelephone.getText());
		companyCurrent.setEffectif(this.textFieldEffectif.getText());
		companyCurrent.setComment(this.textAreaComment.getText());

		companyCurrent.save();
		this.initTextField();
	}

	private void delete() {
		if (this.companyCurrent == null) {
		} else {
			int i = JOptionPane.showConfirmDialog(this, "Confirm delete ?");
			System.out.println(" JOptionPane. return i:: " + i);
			if (i == JOptionPane.YES_OPTION) {
				CompanyFactory.getInstance().delete(this.companyCurrent);
			}
			//CompanyFactory.getInstance().saveAsXml();
		}
		this.initTextField();
	}

	private void back() {
		System.out.println("back");
		this.companyCurrent = CompanyFactory.getInstance().getPreviousCompany(this.companyCurrent);
		this.update();
	}

	private void next() {
		System.out.println("next");
		this.companyCurrent = CompanyFactory.getInstance().getNextCompany(this.companyCurrent);
		this.update();
	}

	private void update() {
		if (this.companyCurrent == null) {
			this.initTextField();
			return;
		}
		displayCompany(this.companyCurrent);
	}

	public void displayCompany(Company c) {
		this.textAreaAdresse.setText(c.getAdresse());
		this.textFieldEffectif.setText(c.getEffectif());
		this.textFieldEmail.setText(c.getEMail());
		this.textFieldFAx.setText(c.getFax());
		this.textFieldNaf.setText(c.getNaf());
		this.textFieldNAme.setText(c.getName());
		this.textFieldSiret.setText(c.getSiret());
		this.textFieldSite.setText(c.getSite());
		this.textFieldTelephone.setText(c.getTelephone());
		this.textAreaComment.setText(c.getComment());
	}

	public Company getCompanyCurrent() {
		return companyCurrent;
	}

	public void setCompanyCurrent(Company companyCurrent) {
		this.companyCurrent = companyCurrent;
		if (this.companyCurrent == null) {
		} else {
			this.displayCompany(companyCurrent);
		}
	}

}
