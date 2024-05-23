package app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@jakarta.persistence.Entity
@Table( name = "book" )
public class BookSet extends Entity {
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
    List<Integer> availableBooks;
    @Transient
    List<Integer> unavailableBooks;

    public BookSet(String name, String author, String ISBN, String genre, String language) {
        this.name = name;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre;
        this.language = language;
        availableBooks = new ArrayList<>();
        unavailableBooks = new ArrayList<>();
    }

    public BookSet() {
        availableBooks = new ArrayList<>();
        unavailableBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNoCopies() {
        return availableBooks.size() + unavailableBooks.size();
    }

    public int getNoCopiesAvailable() {
        return availableBooks.size();
    }

    public void MoveBookToAvailable(Integer id) {
        availableBooks.add(id);
        unavailableBooks.remove(Integer.valueOf(id));
    }

    public void MoveBookToUnavailable(Integer id) {
        unavailableBooks.add(id);
        availableBooks.remove(Integer.valueOf(id));
    }

    @Override
    public String toString() {
        return "BookSet{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", genre='" + genre + '\'' +
                ", language='" + language + '\'' +
                ", available='" + this.getNoCopiesAvailable() + '\'' +
                ", total='" + this.getNoCopies() + '\'' +
                '}';
    }
}
