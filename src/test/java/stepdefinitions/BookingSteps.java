package stepdefinitions;

import apis.RestResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.BookingRequest;
import pojo.BookingResponse;
import pojo.PartialUpdateBookingRequest;
import utils.JacksonUtils;
import utils.TestContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class BookingSteps {

    private final TestContext context = TestContext.getInstance();

    @Given("I have a valid booking payload")
    public void iHaveAValidBookingPayload() throws IOException {
        BookingRequest bookingRequest = JacksonUtils.deserializeJson(
                "testdata/create-booking.json",
                BookingRequest.class
        );

        makeBookingDatesUnique(bookingRequest);

        context.setBookingRequest(bookingRequest);
    }

    @Given("I have a valid update booking payload")
    public void iHaveAValidUpdateBookingPayload() throws IOException {
        BookingRequest updatedBookingRequest = JacksonUtils.deserializeJson(
                "testdata/update-booking.json",
                BookingRequest.class
        );

        makeBookingDatesUnique(updatedBookingRequest);

        context.setUpdatedBookingRequest(updatedBookingRequest);
    }

    @Given("I have a valid partial update booking payload")
    public void iHaveAValidPartialUpdateBookingPayload() throws IOException {
        PartialUpdateBookingRequest partialUpdateBookingRequest = JacksonUtils.deserializeJson(
                "testdata/partial-update-booking.json",
                PartialUpdateBookingRequest.class
        );

        context.setPartialUpdateBookingRequest(partialUpdateBookingRequest);
    }

    @Given("I have created a booking")
    public void iHaveCreatedABooking() throws IOException {
        BookingRequest bookingRequest = JacksonUtils.deserializeJson(
                "testdata/create-booking.json",
                BookingRequest.class
        );

        makeBookingDatesUnique(bookingRequest);

        Response response = RestResource.post("/booking", bookingRequest);
        context.setResponse(response);

        int statusCode = response.getStatusCode();

        Assert.assertTrue(
                statusCode == 200 || statusCode == 201,
                "Booking creation failed. Status code: " + statusCode + ", Response: " + response.asString()
        );

        Integer bookingId = extractBookingId(response);

        Assert.assertNotNull(bookingId, "Booking id is null after creating booking");
        Assert.assertTrue(bookingId > 0, "Booking id should be greater than zero");

        context.setBookingId(bookingId);
        context.setBookingRequest(bookingRequest);
    }

    @When("I send a POST request to create booking")
    public void iSendAPostRequestToCreateBooking() {
        Assert.assertNotNull(context.getBookingRequest(), "Booking request payload is null");

        Response response = RestResource.post("/booking", context.getBookingRequest());
        context.setResponse(response);

        Integer bookingId = extractBookingId(response);

        if (bookingId != null) {
            context.setBookingId(bookingId);
        }
    }

    @When("I send a GET request using the booking id")
    public void iSendAGetRequestUsingTheBookingId() {
        Integer bookingId = getBookingId();

        Response response = RestResource.get("/booking/" + bookingId);
        context.setResponse(response);
    }

    @When("I send a PUT request to update the booking")
    public void iSendAPutRequestToUpdateTheBooking() throws IOException {
        Integer bookingId = getBookingId();

        if (context.getUpdatedBookingRequest() == null) {
            BookingRequest updatedBookingRequest = JacksonUtils.deserializeJson(
                    "testdata/update-booking.json",
                    BookingRequest.class
            );

            makeBookingDatesUnique(updatedBookingRequest);

            context.setUpdatedBookingRequest(updatedBookingRequest);
        }

        Response response = RestResource.put(
                "/booking/" + bookingId,
                context.getUpdatedBookingRequest()
        );

        context.setResponse(response);
    }

    @When("I send a PATCH request to partially update the booking")
    public void iSendAPatchRequestToPartiallyUpdateTheBooking() throws IOException {
        Integer bookingId = getBookingId();

        if (context.getPartialUpdateBookingRequest() == null) {
            PartialUpdateBookingRequest partialUpdateBookingRequest = JacksonUtils.deserializeJson(
                    "testdata/partial-update-booking.json",
                    PartialUpdateBookingRequest.class
            );

            context.setPartialUpdateBookingRequest(partialUpdateBookingRequest);
        }

        Response response = RestResource.patch(
                "/booking/" + bookingId,
                context.getPartialUpdateBookingRequest()
        );

        context.setResponse(response);
    }

    @When("I send a DELETE request using the booking id")
    public void iSendADeleteRequestUsingTheBookingId() {
        Integer bookingId = getBookingId();

        Response response = RestResource.delete("/booking/" + bookingId);
        context.setResponse(response);
    }

    @Then("the response should contain booking id")
    public void theResponseShouldContainBookingId() {
        Integer bookingId = extractBookingId(context.getResponse());

        Assert.assertNotNull(bookingId, "Booking id is null");
        Assert.assertTrue(bookingId > 0, "Booking id should be greater than zero");

        context.setBookingId(bookingId);
    }

    @Then("the booking details should be correct")
    public void theBookingDetailsShouldBeCorrect() {
        BookingRequest expectedBooking = context.getBookingRequest();
        BookingRequest actualBooking = getBookingDetailsFromResponse();

        assertBookingDetails(expectedBooking, actualBooking);
    }

    @Then("the updated booking details should be correct")
    public void theUpdatedBookingDetailsShouldBeCorrect() {
        BookingRequest expectedBooking = context.getUpdatedBookingRequest();
        BookingRequest actualBooking = getBookingDetailsFromResponse();

        assertBookingDetails(expectedBooking, actualBooking);
    }

    @Then("the partially updated booking details should be correct")
    public void thePartiallyUpdatedBookingDetailsShouldBeCorrect() {
        PartialUpdateBookingRequest expectedBooking = context.getPartialUpdateBookingRequest();
        BookingRequest actualBooking = getBookingDetailsFromResponse();

        Assert.assertNotNull(expectedBooking, "Expected partial update booking payload is null");
        Assert.assertNotNull(actualBooking, "Actual booking response is null");

        Assert.assertEquals(actualBooking.getFirstname(), expectedBooking.getFirstname(), "Firstname mismatch");
        Assert.assertEquals(actualBooking.getLastname(), expectedBooking.getLastname(), "Lastname mismatch");
    }

    @Then("the booking should not be available")
    public void theBookingShouldNotBeAvailable() {
        Integer bookingId = getBookingId();

        Response getResponse = RestResource.getForNegativeValidation("/booking/" + bookingId);
        int statusCode = getResponse.getStatusCode();

        Assert.assertTrue(
                statusCode == 404 || statusCode == 405,
                "Booking is still available. Status code: " + statusCode + ", Response: " + getResponse.asString()
        );
    }

    private void assertBookingDetails(BookingRequest expectedBooking, BookingRequest actualBooking) {
        Assert.assertNotNull(expectedBooking, "Expected booking payload is null");
        Assert.assertNotNull(actualBooking, "Actual booking response is null");

        Assert.assertEquals(actualBooking.getFirstname(), expectedBooking.getFirstname(), "Firstname mismatch");
        Assert.assertEquals(actualBooking.getLastname(), expectedBooking.getLastname(), "Lastname mismatch");
        Assert.assertEquals(actualBooking.getRoomid(), expectedBooking.getRoomid(), "Room id mismatch");
        Assert.assertEquals(actualBooking.isDepositpaid(), expectedBooking.isDepositpaid(), "Deposit paid mismatch");

        Assert.assertNotNull(actualBooking.getBookingdates(), "Actual booking dates is null");
        Assert.assertNotNull(expectedBooking.getBookingdates(), "Expected booking dates is null");

        Assert.assertEquals(
                actualBooking.getBookingdates().getCheckin(),
                expectedBooking.getBookingdates().getCheckin(),
                "Checkin date mismatch"
        );

        Assert.assertEquals(
                actualBooking.getBookingdates().getCheckout(),
                expectedBooking.getBookingdates().getCheckout(),
                "Checkout date mismatch"
        );
    }

    private BookingRequest getBookingDetailsFromResponse() {
        Response response = context.getResponse();

        Assert.assertNotNull(response, "Response is null");

        BookingResponse bookingResponse = response.as(BookingResponse.class);

        Assert.assertNotNull(bookingResponse, "Booking response is null");

        return bookingResponse.getBookingDetails();
    }

    private Integer getBookingId() {
        Integer bookingId = context.getBookingId();

        Assert.assertNotNull(
                bookingId,
                "Booking id is null. Create booking first before calling GET, PUT, PATCH, or DELETE."
        );

        Assert.assertTrue(bookingId > 0, "Booking id should be greater than zero");

        return bookingId;
    }

    private Integer extractBookingId(Response response) {
        Assert.assertNotNull(response, "Response is null");

        try {
            Integer bookingId = response.jsonPath().getInt("bookingid");

            if (bookingId != null && bookingId > 0) {
                return bookingId;
            }
        } catch (Exception ignored) {
        }

        try {
            Integer bookingId = response.jsonPath().getInt("id");

            if (bookingId != null && bookingId > 0) {
                return bookingId;
            }
        } catch (Exception ignored) {
        }

        try {
            BookingResponse bookingResponse = response.as(BookingResponse.class);

            if (bookingResponse.getBookingid() != null && bookingResponse.getBookingid() > 0) {
                return bookingResponse.getBookingid();
            }

            if (bookingResponse.getId() != null && bookingResponse.getId() > 0) {
                return bookingResponse.getId();
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    private void makeBookingDatesUnique(BookingRequest bookingRequest) {
        Assert.assertNotNull(bookingRequest, "Booking request is null");
        Assert.assertNotNull(bookingRequest.getBookingdates(), "Booking dates is null");

        int randomDays = ThreadLocalRandom.current().nextInt(30, 500);

        LocalDate checkinDate = LocalDate.now().plusDays(randomDays);
        LocalDate checkoutDate = checkinDate.plusDays(2);

        bookingRequest.getBookingdates().setCheckin(checkinDate.toString());
        bookingRequest.getBookingdates().setCheckout(checkoutDate.toString());
    }
}