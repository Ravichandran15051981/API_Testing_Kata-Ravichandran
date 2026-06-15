package apis;

import io.restassured.response.Response;

import static apis.SpecBuilder.getRequestSpec;
import static apis.SpecBuilder.getResponseSpec;
import static apis.TokenManager.getToken;
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

    public static Response get(String path) {
        return given(getRequestSpec()).
                header("Cookie", "token=" + getToken()).
                when().
                get(path).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response post(String path, Object bookingRequest) {
        return given(getRequestSpec())
                .header("Cookie", "token=" + getToken())
                .body(bookingRequest)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response put(String path, Object updatedBookingRequest) {
        return given(getRequestSpec())
                .header("Cookie", "token=" + getToken())
                .body(updatedBookingRequest)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response patch(String path, Object updatedPartialBookingRequest) {
        return given(getRequestSpec())
                .header("Cookie", "token=" + getToken())
                .body(updatedPartialBookingRequest)
                .when()
                .patch(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response delete(String path) {
        return given(getRequestSpec())
                .header("Cookie", "token=" + getToken())
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }

    public static Response getForNegativeValidation(String path) {
        return given(getRequestSpec())
                .header("Cookie", "token=" + getToken())
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

}
