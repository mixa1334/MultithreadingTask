package by.epam.task5.base;

import by.epam.task5.entity.Van;

public interface LogisticBase {
    Terminal getTerminal(Van van);

    void closeTerminal(Van van, Terminal terminal);
}
