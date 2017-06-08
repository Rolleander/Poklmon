package com.broll.poklmon.model.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.broll.poklmon.data.DataContainer;

public class ShopInstance {

	public final static String[] SHOP_ACTIONS=new String[]{"Kaufen","Verkaufen","Abbruch"};
	private final static float SELL_FACTOR = 0.3f;
	private float sellFactor = SELL_FACTOR;
	private List<ShopArticle> articles = new ArrayList<ShopArticle>();
	private HashMap<Integer, Integer> customSellPrice = new HashMap<Integer, Integer>();
	private DataContainer data;
	private boolean buy, sell;

	public ShopInstance(DataContainer data, boolean buy, boolean sell) {
		this.data = data;
		this.buy = buy;
		this.sell = sell;
	}

	public boolean isBuy() {
		return buy;
	}

	public boolean isSell() {
		return sell;
	}

	public void addArticle(ShopArticle article) {
		if (article.getPrice() == -1) {
			int price = data.getItems().getItem(article.getItemId()).getPrice();
			article.setPrice(price);
		}
		this.articles.add(article);
	}

	public void setSellFactor(float sellFactor) {
		this.sellFactor = sellFactor;
	}

	public int getSellWorth(int item) {
		if (customSellPrice.containsKey(item)) {
			return customSellPrice.get(item);
		}
		return (int) (data.getItems().getItem(item).getPrice() * sellFactor);
	}

	public void addCustomSellValue(int id, int worth) {
		customSellPrice.put(id, worth);
	}

	public List<ShopArticle> getArticles() {
		return articles;
	}

}
