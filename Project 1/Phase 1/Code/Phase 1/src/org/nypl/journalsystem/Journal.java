package org.nypl.journalsystem;

import java.util.*;


public class Journal {
    private String name;
    private Publisher publisher;
    private String ISSN;
    private List<Article> articles = new ArrayList<Article>();

    Journal(String name, Publisher publisher, String ISSN) {
        this.name = name;
        this.publisher = publisher; 
        this.ISSN = ISSN;
    }

    public boolean isFull() {
        return articles.size() >= 3;
    }

    public int hashCode() {
        return ISSN.hashCode();
    }

    public void addArticle(Article article) {
        articles.add(article);

    }
    public String getISSN() {
        return ISSN;
    }

    public void print() {
        String suffix = "";
        if (!isFull()) suffix = " - NOT FULL";
        System.out.println("Journal name: " + name + suffix);
        System.out.println("\tISSN: " + ISSN);
        System.out.println("\tPublisher: " + publisher.getName() + ", located in " + publisher.getLocation());


        System.out.println("\tArticles in Journal:");
        for (Article article : articles) {
            article.print("\t\t");
        }

    }
}
