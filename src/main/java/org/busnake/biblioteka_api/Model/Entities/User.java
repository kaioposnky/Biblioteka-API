package org.busnake.biblioteka_api.Model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "users")
public class User implements Identifiable{

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    private Date dateCreated;

    public User(){}

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String name, String email, String password, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getCollectionRel() {
        return "users";
    }
}
