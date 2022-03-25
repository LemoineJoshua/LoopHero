package theGame;

public class TimeData {
	private long tick = System.currentTimeMillis();
	private long elapsedTotal = 0; 	// temps écoulé depuis le début
	private long elapsedHero = 0; 	// temps écoulé depuis la dernière action de Hero
	private long elapsedDay = 0;
	private boolean stopped;
	public static double DAY_MILLISECONDS = 24_000; //nombre de milliseconde dans une journée
	public static int HERO_DELAY = 1500; //temps entre deux actions de Hero
	private float timeModifier =1;
	

	private void tickTock() {
		long tock = System.currentTimeMillis();
		elapsedTotal += (tock - tick) * timeModifier;
		elapsedHero += (tock - tick) * timeModifier;
		elapsedDay += (tock - tick) * timeModifier;
		tick = tock;
	}

	public double timeFraction() {
		if (!stopped) {
			tickTock();
		}
		return (elapsedTotal % DAY_MILLISECONDS) / (double) DAY_MILLISECONDS;
	}

	public void resetelapsedDay() {
		elapsedDay = 0;
	}
	
	public long elapsedDay() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedDay;
	}
	
	public long elapsedHero() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedHero;
	}

	public void resetElapsedHero() {
		elapsedHero = 0;
	}

	public boolean stopped() {
		return stopped;
	}

	public void stop() {
		tickTock();
		stopped = true;
	}

	public void start() {
		stopped = false;
		tick = System.currentTimeMillis();
	}
	
	public void slower() {
		if (timeModifier > 0.25) {
			timeModifier /=2;
		}
	}
	
	public void faster() {
		if (timeModifier < 4) {
			timeModifier *=2;
		}
	}
	
	public void fight() {
		elapsedTotal+=2000;
		elapsedHero+=2000;
		elapsedDay+=2000;
	}
}
