package by.epam.task5.base;

import by.epam.task5.entity.Van;

public class VanProcess implements Runnable {
    private final Van van;
    private final LogisticBase base;

    public VanProcess(LogisticBase base, Van van) {
        this.van = van;
        this.base = base;
    }

    @Override
    public void run() {
        Terminal terminal = base.getTerminal();
        terminal.vanHandling(van);
        base.closeTerminal(terminal);
    }
}
