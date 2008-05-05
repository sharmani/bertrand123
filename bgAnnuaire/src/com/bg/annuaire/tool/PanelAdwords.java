package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
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

import org.springframework.beans.factory.BeanFactory;

import com.bg.annuaire.test.UtilHibernateBg;
import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;
import com.bg.util2.email.smtp.ClientSmtp;
import com.bg.util2.spring.UtilSpring;

public class PanelAdwords extends JPanel {

	private static final String P_LOG = "log";

	private static final String P_OBJECT = "object";

	private static final String P_MAIL_TEST = "mailTest";

	private static final String P_MAIL = "mail";

	private static final String P_HELP = "help";
	private static final String P_FROM = "from";

	private static final String K_From = "from";

	private CardLayout cardLayoutPanelCenter = new CardLayout();

	private JPanel panelCenter;

	private JButton buttonSendMail = new JButton("SendMail");

	private JLabel labelCommand = new JLabel("");

	private JTextArea textAreaLog = new JTextArea("Log", 20, 120);

	private JTextArea textAreaObject = new JTextArea("object", 20, 120);

	private JTextArea textAreaMAilTest = new JTextArea("MAilTest", 20, 120);

	private JTextArea textAreaMAil = new JTextArea("MAil", 20, 120);
	
	private JTextArea textAreaFrom = new JTextArea("From", 20, 120);

	private JTextArea textAreaHelp = new JTextArea("Help", 20, 120);

	private File fileTemp = null;

	private File fileSauvegarde = new File("saveMail");

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
		buttonEditMail.setToolTipText("Edit le corps du mail");
		buttonEditMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editMail();
			}
		});

		JButton buttonEditObject = new JButton("Edit Object");
		buttonEditObject.setToolTipText("L'objet du mail");
		buttonEditObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editObject();
			}
		});

		JButton buttonSendEditAdresseTest = new JButton("@ Test");
		buttonSendEditAdresseTest.setToolTipText("Adresse d'un email de test");
		buttonSendEditAdresseTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendEditMailTest();
			}
		});

		JButton buttonSendMAilTest = new JButton("Test");
		buttonSendMAilTest.setToolTipText("Envoi d'un email de test");
		buttonSendMAilTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMailTest();
			}
		});

		JButton buttonDisplayHelp = new JButton("Help");
		buttonSendMAilTest.setToolTipText("Affiche un help");
		buttonDisplayHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayHelp();
			}
		});

		JButton buttonLog = new JButton("log");
		buttonLog.setToolTipText("Montrer les logs");
		buttonLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLog();
			}
		});

		JButton buttonSave = new JButton("save");
		buttonSave.setToolTipText("Démarrer le process");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		JButton buttonFrom = new JButton("From");
		buttonFrom.setToolTipText("Définir l'expediteur du message");
		buttonFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFrom();
			}
		});

		JButton buttonStart = new JButton("startProcess");
		buttonStart.setToolTipText("Démarrer le process");
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
		panelT.add(buttonEditMail);
		panelT.add(new JLabel(" "));
		panelT.add(buttonEditObject);
		panelT.add(new JLabel(" "));
		panelT.add(buttonFrom);
		panelT.add(new JLabel(" "));
		panelT.add(buttonDisplayHelp);
		panelT.add(new JLabel(" "));
		panelT.add(buttonSendEditAdresseTest);
		panelT.add(new JLabel(" "));
		panelT.add(buttonSendMAilTest);
		panelT.add(new JLabel(" "));
		panelT.add(buttonLog);
		panelT.add(new JLabel(" "));
		panelT.add(buttonSave);
		// JPanel panelButton = new JPanel();
		// panelButton.add(panelT);
		// Dimension dim = new Dimension(100,100);
		// this.textArea.setMinimumSize(dim);
		JPanel pTextArea = new JPanel(new GridLayout(1, 1));
		pTextArea.setBackground(Color.GREEN);
		// this.textArea.setPreferredSize(dim);
		JScrollPane scollPAneTextAreaLog = new JScrollPane(this.textAreaLog);
		pTextArea.add(scollPAneTextAreaLog);
		panelCenter = new JPanel(this.cardLayoutPanelCenter);
		panelCenter.add(pTextArea, P_LOG);
		panelCenter.add(textAreaMAil, P_MAIL);
		panelCenter.add(textAreaMAilTest, P_MAIL_TEST);
		panelCenter.add(textAreaObject, P_OBJECT);
		panelCenter.add(textAreaMAil, P_MAIL);
		panelCenter.add(textAreaHelp, P_HELP);
		panelCenter.add(textAreaFrom, P_FROM);
		JPanel panelCenter2 = new JPanel(new BorderLayout());
		this.labelCommand.setBackground(Color.BLUE);
		this.labelCommand.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelCenter2.add(this.labelCommand, BorderLayout.NORTH);
		panelCenter2.add(panelCenter, BorderLayout.CENTER);
		// add(panelCenter, BorderLayout.CENTER);
		this.add(panelT, BorderLayout.WEST);
		this.add(panelCenter2, BorderLayout.CENTER);
		this.showCard(P_LOG);
		this.updateUI();
		this.initFromFile();
	}

	

	private static String K_adress_mail_test = "addressMailTest";

	private static String K_Objectt = "object";

	private static String K_Mail = "mail";

	protected void save() {
		try {
			this.showCard(P_LOG);
			this.textAreaLog.setText("save");
			Properties p = new Properties();
			p.setProperty(K_adress_mail_test, this.textAreaMAilTest.getText());
			p.setProperty(K_Objectt, this.textAreaObject.getText());
			p.setProperty(K_Mail, this.textAreaMAil.getText());
			FileWriter fw = new FileWriter(fileSauvegarde);
			p.store(fw, "");
			fw.close();
			this.textAreaLog.setText("save done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void initFromFile() {
		try {
			this.showCard(P_LOG);
			this.textAreaLog.setText("initFromFile\n");
			this.textAreaLog.append("file :" + this.fileSauvegarde.getAbsolutePath() + " exists:" + this.fileSauvegarde.exists());
			Properties p = new Properties();
			if (!fileSauvegarde.exists()) {
				this.textAreaLog.append("File doen't exists\n");
			} else {
				FileReader fr = new FileReader(fileSauvegarde);
				p.load(fr);
			}
			this.textAreaMAilTest.setText(p.getProperty(K_adress_mail_test, ""));
			this.textAreaObject.setText(p.getProperty(K_Objectt, ""));
			this.textAreaMAil.setText(p.getProperty(K_Mail, ""));
			this.textAreaFrom.setText(p.getProperty(K_From, "adwords@java-consultant.com"));
		} catch (Exception e) { 
			this.textAreaLog.append("" + e);
			e.printStackTrace();
		}
	}

	protected void displayLog() {
		this.showCard(P_LOG);
	}

	protected void displayHelp() {
		this.showCard(P_HELP);
		String help = "HELP\n";
		help += "Start examine la liste des société et génère le fichier  temporaire\n";
		help += "SendMAil Envoie pour chaque email le texte\n";
		help += "ShowTextEmail Edite le texte envoyer\n";
		help += "ShowObject Edite l'objet\n";
		help += "SendMAilTest Envoi un mail de test\n";
		help += "AdressTest edite l'adresse de test\n";
		this.textAreaHelp.setText(help);
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
				textAreaLog.append(i + "  | name:" + c.getName() + "  | site: " + c.getSite() + " | mail: " + c.getEMail() + " \n");
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
		this.showCard(P_LOG);
		String to = this.textAreaMAilTest.getText();
		String object = this.textAreaObject.getText();
		String text = this.textAreaMAil.getText();
		String from = this.textAreaFrom.getText();
		this.textAreaLog.setText("sendMAilTest \n");
		this.sendMail(to, object, text, from, "http://bertrand.guiral.free.fr");
	}

	private void sendMail(String to, String subject, String text, String from, String dollard_1) {
		text = text.replace("$1", dollard_1);
		this.textAreaLog.append("sendMAilTest to : "+to+"  subject : "+subject+" text.length : "+text.length()+"\n");
		ClientSmtp clientSmtp = this.getClientSmtp();
		//clientSmtp.sendMessageHtml(to, subject+" ", text, from);
		clientSmtp.sendMessage(to, subject+" ", text, from);
		System.out.println("sendMAil to:"+to+" object: "+subject+"  from: "+from);
	}

	private void sendEditMailTest() {
		System.out.println("Edit MailTest");
		this.showCard(P_MAIL_TEST);
	}
	
	protected void setFrom() {
		this.showCard(P_FROM);
	}

	protected void editObject() {
		System.out.println("editObject");
		this.showCard(P_OBJECT);
	}

	protected void editMail() {
		System.out.println("editMail");
		this.showCard(P_MAIL);
	}

	private void showCard(String k) {
		System.out.println("showCard : ------------------- : " + k);
		this.labelCommand.setText(k);
		cardLayoutPanelCenter.show(panelCenter, k);
	}
	
	private ClientSmtp getClientSmtp(){
		BeanFactory beanFactory = UtilSpring.getInstance().getBeanFactory();
		Object o = beanFactory.getBean("smtp");
		return (ClientSmtp) o;
	}

}
