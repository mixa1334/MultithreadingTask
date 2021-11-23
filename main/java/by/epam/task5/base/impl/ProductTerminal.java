package by.epam.task5.base.impl;

import by.epam.task5.base.Terminal;
import by.epam.task5.entity.Van;
import by.epam.task5.entity.VanState;
import by.epam.task5.exception.LogisticBaseException;
import by.epam.task5.service.WorkProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.StringJoiner;

public class ProductTerminal implements Terminal {
    private static final Logger logger = LogManager.getLogger();
    private final int terminalId;
    private final ProductStorage storage;

    public ProductTerminal(int terminalId, ProductStorage storage) {
        this.terminalId = terminalId;
        this.storage = storage;
    }

    public void vanHandling(Van van) {
        try {
            switch (van.getState()) {
                case NEEDS_LOADING -> {
                    van.setState(VanState.IN_PROCESS);
                    List<String> products = storage.getFromStorage(van.getCapacity());
                    WorkProcess.work();
                    van.loadProducts(products);
                }
                case NEEDS_UNLOADING -> {
                    van.setState(VanState.IN_PROCESS);
                    List<String> products = van.retrieveProducts();
                    WorkProcess.work();
                    storage.putInStorage(products);
                }
            }
            van.setState(VanState.FINISHED);
        } catch (LogisticBaseException e) {
            logger.error("unloading / loading error");
        }
    }

    public int getTerminalId() {
        return terminalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTerminal terminal = (ProductTerminal) o;

        return terminalId == terminal.terminalId;
    }

    @Override
    public int hashCode() {
        return terminalId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductTerminal.class.getSimpleName() + "[", "]")
                .add("terminalId=" + terminalId)
                .toString();
    }
}
