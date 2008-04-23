package com.bg.annuaire.tool.company.action;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bg.annuaire.test.UtilHibernateBg;
import com.bg.annuaire.tool.company.Company;

public class ActionFactory {

	private static ActionFactory instance;

	public ActionFactory() {
	}

	public static ActionFactory getInstance() {
		if (instance == null){
			instance= new ActionFactory();
		}
		return instance;
	}

	public List<Action> getListActionByCompany(Company c) {
		if (c == null) {
			return new ArrayList<Action>();
		}
		long idCompany = c.getId();
		return getListActionByCompany(idCompany);
	}

	public List<Action> getListActionByCompany(long idCompany) {
		Session session = UtilHibernateBg.getInstance().getSession();
		String q = "from " + Action.class.getName() + " as aa where (aa.idCompany = " + idCompany + ")";
		Query query = session.createQuery(q);
		List<Action> list = query.list();
		if (list == null){
			return new ArrayList<Action>();
		}
		return list;
	}

}
