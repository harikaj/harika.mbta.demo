Feature: Routes

Scenario: Verify the route long name are printed when the route service is called
    Given the routes service is called
    When route type is either 'Light Rail' and 'Heavy Rail'
    And the routes service executed successfully with HTtp code 200 returned
    Then Print the long name of all the routes that are turned by the service