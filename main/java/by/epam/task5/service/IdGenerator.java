package by.epam.task5.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class IdGenerator {
    private static IdGenerator instance;
    private static AtomicBoolean firstAppeal = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private final AtomicLong id = new AtomicLong(0);

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        if (firstAppeal.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new IdGenerator();
                    firstAppeal.set(false);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public long generateId() {
        return id.getAndIncrement();
    }
}
