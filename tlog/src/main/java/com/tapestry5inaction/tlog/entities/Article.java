package com.tapestry5inaction.tlog.entities;

import org.apache.tapestry5.beaneditor.NonVisual;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Article {
    @Id
    @GeneratedValue
    @NonVisual
    private Long id;

    @Column
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    @Column
    private String content;

    @ManyToOne
    private Blog blog;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    public Article() {
        super();
        this.publishDate = new Date();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(final Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Blog getBlog() {
        return this.blog;
    }

    public void setBlog(final Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
