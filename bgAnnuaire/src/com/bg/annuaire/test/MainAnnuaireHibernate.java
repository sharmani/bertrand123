package com.bg.annuaire.test;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


public class MainAnnuaireHibernate {

	
	
	public static void main(String[] a){
		UtilHibernateBg bgHibernateUtil;
		BasicConfigurator.configure();//log4j
		createTablesFromHibernateMapping("hibernate.cfg.xml");
			
	}
	
	
	public static void createTablesFromHibernateMapping(String configName) {
		System.out.println("createTablesFromHibernateMapping start");
		Configuration configuration = new Configuration();
		Configuration conf = configuration.configure(configName);
		SchemaExport shemaExport= new SchemaExport(conf);
		shemaExport.create(false, true);
		System.out.println("createTablesFromHibernateMapping done");		
	}
}
