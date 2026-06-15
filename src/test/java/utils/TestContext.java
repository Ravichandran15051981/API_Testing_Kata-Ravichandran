package utils;

import io.restassured.response.Response;
import pojo.AuthRequest;
import pojo.BookingRequest;
import pojo.PartialUpdateBookingRequest;

public class TestContext {

    private static final ThreadLocal<TestContext> CONTEXT = ThreadLocal.withInitial(TestContext::new);

    private Response response;
    private String token;
    private Integer bookingId;
    private AuthRequest authRequest;
    private BookingRequest bookingRequest;
    private BookingRequest updatedBookingRequest;
    private PartialUpdateBookingRequest partialUpdateBookingRequest;

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

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    public void setAuthRequest(AuthRequest authRequest) {
        this.authRequest = authRequest;
    }

    public BookingRequest getBookingRequest() {
        return bookingRequest;
    }

    public void setBookingRequest(BookingRequest bookingRequest) {
        this.bookingRequest = bookingRequest;
    }

    public BookingRequest getUpdatedBookingRequest() {
        return updatedBookingRequest;
    }

    public void setUpdatedBookingRequest(BookingRequest updatedBookingRequest) {
        this.updatedBookingRequest = updatedBookingRequest;
    }

    public PartialUpdateBookingRequest getPartialUpdateBookingRequest() {
        return partialUpdateBookingRequest;
    }

    public void setPartialUpdateBookingRequest(PartialUpdateBookingRequest partialUpdateBookingRequest) {
        this.partialUpdateBookingRequest = partialUpdateBookingRequest;
    }
}