package by.epam.task5.main;

import by.epam.task5.base.impl.ProductStorage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Temp {
    public static void main(String[] args) {

        ResourceBundle bundle = ResourceBundle.getBundle("baseConfig");
        String numebrOfTerminals = bundle.getString("terminals");
        int c = Integer.parseInt(numebrOfTerminals);
        String storageCapacity = bundle.getString("storagecapacity");

        System.out.println(c);
        System.out.println(storageCapacity);

//        ProductStorage storage = new ProductStorage(10);
//        ExecutorService executor = Executors.newFixedThreadPool(7);
//        executor.execute(new T(3, storage, "1", false));
//        executor.execute(new T(10, storage, "2", false));
//        executor.execute(new T(7, storage, "3", true));
//        executor.execute(new T(5, storage, "4", false));
//        executor.execute(new T(1, storage, "5", true));
//        executor.execute(new T(9, storage, "6", true));
//        executor.execute(new T(3, storage, "7", true));
//        executor.execute(new T(2, storage, "8", false));
//
//        executor.shutdown();
    }
}

class T extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private String name;
    private int cap;
    private ProductStorage storage;
    private boolean st;

    public T(int c, ProductStorage s, String n, boolean zagruzka) {
        cap = c;
        storage = s;
        name = n;
        st = zagruzka;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "start T - " + name);
        if (st) {
            logger.log(Level.INFO, storage.getFromStorage(cap) + " received to ->" + name);
        } else {
            ArrayList<String> products = new ArrayList<>(cap);
            for (int i = 0; i < cap; i++) {
                products.add(name + "prod");
            }
            storage.putInStorage(products);
            logger.log(Level.INFO, cap + " loaded from -> " + name);
        }
        logger.log(Level.INFO, "end T - " + name);
    }
}
