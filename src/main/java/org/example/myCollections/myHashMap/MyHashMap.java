package org.example.myCollections.myHashMap;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements MyMap<K, V>{
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private List<Entry<K, V>>[] buckets;
    private int size;
    public MyHashMap(){
        this(DEFAULT_CAPACITY);
    }
    public MyHashMap(int capacity){
        buckets = new ArrayList[capacity];
        size = 0;
    }
    private static class Entry<K, V>{
        private final K key;
        private V value;

        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }
        public K getKey(){
            return key;
        }
        public V getValue(){
            return value;
        }
        public void setValue(V value){
            this.value = value;
        }
    }
    private int getIndex(K key){
        return Math.abs(key.hashCode() % buckets.length);
    }
    private void resize(){
        List<Entry<K, V>>[] oldBuckets = buckets;
        buckets = new ArrayList[oldBuckets.length * 2];
        size = 0;
        for(List<Entry<K, V>> bucket : oldBuckets){
            if(bucket != null){
                for(Entry<K, V> entry : bucket){
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public void put(K key, V value){
        int index = getIndex(key);
        if(buckets[index] == null){
            buckets[index] = new ArrayList<>();
        }
        for(Entry<K, V> entry : buckets[index]){
            if(entry.getKey().equals(key)){
                entry.setValue(value);
                return;
            }
        }
        buckets[index].add(new Entry<>(key, value));
        size++;
        if((double) size / buckets.length > LOAD_FACTOR){
            resize();
        }
    }

    @Override
    public V get(K key){
        int index = getIndex(key);
        if(buckets[index] != null){
            for(Entry<K, V> entry : buckets[index]){
                if(entry.getKey().equals(key)){
                    return entry.getValue();
                }
            }
        }
        return null;
    }
    @Override
    public V remove(K key){
        int index = getIndex(key);
        if(buckets[index] != null){
            for(Entry<K, V> entry : buckets[index]){
                if(entry.getKey().equals(key)){
                    V value = entry.getValue();
                    buckets[index].remove(entry);
                    size--;
                    return value;
                }
            }
        }
        return null;
    }
    @Override
    public boolean containsKey(K key){
        int index = getIndex(key);
        if(buckets[index] != null){
            for(Entry<K, V> entry : buckets[index]){
                if(entry.getKey().equals(key)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size(){
        return size;
    }
}
