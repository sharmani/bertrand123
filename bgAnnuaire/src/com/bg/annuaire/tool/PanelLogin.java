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
	private JTextField textFieldIP = new JTextField("88.191.37.65");
	private JTextField textFieldBdd= new JTextField("annuaire_pro");;
	public PanelLogin() {
		super();
		JButton buttonLogin = new JButton("login");
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		JPanel panelT = new JPanel(new GridLayout(9,2));
		panelT.add(new JLabel());
		panelT.add(new JLabel());
		panelT.add(new JLabel("User"));
		panelT.add(this.textFieldUser);
		panelT.add(new JLabel("Password"));
		panelT.add(this.textFieldPAssword);
		panelT.add(new JLabel());
		panelT.add(new JLabel());
		panelT.add(new JLabel("Bdd"));
		panelT.add(this.textFieldBdd);
		panelT.add(new JLabel("IP"));
		panelT.add(this.textFieldIP);
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
			String login = this.textFieldUser.getText();
			ToolAnuaireGui.getInstance().setLogin(login);
			//UtilHibernateBg.getInstance().initHibernate(login,this.textFieldPAssword.getText());
			String ip =this.textFieldIP.getText().trim();
			String bdd =this.textFieldBdd.getText().trim();
			UtilHibernateBg.getInstance().initHibernate("shubaka","shubaka",bdd,ip);
			CompanyFactory.getInstance().updateListCompanyFromBdd();
			ToolAnuaireGui.getInstance().listCompanies();
			ToolAnuaireGui.getInstance().log("connecting bdd done size :"+CompanyFactory.getInstance().getList().size());
		} catch (Throwable e) {
			ToolAnuaireGui.getInstance().log("connecting bdd Exception "+e.getMessage());
			e.printStackTrace();
		}
	}

}
