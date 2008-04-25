/**
 * 
 */
package com.bg.annuaire.tool.company.action;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Id;
import javax.persistence.Table;

import com.bg.annuaire.test.UtilHibernateBg;

/**
 * @author BertGuir
 * 
 */
@Entity
@Table(name = "Action2")
public class Action {

	private long id = 0;
	private long idCompany = 0;
	private String comment = "";
	private Date date = new Date();
	private String login="";

	/**
	 * 
	 */
	public Action() {
	}
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	public long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(long idCompany) {
		this.idCompany = idCompany;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void saveOrUpdate() {
		UtilHibernateBg.getInstance().saveOrUpdate(this);
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
}
