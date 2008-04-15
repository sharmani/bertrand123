package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bg.annuaire.test.UtilHibernateBg;
import com.bg.annuaire.tool.company.CompanyFactory;


public class PanelLogin extends JPanel {

	
	private JTextField textFieldUser = new JTextField(12);
	private JPasswordField textFieldPAssword = new JPasswordField(12);
	public PanelLogin() {
		super();
		JButton buttonLogin = new JButton("login");
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		JPanel panelT = new JPanel(new GridLayout(8,2));
		panelT.add(new JLabel());
		panelT.add(new JLabel());
		panelT.add(new JLabel("User"));
		panelT.add(this.textFieldUser);
		panelT.add(new JLabel("Password"));
		panelT.add(this.textFieldPAssword);
		panelT.add(new JLabel());
		panelT.add(new JLabel());
		panelT.add(new JLabel());
		panelT.add(buttonLogin);
		panelT.add(new JLabel());
		panelT.add(new JLabel());
		JPanel panelTT = new JPanel();
		panelTT.add(panelT);
		
		this.add(panelTT);
		
		
		
	}
	
	private void login() {
		try {
			ToolAnuaireGui.getInstance().log("connecting bdd");
			System.out.println("login");
			UtilHibernateBg.getInstance().initHibernate(this.textFieldUser.getText(),this.textFieldPAssword.getText());
			CompanyFactory.getInstance().updateListCompanyFromBdd();
			ToolAnuaireGui.getInstance().listCompanies();
			ToolAnuaireGui.getInstance().log("connecting bdd done size :"+CompanyFactory.getInstance().getList().size());
		} catch (Throwable e) {
			ToolAnuaireGui.getInstance().log("connecting bdd Exception "+e.getMessage());
			e.printStackTrace();
		}
	}

}
