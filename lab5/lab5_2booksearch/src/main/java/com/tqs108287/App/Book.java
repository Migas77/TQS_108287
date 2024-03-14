package com.tqs108287.App;

import java.time.LocalDate;
import java.util.Date;

public class Book {
    private final String title;
    private final String author;
    private final LocalDate published;
    private final String category;

    public Book(String title, String author, LocalDate published, String category) {
        this.title = title;
        this.author = author;
        this.published = published;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublished() {
        return published;
    }

    public String getCategory() {
        return category;
    }
}
