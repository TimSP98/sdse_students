package org.nypl.journalsystem;

import java.util.*;

import org.nypl.journalsystem.core.IJournal;


public class Journal implements IJournal {
    private String name;
    private Publisher publisher;
    private String ISSN;
    private List<Article> articles = new ArrayList<Article>();

    public Journal(String name, Publisher publisher, String ISSN) {
        this.name = name;
        this.publisher = publisher; 
        this.ISSN = ISSN;
    }

    @Override
    public boolean isFullIssue() {
        return articles.size() >= 3;
    }

    public int hashCode() {
        return ISSN.hashCode();
    }

    public void addArticle(Article article) {
        articles.add(article);

    }

    @Override
    public String getIssn() {
        return ISSN;
    }

    public void print() {
        String suffix = "";
        if (!isFullIssue()) suffix = " - NOT FULL";
        System.out.println("Journal name: " + name + suffix);
        System.out.println("\tISSN: " + ISSN);
        System.out.println("\tPublisher: " + publisher.getName() + ", located in " + publisher.getLocation());


        System.out.println("\tArticles in Journal:");
        for (Article article : articles) {
            article.print("\t\t");
        }

    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

}
