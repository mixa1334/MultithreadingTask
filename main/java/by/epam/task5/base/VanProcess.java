package by.epam.task5.base;

import by.epam.task5.entity.Van;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanProcess implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private final Van van;
    private final LogisticBase base;

    public VanProcess(LogisticBase base, Van van) {
        this.van = van;
        this.base = base;
    }

    @Override
    public void run() {
        String name = van.getName();
        logger.log(Level.INFO, "van " + name + " start work");
        Terminal terminal = base.getTerminal(van);
        logger.log(Level.INFO, "van " + name + " got a terminal");
        terminal.vanHandling(van);
        base.closeTerminal(terminal);
        logger.log(Level.INFO, "van " + name + " return the terminal");
        logger.log(Level.INFO, "van " + name + " end work");
    }
}
