package com.bg.annuaire.tool.company;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bg.util2.GenerateurId;
@Entity
@Table(name="Company2")
public class Company {
	
	private long id=0;

	private String name = "";

	private String telephone = "";

	private String fax = "";

	private String adresse = "";

	private String eMail = "";

	private String site = "";

	private String siret = "";

	private String effectif = "";

	private String naf = "";

	private String ville = "";

	private String codePostal = "";

	private String dateModification = "";

	private String comment = "";

	private final static String tag_name = "name";

	private final static String tag_telephone = "telephone";

	private final static String tag_fax = "fax";

	private final static String tag_adresse = "adresse";

	private final static String tag_eMail = "eMail";

	private final static String tag_site = "site";

	private final static String tag_siret = "siret";

	private final static String tag_effectif = "effectif";

	private final static String tag_naf = "naf";

	private final static String tag_comment = "comment";

	private final static String tag_ville = "ville";

	private final static String tag_codePostal = "codePostal";

	private final static String tag_date_modification = "dateModification";
	private final static String tag_id = "id";

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	public Company() {
		super();

	}

	public Company(Element e) {
		NodeList nodeList = e.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			String tagName = child.getNodeName();
			String value = child.getTextContent();
			if (tagName.equals(tag_adresse)) {
				this.adresse = value;
			} else if (tagName.equals(tag_date_modification)) {
				this.dateModification = value;
			} else if (tagName.equals(tag_effectif)) {
				this.effectif = value;
			} else if (tagName.equals(tag_eMail)) {
				this.eMail = value;
			} else if (tagName.equals(tag_fax)) {
				this.fax = value;
			} else if (tagName.equals(tag_naf)) {
				this.naf = value;
			} else if (tagName.equals(tag_name)) {
				this.name = value;
			} else if (tagName.equals(tag_siret)) {
				this.siret = value;
			} else if (tagName.equals(tag_site)) {
				this.site = value;
			} else if (tagName.equals(tag_telephone)) {
				this.telephone = value;
			} else if (tagName.equals(tag_comment)) {
				this.comment = value;
			} else if (tagName.equals(tag_ville)) {
				this.ville = value;
			} else if (tagName.equals(tag_codePostal)) {
				this.codePostal = value;
			} else if (tagName.equals(tag_id)) {
				try {
					this.id=Long.parseLong(value);
				} catch (NumberFormatException e1) {
				}
			} else if (tagName.equals("#text")) {

			} else{
				//System.err.println("TAg non reconnu!!! tagName>" + tagName + "<   value:>" + value + "<");
			}
		}
		//System.out.println("Company.constructeur " + this);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String mail) {
		eMail = mail;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getNaf() {
		return naf;
	}

	public void setNaf(String naf) {
		this.naf = naf;
	}

	public void save() {
		this.dateModification = simpleDateFormat.format(new Date());
		CompanyFactory.getInstance().save();
	}

	public String toStringXml() {
		String s = "";
		s += "\n <company>";
		s += this.putInTag(tag_name, this.name);
		s += this.putInTag(tag_telephone, this.telephone);
		s += this.putInTag(tag_fax, this.fax);
		s += this.putInTag(tag_adresse, this.adresse);
		s += this.putInTag(tag_codePostal, this.codePostal);
		s += this.putInTag(tag_ville, this.ville);
		s += this.putInTag(tag_siret, this.siret);
		s += this.putInTag(tag_naf, this.naf);
		s += this.putInTag(tag_eMail, this.eMail);
		s += this.putInTag(tag_site, this.site);
		s += this.putInTag(tag_date_modification, this.dateModification);
		s += this.putInTag(tag_comment, this.comment);
		s += this.putInTag(tag_id, ""+this.id);
		s += "\n </company>";
		return s;
	}

	private String putInTag(String tag, String name2) {

		return "\n     <" + tag + ">" + name2 + "</" + tag + ">";
	}

	public String getEffectif() {
		return effectif;
	}

	public void setEffectif(String effectif) {
		this.effectif = effectif;
	}

	public String toString() {
		return "Company: " + this.name + " " + this.telephone + " " + this.fax + " " + this.eMail + " " + this.effectif + "  " + this.naf + "  " + this.siret + "  " + this.site + "  " + this.adresse + "  " + this.dateModification;
	}

	public String toStringDetail() {
		String s = "";
		s += " name : " + this.name;
		s += "\n telephone : " + this.telephone;
		s += "\n  fax : " + this.fax;
		s += "\n  mail : " + this.eMail;
		s += "\n  effectif : " + this.effectif;
		s += "\n  naf : " + this.naf;
		s += "\n  siret : " + this.siret;
		s += "\n  sitet : " + this.site;
		s += "\n  adress : " + this.adresse;
		s += "\n  coment : " + this.comment;
		return s;
	}

	@Transient
	public boolean isEmpty() {
		boolean b1 = (this.name.trim().length() == 0);
		boolean b2 = (this.telephone.trim().length() == 0);
		boolean b3 = (this.eMail.trim().length() == 0);
		boolean b4 = (this.siret.trim().length() == 0);
		boolean b5 = (this.adresse.trim().length() == 0);
		return b1 && b2 && b3 && b4 && b5;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void delete() {
		System.out.println("delete --------------NO IMPLEMENTED ----------" + this.name);
	}

	public void trim() {
		this.adresse = adresse.trim();
		this.telephone = telephone.trim();
		this.name = name.trim();
		this.fax = fax.trim();
		this.siret = siret.trim();
		this.naf = naf.trim();
		this.eMail = eMail.trim();
		StringTokenizer st = new StringTokenizer(this.eMail, " ");
		if (st.hasMoreTokens()) {
			this.eMail = st.nextToken();
		}
		effectif = effectif.trim();
		comment = comment.trim();
	}

	public void parseFullAdress() {
		StringTokenizer st = new StringTokenizer(this.adresse);
		String v = "";
		String c = "";
		while (st.hasMoreTokens()) {
			String e = st.nextToken();
			if (e.equalsIgnoreCase("cedex")) {
			} else {
				c = v;
				v = e;
			}
		}
		this.ville = v;
		this.codePostal = c;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	@Transient
	public String getVilleEncoded() {
		return URLEncoder.encode(this.ville);
	}
	@Transient
	public String getNameEncoded() {
		return URLEncoder.encode(this.name);
	}
	@Transient
	public String getDepartement() {
		if (this.codePostal.length() == 5) {
			return this.codePostal.substring(0, 2);
		}
		return "";
	}
	@Id
	public long getId() {		
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}