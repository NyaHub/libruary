package miruchan.ml.libruary.models;

import miruchan.ml.libruary.entities.Book;

import java.util.Optional;

public class BookModel {
    private Integer id;
    private String title;
    private Integer authorId;
    private String authorFullName;

    public BookModel(Book b) {
        this.id = b.getId();
        this.title = b.getTitle();
        this.authorId = b.getAuthor().getId();
        this.authorFullName = b.getAuthor().getName() + " " + b.getAuthor().getSurname();
    }

    public static BookModel toModel(Book b){
        return new BookModel(b);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }
}
