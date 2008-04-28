package bg.imc.web;

import java.io.Serializable;
import java.util.Properties;
import java.util.Vector;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.bg.util.email.smtp.ClientSmtp;
import com.bg.util.log4j.LoggerFactoryBg;



//bg.imc.web.BeanInfos
public class BeanInfos {
	
	private Logger logger = LoggerFactoryBg.getLogger("BeanInfos");

	private String action = "No Action";

	private String comment = "No Comment";
	private String comment2 = "";

	private String name = "";

	private String firstName = "";

	private String e_mail = "";

	private String date = "";

	private String ref = "";
	
	private String company = "";

	private String telephone="";

	public BeanInfos() {
		super();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String doTest() {
		System.out.println("BeanInfos.doTest ");
		return "doTest";
	}

	public String doInscription() {
		logger.info("do Inscription: | company:"+this.company+"  | name:"+this.name+" | firstName: "+this.firstName+"  | email:"+this.e_mail+" | telephone : "+telephone+"  | comment2:"+this.comment2);
		System.out.println("BeanInfos.doInscription ");
		this.doAlertMail();
		return "doInscription";
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	/**
	 * Appel a JMS
	 * Attention: Glassfish doit être configurer corrctement: avec un ConnectionFactory binder  a "jms/bg"
	 * et une ressource destination binder a "jms/bg_mail";
	 */

	private void doAlertMail() {
		try {
			this.logger.info("doAlertMail "); 
			String text = this.toString();
			Vector<String> v  = Params.getInstance().getVAlertDestinataires();
			ClientSmtp.getInstance().sendMessage(v, "Nouvelle Inscription formation adword", text);
		} catch (Exception e) {
			this.logger.info("Exception ",e);
			this.comment += "doAlertMAil Exception " + e.getMessage();
		}
	}

	private Properties getPropertiesMailALert() {
		Properties p = new Properties();
		p.setProperty("to", "bertrand.guiral@free.fr");
		p.setProperty("from", "bertrand.guiral@java-consultant.com");
		p.setProperty("objet", "Inscription Formation Adwords");
		
		p.setProperty("message", ""+this.toString());
		return p;
	}
	
	public String toString() {
		String s ="";
		s +=" Nom : "+this.name;
		s +="\n FirstNAme : "+this.firstName;
		s +="\n Email: "+this.e_mail;
		s +="\n date: "+this.date;
		s +="\n company: "+this.company; 
		s +="\n telephone: "+this.telephone;
		s += "\n comment: " +this.comment;
		return s;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getComment2() {
		return comment2;
	}

	public void setComment2(String comment2) {
		this.comment2 = comment2;
	}

}
