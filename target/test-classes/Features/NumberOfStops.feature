Feature: NumberOfStops

Scenario: Verify the routes with max number of stops and min number of stops are executed
    Given the routes service is called
    When route type is either 'Light Rail' and 'Heavy Rail'
    Then the routes service executed successfully
    When the stops service is called for each route
    And if we get the stops for the route successfully
    Then Get the routes with number of stops for each route
    Then Print the Route name with max number of stops and another route name with min number of stops