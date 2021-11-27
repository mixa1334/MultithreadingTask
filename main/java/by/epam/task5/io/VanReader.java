package by.epam.task5.io;

import by.epam.task5.exception.LogisticBaseException;

import java.util.List;

public interface VanReader {
    List<String> readVans(String path) throws LogisticBaseException;
}
