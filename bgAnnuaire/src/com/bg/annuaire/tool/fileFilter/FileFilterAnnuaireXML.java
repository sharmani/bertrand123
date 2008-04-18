package com.bg.annuaire.tool.fileFilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterAnnuaireXML extends FileFilter {

	private static final String TYPE_XML = "xml";

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String extension = UtilFileFilter.getExtension(f);
		
		if (extension == null) {
		} else  if (extension.equalsIgnoreCase(TYPE_XML)) {
			return true;
		}
		return false;

		
	}


	@Override
	public String getDescription() {		
		return "xml";
	}

	
}
