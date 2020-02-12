Feature: Routes

Scenario: Verify the routes are printed when the source location and destination location are provided
    Given the routes service is called
    When route type is either 'Light Rail' and 'Heavy Rail'
    Then All the ruotes matching the type provided are returned
    When the stops service is called for each route
    And if we get the stops for the route successfully
    And if the stops contain either the source or destination route
    Then Print the Route name along with source and destination in the format 'Ashmont to Arlington -> "Red Line","Green Line D","Green Line E","Green Line B","Green Line C","Mattapan Trolley"'
