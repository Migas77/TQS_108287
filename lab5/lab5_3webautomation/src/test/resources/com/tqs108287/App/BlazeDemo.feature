@blazedemo_sample
Feature: Blazedemo Flow
  To allow a customer to find his and buy a plane ticket.

  # h1 -> description; h2 -> subdescription; h3 -> sub-subdesription
  Scenario: Reserving a flight
    Given I am on "https://blazedemo.com/"
    When I chose my flight from "Portland" to "Berlin"
      And I click submit
    Then the sub-subdescription text should be "Flights from Portland to Berlin:"
    When I chose flight number 3
      And fill in my information "First Last" "123 Main St." "Anytown" "State" "12345" "American Express" "12345678" "12" "2018" "John Smith"
      And I click submit
    Then the description text should be "Thank you for your purchase today!"
      And the title should be "BlazeDemo Confirmation"

