package by.epam.task5.base;

import by.epam.task5.base.impl.ProductLogisticBase;
import by.epam.task5.entity.Van;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanProcess implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private final Van van;

    public VanProcess(Van van) {
        this.van = van;
    }

    @Override
    public void run() {
        ProductLogisticBase base = ProductLogisticBase.getInstance();
        String name = van.getName();
        logger.log(Level.INFO, name + " start work");
        Terminal terminal = base.getTerminal(van);
        terminal.vanHandling(van);
        base.closeTerminal(van, terminal);
        logger.log(Level.INFO, name + " end work");
    }
}
