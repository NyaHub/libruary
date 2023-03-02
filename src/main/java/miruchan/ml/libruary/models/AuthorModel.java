package miruchan.ml.libruary.models;

import miruchan.ml.libruary.entities.Author;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorModel {
    private Integer id;
    private String name;
    private String surname;
    private String fullname;
    private List<BookModel> books;

    public AuthorModel(Author a) {
        this.id = a.getId();
        this.name = a.getName();
        this.surname = a.getSurname();
        this.fullname = a.getName() + " " + a.getSurname();
        this.books = a.getBooks().stream().map(BookModel::toModel).collect(Collectors.toList());
    }

    public static AuthorModel toModel(Author a){
        return new AuthorModel(a);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<BookModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookModel> books) {
        this.books = books;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
