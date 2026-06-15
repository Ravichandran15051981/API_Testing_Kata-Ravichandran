package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.AuthRequest;
import utils.JacksonUtils;
import apis.RestResource;
import utils.TestContext;

import java.io.IOException;

public class AuthSteps {

    private final TestContext context = TestContext.getInstance();

    @Given("I have valid authentication credentials")
    public void iHaveValidAuthenticationCredentials() throws IOException {
        AuthRequest authRequest = JacksonUtils.deserializeJson("testdata/auth-login.json",AuthRequest.class);
        context.setAuthRequest(authRequest);
    }

    @When("I send a POST request to generate auth token")
    public void iSendAPostRequestToGenerateAuthToken() {
        Response response = RestResource.postAuth("/auth/login", context.getAuthRequest());
        context.setResponse(response);
        String token = extractToken(response);

        if (token != null && !token.trim().isEmpty()) {
            context.setToken(token);
        }
    }

    @Then("the response should contain auth token")
    public void theResponseShouldContainAuthToken() {
        String token = extractToken(context.getResponse());

        Assert.assertNotNull(token, "Auth token is null");
        Assert.assertFalse(token.trim().isEmpty(), "Auth token is empty");

        context.setToken(token);
    }

    @Given("I have a valid auth token")
    public void iHaveAValidAuthToken() throws IOException {
        if (context.getToken() == null || context.getToken().trim().isEmpty()) {
            AuthRequest authRequest = JacksonUtils.deserializeJson("testdata/auth-login.json", AuthRequest.class);

            Response response = RestResource.postAuth("/auth/login", authRequest);
            context.setResponse(response);

            String token = extractToken(response);

            Assert.assertNotNull(token, "Unable to generate auth token");
            Assert.assertFalse(token.trim().isEmpty(), "Generated auth token is empty");

            context.setToken(token);
        }
    }

    private String extractToken(Response response) {
        if (response == null) {
            return null;
        }

        String tokenFromCookie = response.getCookie("token");

        if (tokenFromCookie != null && !tokenFromCookie.trim().isEmpty()) {
            return tokenFromCookie;
        }

        try {
            String tokenFromBody = response.jsonPath().getString("token");

            if (tokenFromBody != null && !tokenFromBody.trim().isEmpty()) {
                return tokenFromBody;
            }
        } catch (Exception ignored) {
        }

        return null;
    }
}
