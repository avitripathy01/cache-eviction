package com.cache.eviction.strategy.service.impl;

import com.cache.ds.DoubleLinkedList;
import com.cache.ds.impl.DoubleLinkedListImpl;
import com.cache.eviction.strategy.service.LRUCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class LRUCacheImpl<K, V> implements LRUCache<K, V> {

    private int size;
    private Map<K, V> lruMap;
    private DoubleLinkedList<K> dll;

    private LRUCacheImpl() {
    }

    /**
     * Static factory instatiation method
     *
     * @param cacheSize
     * @return
     */
    public static LRUCacheImpl getInstanceOfLRUCache(int cacheSize) {
        var lruCache = new LRUCacheImpl<>();
        lruCache.size = cacheSize;
        lruCache.dll = DoubleLinkedListImpl.getInstanceOf(lruCache.size);
        lruCache.lruMap = new HashMap<>();
        return lruCache;
    }


    public void addToCache(K k, V v) {

        final var cacheMap = this.lruMap;
        final var dll = this.dll;
        this.lruMap.computeIfAbsent(k, k1 -> {
            if (dll.addFirst(k)) {
                cacheMap.put(k1, v);
            } else {
                dll.removeLast();
                dll.addFirst(k);
                cacheMap.put(k, v);
            }
            return null;
        });
    }

    public Optional<V> getFromCache(K k) {
        final DoubleLinkedList linkedListCache = this.dll;
        return Optional.ofNullable(this.lruMap.computeIfPresent(k, (k1, v1) -> {
            linkedListCache.moveFirst(k);
            return v1;
        }));
    }

}
