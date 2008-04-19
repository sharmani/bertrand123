package com.bg.annuaire.tool.fileFilter;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public  class FileFilterBg extends FileFilter{

	private String extension="";
	private String description="";
	public FileFilterBg(String extension) {
		this(extension,extension);
	}
		public FileFilterBg(String extension, String description) {
		super();
		this.extension=extension;
		this.description= description;
	}
	public  String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
	public  String getFirstPartOfName(File f) {
		String firstPart = null;
		String s = f.getName();
		int i = s.indexOf('.');
		if (i > 0 && i < s.length() - 1) {
			firstPart = s.substring(0,i ).toLowerCase();
		}
		return firstPart;
	}
	public  String getTypeExtension(){
		return this.extension;
	}
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String extension =this.getExtension(f);
		if (extension == null) {
		} else if (extension.equalsIgnoreCase(getTypeExtension())) {
			return true;
		}
		return false;
	}
	public void changeExtensionFileSelected(JFileChooser fileChooser){
		File f = fileChooser.getSelectedFile();
		if (f==null){
			System.out.println("AbstractFileChoser: f is null !! -- ");
			return;
		}
		String fName = f.getName();
		String nameNew = getFirstPartOfName(f)+"."+getTypeExtension();
		System.out.println("changeExtensionFileSelected "+fName+"  nameNew:"+nameNew);
		File fNew = new File(f.getParentFile(),nameNew);
		fileChooser.setSelectedFile(fNew);
	
	}
	@Override
	public String getDescription() {
		
		return description;
	}
	
	
}
