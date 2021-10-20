package org.nypl.journalsystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;



public class LibrarySystem {
    // Class Constants
    private final static String ROOT_DIR = "/home/tim/cloud/ITU/5. Semester/SoftwareDev/sdse_students/Project 1/Phase 1/Code/Phase 1/";
    private final static String DATA_PATH = ROOT_DIR + "data/";
    private final static Integer MAX_AUTHORS  = 500;
    private final static Integer MAX_ARTICLES = 500;

    private String[] all_authors = new String[MAX_AUTHORS];
    private Article[] all_articles = new Article[MAX_ARTICLES];
    private HashMap<String,Journal> ISSN_to_journal = new HashMap<String,Journal>();

    public LibrarySystem() {
        //TODO: Initialize system with default journals.
    }
    
    public void load() throws FileNotFoundException, IOException, Exception {
        loadJournals();
        loadAuthors();
        loadArticles();
    }
    
    private Iterable<CSVRecord> loadCSV(String file_path) throws FileNotFoundException, IOException {
        FileReader in = new FileReader(file_path);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreSurroundingSpaces()
            .parse(in);
        return records;
    }
    
    protected void loadAuthors() throws FileNotFoundException, IOException, Exception {
        //TODO: Load authors from file 
        String authors_path = DATA_PATH + "Authors.csv";
        Iterable<CSVRecord> records = loadCSV(authors_path);
        for (CSVRecord record : records) {
            int id = Integer.parseInt(record.get(0));
            String name = record.get(1);
            if (all_authors[id] != null) {
                throw new Exception("Author with id: " + id + " is already set");
            }
            all_authors[id] = name;
        }
    }
    
    private List<String> convertAuthorIDs(String authorsIDs_string) {
        authorsIDs_string = authorsIDs_string.substring(1, authorsIDs_string.length()-1);
        List<String> authorsIDs_string_list = Arrays.asList(authorsIDs_string.split(";"));
        List<String> authors = new ArrayList<String>();
        for (String authorID_string: authorsIDs_string_list) {
            int authorID = Integer.parseInt(authorID_string.trim());
            authors.add(all_authors[authorID]);
        }
        return authors;
    }

    protected void loadJournals() throws FileNotFoundException, IOException {
        String articles_path = DATA_PATH + "Journals.csv";
        Iterable<CSVRecord> records = loadCSV(articles_path);
        for (CSVRecord record : records) {
            String  name = record.get(0),
                    publisher_string = record.get(1),
                    location = record.get(2),
                    ISSN = record.get(3);

            Publisher journal_publisher = new Publisher(publisher_string, location);
            Journal current_journal = new Journal(name, journal_publisher, ISSN);
            ISSN_to_journal.put(current_journal.getISSN(), current_journal);

        }

    }
    
    protected void loadArticles() throws FileNotFoundException, IOException {
        String articles_path = DATA_PATH + "Articles.csv";
        Iterable<CSVRecord> records = loadCSV(articles_path);

        for (CSVRecord record : records) {
            int id = Integer.parseInt(record.get(0));
            String title = record.get(1);
            List<String> current_authors = convertAuthorIDs(record.get(2));
            String ISSN = record.get(3);
            
            Article current_article = new Article(title, current_authors);
            all_articles[id] = current_article;
            Journal current_journal = ISSN_to_journal.get(ISSN);
            current_journal.addArticle(current_article);
        }
    }
    
    
    public void listContents() {
        //TODO: Print all journals with their respective articles and authors to the console.
        for (Journal journal : ISSN_to_journal.values()) {
            journal.print();
            System.out.println();

        }

    }
    
    public static final void main(String[] args) throws Exception {
        System.out.println();
        LibrarySystem librarySystem = new LibrarySystem();
        
        librarySystem.load();
        librarySystem.listContents();
        System.out.println("\nSUCCESS");
    }
}
