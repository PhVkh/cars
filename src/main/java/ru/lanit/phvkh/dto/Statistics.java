package ru.lanit.phvkh.dto;

public class Statistics {
    private long numberOfPeople;
    public long getNumberOfPeople() {
        return numberOfPeople;
    }

    private long numberOfCars;
    public long getNumberOfCars() {
        return numberOfCars;
    }

    private long numberOfVendors;
    public long getNumberOfVendors() {
        return numberOfVendors;
    }

    public Statistics(long numberOfPeople, long numberOfCars, long numberOfVendors) {
        this.numberOfPeople = numberOfPeople;
        this.numberOfCars = numberOfCars;
        this.numberOfVendors = numberOfVendors;
    }
}
