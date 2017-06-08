package com.broll.poklmon.save;

public class PokldexEntry {

	private int cacheCount;
	private String seenDate;

	public PokldexEntry() {
		cacheCount = 0;
		this.seenDate = null;
	}

	public PokldexEntry(String data) {
		cacheCount = 0;
		this.seenDate = data;
	}

	public String getSeenDate() {
		return seenDate;
	}

	public int getCacheCount() {
		return cacheCount;
	}

	public void setCacheCount(int cacheCount) {
		this.cacheCount = cacheCount;
	}

}
