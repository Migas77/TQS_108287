package com.tqs108287.App;

import java.time.LocalDate;
import java.util.List;

public class Library {
    private final List<Book> store;

    public Library(List<Book> store) {
        this.store = store;
    }

    public void addBook(final Book book) {
        store.add(book);
    }

    public List<Book> findBooksBetweenDates(final LocalDate from, final LocalDate to) {
        return store.stream().filter(book -> book.getPublished().isAfter(from) && book.getPublished().isBefore(to)).toList();
    }

    public List<Book> findBooksByAuthor(final String authorName){
        return store.stream().filter(book -> book.getAuthor().equals(authorName)).toList();
    }

    public List<Book> findBooksByCategory(final String category){
        return store.stream().filter(book -> book.getCategory().equals(category)).toList();
    }


}
