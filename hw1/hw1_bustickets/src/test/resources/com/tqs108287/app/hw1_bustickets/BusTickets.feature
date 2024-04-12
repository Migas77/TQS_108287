@bustickets
Feature: Bustickets Flow
  To allow a customer to find his and buy a bus ticket.

  Scenario: Reserving a bus ticket
    Given I am on "http://localhost:4200/"
    When I choose my flight from "Lisboa" to "Aveiro"
      And I choose date 2024-06-02
      And I choose currency USD
      And I click search trips
    Then 2 trips should be found
      And found trip 1 should be presented with Route, Bus Capacity and Available Seats "Lisboa -> Coimbra -> Aveiro -> Porto" "50" "2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50"
      And I select trip 1
      And I fill in my reservation information 3 "ClienteCucumber" "MoradaCucumber"
      And make my reservation
    Then should be presented successful reservation message

  # Wanted to do scenarios for not found and reservation details but didn't have the time
