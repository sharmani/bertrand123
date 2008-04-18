package com.bg.annuaire.tool;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterAnnuaireExcel extends FileFilter {

	private static final String TYPE_CSV_excel = "CSV";

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		return true;
	}

	public String getTypeDescription(File f) {
		String extension = getExtension(f);
		String type = null;
		if (extension == null) {
			type = "unknow ;";
		} else if (extension.equalsIgnoreCase(TYPE_CSV_excel)) {
			type = "cvs ;";
		} 

		return type;
	}

	@Override
	public String getDescription() {		
		return "Excel";
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
