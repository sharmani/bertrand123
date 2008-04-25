package com.bg.annuaire.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

public class MainTest {

	// http://www.pagespro.com/recherche.php?p_ORDRE=AfficheRes&p_ACTION=&a_PAGE=&a_TAG=&a_Etat=&a_avec_crit=&a_siren=&a_naf=&a_theme=
	// &a_rai_soc=A2B&a_activ=&a_loc=Labege&a_geo=31
	public static void main(String[] a) throws Exception {
		System.out.println("MainTest");
		String urlStr = "http://www.pagespro.com/recherche.php";
		String s = "p_ORDRE=AfficheRes&p_ACTION=&a_PAGE=&a_TAG=&a_Etat=&a_avec_crit=&a_siren=&a_naf=&a_theme=&a_rai_soc=A2B&a_activ=&a_loc=Labege&a_geo=31";
		// 
		testPost(urlStr, s);
	}

	private static void testPost(String urlStr, String a_rai_soc, String a_loc, String a_geo) throws Exception {
		Properties p = new Properties();
		p.setProperty("p_ORDRE", "AfficheRes");
		p.setProperty("p_ACTION", "");
		p.setProperty("a_PAGE", "");
		p.setProperty("a_TAG", "");
		p.setProperty("a_Etat", "");
		p.setProperty("a_avec_crit", "");
		p.setProperty("a_siren", "");
		p.setProperty("a_naf", "");
		p.setProperty("a_theme", "");
		p.setProperty("a_rai_soc", "A2B");
		p.setProperty("a_activ", "");
		p.setProperty("a_loc", "Labege");
		p.setProperty("a_geo", "31");

		testPost(urlStr, p);
	}

	private static void testPost(String urlStr, Properties p) throws Exception {
		String params = "";
		Enumeration<Object> enu = p.keys();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = p.getProperty(key);
			params += "key=" + URLEncoder.encode(value);
		}
		testPost(urlStr, params);
	}

	private static void testPost(String urlStr, String s) throws Exception {

		URL url = new URL(urlStr);
		URLConnection urlConnection = url.openConnection();
		HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;

		httpUrlConnection.setDoOutput(true);
		OutputStream oS = httpUrlConnection.getOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(oS);
		out.write(s);
		out.close();
		InputStream iS = httpUrlConnection.getInputStream();
		InputStreamReader iSR = new InputStreamReader(iS);
		BufferedReader in = new BufferedReader(iSR);

		String decodedString;

		while ((decodedString = in.readLine()) != null) {
			System.out.println(decodedString);
		}
		in.close();

	}
}
