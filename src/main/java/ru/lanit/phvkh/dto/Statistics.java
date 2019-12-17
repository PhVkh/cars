package ru.lanit.phvkh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {
    @JsonProperty("personcount")
    private long numberOfPeople;
    public long getNumberOfPeople() {
        return numberOfPeople;
    }

    @JsonProperty("carcount")
    private long numberOfCars;
    public long getNumberOfCars() {
        return numberOfCars;
    }

    @JsonProperty("uniquevendorcount")
    private long numberOfVendors;
    public long getNumberOfVendors() {
        return numberOfVendors;
    }

    public Statistics(long numberOfPeople, long numberOfCars, long numberOfVendors) {
        this.numberOfPeople = numberOfPeople;
        this.numberOfCars = numberOfCars;
        this.numberOfVendors = numberOfVendors;
    }

    public Statistics(){}
}
