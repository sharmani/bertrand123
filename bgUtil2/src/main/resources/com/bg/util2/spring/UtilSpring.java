package com.bg.util2.spring;


import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class UtilSpring {

	
	private XmlBeanFactory beanFactory;
	private final static UtilSpring instance = new UtilSpring();
	
	private UtilSpring() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("MainTestSpring");
		String nameFileConfig = "bgSpring.xml";
		instance.initSpringConfig(new File("/"),nameFileConfig);
	}

	public  boolean initSpringConfig() {
		String nameRoot = "spring";
		return initSpringConfigFromNameRoot(new File("/"),nameRoot);
	}
	
		

	public boolean initSpringConfigFromNameRoot(File dir,String nameRoot) {

		String name = nameRoot + "_" + hostname() + ".xml";
		if (initSpringConfig(dir,name)) {
			return true;
		}

		InetAddress[] inetAdresses = inetAdresses();
		for (int i = 0; i < inetAdresses.length; i++) {
			name = nameRoot + "_" + inetAdresses[i].getHostAddress() + ".xml";
			if (initSpringConfig(dir,name)) {
				return true;
			}
		}
		name = nameRoot + ".xml";
		if (initSpringConfig(dir,name)) {
			return true;
		}
		return false;
	}

	private static InetAddress[] inetAdresses() {
		try {
			return InetAddress.getAllByName(hostname());
		} catch (Exception e) {
			return new InetAddress[0];
		}
	}

	private static String hostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "localhost";
		}
	}

	public  boolean initSpringConfig(File dir,String nameFileConfig) {
		Resource ressource = this.getRessource(dir, nameFileConfig);
		System.out.println("UtilSpring resource:"+ressource);
		return initSpringConfig(ressource);
	}
		public  Resource getRessource(File dir,String nameFileConfig) {
		try {		
			if (dir== null){
				dir = new File("");
			}
			dir = dir.getAbsoluteFile();
			File f1 = new File(dir,nameFileConfig);
			Enumeration<?> enuRessources = instance.getClass().getClassLoader().getResources(nameFileConfig);
			System.out.println("UtilSpring file :  " + f1.getAbsolutePath() + " exists:" + f1.exists());
			Resource ressource = null;
			if (f1.exists()) {
				return ressource = new FileSystemResource(f1);
				
			} else if (enuRessources.hasMoreElements()) {
				return ressource = new ClassPathResource(nameFileConfig);
				
			} else {
				System.out.println("No Spring Ressource ");
				return null;
			}			
			
		} catch (Exception e) {
			System.err.println("initSpringConfig Exception nameFileConfig >" + nameFileConfig + "<");
			e.printStackTrace();
			return null;
		}
		
	}
	
	private  boolean initSpringConfig(Resource ressource) {
		System.out.println("UtilSpring init start ressource:"+ressource);
		this.beanFactory = new XmlBeanFactory(ressource);
		System.out.println("UtilSpring init done :"+beanFactory);
		return true;
	}

	public  XmlBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static UtilSpring getInstance() {
		
		return instance;
	}
	
	public  void preInstantiateSingletons() {
		try {
			if (beanFactory==null){
				System.out.println("!UilSpring preInstantiateSingletons no beanFactory!");
				
			}else {
			System.out.println("preInstantiateSingletons xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "+beanFactory);
			beanFactory.preInstantiateSingletons();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void destroySingletons() {
		try {
			if (beanFactory==null){
				System.out.println("!UtilSpring destroySingleton: No beanFactory available!");
			}else {
				beanFactory.destroySingletons();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void  initSpringConfigFromNameRootThread(File dir,String nameRoot,boolean preInstantiateSingletons) {
		new ThreadInit(dir,nameRoot,preInstantiateSingletons);
	}
	
	class ThreadInit implements Runnable{
		private File dir;
		private String nameRoot;
		private boolean preInstantiateSingletons=true;
		ThreadInit(File dir_, String nameRoot_,boolean preInstantiateSingletons_){
			dir=dir_;
			nameRoot=nameRoot_;			
			preInstantiateSingletons=preInstantiateSingletons_;
			Thread t = new Thread(this);
			t.start();
		}
		public void run() {
			initSpringConfigFromNameRoot(dir,nameRoot);
			if (preInstantiateSingletons){
				preInstantiateSingletons();
			}
		}
	}


}
