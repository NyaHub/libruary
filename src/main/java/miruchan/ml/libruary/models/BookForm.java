package miruchan.ml.libruary.models;

import miruchan.ml.libruary.entities.Book;

public class BookForm {
    private Integer id;
    private String title;
    private Integer authorId;

    public BookForm() {
    }

    public static BookForm toModel(Book b){
        BookForm o = new BookForm();
        o.id = b.getId();
        o.title = b.getTitle();
        if(b.getAuthor() != null) {
            o.authorId = b.getAuthor().getId();
        }
        return o;
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
}
