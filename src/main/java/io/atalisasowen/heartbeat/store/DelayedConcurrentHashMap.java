package io.atalisasowen.heartbeat.store;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class DelayedConcurrentHashMap<K, V> {
    private ConcurrentHashMap<K, V> currentHashMap;
    private ConcurrentHashMap<K, V> lastHashMap;
    private final ReentrantLock lock;

    private final Runnable clearTimeoutValue = () -> {
        for (;;){
            this.clearOld();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public DelayedConcurrentHashMap() {
        this.currentHashMap = new ConcurrentHashMap<>();
        this.lastHashMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
        new Thread(this.clearTimeoutValue).start();
    }

    private void clearOld(){
        this.lock.lock();
        this.lastHashMap = this.currentHashMap;
        this.currentHashMap = new ConcurrentHashMap<>();
        this.lock.unlock();
    }

    public V get(K key) {
        this.lock.lock();
        try{
            if (currentHashMap.containsKey(key)){
                return currentHashMap.get(key);
            }
            if (lastHashMap.containsKey(key)){
                return lastHashMap.get(key);
            }
            return null;
        }finally {
            this.lock.unlock();
        }

    }

    public V put(K key, V value){
        this.lock.lock();
        try {
            return this.currentHashMap.put(key, value);
        }finally {
            this.lock.unlock();
        }
    }

    public V remove(K key){
        this.lock.lock();
        try{
            if (currentHashMap.containsKey(key)){
                return currentHashMap.remove(key);
            }
            if (lastHashMap.containsKey(key)){
                return lastHashMap.remove(key);
            }
            return null;
        }finally {
            this.lock.unlock();
        }
    }




}
