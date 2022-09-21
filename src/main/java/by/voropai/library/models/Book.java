package by.voropai.library.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Field 'Book name' shouldn't be empty")
    private String title;
    @NotEmpty(message = "Field 'Author' shouldn't be empty")
    @Size(min = 2, max = 50, message = "Enter author's full name!")
    private String author;
    private int releaseYear;
    private Integer personId;

    public Book(int id, String title, String author, int releaseYear, Integer personId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.personId = personId;
    }

    public Book() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReleaseYear() {
        return this.releaseYear;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPersonId() {
        return this.personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
