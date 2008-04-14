package com.bg.util2;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 */
public class HostName {
	public static String hostname() {
		String hostName = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			// Get IP Address
			//byte[] ipAddr = addr.getAddress();
			// Get hostname
			hostName = addr.getHostName().toLowerCase();
			int point = hostName.indexOf('.');
			if (point > 0) {
				// On ne garde que le nom de host, on ne veut pas du FQDN
				hostName = hostName.substring(0, point);
			}
		} catch (UnknownHostException e) {
			System.out.println("hostname() : UnknownHostException:"
					+ e.getMessage());
		}
		if (hostName == null)
			hostName = "";
		return hostName;
	}

	/**
	 * 
	 * @return
	 */
	public static String getIp() {
		String r = "";
		try {
			InetAddress[] a = InetAddress.getAllByName(hostname());
			String separator="; ";
			
			for(int i=0;i<a.length;i++){
				r += a[i].getHostAddress()+separator;
			}
		} catch (UnknownHostException e) {
			r = "?.?.";
		}
		return r;
	}
	
	public static void main(String[] a) throws Exception{
	    System.out.println("HostNAme");
	    System.out.println("InetAddress.getLocalHost:    "+ InetAddress.getLocalHost());
	    String name = InetAddress.getLocalHost().getHostName();
	    System.out.println(" ............. getAllIPs  "+getAllIPs());
	    System.out.println(" ............. hosNAme  "+name);
	}
	
	public static String getAllIPs()  {
	    String s = "";
	    try {
            String host = InetAddress.getLocalHost().getHostName();
            s +=InetAddress.getByName(host);
        } catch (Throwable e) {
           // e.printStackTrace();
        }
	    return s;
	}
}