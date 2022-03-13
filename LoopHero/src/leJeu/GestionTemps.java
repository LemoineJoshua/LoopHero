package leJeu;

public class GestionTemps {

	private long tick = System.currentTimeMillis();
	private long TempsEcouleDebut = 0; 	// temps écoulé depuis le début
	private long TempsEcouleBob = 0; 	// temps écoulé depuis la dernière action de BOB
	private boolean stopped;
	public final static double DUREE_JOUR = 20_000; //nombre de milliseconde dans une journée
	public final static int DELAI_HERO = 1000;       //temps entre deux actions de BOB

	private void tickTock() {
		long tock = System.currentTimeMillis();
		TempsEcouleDebut += tock - tick;
		TempsEcouleBob += tock - tick;
		tick = tock;
	}

	public double timeFraction() {
		if (!stopped) {
			tickTock();
		}
		return (TempsEcouleDebut % DUREE_JOUR) / (double) DUREE_JOUR;
	}

	public long TempsEcouleBob() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return TempsEcouleBob;
	}

	public void resetTempsEcouleBob() {
		TempsEcouleBob = 0;
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
}