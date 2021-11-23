package by.epam.task5.service;

import by.epam.task5.exception.LogisticBaseException;

import java.util.concurrent.TimeUnit;

public class WorkProcess {
    public static final int FIVE_SECONDS_DELAY = 5_000;

    public static void work() throws LogisticBaseException {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (Math.random() * FIVE_SECONDS_DELAY));
        } catch (InterruptedException e) {
            throw new LogisticBaseException("work is impossible", e);
        }
    }
}
