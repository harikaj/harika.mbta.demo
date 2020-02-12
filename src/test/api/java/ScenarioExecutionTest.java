package src.test.api.java;

import main.NumberOfStopsEvaluator;
import main.RouteLongNameEvaluator;
import main.RoutesEvaluator;
import org.testng.annotations.Test;

public class ScenarioExecutionTest {

    @Test
    public void validateScenarios() throws Exception {
        RouteLongNameEvaluator routeLongNameEvaluator = new RouteLongNameEvaluator();
        routeLongNameEvaluator.getLongNameForRoute();

        NumberOfStopsEvaluator numberOfStopsEvaluator = new NumberOfStopsEvaluator();
        numberOfStopsEvaluator.printHighestAndLowestNoOfStops();

        RoutesEvaluator routesEvaluator = new RoutesEvaluator();
        routesEvaluator.printRoutesForProvidedStops("Davis", "Kendall/MIT");
        //routesEvaluator.printRoutesForProvidedStops("Ashmont", "Arlington");
    }
}
