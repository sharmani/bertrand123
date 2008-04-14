package com.bg.util2.email.smtp;
//com.bg.util2.email.smtp.ClientSmtp
import java.net.InetAddress;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bg.util2.HostName;
import com.bg.util2.UtilString;
import com.bg.util2.email.AuthentificatorBg;
import com.bg.util2.logger.LoggerFactoryBg;
import com.sun.mail.smtp.SMTPAddressFailedException;

/**
 * Pour un deploiement sur un tomcat, ca marche avec mail.jar et activation.jar
 * dans common/lib com.bg.util.email.smtp.ClientSmtp
 */
public class ClientSmtp implements Runnable {
	
	/**
	 * 
	 */
	private static ClientSmtp instance;

	private boolean debug = false;
	
	private boolean starttls =false;

	private String host;

	private String from;

	private String name = "";

	private String user;

	private String password;

	private Properties props = new Properties();

	private AuthentificatorBg autthenticator;

	private boolean sendMessageOnActivation = true;

	private String destinaireActivationMessage;

	private String textActivationMessage = "Test activation";

	private String subjectActivationMessage = "Test activation";

	private Logger logger = LoggerFactoryBg.getLogger("MailSmtp");
	
	private static Hashtable hHostNameClientSmtp = new Hashtable();

	private Vector vQueueMessage = new Vector();

	private Vector vThreads = new Vector();

	private boolean isOn = true;
	
	

	public ClientSmtp() {
		super();
		logger.info("clientSmtp constructeur ooooooooooooooooooooooooooooooooooooooooooooooooooo");
	}

	/*
	 * @see org.apache.log4j.Appender#close()
	 */
	public void deActivate() {
		this.isOn = false;
		this.logger.info("deActivate");
		this.deActivateMAinThreads();
	}

	private void deActivateMAinThreads() {
		Enumeration enu = this.vThreads.elements();
		while (enu.hasMoreElements()) {
			try {
				Thread t = (Thread) enu.nextElement();
				t.interrupt();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		this.vThreads.removeAllElements();
	}

	public static ClientSmtp getInstance() {
		if (instance == null) {
			String hostName = HostName.hostname();
			instance = (ClientSmtp) hHostNameClientSmtp.get(hostName);

			try {
				InetAddress[] a = InetAddress.getAllByName(hostName);
				for (int i = 0; (i < a.length) && (instance == null); i++) {
					String ip = a[i].getHostAddress();
					instance = (ClientSmtp) hHostNameClientSmtp.get(ip);
				}
				if (instance == null) {
					instance = (ClientSmtp) (ClientSmtp) hHostNameClientSmtp.get("default");
					;
				}
				if ((instance == null) && (hHostNameClientSmtp.size() > 0)) {
					instance = (ClientSmtp) hHostNameClientSmtp.elements().nextElement();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		System.out.println("Smtp test");
		String to = "bertrand.guiral@free.fr";
		String from_ = "bertrand.guiral@free.fr";
		String host_ = "10.0.0.81";
		// create some properties and get the default Session
		ClientSmtp client = new ClientSmtp();
		client.sendMessage(to, from_, host_, "bertrand.guiral@free.fr", "text/html");
		System.out.println("Smtp test done");
	}

	public void activate() {
		try {
			this.logger = LoggerFactoryBg.getLogger("SMTP_" + this.name);
			this.logger.info("================================================== " + this.name + "  ====== user: " + this.user + " === password: " + this.password + "  === host:" + this.host);
			this.activateMail();
			Thread t = new Thread(this);
			t.setDaemon(true);
			t.start();
			t.setName("ThreadClientSmtp");
			this.vThreads.add(t);
			hHostNameClientSmtp.put(this.name, this);
			if (sendMessageOnActivation) {
				String text = "host: " + HostName.hostname() + "  ip:" + HostName.getIp() + "   : test Accents:éà  :" + this.textActivationMessage;
				this.sendMessage(this.destinaireActivationMessage, this.subjectActivationMessage + "  : " + HostName.hostname(), text);
			}
			this.logger.info("Activation done");

		} catch (Throwable e) {
			this.logger.info("Activate Exception "+ e);
		}

	}

	private void activateMail() {
		this.logger.info("activateMAil");
		this.props.put("mail.smtp.host", host);
		if (starttls){
			props.put("mail.smtp.starttls.enable","true");
			props.put("mail.smtp.starttls.enable","true");
		}
		if (debug) {
			props.put("mail.debug", "" + debug);
			props.put("mail.smtp.auth", "true");			 

		}
		this.autthenticator = new AuthentificatorBg(this.user, this.password);
	}

	/*
	 * private void sendMessageTest(String to, String from_, String host_){
	 * this.from = from_; this.host = host_; this.activate();
	 * this.sendMessage(to,"message de test","Test");
	 */
	public boolean sendMessage(String to, String subject, String text) {
		return this.sendMessage(to, subject, text, this.from);
	}

	public boolean sendMessageHtml(String to, String subject, String text) {
		return this.sendMessageHtml(to, subject, text, this.from);
	}

	public boolean sendMessageHtml(Vector vTo, String subject, String text) {
		return this.sendMessage(vTo, subject, text, this.from, "text/html;	charset=\"iso-8859-1\"");
	}

	public boolean sendMessage(Vector vTo, String subject, String text) {
		return this.sendMessage(vTo, subject, text, this.from);
	}

	public boolean sendMessage(String to, String subject, String text, String from_) {
		return sendMessage(to, subject, text, from_, null);
	}

	public boolean sendMessageHtml(String to, String subject, String text, String from_) {
		// text/html; charset="iso-8859-1"

		// return sendMessage(to, subject, text, from_, "text/html");// UTF8 par
		// default
		return sendMessage(to, subject, text, from_, "text/html;	charset=\"iso-8859-1\"");
	}

	public boolean sendMessage(String to, String subject, String text, String from_, String contentType) {
		Vector vTo = new Vector();
		vTo.add(to);
		return sendMessage(vTo, subject, text, from_, contentType);
	}

	public boolean sendMessage(Vector vTo, String subject, String text, String from_) {
		return sendMessage(vTo, subject, text, from_, null);
	}

	public boolean sendMessage(Vector vTo, String subject, String text, String from_, String contentType) {
		try {
			InternetAddress[] address = toInternetAddress(vTo);
			return sendMessage(address, subject, text, from_, contentType);
		} catch (Exception e) {
			this.logger.info("sendMessage EXCEPTION "+ e);
			return false;
		}
	}

	/**
	 * 
	 * @param address
	 * @param subject
	 * @param text
	 * @param from_
	 * @return
	 * @throws Exception
	 */
	public boolean sendMessage(InternetAddress[] address, String subject, String text, String from_) {
		return sendMessage(address, subject, text, from_, null);
	}

	public boolean sendMessage(InternetAddress[] address, String subject, String text, String from_, String contentType) {
		try {
			Session session = Session.getInstance(props, this.autthenticator);
			if (session == null) {
				this.logger.info(" sendMessage !session is null! autthenticator :" + this.autthenticator);
				return false;
			}
			session.setDebug(debug);
			this.logger
					.info("ClientSmtp.sendMessage host:" + host + "   user:" + user + "   password:" + password + " from: " + from + "  to:" + UtilString.toString(address) + " from: " + from_ + "  subject:" + subject + "  text:" + text + "  content Type: " + contentType + "   session " + session);
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(from_));
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			if (contentType == null) {
				msg.setText(text);
			} else {
				msg.setContent(text, contentType);
				msg.saveChanges();
			}
			// Transport.send(msg);
			if (isCheckedMessage(msg)) {
				this.addMessageToQueue(msg);
			} else {
				this.logger.info("Message Error !! No add to queue");
			}
			this.logger.info("ClientSmtp.sendMessage done vQueueMessage.size" + this.vQueueMessage.size());
			return true;
		} catch (Throwable e) {
			this.logger.info("SendMessageExcept "+e);
			return false;
		}
	}

	private boolean isCheckedMessage(Message msg) {
		try {
			if (msg == null) {
				return false;
			}
			if (msg.getRecipients(Message.RecipientType.TO) == null) {
				this.logger.info("Message.RecipientType.TO == null ");
				return false;
			}
			if (msg.getRecipients(Message.RecipientType.TO).length == 0) {
				this.logger.info("Message.RecipientType.TO).length==0 ");
				return false;
			}
		} catch (Exception e) {
			this.logger.info("Exception isChecked"+ e);
		}
		return true;
	}

	private boolean postMessage(Message msg) {
		if (msg == null) {
			return false;
		}
		if (!isOn) {
			System.out.println("!client smtp is off! message no send :" + msg);
			return false;
		}
		try {
			this.logger.info("postMessage start to: " + toString(msg));
			Transport.send(msg);
			this.logger.info("Send Message done ");
			return true;
		} catch (SMTPAddressFailedException e) {
			this.logger.info("postMessage Exception ::" + e);
			return true;// Avec une telle exception, il est inutile d'essayer de poster encore et encore
		} catch (Exception e) {
			this.logger.info("postMessage Exception ::" + e);
			return false;
		}
	}

	private void flushQueue() {
		this.logger.info("------------------------------flush Queue start ----" + this.vQueueMessage.size());
		Enumeration enu = this.vQueueMessage.elements();
		while (enu.hasMoreElements()) {
			Message msg = (Message) enu.nextElement();
			this.logger.info("flush | " + toString(msg));
			this.vQueueMessage.removeAllElements();
		}
		this.logger.info("------------------------------flush Queue done ----");
	}

	private void addMessageToQueue(Message msg) {
		this.vQueueMessage.add(msg);
		if (this.vQueueMessage.size() > 500) {
			this.flushQueue();
		}
		this.awake();
	}

	private String toString(Message msg) {
		String r = "";
		try {
			r += " | hashCode=" + msg.hashCode();
			r += " | subject= " + msg.getSubject();
			r += " | subject= " + msg.getRecipients(Message.RecipientType.TO);
			r += " | content=" + msg.getContent();
		} catch (Exception e) {
			r += " | Exception=" + e.getMessage();
		}
		return r;
	}

	private synchronized Message getFirstMessageAndRemove() {
		if (this.vQueueMessage.size() == 0) {
			return null;
		}
		Object o = this.vQueueMessage.firstElement();
		this.vQueueMessage.remove(o);
		return (Message) o;
	}

	private synchronized void awake() {
		this.notify();
	}

	private synchronized void attendre() {
		try {
			this.wait();
		} catch (Exception e) {

		}
	}

	/**
	 * @param v
	 * @return
	 * @throws Exception
	 */
	private InternetAddress[] toInternetAddress(Vector v) throws Exception {
		InternetAddress[] address = new InternetAddress[v.size()];
		Enumeration enu = v.elements();
		int i = 0;
		while (enu.hasMoreElements()) {
			address[i] = new InternetAddress((String) enu.nextElement());
			i++;
		}
		return address;
	}

	/**
	 * @return Returns the from.
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from_
	 *            The from to set.
	 */
	public void setFrom(String from_) {
		this.from = from_;
	}

	/**
	 * @return Returns the host.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host_
	 *            The host to set.
	 */
	public void setHost(String host_) {
		this.host = host_;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name_
	 *            The name to set.
	 */
	public void setName(String name_) {
		this.name = name_;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password_) {
		this.password = password_;
	}

	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            The user to set.
	 */
	public void setUser(String user_) {
		this.user = user_;
	}

	/**
	 * @param debug
	 *            The debug to set.
	 */
	public void setDebug(String debugStr) {
		this.debug = "true".equals("" + debugStr);
	}
	public void setStarttls(String starttlsStr) {
		this.starttls = "true".equals("" + starttlsStr);
	}

	
	/**
	 * @return Returns the destinaireActivationMessage.
	 */
	public String getDestinaireActivationMessage() {
		return destinaireActivationMessage;
	}

	/**
	 * @param destinaireActivationMessage
	 *            The destinaireActivationMessage to set.
	 */
	public void setDestinaireActivationMessage(String destinaireActivationMessage_) {
		this.destinaireActivationMessage = destinaireActivationMessage_;
	}

	/**
	 * @param sendMessageOnActivation
	 *            The sendMessageOnActivation to set.
	 */
	public void setSendMessageOnActivation(String sendMessageOnActivation_) {
		this.sendMessageOnActivation = "true".equals("" + sendMessageOnActivation_);
	}

	/**
	 * @param subjectActivationMessage
	 *            The subjectActivationMessage to set.
	 */
	public void setSubjectActivationMessage(String subjectActivationMessage_) {
		this.subjectActivationMessage = subjectActivationMessage_;
	}

	/**
	 * @param textActivationMessage
	 *            The textActivationMessage to set.
	 */
	public void setTextActivationMessage(String textActivationMessage_) {
		this.textActivationMessage = textActivationMessage_;
	}

	public void run() {
		this.logger.info("ClientSmtp.run  " + isOn);
		while (isOn) {
			while ((this.vQueueMessage.size() > 0) && isOn) {
				Message message = null;
				try {
					message = this.getFirstMessageAndRemove();
					if (message == null) {
					} else {
						if (this.postMessage(message)) {

						} else {
							this.logger.info("! Message pas envoyé! vQueueMessage.size: " + this.vQueueMessage.size());
							Thread.sleep(10000);
							this.addMessageToQueue(message);
						}
					}
				} catch (Throwable e) {
					this.logger.info("run.EXCEPTION "+ e);
					e.printStackTrace();
				}

			}
			this.attendre();
		}
	}

}