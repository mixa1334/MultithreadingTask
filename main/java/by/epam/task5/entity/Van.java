package by.epam.task5.entity;

import java.util.StringJoiner;

import static by.epam.task5.entity.VanState.*;

public class Van implements Runnable {
    private int capacity;
    private String name;
    private int numberOfGoods;
    private boolean expressDelivery;
    private VanState state;

    public Van(String name, int numberOfGoods, boolean expressDelivery, int capacity) {
        this.name = name;
        this.numberOfGoods = numberOfGoods;
        this.expressDelivery = expressDelivery;
        this.capacity = capacity;
        state = NEW;
    }

    @Override
    public void run() {
        // TODO: 11/22/2021
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfGoods() {
        return numberOfGoods;
    }

    public void setNumberOfGoods(int numberOfGoods) {
        this.numberOfGoods = numberOfGoods;
    }

    public boolean isExpressDelivery() {
        return expressDelivery;
    }

    public void setExpressDelivery(boolean expressDelivery) {
        this.expressDelivery = expressDelivery;
    }

    public VanState getState() {
        return state;
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
        if (numberOfGoods != van.numberOfGoods) return false;
        if (expressDelivery != van.expressDelivery) return false;
        return name != null ? name.equals(van.name) : van.name == null;
    }

    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + numberOfGoods;
        result = 31 * result + (expressDelivery ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Van.class.getSimpleName() + "[", "]")
                .add("capacity=" + capacity)
                .add("name='" + name + "'")
                .add("numberOfGoods=" + numberOfGoods)
                .add("expressDelivery=" + expressDelivery)
                .toString();
    }
}