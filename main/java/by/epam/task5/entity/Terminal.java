package by.epam.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Terminal {
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_TIME_DELAY = 5;
    private final int terminalId;

    public Terminal(int terminalId) {
        this.terminalId = terminalId;
    }

    public void vanHandling(Van van) {
        // TODO: 11/22/2021
        try {
            TimeUnit.SECONDS.sleep((int) (Math.random() * MAX_TIME_DELAY));
        } catch (InterruptedException e) {
            logger.error("unloading / loading error");
            Thread.currentThread().interrupt();
        }
    }

    public int getTerminalId() {
        return terminalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Terminal terminal = (Terminal) o;

        return terminalId == terminal.terminalId;
    }

    @Override
    public int hashCode() {
        return terminalId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Terminal.class.getSimpleName() + "[", "]")
                .add("terminalId=" + terminalId)
                .toString();
    }
}
