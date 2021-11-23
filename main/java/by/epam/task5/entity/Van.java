package by.epam.task5.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static by.epam.task5.entity.VanState.*;

public class Van implements Runnable {
    private final int capacity;
    private final String name;
    private final List<String> products;
    private final boolean expressDelivery;
    private VanState state;

    public Van(String name, boolean expressDelivery, int capacity) {
        this.name = name;
        this.expressDelivery = expressDelivery;
        this.capacity = capacity;
        products = new ArrayList<>(capacity);
        state = NEW;
    }

    @Override
    public void run() {
        // TODO: 11/22/2021
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public List<String> retrieveProducts() {
        return products;
    }

    public boolean isExpressDelivery() {
        return expressDelivery;
    }

    public VanState getState() {
        return state;
    }

    public void loadProducts(List<String> products) {
        Collections.copy(this.products, products);
    }

    public void setState(VanState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Van van = (Van) o;

        if (capacity != van.capacity) return false;
        if (expressDelivery != van.expressDelivery) return false;
        if (name != null ? !name.equals(van.name) : van.name != null) return false;
        if (products != null ? !products.equals(van.products) : van.products != null) return false;
        return state == van.state;
    }

    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (expressDelivery ? 1 : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Van.class.getSimpleName() + "[", "]")
                .add("capacity=" + capacity)
                .add("name='" + name + "'")
                .add("products=" + products)
                .add("expressDelivery=" + expressDelivery)
                .add("state=" + state)
                .toString();
    }
}