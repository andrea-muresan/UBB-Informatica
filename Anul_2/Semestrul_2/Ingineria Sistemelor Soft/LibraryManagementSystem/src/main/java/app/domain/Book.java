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
    @Transient
    private Integer noCopies;


    public Book(String name, String author, String ISBN, String genre, String language) {
        this.name = name;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre;
        this.language = language;
        this.noCopies = 1;
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

    public Integer getNoCopies() {
        return noCopies;
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

    public void setNoCopies(Integer noCopies) {
        this.noCopies = noCopies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", genre='" + genre + '\'' +
                ", language='" + language + '\'' +
                ", noCopies=" + noCopies +
                '}';
    }
}
