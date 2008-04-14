package com.bg.util2;



public class StringTokenizerBg {

	
	private String s ;
	private String token ;
	private int i=0;
	
	public StringTokenizerBg(String s, String token) {
		this.s=s;
		if (s.startsWith(token)){
			this.s=s.substring(token.length());
		}
		this.token=token;
	}
	
	public boolean hasMoreTokens() {
		return (i<s.length());
	}
	             
	public String nextToken(){
		int j = s.indexOf(token,i+1);
		int ii;
		if (i >0){
			ii=i+token.length();
		}else {
			ii=i;
		}
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
		String s4= "poueta123456789apoueta123456789apoueta123456789apoueta123456789apouet";
		String s5 ="IB - GROUPE CEGOS IB ESPACES ET SERVICES - Labège-Innopole";
		testUnit(s1,"pouet");
		testUnit(s2,"pouet");
		testUnit(s3,"pouet");
		testUnit(s4,"pouet");
		testUnit(s5," - ");
		
	}
	
	private static void testUnit(String ss, String token){
		System.out.println("-----------------------------------");
		try {
			StringTokenizerBg st = new StringTokenizerBg(ss,token);
			int i=0;
			while(st.hasMoreTokens()){
				String t = st.nextToken();
				System.out.println(i+":"+t);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
