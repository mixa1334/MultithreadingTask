package by.epam.task5.base;

import java.util.List;

public interface Storage {
    List<String> getFromStorage(int numberOfEntities);

    void putInStorage(List<String> entity);
}
