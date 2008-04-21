package com.bg.annuaire.test;

import java.io.Serializable;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.action.Action;

public class UtilHibernateBg {

	private  SessionFactory sessionFactory;

	private static Logger logger = Logger.getLogger("Hibernate");

	private static UtilHibernateBg instance;

	public void initHibernate(String userName, String password) {
		try {
			System.out.println("------------ UtilHibernateBg  static start "+userName+"  "+password);
			AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration();
			annotationConfiguration .configure("hibernate.cfg.xml");
			annotationConfiguration.setProperty("hibernate.connection.username", userName);
			annotationConfiguration.setProperty("hibernate.connection.password", password);
			String packageName ;
			packageName = Company.class.getPackage().getName();
			System.out.println("------------ UtilHibernateBg  static start packageName: "+packageName);
			annotationConfiguration.addPackage(packageName); //
			packageName = Action.class.getPackage().getName();
			annotationConfiguration.addPackage(packageName); //
			System.out.println("------------ UtilHibernateBg  addPackage done");
			annotationConfiguration.addAnnotatedClass(Company.class);
			annotationConfiguration.addAnnotatedClass(Action.class);
			
			sessionFactory = annotationConfiguration.buildSessionFactory();
			logger.info("!UtilHibernateBg sessionFactory :::: "+sessionFactory);
		} catch (Throwable ex) {
			logger.info("!UtilHibernateBg Zorg in th potage!");
			//throw new ExceptionInInitializerError(ex);
			ex.printStackTrace();
		}

	}

	public Session getSession() throws HibernateException {
		return sessionFactory.openSession();
	}

	public boolean save(Object o) {
		Session session = null;
		try {

			session = getSession();
			return this.save(o, session);

		} catch (Throwable e) {
			logger.info("save 1 -------------------------------   Exception o : " + o + " Exception e:" + e);
			e.printStackTrace();
			if (session == null) {
			} else {
				try {
					session.close();
					session = getSession();
					return this.save(o, session);
				} catch (Exception e1) {
					logger.info("save 2 ------------------------------------- Exception commit 2 attempt" + e);
				}
			}
			return false;
		}
	}

	private boolean save(Object o, Session session) throws Exception {
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public void saveOrUpdate(Object o) {
		try {
			Session session = getSession();
			saveOrUpdate(o, session);

		} catch (Exception e) {
			try {
				if (o == null) {
					logger.info("UtilHibernate.update --------------  null!!!");
				} else {
					logger.info("UtilHibernate.update 1 ---------------" + o.getClass() + "  " + o + "  :: Exception" + e);
					e.printStackTrace();
					Session session = getSession();
					saveOrUpdate(o, session);
					logger.info("update 2 attempt done ");
				}
			} catch (Exception e1) {
				logger.info("update Exception 2 attempt  --------------------" + e);
			}
		}
	}

	private void saveOrUpdate(Object o, Session session) throws Exception {
		session.beginTransaction();
		session.saveOrUpdate(o);
		session.getTransaction().commit();
		session.close();
	}

	public void delete(Object o) {
		Session session = getSession();
		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
		session.close();
	}

	public void update(Object o) {
		try {
			Session session = getSession();
			update(o, session);
		} catch (Exception e) {
			try {
				if (o == null) {
					logger.info("UtilHibernate.update --------------  null!!!");
				} else {
					logger.info("UtilHibernate.update 1 ---------------" + o.getClass() + "  " + o + "  :: Exception" + e);
					e.printStackTrace();
					Session session = getSession();
					update(o, session);
					logger.info("update 2 attempt done ");
				}
			} catch (Exception e1) {
				logger.info("update Exception 2 attempt  --------------------" + e);
			}
		}
	}

	private void update(Object o, Session session) throws Exception {
		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
		session.close();
	}

	public Object load(Class clazz, long id) {
		return load_(clazz, id);
	}

	public Object load_(Class clazz, Serializable id) {
		// Session session =
		// UtilHibernate.getInstance().getSessionFactory().getCurrentSession();
		Session session = this.getSession();
		try {
			Object o = session.load(clazz, id);
			return o;
		} catch (Exception e) {
			if (session == null) {
				return null;
			} else {
				session.close();
				session = getSession();
				Object o = session.load(clazz, id);
				return o;
			}
		}
	}

	public static UtilHibernateBg getInstance() {
		if (instance == null) {
			instance = new UtilHibernateBg();
		}
		return instance;
	}
	
	
}
