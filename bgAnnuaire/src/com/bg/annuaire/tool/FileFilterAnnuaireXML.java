package com.bg.annuaire.tool;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterAnnuaireXML extends FileFilter {

	private static final String TYPE_XML = "xml";

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		return true;
	}

	public String getTypeDescription(File f) {
		String extension = this.getExtension(f);
		String type = null;
		if (extension == null) {
			type = "unknow ;";
		} else if (extension.equalsIgnoreCase(TYPE_XML)) {
			type = "Xml ;";
		} 

		return type;
	}

	@Override
	public String getDescription() {		
		return "xml";
	}

	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

}
