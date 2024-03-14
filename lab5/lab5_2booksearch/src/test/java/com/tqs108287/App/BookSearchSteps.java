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
    private List<Book> result;

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
    }

    @Then("there are found the following book titles")
    public void thereAreFoundTheFollowingBookTitles(List<String> bookTitles) {
        List<String> resultBookTitles = result.stream().map(Book::getTitle).toList();
        log.debug("Result: {} (expected {})", resultBookTitles, bookTitles);
        assertEquals(resultBookTitles, bookTitles);
    }


    @When("the customer searches for books published between {int} and {int}")
    public void theCustomerSearchesForBooksPublishedBetweenAnd(int arg0, int arg1) {
        LocalDate date0 = Utils.getFirstDateOfYear(arg0);
        LocalDate date1 = Utils.getLastDateOfYear(arg1);
        log.debug("Searching books between {} and {}", date0, date1);
        result = library.findBooksBetweenDates(date0, date1);
    }

    @When("the customer searches for books published by {string}")
    public void theCustomerSearchesForBooksPublishedByAuthor(String authorName) {
        log.debug("Searching books of author: {}", authorName);
        result = library.findBooksByAuthor(authorName);
        log.debug("result: {}", result);
    }

    @When("the customer searches for books of category {string}")
    public void theCustomerSearchesForBooksOfCategory(String category) {
        log.debug("Searching books of category: {}", category);
        result = library.findBooksByCategory(category);
        log.debug("result: {}", result);
    }
}

