package theGame;

public class TimeData {
	private long tick = System.currentTimeMillis();
	private long elapsedHero = 0; 	
	private long elapsedDay = 0;
	private boolean stopped;
	static double DAY_MILLISECONDS = 24_000; 
	static int HERO_DELAY = 1500; 
	private float timeModifier =10;
	private float beaconModifier = 1;
	

	/**
	 * Update the pass time since the last call
	 */
	private void tickTock() {
		long tock = System.currentTimeMillis();
		elapsedHero += (tock - tick) * timeModifier * beaconModifier;
		elapsedDay += (tock - tick) * timeModifier;
		tick = tock;
	}

	/**
	 * Give the time fraction of the day to display
	 * 
	 * @return the percentage of day passed
	 */
	public double timeFraction() {
		if (!stopped) {
			tickTock();
		}
		return (elapsedDay % DAY_MILLISECONDS) / (double) DAY_MILLISECONDS;
	}

	/**
	 * Reset day time 
	 */
	public void resetelapsedDay() {
		elapsedDay = 0;
	}
	
	/**
	 * Get the elapsed time of the day
	 * 
	 * @return the elapsed time of the day
	 */
	public long elapsedDay() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedDay;
	}
	
	/**
	 * Get the elapsed time since the hero last action
	 * 
	 * @return the elapsed time since the hero last action
	 */
	public long elapsedHero() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedHero;
	}

	/**
	 * Reset elapsed time since the hero last action
	 */
	public void resetElapsedHero() {
		elapsedHero = 0;
	}

	/**
	 * Stop the time
	 */
	public void stop() {
		if (!stopped) {
			tickTock();
			stopped = true;
		}		
	}

	/**
	 * Restart the time progression
	 */
	public void start() {
		stopped = false;
		tick = System.currentTimeMillis();
	}
	
	/**
	 * Divide the time pass speed by half
	 */
	public void slower() {
		if (timeModifier > 0.25) {
			timeModifier /=2;
		}
	}
	
	/**
	 * Multiply the time pass speed by two
	 */
	public void faster() {
		if (timeModifier < 4) {
			timeModifier *=2;
		}
	}
	
	/**
	 * Check if the time is stopped
	 * 
	 * @return true if time is stopped, else false 
	 */
	public boolean isStopped() {
		return stopped;
	}
	
	/**
	 * Actualise the beaconModifier
	 */
	public void thereIsABeacon() {
		beaconModifier=(float)1.4;
	}
	
	/**
	 * Actualise the beaconModifier
	 */
	public void thereIsNoBeacon() {
		beaconModifier=1;
	}
}
