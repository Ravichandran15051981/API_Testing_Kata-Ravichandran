package utils;

import io.restassured.response.Response;
import pojo.AuthRequest;

public class TestContext {

    private static final ThreadLocal<TestContext> CONTEXT = ThreadLocal.withInitial(TestContext::new);

    private Response response;
    private String token;
    private AuthRequest authRequest;

    private TestContext() {
    }

    public static TestContext getInstance() {
        return CONTEXT.get();
    }

    public static void reset() {
        CONTEXT.remove();
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    public void setAuthRequest(AuthRequest authRequest) {
        this.authRequest = authRequest;
    }
}

