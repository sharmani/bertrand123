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

	private JTextArea textArea = new JTextArea(20,120);
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
		JButton buttonStart = new JButton("startProcess");
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startProcess();
			}
		});
		JPanel panelT = new JPanel();
		panelT.setLayout(new BoxLayout(panelT,BoxLayout.Y_AXIS));
		panelT.add(new JLabel(""));
		panelT.add(buttonStart);
		panelT.add(new JLabel(""));
		panelT.add(buttonSendMail);
		panelT.add(new JLabel(""));
		panelT.add(this.checkBox);
		//JPanel panelButton = new JPanel();
		//panelButton.add(panelT);
		//Dimension dim = new Dimension(100,100);
		//this.textArea.setMinimumSize(dim);
		JPanel pTextArea = new JPanel(new GridLayout(1,1));
		pTextArea.setBackground(Color.GREEN);
		//this.textArea.setPreferredSize(dim);
		JScrollPane scollPAneTextArea = new JScrollPane(this.textArea);
		pTextArea.add(scollPAneTextArea);
		this.add(panelT, BorderLayout.WEST);
		this.add(pTextArea, BorderLayout.CENTER);
		this.updateUI();
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
				textArea.append(i + "  | name:" + c.getName() + "  | site: " + c.getSite() + " | mail: " + c.getEMail()+" \n");
				ps.println(i + ";" + c.getName() + ";" + c.getSite() + ";" + c.getEMail());
				System.out.println("process:: " + i + "  :: " + c.getName() + "  " + c.getSite() + "  " + c.getEMail());
				i++;
			}
		}
		ps.close();
	}

	private void sendMail(){
		try {
			buttonSendMail.setEnabled(false);
			System.out.println("SendMail");
			FileReader fr = new FileReader(this.fileTemp);
			BufferedReader br = new BufferedReader(fr);
			String line ;
			while ((line=br.readLine())!=null){
				StringTokenizer st = new StringTokenizer(line,";");
				String iStr = st.nextToken();
				String name = st.nextToken();
				String site = st.nextToken();
				String mail = st.nextToken();
				System.out.println(" "+iStr+"  "+name+"   "+site+"  "+mail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
