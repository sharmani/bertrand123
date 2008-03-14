package fr.imc.simu.sequenceur;

public class Sequenceur implements Runnable {

	private long periode = 500; // long = type d'entier long (64 bits)
	private boolean isOn = true;
	private Reveil mon_reveil;

	/* private Thread t; */

	public Sequenceur(Reveil r) {
		mon_reveil = r;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		while (isOn) {
			try {
				long timeStart = System.currentTimeMillis();
				mon_reveil.reveil();
				long dt = System.currentTimeMillis() - timeStart;
				long delaiAttente = periode - dt;
				if (delaiAttente > 0) {
					Thread.sleep(periode - dt);
				}

			} catch (InterruptedException e) {
			}
			System.out.println("top");
		}

	}

}
