package com.bg.util2.junit;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

import com.bg.util2.logger.LoggerFactoryBg;

import junit.framework.TestCase;

public class LoggerFactoryTest extends TestCase {

	public LoggerFactoryTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testRemovAllFiles() throws Exception{
		LoggerFactoryBg.removAllFiles();
		File f = new File(LoggerFactoryBg.dirLog, "testBidon");
		FileWriter fw = new FileWriter(f);
		fw.write("blablabla");
		fw.close();
		System.out.println(" LoggerFactoryBg.dirLog.exists: "+LoggerFactoryBg.dirLog.exists());
		System.out.println(" testRemovAllFiles f.exists : "+f.exists());
		assertTrue(f.exists());
		LoggerFactoryBg.removAllFiles();
		assertTrue(!f.exists());		
	}

	public void testGetLogger() {
		Logger logger = LoggerFactoryBg.getLogger("myJUnitTest");
		logger.info("pouet pouet tralala");
	}

	public void testGetFile() {
		String name = "myJUnitTest";
		Logger logger = LoggerFactoryBg.getLogger(name);
		logger.info("pouet pouet tralala");
		File f = LoggerFactoryBg.getFile(name);
		assertTrue(f.exists());
		for(int i=0;i<12000; i++){
			logger.info(i+" blbalabalalaalalalalalalalalalalala aaalalalalalalaal "+i);
		}
		assertTrue(LoggerFactoryBg.getFile(name,0).exists());
		assertTrue(LoggerFactoryBg.getFile(name,1).exists());
		assertTrue(LoggerFactoryBg.getFile(name,2).exists());
	}

}
