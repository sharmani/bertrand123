package bg.simuAccordeon.cadenceur;


public class Cadenceur implements Runnable{
	
	
	private  long time = 100l;
	private IAwakable awakable;
	public Cadenceur(int delta) {
		this.time=delta;
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}
	
	
	public void run() {
		try {
			while(true){
				Thread.sleep(time);
				if (this.awakable== null){				
				}else {
					new ThreadAwake();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class ThreadAwake implements Runnable{
		ThreadAwake() {
			Thread tt = new Thread(this);
			tt.setPriority(Thread.MAX_PRIORITY);
			tt.start();
		}
		public void run(){
			awakable.awake();
		}
	}

	public void setAwakable(IAwakable awakable) {
		this.awakable = awakable;
	}

}
