package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class CreateUserWithPOJOTest {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    static String emailId;

    @BeforeTest
    public void setup(){
        playwright = Playwright.create();
        request =  playwright.request();
        requestContext = request.newContext();
    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }

    public static String getRandomEmailId(){
        return "testautopw"+ UUID.randomUUID() + "@gmail.com";
    }

    @Test
    public void createUserTest() throws IOException {

        User userData = new User("Naveen", "male", "active");
        userData.setEmail(getRandomEmailId());


        //POST Call: create a user
        APIResponse apiPostResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer abc123")
                        .setData(userData));

        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");

        System.out.println(apiPostResponse.text());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println(postJsonResponse.toPrettyString());

        //capture id from the post json response:
        String userId = postJsonResponse.get("id").asText();
        System.out.println("user id : " + userId);

        //GET Call: Fetch the same user by id:

        System.out.println("===============get call response============");

        APIResponse apiGetResponse =
                requestContext.get("https://gorest.co.in/public/v2/users/"+ userId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer f4bd5cb99e27882658a2233c4ddd1e8a14f49788f92938580971a46439aa774f"));

        Assert.assertEquals(apiGetResponse.status(), 200);
        Assert.assertEquals(apiGetResponse.statusText(), "OK");
        String apiGetResponseText = apiGetResponse.text();
        System.out.println(apiGetResponseText);
        Assert.assertTrue(apiGetResponse.text().contains(userId));
        Assert.assertTrue(apiGetResponse.text().contains(userData.getName()));
        Assert.assertTrue(apiGetResponse.text().contains(userData.getEmail()));

        //pojo to pojo mapping:
        User actUser = objectMapper.readValue(apiGetResponseText, User.class);
        System.out.println(actUser);

        Assert.assertEquals(actUser.getName(), userData.getName());
        Assert.assertEquals(actUser.getEmail(), userData.getEmail());
        Assert.assertEquals(actUser.getGender(), userData.getGender());
        Assert.assertEquals(actUser.getStatus(), userData.getStatus());



    }

}
