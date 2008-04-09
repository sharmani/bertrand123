package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.bg.annuaire.tool.company.Company;

public class PanelSaisieAnnuairePro extends JPanel {

	private JTextArea textArea = new JTextArea(10,10);

	public PanelSaisieAnnuairePro() {
		super();
		Dimension dim = new Dimension(60,100);
		this.setPreferredSize(dim);
		this.setSize(dim);
		this.setLayout(new BorderLayout());
		this.add(textArea, BorderLayout.CENTER);
		this.add(new JLabel("Saisie Annuaire Pro"), BorderLayout.NORTH);
		JButton buttonSave = new JButton("Parse");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		this.add(buttonSave, BorderLayout.SOUTH);
	}

	private void save() {
		System.out.println("save pro");
		String text = this.textArea.getText();
		StringTokenizer st = new StringTokenizer(text,"\n");
		int i=0;
		int indexMail_Debut= text_indexOf(text,"mail :");
		int indexMail_Fin= indexMail_Debut+30;
		int indexAdresse_Fin = getMini(text.indexOf("tél :"), text.indexOf("E-mail "));
		
		int indexTel_Debut = text_indexOf(text,"tél :");
		int indexTel_Fin = indexTel_Debut+15;
		int indexFax_Debut= text_indexOf(text,"fax :");
		int indexFax_Fin= indexFax_Debut+15;
		int indexSiret_Debut= text_indexOf(text, "Siret :");
		int indexSiret_Fin= indexSiret_Debut+20;
		int indexNaf_Debut= text_indexOf(text,"Code NAF :");
		int indexNaf_Fin= indexNaf_Debut+7;
		int indexEffectif_Debut= text_indexOf(text,"Effectif Etabl :");
		int indexEffectif_Fin= indexEffectif_Debut+20;
		if (indexEffectif_Fin<0){
			indexEffectif_Fin=text.length();
		}
		Company c = new Company();
		while(st.hasMoreElements()){
			String line = st.nextToken();
			System.out.println("--- "+i+" --- "+line);
			if (i==0){
				String name = line.trim();
				name = name.replace("Demander un devis", "");
				name.trim();
				c.setName(name);
			}
			i++;
		}
		int indexAdressDebut=Math.max(c.getName().length(),text_indexOf(text,"demander un devis" ));
		String adresse = text_substring(text,c.getName().length()+1,indexAdresse_Fin);
		String telephone = text_substring(text,indexTel_Debut,indexTel_Fin);
		String fax = text_substring(text,indexFax_Debut,indexFax_Fin);
		String siret = text_substring(text,indexSiret_Debut,indexSiret_Fin);
		String naf = text_substring(text,indexNaf_Debut,indexNaf_Fin);
		String effectif = text_substring(text,indexEffectif_Debut,indexEffectif_Fin);
		String mail = text_substring(text,indexMail_Debut,indexMail_Fin);
		c.setAdresse(adresse);
		c.setTelephone(telephone);
		c.setFax(fax);
		c.setSiret(siret);
		c.setEMail(mail);
		c.setNaf(naf);
		c.setEffectif(effectif);
		System.out.println(" -- "+c.toStringDetail());
		ToolAnuaireGui.getInstance().displayDetailForValidation(c);
	}
	
	private int getMini(int i1, int i2) {
		if (i1 < 0){
			return i2;
		}if (i2 < 0){
			return i1;
		}
		return Math.min(i1,i2);
	}

	private int text_indexOf(String text, String search){
		int i = text.indexOf(search);
		if (i <0){
			return i;
		}
		return i+search.length();
	}
	private String text_substring(String text, int debut, int fin){
		if (debut <=0){
			return "";
		}
		if (fin < debut){
			return "";
		}
		if (fin > text.length()){
			fin = text.length();
		}
		String s = text.substring(debut,fin);
		return s.trim();
	}

	public void clean() { 
		this.textArea.setText("");
	}
}
