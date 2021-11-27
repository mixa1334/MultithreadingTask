package by.epam.task5.base.impl;

import by.epam.task5.base.Storage;
import by.epam.task5.base.Terminal;
import by.epam.task5.entity.Van;
import by.epam.task5.entity.VanState;
import by.epam.task5.exception.LogisticBaseException;
import by.epam.task5.base.WorkProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.StringJoiner;

import static by.epam.task5.entity.VanState.*;

public class ProductTerminal implements Terminal {
    private static final Logger logger = LogManager.getLogger();
    private final Storage storage;
    private String name;

    public ProductTerminal(String name, Storage storage) {
        this.name = name;
        this.storage = storage;
    }

    public void vanHandling(Van van) {
        try {
            VanState state = van.getState();
            van.setState(IN_PROCESS);
            switch (state) {
                case NEEDS_LOADING -> {
                    List<String> products = storage.getFromStorage(van.getCapacity());
                    WorkProcess.work();
                    van.loadProducts(products);
                    logger.info(van.getName() + " loaded - " + products);
                }
                case NEEDS_UNLOADING -> {
                    List<String> products = van.retrieveProducts();
                    WorkProcess.work();
                    storage.putInStorage(products);
                    logger.info(van.getName() + " unloaded - " + products);
                }
            }
            van.setState(FINISHED);
        } catch (LogisticBaseException e) {
            logger.error("unloading / loading error");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductTerminal.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
