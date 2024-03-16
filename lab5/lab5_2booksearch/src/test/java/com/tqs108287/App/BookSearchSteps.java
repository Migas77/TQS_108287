package com.tqs108287.App;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

public class BookSearchSteps {

    static final Logger log = getLogger(lookup().lookupClass());
    private Library library;
    private Library.BookFinder bookFinder;

    /*
	create a registered type named iso8601Date to map a string pattern from the feature
	into a custom datatype. Extracted parameters should be strings.
	 */
    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day){
        return Utils.localDateFromDateParts(year, month, day);
    }


    /**
     * load a data table from the feature (tabular format) and call this method
     * for each row in the table. Injected parameter is a map with column name --> value
     */
    @DataTableType
    public Book bookEntry(Map<String, String> tableEntry){
        return new Book(
                tableEntry.get("title"),
                tableEntry.get("author"),
                Utils.isoTextToLocalDate( tableEntry.get("published") ),
                tableEntry.get("category"));
    }

    @Given("the library has the following books")
    public void theLibraryHasTheFollowingBooks(List<Book> books) {
        library = new Library(books);
        bookFinder = new Library.BookFinder();
    }

    @Then("there are found the following book titles")
    public void thereAreFoundTheFollowingBookTitles(List<String> bookTitles) {
        List<String> resultBookTitles = library.findBooks(bookFinder).stream().map(Book::getTitle).toList();
        log.debug("Result: {} (expected {})", resultBookTitles, bookTitles);
        assertEquals(resultBookTitles, bookTitles);
    }


    @When("the customer searches for books published between {int} and {int}")
    public void theCustomerSearchesForBooksPublishedBetweenYears(int arg0, int arg1) {
        log.debug("Searching books between years {} (inclusive) and {} (exclusive)", arg0, arg1);
        bookFinder.BetweenYears(arg0, arg1);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void theCustomerSearchesForBooksPublishedBetweenDates(LocalDate date0, LocalDate date1) {
        log.debug("Searching books between dates {} (inclusive) and {} (exclusive)", date0, date1);
        bookFinder.BetweenDates(date0, date1);
    }

    @When("the customer searches for books published by {string}")
    public void theCustomerSearchesForBooksPublishedByAuthor(String authorName) {
        log.debug("Searching books of author: {}", authorName);
        bookFinder.ByAuthor(authorName);
    }

    @When("the customer searches for books of category {string}")
    public void theCustomerSearchesForBooksOfCategory(String category) {
        log.debug("Searching books of category: {}", category);
        bookFinder.ByCategory(category);
    }
}

