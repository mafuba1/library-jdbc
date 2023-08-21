package ru.nasrulaev.spring.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;

    @NotBlank(message = "This field must be filled")
    @Pattern(regexp = "^[A-ZА-Яа-я][a-zа-я]+(?: [A-ZА-Я][a-zа-я]*){1,2}$", message = "Please input a valid full name")
    private String fullName;

    @NotNull(message = "This field must be filled")
    @Min(value = 1900, message = "Year of birth must be at least 1900")
    @Max(value = 2009, message = "Year of birth must be less than 2010")
    private int birthYear;


    public Person() {
    }

    public Person(int id, String fullName, int birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
