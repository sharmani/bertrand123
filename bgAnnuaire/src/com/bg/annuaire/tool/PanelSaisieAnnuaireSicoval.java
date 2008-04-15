package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;
import com.bg.util2.StringTokenizerBg;

public class PanelSaisieAnnuaireSicoval extends JPanel {

	private JTextArea textArea = new JTextArea(10, 10);

	private int iParse = 0;

	public PanelSaisieAnnuaireSicoval() {
		super();
		Dimension dim = new Dimension(60, 100);
		this.setPreferredSize(dim);
		this.setSize(dim);
		this.setLayout(new BorderLayout());
		this.add(textArea, BorderLayout.CENTER);
		this.add(new JLabel("Saisie Annuaire Sicoval"), BorderLayout.NORTH);
		JButton buttonSave = new JButton("Parse");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		this.add(buttonSave, BorderLayout.SOUTH);
	}

	private void save() {
		try {
			this.iParse = 0;
			String text = this.textArea.getText();
			Reader r = new StringReader(text);
			BufferedReader br = new BufferedReader(r);
			String line;
			String telephone = "";
			String contact = "";
			String classification = "";
			String name = "";
			String addresse = "";
			boolean endDiv = false;
			String[] vLines = new String[5];
			int k = 0;
			while ((line = br.readLine()) != null) {

				if (line.trim().length() <= 0) {
				} else if (line.trim().charAt(0) <= '0') {
				} else if (line.trim().startsWith("Tél :")) {
					telephone = line;
				} else if (line.trim().startsWith("Contacts")) {
					contact = line;
					endDiv = true;
				} else if (line.trim().startsWith("Classification ")) {
					classification = line;
				} else if (line.trim().startsWith("Classification ")) {
					classification = line;
				} else {
					
					if (k < vLines.length) {
						vLines[k] = line.trim();
						k++;
					}
				}
				
				if (endDiv) {
					endDiv = false;
					k = 0;
					name = vLines[0];
					addresse = vLines[1];
					vLines[0]="";
					vLines[1]="";
					saveOneCompany_1(name, addresse, telephone, contact, classification);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveOneCompany_1(String nameS, String addresse, String telephoneS, String contactS, String classificationS) {
		System.out.println("--------------------------------------------");
		String name = getFirstToken(nameS);
		String telephoneLine = getSecondToken(telephoneS);
		String contact = getSecondToken(contactS);
		String mail = getMailFromLine(telephoneLine);
		String site = getSiteFromLine(telephoneLine);
		String telephone = getTelephoneFromLine(telephoneLine);
		String codePostal = getCodePostal(addresse);
		String ville = getVille(addresse);
		String classification = getSecondToken(classificationS);
		saveOneCompany_2(name, addresse, telephone, mail, contact, classification, codePostal, ville, site);
	}

	private String getCodePostal(String adresse) {
		if (adresse == null) {
			return "";
		}
		StringTokenizer st = new StringTokenizer(adresse, "-");
		String lastToken = "";
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}
		StringTokenizer st2 = new StringTokenizer(lastToken, " ");
		if (st2.hasMoreTokens()) {
			return st2.nextToken();
		}
		return "";
	}

	private String getVille(String adresse) {
		if (adresse == null) {
			return "";
		}
		StringTokenizer st = new StringTokenizer(adresse, "-");
		String lastToken = "";
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}
		StringTokenizer st2 = new StringTokenizer(lastToken, " ");
		if (st2.hasMoreTokens()) {
			st2.nextToken();
		}
		if (st2.hasMoreTokens()) {
			return st2.nextToken();
		}
		return "";
	}

	// Tél : 05 61 55 58 04 - contact@urosphere.com - www.urosphere.com

	private String getMailFromLine(String s) {
		if (s == null) {
			return "";
		}
		StringTokenizerBg st = new StringTokenizerBg(s, " - ");
		while (st.hasMoreTokens()) {
			String t = st.nextToken();
			t = t.trim();
			if (t.indexOf('@') > 0) {
				return t;
			}
		}
		return "";
	}

	private String getTelephoneFromLine(String s) {
		if (s == null) {
			return "";
		}
		StringTokenizer st = new StringTokenizer(s, "-:");
		while (st.hasMoreTokens()) {
			String t = st.nextToken();
			t = t.trim();
			if (isNumerique(t)) {
				return t;
			}
		}
		return "";
	}

	private boolean isNumerique(String t) {
		if (t == null) {
			return false;
		}
		for (int i = 0; i < t.length(); i++) {
			char c = t.charAt(i);
			if (c == ' ') {
			} else if (c < '0') {
				return false;
			}
			if (c > '9') {
				return false;
			}
		}
		return true;
	}

	private String getSiteFromLine(String s) {
		if (s == null) {
			return "";
		}
		StringTokenizerBg st = new StringTokenizerBg(s, " - ");
		while (st.hasMoreTokens()) {
			String t = st.nextToken();
			t = t.trim();
			if (t.indexOf('@') > 0) {
			} else {
				if (t.indexOf(".fr") > 0) {
					return t;
				}
				if (t.indexOf(".com") > 0) {
					return t;
				}
				if (t.indexOf("www.") > 0) {
					return t;
				}
			}
		}
		return "";
	}

	private String getFirstToken(String s) {
		if (s == null) {
			return "";
		}
		s = s.trim();
		if (s.length() == 0) {
			return "";
		}
		StringTokenizerBg st = new StringTokenizerBg(s, " - ");
		return st.nextToken();
	}

	private String getSecondToken(String s) {
		if (s == null) {
			return "";
		}
		s = s.trim();
		if (s.length() == 0) {
			return "";
		}
		StringTokenizer st = new StringTokenizer(s, ":");
		if (st.hasMoreTokens()) {
			st.nextToken();
		}
		if (st.hasMoreTokens()) {
			return st.nextToken();
		}
		return "";

	}

	private void saveOneCompany_2(String name, String addresse, String telephone, String mail, String contacts, String classification, String codePostal, String ville, String site) {
		System.out.println("save Sicoval");

		Company c = new Company();

		c.setAdresse(addresse);
		c.setTelephone(telephone);
		c.setFax("");
		c.setSiret("");
		c.setEMail(mail);
		c.setNaf("");
		c.setEffectif("");
		c.setCodePostal(codePostal);
		c.setVille(ville);
		c.setSite(site);
		c.setName(name);
		c.setContacts(contacts);
		c.setClassification(classification);
		System.out.println(" -----------------------------------------------\n " + c.toStringDetail());
		// ToolAnuaireGui.getInstance().displayDetailForValidation(c);
		if (CompanyFactory.getInstance().existSimilaireInsideBdd(c)) {
			CompanyFactory.getInstance().merge(c);

		} else {
			CompanyFactory.getInstance().add(c);
		}
		iParse++;
		ToolAnuaireGui.getInstance().log(iParse + " parse sicoval: " + c.getName());
	}

	public void clean() {
		this.textArea.setText("");
	}
}
