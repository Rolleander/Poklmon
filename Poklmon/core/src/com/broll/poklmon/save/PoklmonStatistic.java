package com.broll.poklmon.save;

public class PoklmonStatistic {

	private int fightedBattles;
	private int trainerBattles;
	private int killedPoklmons;
	private int faintedCount;

	private int defeatedArenas;
	private int ligaWins;

	public PoklmonStatistic() {

	}

	public void wonArena() {
		defeatedArenas++;
	}

	public void wonLiga() {
		ligaWins++;
	}

	public void killedPoklmon() {
		killedPoklmons++;
	}

	public void fainted() {
		faintedCount++;
	}

	public void foughtInBattle() {
		fightedBattles++;
	}

	public void foughtInTrainerBattle() {
		trainerBattles++;
		foughtInBattle();
	}

	public int getDefeatedArenas() {
		return defeatedArenas;
	}

	public int getLigaWins() {
		return ligaWins;
	}

	public void setDefeatedArenas(int defeatedArenas) {
		this.defeatedArenas = defeatedArenas;
	}

	public void setLigaWins(int ligaWins) {
		this.ligaWins = ligaWins;
	}

	/**
	 * Returns the {@link #fightedBattles}.
	 * 
	 * @return The fightedBattles.
	 */
	public int getFightedBattles() {
		return fightedBattles;
	}

	/**
	 * Sets the {@link #fightedBattles}.
	 * 
	 * @param fightedBattles
	 *            The new fightedBattles to set.
	 */
	public void setFightedBattles(int fightedBattles) {
		this.fightedBattles = fightedBattles;
	}

	/**
	 * Returns the {@link #trainerBattles}.
	 * 
	 * @return The trainerBattles.
	 */
	public int getTrainerBattles() {
		return trainerBattles;
	}

	/**
	 * Sets the {@link #trainerBattles}.
	 * 
	 * @param trainerBattles
	 *            The new trainerBattles to set.
	 */
	public void setTrainerBattles(int trainerBattles) {
		this.trainerBattles = trainerBattles;
	}

	/**
	 * Returns the {@link #killedPoklmons}.
	 * 
	 * @return The killedPoklmons.
	 */
	public int getKilledPoklmons() {
		return killedPoklmons;
	}

	/**
	 * Sets the {@link #killedPoklmons}.
	 * 
	 * @param killedPoklmons
	 *            The new killedPoklmons to set.
	 */
	public void setKilledPoklmons(int killedPoklmons) {
		this.killedPoklmons = killedPoklmons;
	}

	/**
	 * Returns the {@link #faintedCount}.
	 * 
	 * @return The faintedCount.
	 */
	public int getFaintedCount() {
		return faintedCount;
	}

	/**
	 * Sets the {@link #faintedCount}.
	 * 
	 * @param faintedCount
	 *            The new faintedCount to set.
	 */
	public void setFaintedCount(int faintedCount) {
		this.faintedCount = faintedCount;
	}

}
