package by.epam.task5.io;

import by.epam.task5.entity.Van;

import java.util.List;

public interface VanParser {
    List<Van> parse(List<String> vans);
}