package ru.klimov.project2.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Clients")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Client's name can not be empty!")
    @Size(max = 100, message = "Client's name can not be longer than 100 characters!")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Name should be like \"Name Surname\"")
    @Column(name = "name")
    private String name;

    @Max(value = 2022, message = "Year of birth can not be greater than current year!")
    @Min(value = 1900, message = "Minimal year of birth allowed is 1900!")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "currentOwner")
    private List<Book> books;

    public Client() {

    }

    public Client(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && yearOfBirth == client.yearOfBirth && name.equals(client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, yearOfBirth);
    }
}
