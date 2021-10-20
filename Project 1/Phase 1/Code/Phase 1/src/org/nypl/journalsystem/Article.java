package org.nypl.journalsystem;

import java.util.*;

public class Article {
    private String title;
    private List<String> authors;

    Article(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle(){
        return title;
    }

    public void print(String prefix) {
        System.out.println(prefix + title);
        System.out.println(prefix + "Written By:");
        for (String author : authors) {
            System.out.println(prefix + "\t" + author);
        }
    }
    
    



}
