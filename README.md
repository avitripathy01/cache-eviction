# cache-eviction
Mechanism of evicting the cache based on the priority of the the keyed items and application usage pattern. Typically manifests in 
1. Least recently used abbreviated as LRU is a mechanism of storing the recently accessed item in cache and evicting the older ones.
2. Least Frequently used abbreviated as LFU is a mechanism of evicting the cached items with the least access usage pattern. LFU shines in usecases where the content is frequently accessed for e.g. the website logo could be the most frequently accessed content in cdn cache while other content of least usage could be evicted based on the ussage patterns and cache capacity limit.
