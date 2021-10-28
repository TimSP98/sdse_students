package org.nypl.journalsystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.nypl.journalsystem.core.IArticle;
import org.nypl.journalsystem.core.IAuthor;
import org.nypl.journalsystem.core.ILibrarySystem;



public class LibrarySystem implements ILibrarySystem{
    // Class Constants
    private final static String ROOT_DIR = "/home/tim/cloud/ITU/5. Semester/SoftwareDev/sdse_students/Project 1/Phase 2/Code/Phase 2/";
    private final static String DATA_PATH = ROOT_DIR + "data/";
    private final static Integer MAX_AUTHORS  = 500;
    private final static Integer MAX_ARTICLES = 500;

    private Author[] all_authors = new Author[MAX_AUTHORS];
    private HashMap<Integer,Article> all_articles = new HashMap<Integer,Article>();
    private HashMap<String,Journal> ISSN_to_journal = new HashMap<String,Journal>();

    public LibrarySystem() throws FileNotFoundException, IOException, Exception {
        loadJournals();
    }
    
    public void load() throws FileNotFoundException, IOException, Exception {
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
    

    private List<Integer> convertListString(String list_string) {
        // Takes String in format [1;2;3;4;5;...;999]
        // Returns List of integers in Java ArrayList
        list_string = list_string.substring(1, list_string.length()-1);
        List<String> id_string_list = Arrays.asList(list_string.split(";"));
        List<Integer> ids = new ArrayList<Integer>();
        for (String s : id_string_list) {
            ids.add(Integer.parseInt(s.trim()));
        }
        return ids;
    }

    private List<Article> convertCitingsID(String articlesIDs_string) {
        List<Integer> articles_ids = convertListString(articlesIDs_string);
        List<Article> articles = new ArrayList<Article>();
        for (Integer id : articles_ids) {
            articles.add(all_articles.get(id));
        }
        return articles;
    }
    
    private List<Author> convertAuthorIDs(String authorsIDs_string) {
        List<Integer> author_ids = convertListString(authorsIDs_string);
        List<Author> authors = new ArrayList<Author>();
        for (Integer id : author_ids) {
            authors.add(all_authors[id]);
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
            ISSN_to_journal.put(current_journal.getIssn(), current_journal);

        }

    }
    
    protected void loadArticles() throws FileNotFoundException, IOException, Exception {
        String articles_path = DATA_PATH + "Articles.csv";
        Iterable<CSVRecord> records = loadCSV(articles_path);

        for (CSVRecord record : records) {
            int id = Integer.parseInt(record.get(0));
            String title = record.get(1);
            List<Author> current_authors = convertAuthorIDs(record.get(2));
            String ISSN = record.get(3);
            String citingsString = record.get(4);
            
            Article current_article = new Article(title, id, current_authors);
            current_article.setCitingsString(citingsString);

            Journal current_journal = ISSN_to_journal.get(ISSN);
            current_journal.addArticle(current_article);
            all_articles.put(current_article.getID(), current_article);
        }
        for (Article art : all_articles.values()) {
            List<Article> citings = convertCitingsID(art.getCitingsString());
            for (Article cite : citings) {
                art.addCiting(cite);
            }

        }
    }

    protected void loadAuthors() throws FileNotFoundException, IOException, Exception {
        String authors_path = DATA_PATH + "Authors.csv";
        Iterable<CSVRecord> records = loadCSV(authors_path);
        for (CSVRecord record : records) {
            int id = Integer.parseInt(record.get(0));
            String name = record.get(1);
            if (all_authors[id] != null) {
                throw new Exception("Author with id: " + id + " is already set");
            }
            Author current_author = new Author(name, id);
            all_authors[id] = current_author;
        }
    }
    
    
    public void listContents() {
        for (Journal journal : ISSN_to_journal.values()) {
            journal.print();
            System.out.println();
        }

    }

    @Override
    public List<Author> getAllAuthors() {
        return Arrays.asList(all_authors);
    }

    @Override
    public Collection<Journal> getAllJournals() {
        return ISSN_to_journal.values();
    }

    @Override
    public Collection<Article> getArticlesByAuthor(IAuthor arg0) {
        Collection<Article> articles_with_author = new ArrayList<Article>();
        for (Article art : all_articles.values()) {
            if (art.getAuthors().contains(arg0)) {
                articles_with_author.add(art);
            }
        }
        return articles_with_author;
    }

    @Override
    public Collection<Article> getArticlesCitedByArticle(IArticle arg0) {
        for (Article art : all_articles.values()) {
            if (art == arg0) {
                return art.getCitings();
            }
        }
        return null;
    }

    @Override
    public Collection<Article> getArticlesCitingArticle(IArticle arg0) {
        List<Article> cites = new ArrayList<Article>();
        for (Article art : all_articles.values()) {
            if (art.getCitings().contains(arg0)) {
                cites.add(art);
            }
        }
        return cites;
    }
    
    public static final void main(String[] args) throws Exception {
        System.out.println();
        LibrarySystem librarySystem = new LibrarySystem();
        
        librarySystem.load();
        librarySystem.listContents();
        System.out.println("\nSUCCESS");
    }
}
