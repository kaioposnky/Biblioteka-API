package org.busnake.biblioteka_api.Model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book implements Identifiable{

    @Id
    private Long id;
    private String title;
    private Long authorId;
    private Long genreId;
    private Date dateCreated;

    public Book(Long id, String title, Long authorId, Long genreId, Date dateCreated) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.dateCreated = dateCreated;
    }

    public Book(Long id, String title, Long authorId, Long genreId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.dateCreated = null;
    }

    public Book() {

    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getCollectionRel() {
        return "books";
    }

}
