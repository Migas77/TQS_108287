package com.tqs108287.App;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class Library {
    private final List<Book> store;

    public Library(List<Book> store) {
        this.store = store;
    }

    public void addBook(final Book book) {
        store.add(book);
    }

    public List<Book> findBooks(BookFinder bookFinder){
        Predicate<Book> predicate = bookFinder.build();
        return store.stream().filter(predicate).toList();
    }

    /*
    Intended Usage: findBooks(bookfinder.ByAuthor("author").ByCategory("A"))
    */
    public static class BookFinder{
        private final List<Predicate<Book>> predicates = new ArrayList<>();

        public BookFinder BetweenYears(final int year0, final int year1) {
            // 2013 - 2014 (include 2013-01-01 and exclude 2014-01-01)
            LocalDate from = LocalDate.ofYearDay(year0, 1);
            LocalDate to = LocalDate.ofYearDay(year1, 1);
            return BetweenDates(from, to);
        }

        public BookFinder BetweenDates(final LocalDate from, final LocalDate to) {
            // includes from and excludes to
            predicates.add(book ->
                    book.getPublished().isAfter(from.minusDays(1)) &&
                    book.getPublished().isBefore(to)
            );
            return this;
        }

        public BookFinder ByAuthor(final String authorName){
            predicates.add(book -> book.getAuthor().equals(authorName));
            return this;
        }

        public BookFinder ByCategory(final String category){
            predicates.add(book -> book.getCategory().equals(category));
            return this;
        }

        public Predicate<Book> build() {
            return predicates.stream().reduce(Predicate::and).orElse(book -> true);
        }

    }


}
