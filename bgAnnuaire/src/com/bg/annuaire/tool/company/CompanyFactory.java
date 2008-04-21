package com.bg.annuaire.tool.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bg.annuaire.test.UtilHibernateBg;

public class CompanyFactory {

	private Logger logger = Logger.getLogger("CompanyFactory");

	private List<Company> list = new ArrayList<Company>();

	private String fileName = "bddCompanies";

	private File fileXml = new File("bddAnnuaire.xml");

	private File fileHtml = new File("bddCompanies" + ".html");;

	private static CompanyFactory instance;

	public CompanyFactory() {
		super();
		instance = this;
	}

	public void initFromXML() {
		try {
			File f = new File(fileName + ".xml");
			System.out.println("CompanyFactory initFromXML " + f.getAbsolutePath() + "  exists:" + f.exists());

			if (!f.exists()) {
				return;
			}
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			URI uri = f.toURI();
			URL url = uri.toURL();
			InputStream in = url.openStream();
			Document document = documentBuilder.parse(in);
			this.parseConfigXml(document.getDocumentElement());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param element
	 */
	private void parseConfigXml(Element element) {
		String tagName;
		Element elementCurrent;
		Node nodeCurrent;
		NodeList childreen = element.getChildNodes();
		final int length = childreen.getLength();
		for (int loop = 0; loop < length; loop++) {
			nodeCurrent = childreen.item(loop);
			if (nodeCurrent.getNodeType() == Node.ELEMENT_NODE) {
				elementCurrent = (Element) nodeCurrent;
				tagName = elementCurrent.getTagName();
				try {
					this.parseElement(elementCurrent);
				} catch (Throwable e) {
					System.out.println("DomConfiguratorParametersFile.parseConfigXml EXCEPTION tagName:" + tagName);
					e.printStackTrace(System.out);
				}
			}
		}
	}

	private void parseElement(Element e) {
		Company company = new Company(e);
		this.list.add(company);
	}

	public synchronized void saveAsXml() {

		this.saveAsXml(this.fileXml);
	}

	public synchronized void saveAsXml(File f) {
		this.fileXml = new File(this.fileName + ".xml");
		if (f.exists()) {
			File fileOld = new File(this.fileName + "1.xml");
			f.renameTo(fileOld);
		}
		try {
			FileWriter fw = new FileWriter(f);
			// fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			fw.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
			fw.write("\n<companies>");
			for (Company company : this.list) {
				fw.write(company.toStringXml());
			}
			fw.write("\n</companies>");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("save done  " + f.getAbsolutePath());
	}

	public static CompanyFactory getInstance() {
		if (instance == null) {
			new CompanyFactory();
		}
		return instance;
	}

	public void add(Company company) {
		this.list.add(company);
	}

	public void toHtml() {
		toHtml(this.fileHtml);
	}

	public synchronized void toHtml(File f) {
		this.fileHtml = f;
		try {

			FileWriter fw = new FileWriter(f);
			fw.write("<html>");
			fw.write("\n<head> ");
			fw.write("\n<title> Bdd </title> ");
			fw.write("<STYLE type='text/css'>");
			fw.write("table{border-width: 1; border:  2px solid #ff0000;}");
			fw.write("tr{border-width: 1; border: solid;}");
			fw.write("td.naf{border-width: 1; border:  1px solid #000000;;white-space:nowrap;}");
			fw.write("td.telephone{border-width: 1; border:  2px solid #000000;white-space:nowrap;}");
			fw.write("td.name{border-width: 1; border:  2px solid #000000;white-space:nowrap;}");
			fw.write("</STYLE>");
			fw.write("\n</head>");
			fw.write("\n<body>");
			fw.write("<table>");
			for (Company c : this.list) {
				fw.write("\n<tr>");
				fw.write("<td class='name'>" + c.getName() + "</td>");
				fw.write("<td class='telephone'>" + c.getTelephone() + "</td>");
				// fw.write("<td class='telephone'>" + c.getFax() + "</td>");
				// fw.write("<td>" + c.getAdresse() + "</td>");
				fw.write("<td class='telephone'>" + c.getEMail() + "</td>");
				// fw.write("<td class='naf'>" + c.getSiret() + "</td>");
				fw.write("<td>" + getLinkSite(c) + "</td>");
				fw.write("<td class='naf'>naf:" + c.getNaf() + "</td>");
				fw.write("<td class='naf'>" + getLinkToGoogle(c) + "</td>");
				fw.write("<td class='naf'>" + getLinkToKompass(c) + "</td>");
				fw.write("<td class='naf'>" + getLinkToCCI(c) + "</td>");
				fw.write("<td class='naf'>" + getLinkToManageo(c) + "</td>");

				fw.write("</tr>");
			}
			fw.write("\n </table>");
			fw.write("\n </body>");
			fw.write("\n </html>");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private File getFileExcel() {
		File f = new File(this.fileName + ".CSV");
		return f;
	}

	public File toExcel() {
		File f = getFileExcel();
		return toExcel(f);
	}

	public File toExcel(File f) {
		try {
			System.out.println("toExcel: "+f.getAbsolutePath()+" exists:"+f.exists());
			FileWriter fw = new FileWriter(f);
			for (Company c : this.list) {
				fw.write(c.toStringExcel() + "\n");
			}
			fw.close();
			return f;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String getLinkSite(Company c) {
		String site = c.getSite();
		if (site.length() == 0) {
			return "";
		}
		return "<a href='" + site + "'>site</a>";
	}

	private String getLinkToGoogle(Company c) {
		// http://www.google.com/search?source=ig&hl=en&rlz=&q=imc+labege&btnG=Google+Search
		return "<a href='http://www.google.com/search?source=ig&hl=en&rlz=&q=" + c.getNameEncoded() + "+" + c.getVilleEncoded() + "'>google</a>";
	}

	private String getLinkToManageo(Company c) {
		// http://www.manageo.fr/entreprise/rc/preliste.jsp?action=1&Rsociale=imc-sf&Dept=31
		String href = "http://www.manageo.fr/entreprise/rc/preliste.jsp?action=1&Rsociale=" + c.getNameEncoded() + "&Dept=" + c.getDepartement();
		return "<a href='" + href + "'>Manageo</a>";
	}

	private String getLinkToKompass(Company c) {
		// http://fr04.kompass.com/recherche.php?_Choix=CN&_Region=&_Lang=fr&_Version=&_Kprov=FR001&_Language=fr-fr&_Keyword=imc&_Zone=FR&x=68&y=10
		String href = "http://fr04.kompass.com/recherche.php?_Choix=CN&_Region=&_Lang=fr&_Version=&_Kprov=FR001&_Language=fr-fr&_Keyword=" + c.getNameEncoded() + "&_Zone=FR&x=68&y=10";
		return "<a href='" + href + "'>kompass</a>";
	}

	private String getLinkToCCI(Company c) {
		// http://www.toulouse.cci.fr/site/reliantis/rechercher.asp?motgs=Acapela+Group&x=15&y=11
		String href = "http://www.toulouse.cci.fr/site/reliantis/rechercher.asp?motgs=" + c.getNameEncoded() + "&x=15&y=11";
		return "<a href='" + href + "'>cci</a>";
	}

	public Company getPreviousCompany(Company companyCurrent) {
		int i = this.list.indexOf(companyCurrent);
		if ((i + 1) >= this.list.size()) {
			return null;
		}
		return this.list.get(i + 1);
	}

	public Company getNextCompany(Company companyCurrent) {
		int i = this.list.indexOf(companyCurrent);
		if ((i - 1) < 0) {
			return null;
		}
		return this.list.get(i - 1);
	}

	public List<Company> getCompaniesByName(String name) {
		ArrayList<Company> l = new ArrayList<Company>();
		for (Company c : list) {
			String n = c.getName();
			if (n.equalsIgnoreCase(name)) {
				l.add(c);
			}
		}
		return l;
	}

	public List<Company> getCompaniesBySiret(String siret) {
		ArrayList<Company> l = new ArrayList<Company>();
		for (Company c : list) {
			String s = c.getSiret();
			if (s.equalsIgnoreCase(siret)) {
				l.add(c);
			}
		}
		return l;
	}

	public List<Company> getCompaniesByTelephone(String telephone) {
		ArrayList<Company> l = new ArrayList<Company>();
		for (Company c : list) {
			String s = c.getTelephone();
			if (s.equalsIgnoreCase(telephone)) {
				l.add(c);
			}
		}
		return l;
	}

	public void cleanBdd() {
		cleanBdd_deleteEmpty();
		cleanBdd_makeCodePostalAndVille();
		cleanBdd_trim();
		this.saveAsXml();
	}

	public void cleanBdd_makeCodePostalAndVille() {
		Iterator<Company> ite = list.iterator();
		while (ite.hasNext()) {
			Company c = ite.next();
			c.parseFullAdress();
		}
	}

	public void cleanBdd_trim() {
		Iterator<Company> ite = list.iterator();
		while (ite.hasNext()) {
			Company c = ite.next();
			c.trim();
		}
	}

	public void cleanBdd_deleteEmpty() {
		Iterator<Company> ite = list.iterator();
		while (ite.hasNext()) {
			Company c = ite.next();
			if (c.isEmpty()) {
				System.out.println(" remove:: " + c);
				ite.remove();
			}
		}
	}

	public List<Company> getList() {
		return list;
	}

	public void delete(Company c) {
		if (this.list.contains(c)) {
			this.list.remove(c);
		}
	}

	public void commit() {
		try {
			Iterator<Company> ite = list.iterator();
			Session session = UtilHibernateBg.getInstance().getSession();
			int i = 0;
			while (ite.hasNext()) {
				Company c = ite.next();
				if (c == null) {
					logger.info("commit -------------------------- c is null!!!! ");
				} else {
					logger.info("commit ------------------ " + c.getName());
					long id = c.getId();
					logger.info("commit ------------------ " + id);
					//if (id == 0L) {
						//c.setId((long) i);
					//}
					UtilHibernateBg.getInstance().saveOrUpdate(c);
					logger.info("commit -------------------------- " + c.getName() + " done");
				}
				i++;
			}
		} catch (HibernateException e) {
			logger.info("Exception " + e);
			e.printStackTrace();
		}

	}

	public void updateListCompanyFromBdd() {
		this.list = getListCompanies();
	}

	public List<Company> getListCompanies() {
		try {
			this.logger.info("UserFactory updateFromHibernate ==================== ");
			Session session = UtilHibernateBg.getInstance().getSession();
			this.logger.info("UserFactory updateFromHibernate  session : " + session);

			session.beginTransaction();
			Query query = session.createQuery("from " + Company.class.getName());
			List<Company> listCompany = query.list();

			session.getTransaction().commit();
			session.close();
			return listCompany;
		} catch (Throwable e) {
			e.printStackTrace();
			this.logger.info("UserFactory  updateFromHibernate" + e);
			return null;
		}

	}

	public Company getCompanySimilaireInsideBdd(Company c) {
		String name = c.getName();
		String siret = c.getSiret();
		String telephone = c.getTelephone();
		List<Company> cNAmes = CompanyFactory.getInstance().getCompaniesByName(name);
		List<Company> cSirets = CompanyFactory.getInstance().getCompaniesBySiret(siret);
		// List<Company> cTelephones =
		// CompanyFactory.getInstance().getCompaniesByTelephone(telephone);
		boolean r = true;
		if (cNAmes.size() > 0) {
			for (Company cc : cNAmes) {
				String codePostal = cc.getCodePostal();
				if (codePostal.equalsIgnoreCase(c.getCodePostal())) {
					return cc;
				}
			}
		}
		if (cSirets.size() > 0) {
			if (siret.trim().length() > 0) {
				return cSirets.get(0);
			}
		}

		return null;
	}

	public boolean existSimilaireInsideBdd(Company c) {
		Company cc = getCompanySimilaireInsideBdd(c);
		return (cc != null);
	}

	public void merge(Company c) {
		Company cc = getCompanySimilaireInsideBdd(c);
		if (cc == null) {
			return;
		}
		cc.merge(c);
		cc.save();
	}

	public void delete(Company[] cc) {
		for (int i = 0; i < cc.length; i++) {
			Company c = cc[i];
			this.delete(c);
		}
		this.saveAsXml();
	}

}
