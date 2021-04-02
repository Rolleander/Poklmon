package com.broll.poklmon.save;

import com.broll.pokllib.poklmon.PoklmonWesen;
import com.broll.poklmon.battle.poklmon.states.MainFightStatus;

public class PoklmonData {

	public final static int NOT_IN_TEAM = -1;

	private int poklmon;
	private int level;
	private int kp;
	private int exp;
	private String dateCaught;
	private int levelCaught;
	private String name;
	private AttackData[] attacks = new AttackData[4];
	private int teamPlace;
	private short[] dv, fp;
	private int poklball;
	private MainFightStatus status;
	private PoklmonWesen wesen;
	private int carryItem=-1;
	private PoklmonStatistic statistic = new PoklmonStatistic();

	public int getPoklmon() {
		return poklmon;
	}

	public PoklmonStatistic getStatistic() {
		return statistic;
	}

	public void setPoklmon(int poklmon) {
		this.poklmon = poklmon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AttackData[] getAttacks() {
		return attacks;
	}

	public void setAttacks(AttackData[] attacks) {
		this.attacks = attacks;
	}

	public int getTeamPlace() {
		return teamPlace;
	}

	public void setTeamPlace(int teamPlace) {
		this.teamPlace = teamPlace;
	}

	public void setKp(int kp) {
		this.kp = kp;
	}

	public int getKp() {
		return kp;
	}

	public String getDateCaught() {
		return dateCaught;
	}

	public void setDateCaught(String dateCaught) {
		this.dateCaught = dateCaught;
	}

	public int getLevelCaught() {
		return levelCaught;
	}

	public void setLevelCaught(int levelCaught) {
		this.levelCaught = levelCaught;
	}

	public short[] getDv() {
		return dv;
	}

	public short[] getFp() {
		return fp;
	}

	public void setDv(short[] dv) {
		this.dv = dv;
	}

	public void setFp(short[] fp) {
		this.fp = fp;
	}

	public PoklmonWesen getWesen() {
		return wesen;
	}

	public void setWesen(PoklmonWesen wesen) {
		this.wesen = wesen;
	}

	public int getPoklball() {
		return poklball;
	}

	public void setPoklball(int poklball) {
		this.poklball = poklball;
	}

	public MainFightStatus getStatus() {
		return status;
	}

	public void setStatus(MainFightStatus status) {
		this.status = status;
	}

	public int getCarryItem() {
		return carryItem;
	}

	public void setCarryItem(int carryItem) {
		this.carryItem = carryItem;
	}
}
