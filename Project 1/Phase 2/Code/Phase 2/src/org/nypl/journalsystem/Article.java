package org.nypl.journalsystem;

import java.util.*;
import org.nypl.journalsystem.core.IArticle;

public class Article implements IArticle {
    private String title;
    private int id;
    private List<Author> authors;
    private List<Article> citings = new ArrayList<Article>();
    private String citingsString;

    public Article(String title, int id, List<Author> authors) {
        System.out.println("Initializing " + title);
        this.title = title;
        this.authors = authors;
    }

    public String getTitle(){
        return title;
    }

    public void print(String prefix) {
        System.out.println(prefix + title);
        System.out.println(prefix + "Written By:");
        for (Author author : authors) {
            System.out.println(prefix + "\t" + author.getName());
        }
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    public void setCitingsString(String citingsString) throws Exception {
        if (this.citingsString != null) {
            throw new Exception("citingsString has already been set for " + title + " as: " + this.citingsString);
        }
        this.citingsString = citingsString;
    }

    public String getCitingsString() throws Exception{
        if (citingsString == null) {
            throw new Exception("citingsString hasn't been set for " + title);
        }
        return citingsString;

    }

    public void addCiting(Article cite) {
        citings.add(cite);
    }

    public List<Article> getCitings() {
        return citings;
    }
    
    public int getID() {
        return id;
    }

}
