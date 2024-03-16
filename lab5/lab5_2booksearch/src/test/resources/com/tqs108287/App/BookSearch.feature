@booksearch_sample
Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Background: A Calculator
    Given the library has the following books
      | title | author | published | category |
      | One good book | Anonymous | 2013-03-14 | B |
      | Some other book | Tim Tomson | 2013-03-13 | B |
      | How to cook a dino | Fred Flintstone | 2012-01-01 | A |
      | How to cook a dino 2 | Fred Flintstone | 2012-12-31 | C |
      | How to cook a dino 3 | Fred Flintstone | 2013-01-01 | C |

  Scenario: Search books by publication year
    When the customer searches for books published between 2012 and 2013
    Then there are found the following book titles
      | How to cook a dino |
      | How to cook a dino 2 |

  Scenario: Search books by author
    When the customer searches for books published by "Fred Flintstone"
    Then there are found the following book titles
      | How to cook a dino |
      | How to cook a dino 2 |
      | How to cook a dino 3 |

  Scenario: Search books by category
    When the customer searches for books of category "B"
    Then there are found the following book titles
      | One good book |
      | Some other book |

  # Because I've implemented a BookFinder with a builder pattern (Intended Usage: findBooks(bookfinder.ByAuthor("author").ByCategory("A")))
  # I can do this
  Scenario: Search books by category and publication year
    When the customer searches for books of category "B"
      And the customer searches for books published between 2013-03-13 and 2013-03-14
    Then there are found the following book titles
      | Some other book |
