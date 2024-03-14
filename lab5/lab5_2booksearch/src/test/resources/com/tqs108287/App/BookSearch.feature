@booksearch_sample
Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Background: A Calculator
    Given the library has the following books
      | title | author | published | category |
      | One good book | Anonymous | 2013-03-14 | A |
      | Some other book | Tim Tomson | 2014-08-23 | B |
      | How to cook a dino | Fred Flintstone | 2012-01-01 | A |
      | How to cook a dino 2 | Fred Flintstone | 2012-01-01 | C |

  Scenario: Search books by publication year
    When the customer searches for books published between 2013 and 2014
    Then there are found the following book titles
      | One good book |
      | Some other book |

  Scenario: Search books by author
    When the customer searches for books published by "Fred Flintstone"
    Then there are found the following book titles
      | How to cook a dino |
      | How to cook a dino 2 |

  Scenario: Search books by author
    When the customer searches for books of category "A"
    Then there are found the following book titles
      | One good book |
      | How to cook a dino |

