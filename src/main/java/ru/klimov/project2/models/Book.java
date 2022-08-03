package ru.klimov.project2.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 1, max = 200, message = "Название книги должно быть в пределах 200 символов")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть в пределах 100 символов")
    @Column(name = "author")
    private String author;

    @Min(value = 1, message = "Книги не раньше 1 года")
    @Max(value = 2022, message = "Никаких книг из будущего")
    @Column(name = "year_of_publication")
    private int yearOfPublication;

    @ManyToOne
    @JoinColumn(name = "taken_by", referencedColumnName = "id")
    private Client currentOwner;

    @Column(name = "last_taken_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date takenAt;

    @Transient
    private boolean isOverdue;

    public Book() {

    }

    public Book(String title, String author, int yearOfPublication) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Client getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(Client currentOwner) {
        this.currentOwner = currentOwner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date lastTakenAt) {
        this.takenAt = lastTakenAt;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfPublication == book.yearOfPublication && title.equals(book.title) && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, yearOfPublication);
    }
}
