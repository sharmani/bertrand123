package bg.simuAccordeon.junit;

import java.applet.Applet;

import bg.simuAccordeon.voiture.Voiture;
import junit.framework.TestCase;

public class Test_1 extends TestCase {

	public void test_1(){
	}
	
	public void test_convert_Vitesse(){
		double v = 10;
		double v_km_h = Voiture.convertVitesse_m_s_to_km_h(v);
		System.out.println("v_km_h: "+v_km_h+"  v_m_s: "+v);
		assertEquals(v_km_h, 36d);
	}
}
