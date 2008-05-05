package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bg.annuaire.test.UtilHibernateBg;
import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;


public class PanelAdwords extends JPanel {

	
	public PanelAdwords() {
		super();
		JButton buttonLogin = new JButton("startProcess");
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startProcess();
			}
		});
		JPanel panelT = new JPanel(new GridLayout(9,2));
		panelT.add(new JLabel());	
		panelT.add(new JLabel());	
		
		panelT.add(new JLabel());	
		panelT.add(buttonLogin);	
		
		panelT.add(new JLabel());	
		panelT.add(new JLabel());	
		
		panelT.add(new JLabel());	
		panelT.add(new JLabel());	
		this.add(panelT);
	}
	
	private void startProcess() {
		System.out.println("Adwords Start Process");
		List<Company> l = CompanyFactory.getInstance().getList();
		int i=0;
		for(Company c:l){
			if (c.getSite().length()==0){
				
			}else if (c.getEMail().length()==0){
				
			}else {
				System.out.println("process:: "+i+"  :: "+c.getName()+"  "+c.getSite()+"  "+c.getEMail());
				i++;
			}
		}
	}

}
