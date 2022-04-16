package theGame;

public class TimeData {
	private long tick = System.currentTimeMillis();
	private long elapsedHero = 0; 	
	private long elapsedDay = 0;
	private boolean stopped;
	static double DAY_MILLISECONDS = 24_000; 
	static int HERO_DELAY = 1500; 
	private float timeModifier =10;
	

	/**
	 * Augmente le temps �coul� depuis son dernier appel
	 */
	private void tickTock() {
		long tock = System.currentTimeMillis();
		elapsedHero += (tock - tick) * timeModifier;
		elapsedDay += (tock - tick) * timeModifier;
		tick = tock;
	}

	/**
	 * @return le pourcentage de journ�e �coul�
	 */
	public double timeFraction() {
		if (!stopped) {
			tickTock();
		}
		return (elapsedDay % DAY_MILLISECONDS) / (double) DAY_MILLISECONDS;
	}

	/**
	 * Remet le temps du jour � 0
	 */
	public void resetelapsedDay() {
		elapsedDay = 0;
	}
	
	/**
	 * @return le temps �coul� du le jour
	 */
	public long elapsedDay() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedDay;
	}
	
	/**
	 * @return le temps �coul� depuis la derniere action du hero
	 */
	public long elapsedHero() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedHero;
	}

	/**
	 * Remet le temps depuis la derni�re action du hero � 0
	 */
	public void resetElapsedHero() {
		elapsedHero = 0;
	}

	/**
	 * Arrette le temps
	 */
	public void stop() {
		if (!stopped) {
			tickTock();
			stopped = true;
		}		
	}

	/**
	 * Relance le temps
	 */
	public void start() {
		stopped = false;
		tick = System.currentTimeMillis();
	}
	
	/**
	 * Diminue la vitesse de passage du temps de 50%
	 */
	public void slower() {
		if (timeModifier > 0.25) {
			timeModifier /=2;
		}
	}
	
	/**
	 * Augmente la vitesse de passage du temps de 50%
	 */
	public void faster() {
		if (timeModifier < 4) {
			timeModifier *=2;
		}
	}
	
	/**
	 * Fait passer le temps apr�s un combat
	 */
	public void fight() {
		elapsedHero+=2000;
		elapsedDay+=2000;
	}
	
	/**
	 * @return true si le temps est stopp�,false sinon
	 */
	public boolean isStopped() {
		return stopped;
	}
}
