package com.broll.poklmon.model.shop;

public class ShopArticle {

	private int itemId;
	private int price = -1;

	public ShopArticle(int id) {
		this.itemId = id;
	}

	public ShopArticle(int id, int price) {
		this.itemId = id;
		this.price=price;
	}

	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	public int getItemId() {
		return itemId;
	}

}
