package org.nypl.journalsystem;


public class Publisher {
    private String name;
    private String location;

    Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    String getName() {
        return name;
    }

    String getLocation() {
        return location;
    }

}
