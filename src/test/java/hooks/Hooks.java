package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.TestContext;

public class Hooks {


    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("========== Starting Scenario ==========");
        System.out.println("Scenario Name: " + scenario.getName());
        TestContext.reset();
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("========== Completed Scenario ==========");
        System.out.println("Scenario Name: " + scenario.getName());
        System.out.println("Scenario Status: " + scenario.getStatus());
        TestContext.reset();
    }

}




