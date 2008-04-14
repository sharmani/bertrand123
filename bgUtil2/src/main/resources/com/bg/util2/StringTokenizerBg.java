package com.bg.util2;

import java.util.StringTokenizer;

public class StringTokenizerBg {

	
	private String s ;
	private String token ;
	private int i=0;
	
	public StringTokenizerBg(String s, String token) {
		this.s=s;
		this.token=token;
	}
	
	
	public boolean hasMoreToken() {
		return (i<s.length());
	}
	
	public String nextToken()throws Exception{
		int j = s.indexOf(token,i+1);
		int ii=i+token.length();
		if (j == -1){
			this.i = s.length();
			return s.substring(ii);
			
		}else {			
			i=j;
			return s.substring(ii,j);
		}
	}

	public static void main(String[] a){
		String s1= "   0000000  pouet   1111111111    pouet   2222222222    pouet ";
		String s2= "a123456789apoueta123456789apoueta123456789apoueta123456789a";
		String s3= "a123456789apoueta123456789apoueta123456789apoueta123456789apouet";
		testUnit(s1,"pouet");
		testUnit(s2,"pouet");
		testUnit(s3,"pouet");
		
	}
	
	private static void testUnit(String ss, String token){
		System.out.println("-----------------------------------");
		try {
			StringTokenizerBg st = new StringTokenizerBg(ss,"pouet");
			int i=0;
			while(st.hasMoreToken()){
				String t = st.nextToken();
				System.out.println(i+":"+t);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
