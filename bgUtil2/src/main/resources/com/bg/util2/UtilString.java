package com.bg.util2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author bertrand.guiral
 *  
 */
public class UtilString {

    public static final String _acute = "&acute;";

    public static final String _ccedil = "&ccedil;";

    public static final String _ecute = "&ecute;";

    public static final String _egrave = "&egrave;";

    public static final String _ecirc = "&ecirc;";

    public static final String _icirc = "&icirc;";
    
    public static final String _amp="&amp;";

    /**
     *  
     */
    public static String toHexa(String str) {
        if (str == null){
            return null;
        }
        try {
            return toHexa(str.getBytes());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     *  
     */
    public static String toHexa(byte b[]) {
        StringBuffer buffer = new StringBuffer();
        if (b == null) {
            return ""; // Inutile de remonter un null qui pourra généré un jour
            // une exception stupide
        } else {
            for (int i = 0; i < b.length; i++) {
                buffer.append(toHexa(b[i]));
                buffer.append(' ');
            }
            return new String(buffer);
        }
    }

    /**
     * Util pour les conversions
     */
    public static String toHexa0x(byte b[]) {
        StringBuffer buffer = new StringBuffer();
        if (b == null) {
            return ""; // Inutile de remonter un null qui pourra généré un jour
            // une exception stupide
        } else {
            for (int i = 0; i < b.length; i++) {
                buffer.append(" 0x" + toHexa(b[i]));
                buffer.append(", ");
            }
            return new String(buffer);
        }
    }

    /**
     *  
     */
    public static String toHexa(int b[]) {
        StringBuffer buffer = new StringBuffer();
        if (b == null) {
            return ""; // Inutile de remonter un null qui pourra généré un jour
            // une exception stupide
        } else {
            for (int i = 0; i < b.length; i++) {
                buffer.append(toHexa(b[i]));
                buffer.append(' ');
            }
            return new String(buffer);
        }
    }

    /**
     *  
     */
    public static String toHexa(byte b) {
        String tmp = Integer.toHexString(b & 0xff).trim();
        if (tmp.length() == 1) {
            tmp = "0" + tmp;
        }
        return tmp;
    }

    /**
     *  
     */
    public static String toHexa(int b) {
        String tmp = Integer.toHexString(b).trim();
        if (tmp.length() == 1) {
            tmp = "0" + tmp;
        }
        return tmp;
    }

    /**
     * Transforme une chaine de la forme : 0f 08 44 00 20 01 00 (par exemple) en
     * un array de byte
     */
    public static byte[] parseHexa(String sHexa) {
        StringTokenizer st = new StringTokenizer(sHexa, " ");
        byte[] b_ = new byte[sHexa.length()];
        int ii = 0;
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            try {
                b_[ii] = (byte) Integer.parseInt(s, 16);
                ii++;
            } catch (Exception e) { // C'est pas un hexa, quoi faire ?
            }
        }
        byte[] b = new byte[ii];
        for (int k = 0; k < b.length; k++) {
            b[k] = b_[k];
        }
        return b;
    }

    public static String toString(List list) {
        if (list == null) {
            return null;
        }
        Iterator ite = list.iterator();
        StringBuffer r = new StringBuffer();
        while (ite.hasNext()) {
            r.append("" + ite.next());
        }
        return new String(r);
    }

    /**
     *  
     */
    public static String toString(Object[] oo) {
        if (oo == null) {
            return null;
        }
        String r = "";
        for (int i = 0; i < oo.length; i++) {
            r += "  " + oo[i];
        }
        return r;
    }

    /**
     *  
     */
    public static String toString(Vector v) {
        if (v == null) {
            return "null";
        }
        String r = "";
        Enumeration enu = v.elements();
        while (enu.hasMoreElements()) {
            r += "  " + enu.nextElement();
        }
        return r;
    }

    /**
     *  
     */
    public static String toStringAsChar(byte[] b) {
        if (b == null)
            return "null";
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            char c = (char) b[i];
            if ((c > ' ') && (c < '·')) {
                buf.append(c);
            } else {
                buf.append('.');
            }
        }
        return new String(buf);
    }

    /**
     *  
     */
    public static String toString(Object b) {
        if (b == null)
            return "null";
        String r;
        StringBuffer buff = new StringBuffer();
        if (b.getClass().isArray()) {
            Class clazz = b.getClass().getComponentType();
            int length = Array.getLength(b);
            String trace = "\n";
            for (int i = 0; i < length; i++) {
                buff.append("." + Array.get(b, i));
            }
            r = new String(buff);
        } else {
            r = "" + b;
        }
        return r;
    }

    /**
     * 
     * @param i
     * @param nbChiffre
     * @return
     */
    public static String toString(int i, int nbChiffre) {
        String r = "" + i;
        for (int n = r.length(); n < nbChiffre; n++) {
            r = "0" + r;
        }
        return r;
    }

    /**
     * 
     * @param i
     * @param nbChiffre
     * @return
     */
    public static String toString6(int i) {
        String r = "" + i;
        for (int n = r.length(); n < 6; n++) {
            r = "0" + r;
        }
        return r;
    }

    /**
     *  
     */
    public static String toString7(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            char c = (char) (b[i] & 0x7F);
            buf.append(c);
        }
        return new String(buf);
    }

    /**
     * Format d'affichage du type exemple: abc sera affiché: a b c
     */
    public static String toString_3(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            char c = (char) b[i];
            if ((c > ' ') && (c < '·')) {
                buf.append(" " + c + " ");
            } else {
                buf.append(" . ");
            }
        }
        return new String(buf);
    }

    /**
     * Return la representation hexa completé a gauche avec des 0
     */
    public static String toHex4String(int i) {
        String s = Integer.toHexString(i);
        if (s.length() == 1) {
            s = "000" + s;
        } else if (s.length() == 2) {
            s = "00" + s;
        } else if (s.length() == 3) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * Enleve les characteres nn numeriques, et essaye de parser les characteres
     * qui restent. Renvoie 0 en cas d'echec.
     */
    public static int parseInteger(String s) {
        StringBuffer buf = new StringBuffer();
        // filtrage des charaters non numeriques
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= '0') && (c <= '9')) {
                buf.append(c);
            }
        }
        String sF = new String(buf);
        // conversion
        try {
            return Integer.parseInt(sF);
        } catch (Exception e) { // quoi faire ????
            return 0;
        }
    }

    public static int[] parseArrayInt(String argStr) {
        ArrayList list = new ArrayList();
        StringTokenizer st = new StringTokenizer(argStr.trim(), " ,;.:");
        int n = st.countTokens();
        int[] a = new int[n];
        int jj = 0;
        while (st.hasMoreTokens()) {
            a[jj] = Integer.parseInt(st.nextToken());
            jj++;
        }
        return a;
    }

    public static String[] parseArrayString(String argStr) {
        ArrayList list = new ArrayList();
        StringTokenizer st = new StringTokenizer(argStr.trim(), " ,;.:");
        int n = st.countTokens();
        String[] a = new String[n];
        int jj = 0;
        while (st.hasMoreTokens()) {
            a[jj] = st.nextToken();
            jj++;
        }
        return a;
    }

    /**
     *  
     */
    public static String toString(int[] b) {
        StringBuffer buf = new StringBuffer();
        if (b == null)
            return "";
        for (int i = 0; i < b.length; i++) {
            char c = (char) b[i];
            if ((c > ' ') && (c < '·')) {
                buf.append(c);
            } else {
                buf.append('.');
            }
        }
        return new String(buf);
    }

    /**
     * Filtre les characters qui ne sont pas des integers
     */
    public static String toStringIntegersOnly(int[] b) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((b[i] >= '0') && (b[i] <= '9')) {
                buff.append((char) b[i]);
            }
        }
        return new String(buff);
    }

    public static String toStringIntegersOnly(byte[] b) {
        StringBuffer buff = new StringBuffer();
        if (b == null) {
            return "";
        }
        for (int i = 0; i < b.length; i++) {
            char c = (char) b[i];
            if ((c >= '0') && (c <= '9')) {
                buff.append(c);
            }
        }
        return new String(buff);
    }

    /**
     * Filtre les characters qui ne sont pas des integers
     */
    public static String toStringIntegersOnly(String str) {
        if (str == null)
            return null;
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c >= '0') && (c <= '9')) {
                buff.append(c);
            }
        }
        return new String(buff);
    }

    /**
     * Encode les charaters accentues é,è,à en .... <BR>
     * This version of the entity set can be used with any SGML document which
     * uses ISO 8859-1 or ISO 10646 as its document character set. This includes
     * XML documents and ISO HTML documents.
     */
    public static String encodeXML(String str) {
        return encode(str, getPropertiesEncodingXML());
    }

    /**
     * Encode les charaters par leur valeurs définis dans les properties. Bon
     * c'est pas très clair.
     */
    private static String encode(String str, Properties p) {
        Enumeration enu = p.keys();
        while (enu.hasMoreElements()) {
            String oldSt = (String) enu.nextElement();
            String newSt = p.getProperty(oldSt, "");
            str = remplace(str, oldSt, newSt);
        }
        return str;
    }

    /**
     * Encode les charaters pour html (remplce é par &ecute par exemple)
     * marche qu'en 1.5
     * @param s
     * @return
     */

  /*  public static String encodeHTML(String s) {
        if (s == null){
            return null;
        }
        String r = s.replace("à", _acute).replace("ç", _ccedil).replace("é", _ecute).replace("è", _egrave).replace("ê", _ecirc).replace("î", _icirc);
        return r;
    }*/

    /**
     * Return les properties pour encoder XML
     */
    private static Properties getPropertiesEncodingXML() {
        Properties p = new Properties();
        p.setProperty("à", "&#224;");
        p.setProperty("â", "&#226;");
        p.setProperty("@", "&#229;");
        p.setProperty("ç", "&#231;");
        p.setProperty("è", "&#232;");
        p.setProperty("é", "&#233;");
        p.setProperty("ê", "&#234;");
        p.setProperty("î", "&#238;");
        p.setProperty("ô", "&#244;");
        p.setProperty("ù", "&#249;");
        p.setProperty("û", "&#251;");
        return p;
    }

    /**
     * Cherche et remplace dans un String, une chaine de character par une autre
     */
    public static String remplace(String str, String oldStr, String newStr) {
        try {
            int indexOfOld = 0;
            while ((indexOfOld = str.indexOf(oldStr, indexOfOld)) >= 0) {
                str = remplace(str, oldStr, newStr, indexOfOld);
                indexOfOld++;
            }
            return str;
        } catch (Throwable e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * Remplace la chaine de character à l'indice i
     */
    protected static String remplace(String str, String oldStr, String newStr, int i) {
        String begin = str.substring(0, i);
        String end = str.substring(i + oldStr.length(), str.length());
        return begin + newStr + end;
    }

    /**
     *  
     */
    public static String toString(String[] str) {
        if (str == null) {
            return "null";
        }
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buff.append(str[i] + "; ");
        }
        return new String(buff);
    }

    /**
     *  
     */
    public static String justifiedRight(String s, int n) {
        String blancs = "";
        if (s == null)
            s = "";
        for (int i = 0; i < n - s.length(); i++) {
            blancs += " ";
        }
        return blancs + s;
    }

    /**
     * enleve les sauts de lignes (Ox0D, 0xOA)
     */
    public static String cancelLineFeed(String str) {
        if (str == null) {
            return null;
        }
        return str.replace((char) 0x0D, ' ').replace((char) 0x0A, ' ');
    }

    /** ************************************************** */
    /**
     * Converts time in seconds to a <code>String</code> in the format
     * HH:mm:ss.
     * 
     * @param time
     *            the time in seconds.
     * @return a <code>String</code> representing the time in the format
     *         HH:mm:ss.
     */
    public static String secondsToString(long time) {
        int seconds = (int) (time % 60);
        int minutes = (int) ((time / 60) % 60);
        int hours = (int) ((time / 3600));
        String secondsStr = (seconds < 10 ? "0" : "") + seconds;
        String minutesStr = (minutes < 10 ? "0" : "") + minutes;
        String hoursStr = (hours < 10 ? "0" : "") + hours;
        return new String(hoursStr + ":" + minutesStr + ":" + secondsStr);
    }

    /** ************************************************** */
    /**
     * Converts time in seconds to a <code>String</code> in the format
     * HH:mm:ss.
     * 
     * @param time
     *            the time in seconds.
     * @return a <code>String</code> representing the time in the format
     *         HH:mm:ss.
     */
    public static String secondsToString2(long time) {
        int seconds = (int) (time % 60);
        int minutes = (int) ((time / 60) % 60);
        int hours = (int) ((time / 3600));
        String secondsStr = (seconds < 10 ? "0" : "") + seconds;
        String minutesStr = (minutes < 10 ? "0" : "") + minutes;
        String hoursStr = (hours < 10 ? "0" : "") + hours;
        return new String(hoursStr + "h :" + minutesStr + " mn :" + secondsStr + " s");
    }

    /**
     * Converts time in milliseconds to a <code>String</code> in the format
     * HH:mm:ss.SSS.
     * 
     * @param time
     *            the time in milliseconds.
     * @return a <code>String</code> representing the time in the format
     *         HH:mm:ss.SSS.
     */
    public static String millisecondsToString(long time) {
        int milliseconds = (int) (time % 1000);
        int seconds = (int) ((time / 1000) % 60);
        int minutes = (int) ((time / 60000) % 60);
        int hours = (int) ((time / 3600000));
        String millisecondsStr = (milliseconds < 10 ? "00" : (milliseconds < 100 ? "0" : "")) + milliseconds;
        String secondsStr = (seconds < 10 ? "0" : "") + seconds;
        String minutesStr = (minutes < 10 ? "0" : "") + minutes;
        String hoursStr = (hours < 100 ? "00" : (hours < 10 ? "0" : "")) + hours;
        String r = minutesStr + " mn " + secondsStr + "." + millisecondsStr;
        
        if (hours >0){
          r= hoursStr + " h " +r ;
        } 
        return r;
    }
    
    public static String millisecondsToString_2(long time) {
        int milliseconds = (int) (time % 1000);
        int seconds = (int) ((time / 1000) % 60);
        int minutes = (int) ((time / 60000) % 60);
        int hours = (int) ((time / 3600000));
        String millisecondsStr = (milliseconds < 10 ? "00" : (milliseconds < 100 ? "0" : "")) + milliseconds;
        String secondsStr = (seconds < 10 ? "0" : "") + seconds;
        String minutesStr = (minutes < 10 ? "0" : "") + minutes;
        String hoursStr = (hours < 100 ? "00" : (hours < 10 ? "0" : "")) + hours;
        return new String(hoursStr + " h " + minutesStr + " mn " + secondsStr + "." + millisecondsStr);
    }
    public static String toStringHtml(String s){
    	 return s.replace("\n", "<br/>");
    }
    	   
    public static String toStringHtml(Throwable e){
        String comment="Exception "+e.getMessage();
        try {
            for(int i=0;i<e.getStackTrace().length;i++){
                comment +="<BR>"+e.getStackTrace()[i];
            }
        } catch (Exception e1) {
           e1.printStackTrace();
        }
        return comment;
    }
}