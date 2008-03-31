package com.bg.util2.email;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 *  
 */
public class AuthentificatorBg extends Authenticator {
	private String username;
	private String password;
	public AuthentificatorBg(String userName_, String pwd_) {
		this.username = userName_;
		this.password = pwd_;
	}
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);		
	}

}
