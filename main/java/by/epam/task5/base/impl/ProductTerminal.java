package by.epam.task5.base.impl;

import by.epam.task5.base.Storage;
import by.epam.task5.base.Terminal;
import by.epam.task5.entity.Van;
import by.epam.task5.exception.LogisticBaseException;
import by.epam.task5.service.WorkProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.epam.task5.entity.VanState.*;

public class ProductTerminal implements Terminal {
    private static final Logger logger = LogManager.getLogger();
    private final Storage storage;

    public ProductTerminal(Storage storage) {
        this.storage = storage;
    }

    public void vanHandling(Van van) {
        try {
            van.setState(IN_PROCESS);
            switch (van.getState()) {
                case NEEDS_LOADING -> {
                    List<String> products = storage.getFromStorage(van.getCapacity());
                    WorkProcess.work();
                    van.loadProducts(products);
                }
                case NEEDS_UNLOADING -> {
                    List<String> products = van.retrieveProducts();
                    WorkProcess.work();
                    storage.putInStorage(products);
                }
            }
            van.setState(FINISHED);
        } catch (LogisticBaseException e) {
            logger.error("unloading / loading error");
        }
    }
}
