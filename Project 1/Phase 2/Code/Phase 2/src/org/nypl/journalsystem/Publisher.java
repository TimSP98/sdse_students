package org.nypl.journalsystem;

import org.nypl.journalsystem.core.IPublisher;

public class Publisher implements IPublisher {
    private String name;
    private String location;

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLocation() {
        return location;
    }

}
