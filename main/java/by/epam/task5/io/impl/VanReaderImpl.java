package by.epam.task5.io.impl;

import by.epam.task5.exception.LogisticBaseException;
import by.epam.task5.io.VanReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class VanReaderImpl implements VanReader {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<String> readVans(String path) throws LogisticBaseException {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.toList();
        } catch (IOException e) {
            logger.log(Level.ERROR, "invalid path to file - " + path);
            throw new LogisticBaseException("invalid path to file - " + path, e);
        }
    }
}
