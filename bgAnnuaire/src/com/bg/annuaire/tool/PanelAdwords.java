package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.bg.annuaire.test.UtilHibernateBg;
import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;
import com.bg.util2.email.smtp.ClientSmtp;
import com.bg.util2.spring.UtilSpring;

public class PanelAdwords extends JPanel {

	private JButton buttonSendMail = new JButton("SendMail");

	private JTextArea textArea = new JTextArea(20, 120);

	private File fileTemp = null;

	private JCheckBox checkBox = new JCheckBox("Armed");

	public PanelAdwords() {
		super();
		this.setLayout(new BorderLayout());
		buttonSendMail.setEnabled(false);
		buttonSendMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMail();
			}
		});
		
		JButton buttonEditMail = new JButton("Edit Mail");
		buttonEditMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editMail();
			}
		});
 
		
		JButton buttonEditObject = new JButton("Edit Object");
		buttonEditObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editObject();
			}
		});
		
		JButton buttonSendEditAdresseTest = new JButton("@ Test");
		buttonSendEditAdresseTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendEditMailTest();
			}
		});

		
		JButton buttonSendMAilTest = new JButton("Test");
		buttonSendMAilTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMailTest();
			}
		});
		
		JButton buttonDisplayHelp = new JButton("Help");
		buttonDisplayHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayHelp();
			}
		});
		JButton buttonStart = new JButton("startProcess");
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startProcess();
			}
		});
		JPanel panelT = new JPanel();
		panelT.setLayout(new BoxLayout(panelT, BoxLayout.Y_AXIS));
		panelT.add(new JLabel(""));
		panelT.add(buttonStart);
		panelT.add(new JLabel(""));
		panelT.add(buttonSendMail);
		panelT.add(new JLabel(" "));
		panelT.add(this.checkBox);
		panelT.add(new JLabel(" "));
		panelT.add(buttonEditMail );
		panelT.add(new JLabel(" "));
		panelT.add(buttonEditObject );
		panelT.add(new JLabel(" "));
		panelT.add(buttonDisplayHelp );
		panelT.add(new JLabel(" "));
		panelT.add(buttonSendEditAdresseTest);
		panelT.add(new JLabel(" "));
		panelT.add(buttonSendMAilTest);
		// JPanel panelButton = new JPanel();
		// panelButton.add(panelT);
		// Dimension dim = new Dimension(100,100);
		// this.textArea.setMinimumSize(dim);
		JPanel pTextArea = new JPanel(new GridLayout(1, 1));
		pTextArea.setBackground(Color.GREEN);
		// this.textArea.setPreferredSize(dim);
		JScrollPane scollPAneTextArea = new JScrollPane(this.textArea);
		pTextArea.add(scollPAneTextArea);
		this.add(panelT, BorderLayout.WEST);
		this.add(pTextArea, BorderLayout.CENTER);
		this.updateUI();
	}

	

	protected void displayHelp() {
		String help = "HELP\n";
		help += "Start examine la liste des société et génère le fichier  temporaire\n";
		help += "SendMAil Envoie pour chaque email le texte\n";
		help += "ShowTextEmail Edite le texte envoyer\n";
		help += "ShowObject Edite l'objet\n";
		help += "SendMAilTest Envoi un mail de test\n";
		help += "AdressTest edite l'adresse de test\n";
		this.textArea.setText(help);
	}

	private void startProcess() {
		System.out.println("Adwords Start Process");
		Object o1 = UtilSpring.getInstance().getBeanFactory().getBean("smtp");
		ClientSmtp clientSmtp = (ClientSmtp) o1;
		System.out.println("Adwords Start Process clientSmtp:" + clientSmtp);
		try {
			this.fileTemp = new File("TempSendEmail.xls");
			this.generateFileBuffer(this.fileTemp);
			buttonSendMail.setEnabled(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateFileBuffer(File f) throws Exception {
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();
		PrintStream ps = new PrintStream(f);
		List<Company> l = CompanyFactory.getInstance().getList();
		int i = 0;
		for (Company c : l) {
			if (c.getSite().length() == 0) {

			} else if (c.getEMail().length() == 0) {

			} else {
				textArea.append(i + "  | name:" + c.getName() + "  | site: " + c.getSite() + " | mail: " + c.getEMail() + " \n");
				ps.println(i + ";" + c.getName() + ";" + c.getSite() + ";" + c.getEMail());
				System.out.println("process:: " + i + "  :: " + c.getName() + "  " + c.getSite() + "  " + c.getEMail());
				i++;
			}
		}
		ps.close();
	}

	private void sendMail() {
		try {
			buttonSendMail.setEnabled(false);
			System.out.println("SendMail");
			FileReader fr = new FileReader(this.fileTemp);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ";");
				String iStr = st.nextToken();
				String name = st.nextToken();
				String site = st.nextToken();
				String mail = st.nextToken();
				System.out.println(" " + iStr + "  " + name + "   " + site + "  " + mail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendMailTest() {
		System.out.println("SendMailTest");
	}
	
	private void sendEditMailTest(){
		System.out.println("Edit MailTest");
	}
	
	protected void editObject() {
		System.out.println("editObject");
		
	}

	protected void editMail() {
		System.out.println("editMail");
		
	}


}
