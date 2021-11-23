package by.epam.task5.base;

import java.util.List;

public interface Storage<T> {
    List<T> getFromStorage(int numberOfEntities);

    void putInStorage(List<T> entity);
}
