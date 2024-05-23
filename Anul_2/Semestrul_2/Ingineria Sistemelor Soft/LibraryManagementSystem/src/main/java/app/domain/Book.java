package app.domain;

import jakarta.persistence.*;


@jakarta.persistence.Entity
@Table( name = "book" )
public class Book extends Entity{
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "ISBN")
    private String ISBN;
    @Column(name = "genre")
    private String genre;
    @Column(name = "language")
    private String language;
    @Column(name = "availabe")
    private Integer available;

    public Book(String name, String author, String ISBN, String genre, String language, Integer available) {
        this.name = name;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre;
        this.language = language;
        this.available = available;
    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", genre='" + genre + '\'' +
                ", language='" + language + '\'' +
                ", available='" + available + '\'' +
                '}';
    }
}
