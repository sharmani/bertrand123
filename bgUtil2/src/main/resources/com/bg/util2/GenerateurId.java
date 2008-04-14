package com.bg.util2;

/**
 * @author bertrand.guiral
 * 
 * Classe permettant de generer des Id uniques
 */
public class GenerateurId {

    private static long nbRequest = 0;

    /**
     *  
     */

    public static String getId() {
        nbRequest++;
        return System.currentTimeMillis() + "" + nbRequest;
    }

    public static long getIdAsLong() {
        return Long.parseLong(getId());
    }

    public static String getIdAlphaShort() {
        nbRequest++;
        return Long.toString((System.currentTimeMillis() % 2301), Character.MAX_RADIX) + Long.toString(nbRequest % 100000, Character.MAX_RADIX);
    }

    public static void main(String[] arg) {
        int j = 0;
        for (int i = 0; i < 1000000; i++) {
            getIdAlphaShort();
            if (i % 100000 == 0) {
                System.out.println(j + " ::::> GenerateurId  : " + getIdAlphaShort() + "   " + getId() + "  " + nbRequest);
                j++;
            }
        }
    }
}