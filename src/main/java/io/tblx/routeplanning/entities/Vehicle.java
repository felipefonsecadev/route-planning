package io.tblx.routeplanning.entities;

import java.util.UUID;

public class Vehicle {

    private UUID id;
    private String name;

    private Vehicle() {
    }

    public Vehicle(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
