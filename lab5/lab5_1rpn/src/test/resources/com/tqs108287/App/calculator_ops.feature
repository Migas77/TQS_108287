@calc_sample
Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I add 10 and 13
    Then the result is 23

  Scenario: Substraction
    When I substract 5 to 6
    Then the result is -1

  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>

    Examples: Single digits
      | a | b | c  |
      | 1 | 2 | 3  |
      | 3 | 7 | 10 |

  Scenario Outline: Several Multiplications
    When I multiply <a> and <b>
    Then the result is <c>

    Examples: Single digits
      | a | b | c  |
      | -2 | -4 | 8 |
      | -5 | 6 | -30  |
      | 5 | -6 | -30  |
      | 0.5 | 2.1 | 1.05 |
      | 0 | 1 | 0 |

  Scenario Outline: Several Divisions
    When I divide <a> by <b>
    Then the result is <c>

    Examples: Single digits
      | a | b | c  |
      | -20 | -4 | 5  |
      | -10 | 5 | -2 |
      | 10 | -5 | -2 |
      | 2 | 4 | 0.5 |
      | 0 | 1 | 0.0 |

  Scenario: Division by 0
    When I divide 5 by 0
    Then the result is Infinity

