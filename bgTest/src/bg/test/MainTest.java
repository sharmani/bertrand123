package bg.test;

import java.awt.Graphics;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class MainTest {

	public MainTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Test");
		List<String> list = new ArrayList<String>();
		list.add("Itch");
		list.add("Ni");
		list.add("San");
		list.add("Yu");
		list.add("luk");
		Iterator<String> ite = list.iterator();
		while(ite.hasNext()){
			String s = ite.next();
			ite.remove();
			System.out.println("------ "+s);
		}
		System.out.println("------ list.size() "+list.size());
		String s1 ="abcdefgh";
		String s2 ="abcdefgh";
		String s3 =s2.toString();
		System.out.println("------ s "+s1.hashCode()+"   "+s1);
		System.out.println("------ s "+s2.hashCode()+"   "+s2);
		System.out.println("------ s "+s3.hashCode()+"   "+s3);
	}
	

}
