package org.nypl.journalsystem;

import org.nypl.journalsystem.core.IAuthor;

public class Author implements IAuthor{

    private String name;
    private int id;

    public Author(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }
    
}
