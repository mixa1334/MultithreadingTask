package by.epam.task5.io.impl;

import by.epam.task5.entity.Van;
import by.epam.task5.entity.VanState;
import by.epam.task5.io.VanParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VanParserImpl implements VanParser {
    private static final String DELIMITER = "; ";
    private static final String PRODUCT_DELIMITER = ", ";

    @Override
    public List<Van> parse(List<String> vans) {
        ArrayList<Van> result = new ArrayList<>();
        for (String string : vans) {
            string = string.strip();
            Van van = new Van();
            String[] parameters = string.split(DELIMITER);
            int capacity = Integer.parseInt(parameters[0]);
            van.setCapacity(capacity);
            van.setName(parameters[1]);
            boolean isExpressDelivery = Boolean.parseBoolean(parameters[2]);
            van.setExpressDelivery(isExpressDelivery);
            VanState state = VanState.valueOf(parameters[3]);
            van.setState(state);
            if (van.getState() == VanState.NEEDS_UNLOADING) {
                String[] productArray = parameters[4].split(PRODUCT_DELIMITER);
                List<String> products = Arrays.stream(productArray).toList();
                van.loadProducts(products);
            }
            result.add(van);
        }
        return result;
    }
}
