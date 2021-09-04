package com.cache.eviction.strategy.service;

import java.util.Optional;

public interface LRUCache<K, V> {

    void addToCache(K k, V v);

    Optional<V> getFromCache(K k);
}
