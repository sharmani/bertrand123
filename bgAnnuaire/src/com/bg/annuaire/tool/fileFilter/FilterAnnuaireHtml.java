package com.bg.annuaire.tool.fileFilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FilterAnnuaireHtml extends FileFilter {

	public static final String TYPE_html = "html";

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String extension = UtilFileFilter.getExtension(f);
		if (extension == null) {
		} else if (extension.equalsIgnoreCase(TYPE_html)) {
			return true;
		}
		return false;
	}

	
	@Override
	public String getDescription() {
		return "Html";
	}



}
