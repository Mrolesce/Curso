package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;

class GildedRoseTest {
	
	@Nested
	class OK {
		//calidad se degrada a doble de velocidad al pasar la fecha
	    @Test
	    void calidadDegradaDobleDeVelocidad() {
	    	Item[] item = new Item[] { 
	    			new Item("foo", 0 ,6),
	    			new Item("foo", 0 ,3),
	    			new Item("foo", 0 ,7)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertAll("Degradacion doble",
	    			() -> assertEquals(4, app.items[0].quality),
	    			() -> assertEquals(4, app.items[0].quality),
	    			() -> assertEquals(4, app.items[0].quality));
	    	    	
	    }
	    
	    //test para probar si la calidad no es negativa al ejecutar updateQuality
	    @Test
	    void calidadMenorACero() {
	    	Item[] item = new Item[] { new Item("foo", 0 , 0)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertTrue(app.items[0].quality >= 0);
	    }
	    
	    //test para la mejora de calidad de Aged Brie
	    @Test
	    void agedBrieTest() {
	    	Item[] item = new Item[] { new Item("Aged Brie", 12 , 5)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertEquals(6, app.items[0].quality);
	    }
	    
	    //test para la mejora doble de calidad de Aged Brie
	    @Test
	    void agedBrieDoubleQualityTest() {
	    	Item[] item = new Item[] { new Item("Aged Brie", 0 , 5)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertEquals(7, app.items[0].quality);
	    }
	    
	    //test para probar si se sale de la máxima calidad
	    @Test
	    void maxQuality() {
	    	Item[] item = new Item[] { new Item("Aged Brie", 0 , 49)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertTrue(app.items[0].quality <= 50);
	    }
	    
	    //test para ver si las cualidades de sulfuras se modifican al actualizar
	    @Test
	    void sulfurasTest() {
	    	Item[] item = new Item[] { 
	    			new Item("Sulfuras, Hand of Ragnaros", 23 , 49),
	    			new Item("Sulfuras, Hand of Ragnaros", 0 , 0),
	    			new Item("Sulfuras, Hand of Ragnaros", 34 , 45)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertAll("titulo",
	    			() -> assertEquals(23, app.items[0].sellIn),
	    			() -> assertEquals(49, app.items[0].quality),
	    			() -> assertEquals(0, app.items[1].sellIn),
	    			() -> assertEquals(0, app.items[1].quality),
	    			() -> assertEquals(34, app.items[2].sellIn),
	    			() -> assertEquals(45, app.items[2].quality));
	    }
	    
	    //test modificacion de calidad de entradas backstage
	    @Test
	    void entradaBackstageTest() {
	    	Item[] item = new Item[] { 
	    			new Item("Backstage passes to a TAFKAL80ETC concert", 10 , 5),
	    			new Item("Backstage passes to a TAFKAL80ETC concert", 5 , 5),
	    			new Item("Backstage passes to a TAFKAL80ETC concert", 0 , 5)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertAll("Calidad de pase backstage",
	    			() -> assertEquals(7, app.items[0].quality),
	    			() -> assertEquals(8, app.items[1].quality),
	    			() -> assertEquals(0, app.items[2].quality));
	    } 
	    
	    @Test
	    void conjuredTest() {
	    	Item[] item = new Item[] { 
	    			new Item("Conjured Mana Cake", 3 , 5)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertEquals(3, app.items[0].quality);
	    }
	    
	    @Test
	    void conjuredTestSellInZero() {
	    	Item[] item = new Item[] { 
	    			new Item("Conjured Mana Cake", 0 , 5)};
	    	GildedRose app = new GildedRose(item);
	    	
	    	app.updateQuality();
	    	
	    	assertEquals(1, app.items[0].quality);
	    }
	}
	   
}
