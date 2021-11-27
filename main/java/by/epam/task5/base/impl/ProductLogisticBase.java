package by.epam.task5.base.impl;

import by.epam.task5.base.LogisticBase;
import by.epam.task5.base.Storage;
import by.epam.task5.base.Terminal;
import by.epam.task5.entity.Van;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductLogisticBase implements LogisticBase {
    private static final Logger logger = LogManager.getLogger();
    private static ProductLogisticBase instance = null;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean needToCreate = new AtomicBoolean(true);
    private final int NUMBER_OF_TERMINALS;
    private Storage storage;
    private ArrayDeque<Terminal> freeTerminals;
    private ArrayList<Terminal> currentlyUsedTerminals;
    private ArrayDeque<Condition> expressVans;
    private ArrayDeque<Condition> regularVans;
    private ReentrantLock terminalLock;

    private ProductLogisticBase() {
        ResourceBundle bundle = ResourceBundle.getBundle("baseConfig");
        String numberOfTerminalsStr = bundle.getString("terminals");
        NUMBER_OF_TERMINALS = Integer.parseInt(numberOfTerminalsStr);
        String storageCapacityStr = bundle.getString("storagecapacity");
        int storageCapacity = Integer.parseInt(storageCapacityStr);
        storage = new ProductStorage(storageCapacity);
        terminalLock = new ReentrantLock();
        initTerminals();
    }

    private void initTerminals() {
        expressVans = new ArrayDeque<>();
        regularVans = new ArrayDeque<>();
        freeTerminals = new ArrayDeque<>(NUMBER_OF_TERMINALS);
        currentlyUsedTerminals = new ArrayList<>(NUMBER_OF_TERMINALS);
        for (int i = 0; i < NUMBER_OF_TERMINALS; i++) {
            freeTerminals.offerLast(new ProductTerminal(i + "", storage));
        }
    }

    public static ProductLogisticBase getInstance() {
        if (needToCreate.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ProductLogisticBase();
                    needToCreate.set(false);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public Terminal getTerminal(Van van) {
        Terminal terminal = null;
        try {
            terminalLock.lock();
            terminal = freeTerminals.pollFirst();
            while (terminal == null) {
                Condition condition = terminalLock.newCondition();
                if (van.isExpressDelivery()) {
                    expressVans.offerLast(condition);
                } else {
                    regularVans.offerLast(condition);
                }
                condition.await();
                terminal = freeTerminals.pollFirst();
            }
            currentlyUsedTerminals.add(terminal);
            logger.log(Level.INFO, van.getName() + " got " + terminal);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e.getMessage());
        } finally {
            terminalLock.unlock();
        }
        return terminal;
    }

    @Override
    public void closeTerminal(Van van, Terminal terminal) {
        try {
            terminalLock.lock();
            currentlyUsedTerminals.remove(terminal);
            freeTerminals.offerLast(terminal);
            Condition condition = expressVans.pollFirst();
            if (condition == null) {
                condition = regularVans.pollFirst();
            }
            if (condition != null) {
                condition.signal();
            }
            logger.log(Level.INFO, van.getName() + " return " + terminal);
        } finally {
            terminalLock.unlock();
        }
    }
}