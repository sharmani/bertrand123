package com.bg.annuaire.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import javax.swing.DefaultCellEditor;

public class MainAnnuaire {

	public MainAnnuaire() {
		// TODO Auto-generated constructor stub
		DefaultCellEditor d;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		readCodesNaf();
		readCodesNaf2();
	}

	private static void readCodesNaf() throws Exception {
		File f = new File("codes_naf.txt");
		FileReader fr = new FileReader(f);
		String line;
		BufferedReader br = new BufferedReader(fr);
		File fOut = new File("codes_naf_2.txt");
		FileWriter fw = new FileWriter(fOut);
		String codeRubrique = "";
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			String code = st.nextToken();
			String label = line.substring(code.length(), line.length());
			if (code.length() == 2) {
				codeRubrique = code;
				System.out.println(" --------- chapitre ----  " + code + " ::: " + line);
				fw.write("" + code + "  " + label+ "\n");
			} else if (code.length() > 2) {
				int i = 0;
				int j = 2;
				while ((j = line.indexOf(codeRubrique, i + 1)) > 0) {
					String sLigne = line.substring(i, j);
					fw.write(sLigne + "\n");
					i = j;
				}
			}
		}
		fw.write(getPiedPageHTML());
		fw.close();
		fr.close();
	}

	private static void readCodesNaf2() throws Exception {
		File f = new File("codes_naf_2.txt");
		FileReader fr_1 = new FileReader(f);
		String line;
		BufferedReader br_1 = new BufferedReader(fr_1);
		File fOut = new File("codes_naf.html");
		FileWriter fw = new FileWriter(fOut);
		fw.write(getEnTeteHTML());
		fw.write("<table>");
		while ((line = br_1.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line); 
			if (st.hasMoreTokens()) {
				String code = st.nextToken();
				String label = line.substring(code.length(), line.length());
				if (code.length() == 2) {
					fw.write("\n<tr> <td> " + code + "</td> <td>  <a href='#" + code + "'>" + line.substring(code.length()) + "</a> </td> <td> </td> </tr>");
				}
			}
		}
		fw.write("</table>");
		
		br_1.close();
		FileReader fr_2 = new FileReader(f);
		fw.write("<p/>");
		fw.write("\n<table border='1'>");
		BufferedReader br_2 = new BufferedReader(fr_2);

		while ((line = br_2.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			String code = st.nextToken();
			String label = line.substring(code.length(), line.length());
			if (code.length() == 2) {
				System.out.println(" --------- chapitre ----  " + code + " ::: " + line);
				fw.write("\n\n<tr style='background-color: #eeeeee'> <td > <a name='#" + code + "'/>" + code + "</td> ");
				fw.write("<td>  " + label + "</td> <td> </td> </tr>");
			} else if (code.length() > 2) {
				System.out.println(" " + code + " ::: " + label);
				fw.write("\n<tr>");
				fw.write("<td>" + code + "</td> <td> " + label + "</td><td> " + getLinkAnnuairePros(code) + "</td>");
				fw.write("</tr>");
			}
		}
		fw.write("\n</table>");
		fw.write(getPiedPageHTML());
		fw.close();
		br_2.close();

	}

	private static String departement = "31";

	private static String ville = "Labege";

	private static String getLinkAnnuairePros(String code) {
		String s = "";
		s += "<FORM name=formRech action='http://www.pagespro.com/recherche.php' method=post>";
		s += "<INPUT type='hidden' name='p_ORDRE' value='DonneeChoix'/><INPUT type='hidden'  name='a_OccRecherche' value='3'/> <INPUT id='a_naf' type='hidden' name='a_naf'/>";
		s += "<INPUT id='a_theme' type='hidden' name='a_theme'/> ";
		s += " <INPUT type='hidden'  name='a_rai_soc'>";
		s += " <INPUT type='hidden' id=a_activ  style='width:40px' name=a_activ value='" + code + "'>";
		s += " Localité :   <INPUT  id=a_loc  style='width:60px' name='a_loc' value='" + ville + "'  >";
		s += "  département :<INPUT  id=a_geo   style='width: 30px' name='a_geo' value='" + departement + "'> ";
		s += "<INPUT TYPE='SUBMIT' name='submit' value='submit'/> </FORM>";
		return s;
	}

	private static String getEnTeteHTML() {
		String s = "<html> <head> \n<title>naf</title>\n </head> \n<body>";
		return s;
	}

	private static String getPiedPageHTML() {
		String s = "\n</body>\n</html>";
		return s;
	}
}
