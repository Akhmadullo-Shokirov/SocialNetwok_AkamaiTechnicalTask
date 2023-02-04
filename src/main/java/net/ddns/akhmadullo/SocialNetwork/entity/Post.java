package net.ddns.akhmadullo.SocialNetwork.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Component
public class Post {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;

    private String author;
    private String content;
    private int viewCount;

    private LocalDate date;

    public Post() {

    }

    public Post(String author, String content, int viewCount) {
        this.author = author;
        this.content = content;
        this.viewCount += viewCount;
        this.date = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void incrementViewCount(int incrementBy) {
        this.viewCount += incrementBy;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}