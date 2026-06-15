package apis;

import io.restassured.response.Response;

import static apis.SpecBuilder.getRequestSpec;
import static apis.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response postAuth(String path, Object authRequest) {
        return given(getRequestSpec())
                .body(authRequest)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

}
