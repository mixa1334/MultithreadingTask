package by.epam.task5.main;

import by.epam.task5.base.VanProcess;
import by.epam.task5.entity.Van;
import by.epam.task5.exception.LogisticBaseException;
import by.epam.task5.io.VanParser;
import by.epam.task5.io.VanReader;
import by.epam.task5.io.impl.VanParserImpl;
import by.epam.task5.io.impl.VanReaderImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            VanReader reader = new VanReaderImpl();
            VanParser parser = new VanParserImpl();
            List<String> vanStrings = reader.readVans("src/main/resources/vans.txt");
            List<Van> vans = parser.parse(vanStrings);
            ExecutorService executorService = Executors.newFixedThreadPool(vans.size());
            for (Van van : vans) {
                executorService.execute(new VanProcess(van));
            }
            executorService.shutdown();
        } catch (LogisticBaseException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
