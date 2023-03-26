package com.gildedrose;

class GildedRose {
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
	private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
	private static final String AGED_BRIE = "Aged Brie";
	private static final String CONJURED = "Conjured Mana Cake";
	Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateQualityOfItem(item);
        }
    }

	private void updateQualityOfItem(Item item) {
		int degradationOfItem = item.name.equals(CONJURED) ? -2 : -1;
		boolean doesDegrade= !item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE) && !item.name.equals(SULFURAS);
		
		//quality updates
		if (doesDegrade) {
	    	qualityUpdate(item, degradationOfItem);
	    }
		
		if (item.name.equals(AGED_BRIE) || item.name.equals(BACKSTAGE)) {
			qualityUpdate(item, 1);
		}
		
		updateQualityBackstage(item);
		
		//sellIn updates
		if (!item.name.equals(SULFURAS)) {
		    item.sellIn -= 1;
		}
		
		if (item.sellIn < 0) {
			updateQualityExpiredSellIn(item, degradationOfItem, doesDegrade);			
		}
	}

	private void updateQualityBackstage(Item item) {
		if (item.name.equals(BACKSTAGE)) {
	        if (item.sellIn < 11 && item.quality < 50) {
	            qualityUpdate(item, 1);
	        }

	        if (item.sellIn < 6) {
	            qualityUpdate(item, 1);
	        }
	    }
	}

	private void updateQualityExpiredSellIn(Item item, int degradationOfItem, boolean doesDegrade) {		
		if(doesDegrade) {
			qualityUpdate(item, degradationOfItem);
		}else {
			qualityUpdate(item, 1);
		}
		
		if (item.name.equals(BACKSTAGE)) {
            item.quality = 0;
        }
	}

	private void qualityUpdate(Item item, int adjust) {
		int currentQuality = item.quality + adjust;
		
		if(currentQuality >= 0 && currentQuality <= 50) {
			item.quality += adjust;
		}
	}
}