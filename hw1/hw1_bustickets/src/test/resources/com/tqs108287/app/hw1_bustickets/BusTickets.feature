@blazedemo_sample
Feature: Blazedemo Flow
  To allow a customer to find his and buy a bus ticket.

  Scenario: Reserving a bus ticket
    Given I am on "http://localhost:4200/"
    When I choose my flight from "Lisboa" to "Aveiro"
      And I choose date "2024-06-02"
      And I choose currency "USD"
      And I click search trips
    Then "2" trips should be found
      And found trips should be presented with Route, Available Seats, Bus Capacity and Price
      And found trip "1" should contain the route "Lisboa -> Coimbra -> Aveiro"
      And found trip "2" should contain the route "Lisboa -> Aveiro"
      And I select trip "1"
      And selected trip should have form to fill in for seatNumber clientName clientAddress
      And I fill in my reservation information "3" "ClienteCucumber" "MoradaCucumber"
      And make my reservation
    Then should be presented sucessfull reservation message
      And reservation message should contain Seat Number, Client Name, Client Address and Route

  Scenario: Checking reservation details
    Given I am on "http://localhost:4200/reservations/aa0f7252-309c-11ea-a72a-0242ac130002"
    When I check if the details id, route, seat number, client name and client address are present
    Then details should match "aa0f7252-309c-11ea-a72a-0242ac130002" "Lisboa -> Coimbra -> Aveiro -> Porto" "1" "Miguel" "Rua XPTO"

  # not found scenarios