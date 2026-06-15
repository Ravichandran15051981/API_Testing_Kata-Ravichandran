package apis;

import io.restassured.response.Response;
import pojo.AuthRequest;
import utils.ConfigLoader;

import java.time.Instant;

public class TokenManager {

    private static String token;
    private static Instant expiry_time;

    public synchronized static String getToken() {
        try{
            if(token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token ...");
                Response response = renewToken();
                token = response.path("token");
                expiry_time = Instant.now().plusSeconds(120);
            } else{
                System.out.println("Token is good to use");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("ABORT!!! Failed to get token");
        }
        return token;
    }

    private static Response renewToken(){
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(ConfigLoader.getInstance().getAuthUsername());
        authRequest.setPassword(ConfigLoader.getInstance().getAuthPassword());

        Response response = RestResource.postAuth("/auth/login",authRequest);

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!!! Renew Token failed");
        }
        return response;
    }

}

