package ru.nasrulaev.spring.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Book {
    private int id;

    @NotBlank(message = "This field must be filled")
    @Pattern(regexp = "^[A-ZА-Яа-я][a-zа-я]+(?: [A-ZА-Я][a-zа-я]*){1,2}$", message = "Please input a valid full name")
    private String authorName;

    @NotBlank(message = "This field must be filled")
    private String title;

    @NotNull(message = "This field must be filled")
    private int publicationYear;

    private Integer holderId;

    public Book() {
    }

    public Book(int id, String authorName, String title, int publicationYear, Integer holderId) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.publicationYear = publicationYear;
        this.holderId = holderId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getHolderId() {
        return holderId;
    }

    public void setHolderId(Integer holderId) {
        this.holderId = holderId;
    }
}
