package com.bg.util2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author bertrand
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class UtilCopy {
    private static final int internalBufferSize = 4096;

    /**
     *  
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte buf[] = new byte[internalBufferSize];
        int count;
        while ((count = in.read(buf)) > 0) {
            out.write(buf, 0, count);
        }
        out.flush();
    }

    /**
     *  
     */
    public static void copy(String s, OutputStream out) throws IOException {
        byte buf[] = s.getBytes();
        out.write(buf, 0, s.length());
        out.flush();
    }

    /**
     *  
     */
    public static void copy(String s, File f) throws IOException {
        copy(s, f, false);
    }

    /**
     *  
     */
    public static void copy(String s, File f, boolean append) throws IOException {
        OutputStream out = new FileOutputStream(f, append);
        copy(s, out);
    }

    /**
     *  
     */
    public static void copyGrep(BufferedReader in, PrintWriter out, String grep) throws IOException {
        if (grep == null) {
            return;
        }
        String line;
        while ((line = in.readLine()) != null) {
            if (line.indexOf(grep) >= 0) {
                out.println(line);
            }
        }
        out.flush();
    }

    /**
     * @param in
     * @param out
     * @throws Exception
     */
    public static void copyAndCloseStreams(InputStream in, OutputStream out) throws IOException {
        try {
            copy(in, out);
        } finally {
            try {
                in.close();
            } catch (Throwable e0) {
                System.out.println("copyAndCloseStreams EXCEPTION 0:" + e0.getMessage());
            }
            try {
                out.close();
            } catch (Throwable e1) {
                System.out.println("copyAndCloseStreams EXCEPTION 1:" + e1.getMessage());
            }

        }
    }

    /**
     * Copy un fichier dans un flux
     * <p>
     */
    public static void copy(File file, OutputStream out) throws IOException {
        FileInputStream in = new FileInputStream(file);
        UtilCopy.copyAndCloseStreams(in, out);
    }

    /**
     * Copy un fichier dans un flux
     * <p>
     */
    public static void copy(File file, File fDest) throws IOException {
        copy(file, fDest, false);
    }

    /**
     * Copy un fichier dans un flux
     * <p>
     */
    public static void copy(File file, File fDest, boolean append) throws IOException {
       
        if (!fDest.getParentFile().exists()) {
            fDest.getParentFile().mkdirs();
        }
        if (!fDest.exists()) {
            fDest.createNewFile();
        }
        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream(fDest, append);
        UtilCopy.copyAndCloseStreams(in, out);
    }

    /**
     * Copy un fichier dans un flux
     * <p>
     */
    public static void copyGrep(File file, Writer out_, String grep) throws IOException {
        FileReader fin = new FileReader(file);
        BufferedReader in = new BufferedReader(fin);
        PrintWriter pout = new PrintWriter(out_);
        try {
            copyGrep(in, pout, grep);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                System.out.println("copyGrep EXCEPTION A: " + e.getMessage());
            }
            try {
                pout.close();
            } catch (Exception e) {
            }
            try {
                out_.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Copy un fichier dans un flux
     * <p>
     */
    public static void copyTail(File file, Writer out_, int nbLignes, String grep) throws IOException {
        if (grep == null) {
            grep = "";
        }
        FileReader fin_1 = new FileReader(file);
        BufferedReader in_1 = new BufferedReader(fin_1);
        String line;
        int nbLignesTotal = 0;
        while ((line = in_1.readLine()) != null) {
            nbLignesTotal++;
        }
        in_1.close();
        FileReader fin_2 = new FileReader(file);
        BufferedReader in_2 = new BufferedReader(fin_2);
        PrintWriter pout = new PrintWriter(out_);

        int numeroLigne = 0;
        while ((line = in_2.readLine()) != null) {
            numeroLigne++;
            if (numeroLigne > (nbLignesTotal - nbLignes)) {
                if (line.indexOf(grep) >= 0) {
                    pout.println(line);
                }
            }
        }
        try {
            in_2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pout.close();

    }

    /**
     * @param url
     * @param out
     * @throws Exception
     *             copy une URL vers un flux
     */
    public static void copy(URL url, OutputStream out) throws IOException {
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        UtilCopy.copyAndCloseStreams(in, out);
    }

    /**
     * @param url
     * @param out
     * @throws Exception
     *             copy une URL vers un flux
     */
    public static void copy(URL url, File f, boolean append) throws Exception {
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        FileOutputStream fOut = new FileOutputStream(f, append);
        UtilCopy.copyAndCloseStreams(in, fOut);
    }
    /**
     * @param url
     * @param out
     * @throws Exception
     *             copy une URL vers un flux
     */
    public static void copy(URL url, File f) throws Exception {
        copy(url,f,false);
    }

    /**
     * @param in
     * @param fDest
     *            Cette methode static est utile pour copier une image vers un
     *            fichier
     */
    public static int copy(InputStream in, File fDest) {
    	int r=0;
        if (!fDest.getParentFile().exists()) {
            fDest.getParentFile().mkdirs();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(fDest);
            byte[] buff = new byte[1024];
            
            while ((r = in.read(buff)) > 0) {
                out.write(buff, 0, r);
            }
        } catch (Exception e) {
            System.out.println("! copy " + e.getMessage() + " !");
        } finally {
            try {
                out.close();
            } catch (Exception e1) {
            }
            try {
                in.close();
            } catch (IOException e2) {
            }
        }
        return r;
    }

    public static byte[] copy(File f) throws Exception {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        copy(f, bOut);
        return bOut.toByteArray();
    }

    public static byte[] copy(InputStream in) throws Exception {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        copy(in, bOut);
        return bOut.toByteArray();
    }

    public static String copyAsString(File f) throws Exception {
        FileReader fr = new FileReader(f);
        StringBuffer sb = new StringBuffer();
        int c = 0;
        while ((c = fr.read()) != -1) {
            sb.append((char) c);
        }
        fr.close();
        return new String(sb);
    }
    
    public static String copyAsString_ISO_8859_1(File f) throws Exception {
		FileInputStream fin = new FileInputStream(f);
		InputStreamReader in = new InputStreamReader(fin, "ISO-8859-1");
		StringBuffer sb = new StringBuffer();
		int c = 0;
		while ((c = in.read()) != -1) {
			sb.append((char) c);
		}
		in.close();
		return new String(sb);
	}
    
    

    public static void copy(byte[] b, File f) {
        ByteArrayInputStream bIn = new ByteArrayInputStream(b);
        copy(bIn, f);
    }
    
    public static void copy(byte[] b, OutputStream out) throws Exception{
        ByteArrayInputStream bIn = new ByteArrayInputStream(b);
        copy(bIn, out);
    }
}