package com.cache.eviction.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LRUTest {

    @BeforeEach
    void setUp() {

    }


    @Test
    void get() {
        LRU<String, String> lru = new LRU<>();
        lru.put("Batman", "Bruce Wayne");
        lru.put("Superman", "Clark Kent");

        assertEquals(lru.get("Superman"), "Clark Kent");
        assertFalse(lru.get("Batman").equals("Clark Kent"));

        lru.put("The cat", "Selina Kyle");
        assertEquals(lru.get("Superman"), "Clark Kent");
        assertEquals(lru.get("Batman"), null);
    }

    @Test
    void put() {
        LRU<String, String> lru = new LRU<>();
        assertEquals(lru.put("Batman", "Bruce Wayne"), "Bruce Wayne");
        assertFalse("Bruce Wayne".equals(lru.put("Superman", "Clark Kent")));
    }

    @Test
    void reset() {
        LRU<String, String> lru = new LRU<>();
        lru.put("Batman", "Bruce Wayne");
        lru.put("Superman", "Clark Kent");
        lru.reset();
        assertFalse("Clark Kent".equals(lru.get("Superman")));
        assertEquals(lru.get("Superman"), null);
    }
}