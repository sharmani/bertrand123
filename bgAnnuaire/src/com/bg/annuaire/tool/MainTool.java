package com.bg.annuaire.tool;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.bg.annuaire.test.UtilHibernateBg;
import com.bg.util2.spring.UtilSpring;

public class MainTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();//log4j
		//iniHibernateOld("hibernate.cfg.xml");
		UtilHibernateBg.getInstance();
		UtilSpring.getInstance().initSpringConfig();
		//UtilSpring.getInstance().preInstantiateSingletons();
		new ToolAnuaireGui();
	}
	
	

	
	public static void iniHibernateOld(String configName) {
		System.out.println("createTablesFromHibernateMapping start");
		Configuration configuration = new Configuration();
		Configuration conf = configuration.configure(configName);
		SchemaExport shemaExport= new SchemaExport(conf);
		shemaExport.create(false, true);
		System.out.println("createTablesFromHibernateMapping done");		
	}

}
