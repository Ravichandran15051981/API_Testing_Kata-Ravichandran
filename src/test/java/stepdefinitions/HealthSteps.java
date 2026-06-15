package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import apis.RestResource;
import utils.TestContext;

public class HealthSteps {

    private final TestContext context = TestContext.getInstance();

    @Given("the booking API is available")
    public void theBookingApiIsAvailable() {
        // Base URI is already initialized in Hooks.java
    }

    @When("I send a GET request to the health endpoint")
    public void iSendAGetRequestToTheHealthEndpoint() {
        Response response = RestResource.get("/booking/actuator/health");
        context.setResponse(response);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assert.assertNotNull(context.getResponse(), "Response is null");

        Assert.assertEquals(
                context.getResponse().getStatusCode(),
                expectedStatusCode,
                "Unexpected response status code"
        );
    }

    @Then("the response status code should be {int} or {int}")
    public void theResponseStatusCodeShouldBeOr(int firstExpectedStatusCode, int secondExpectedStatusCode) {
        Assert.assertNotNull(context.getResponse(), "Response is null");

        int actualStatusCode = context.getResponse().getStatusCode();

        Assert.assertTrue(
                actualStatusCode == firstExpectedStatusCode || actualStatusCode == secondExpectedStatusCode,
                "Unexpected response status code. Actual: " + actualStatusCode
        );
    }

    @Then("the response body should not be empty")
    public void theResponseBodyShouldNotBeEmpty() {
        Assert.assertNotNull(context.getResponse(), "Response is null");

        String responseBody = context.getResponse().asString();

        Assert.assertNotNull(responseBody, "Response body is null");

        Assert.assertFalse(
                responseBody.trim().isEmpty(),
                "Response body is empty"
        );
    }

    @Then("the health response should contain status {string}")
    public void theHealthResponseShouldContainStatus(String expectedStatus) {
        Assert.assertNotNull(context.getResponse(), "Response is null");

        String actualStatus = context.getResponse().jsonPath().getString("status");

        Assert.assertEquals(
                actualStatus,
                expectedStatus,
                "Health status mismatch"
        );
    }
}
