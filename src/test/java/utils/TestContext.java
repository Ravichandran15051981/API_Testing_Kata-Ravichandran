package utils;

import io.restassured.response.Response;
import pojo.AuthRequest;
import pojo.BookingRequest;
import pojo.PartialUpdateBookingRequest;

import java.util.Map;

public class TestContext {

    private static final ThreadLocal<TestContext> CONTEXT = ThreadLocal.withInitial(TestContext::new);

    private Response response;
    private String token;
    private Integer bookingId;
    private String invalidBookingId;
    private AuthRequest authRequest;
    private BookingRequest bookingRequest;
    private BookingRequest updatedBookingRequest;
    private PartialUpdateBookingRequest partialUpdateBookingRequest;
    private Map<String, Object> invalidCreateBookingPayload;
    private Map<String, Object> invalidUpdateBookingPayload;

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

    public String getInvalidBookingId() {
        return invalidBookingId;
    }

    public void setInvalidBookingId(String invalidBookingId) {
        this.invalidBookingId = invalidBookingId;
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

    public Map<String, Object> getInvalidCreateBookingPayload() {
        return invalidCreateBookingPayload;
    }

    public void setInvalidCreateBookingPayload(Map<String, Object> invalidCreateBookingPayload) {
        this.invalidCreateBookingPayload = invalidCreateBookingPayload;
    }

    public Map<String, Object> getInvalidUpdateBookingPayload() {
        return invalidUpdateBookingPayload;
    }

    public void setInvalidUpdateBookingPayload(Map<String, Object> invalidUpdateBookingPayload) {
        this.invalidUpdateBookingPayload = invalidUpdateBookingPayload;
    }
}