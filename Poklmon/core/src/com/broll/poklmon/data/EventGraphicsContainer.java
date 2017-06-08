package com.broll.poklmon.data;

import com.broll.poklmon.data.basics.Image;

public class EventGraphicsContainer extends ResourceContainer {

	private final static String menuGraphicPath = "event/";
	private Image profPfiffikus;

	public EventGraphicsContainer() {
		setPath(menuGraphicPath);
	}

	public void load() throws DataException {

		profPfiffikus = loadImage("profpfiffi.png");

	}

	public Image getProfPfiffikus() {
		return profPfiffikus;
	}

}
