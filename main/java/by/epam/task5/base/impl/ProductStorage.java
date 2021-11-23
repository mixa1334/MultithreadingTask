package by.epam.task5.base.impl;

import by.epam.task5.base.Storage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProductStorage implements Storage<String> {
    private static final Logger logger = LogManager.getLogger();
    private final int CAPACITY;
    private final LinkedList<String> storageProducts;
    private Timer timer;
    private ReentrantLock lock;
    private Condition waitingForFreeStorageSpace;
    private Condition waitingForFreeGoods;

    public ProductStorage(int capacity) {
        this.CAPACITY = capacity;
        this.storageProducts = new LinkedList<>();
        lock = new ReentrantLock();
        waitingForFreeStorageSpace = lock.newCondition();
        waitingForFreeGoods = lock.newCondition();
        timer = new Timer(true);
        timer.schedule(new GarbageTruck(), 5_000, 10_000);
    }

    public List<String> getFromStorage(int numberOfProducts) {
        ArrayList<String> result = new ArrayList<>(numberOfProducts);
        try {
            lock.lock();
            while (numberOfProducts > 0) {
                String product = storageProducts.poll();
                if (product != null) {
                    result.add(product);
                    numberOfProducts--;
                } else {
                    waitingForFreeStorageSpace.signalAll();
                    waitingForFreeGoods.await();
                }
            }
            waitingForFreeStorageSpace.signalAll();
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e.getMessage());
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void putInStorage(List<String> products) {
        try {
            lock.lock();
            while (products.size() > 0) {
                if (storageProducts.size() < CAPACITY) {
                    String product = products.remove(products.size() - 1);
                    storageProducts.offer(product);
                } else {
                    waitingForFreeGoods.signalAll();
                    waitingForFreeStorageSpace.await();
                }
            }
            waitingForFreeGoods.signalAll();
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private class GarbageTruck extends TimerTask {
        @Override
        public void run() {
            try {
                lock.lock();
                logger.log(Level.INFO, "GarbageTrack start work");
                logger.log(Level.INFO, "Storage before \"GT\" -> " + storageProducts);
                while (storageProducts.size() > CAPACITY / 2) {
                    storageProducts.poll();
                    waitingForFreeStorageSpace.signalAll();
                }
                while (storageProducts.size() < CAPACITY / 2) {
                    storageProducts.offer("from GT");
                    waitingForFreeGoods.signalAll();
                }
                logger.log(Level.INFO, "Storage after \"GT\" -> " + storageProducts);
            } finally {
                logger.log(Level.INFO, "GarbageTrack end work");
                lock.unlock();
            }
        }
    }
}