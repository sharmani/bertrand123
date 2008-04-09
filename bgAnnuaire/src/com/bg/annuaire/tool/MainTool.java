package com.bg.annuaire.tool;
//com.bg.annuaire.tool.MainTool
import org.apache.log4j.BasicConfigurator;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.bg.annuaire.test.UtilHibernateBg;
import com.bg.annuaire.tool.company.CompanyFactory;

public class MainTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();//log4j
		//createTablesFromHibernateMapping("hibernate.cfg.xml");
		UtilHibernateBg.getInstance();
		
		//CompanyFactory.getInstance().initFromXML();//Crée la la liste des companies a partir du fichier xml 
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
