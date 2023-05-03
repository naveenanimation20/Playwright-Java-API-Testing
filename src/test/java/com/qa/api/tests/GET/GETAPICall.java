package com.qa.api.tests.GET;

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

import java.io.IOException;
import java.util.Map;

public class GETAPICall {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup(){
         playwright = Playwright.create();
         request =  playwright.request();
         requestContext = request.newContext();
    }

    @Test
    public void getSpecificUserApiTest() throws IOException {
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users",RequestOptions.create()
                            .setQueryParam("gender", "male")
                            .setQueryParam("status", "active"));
        int statusCode = apiResponse.status();
        System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiResponse.ok(), true);

        String statusResText = apiResponse.statusText();
        System.out.println(statusResText);

        System.out.println("----print api response with plain text----");
        System.out.println(apiResponse.text());

        System.out.println("----print api json response----");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyRespose = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyRespose);

    }

    @Test
    public void getUsersApiTest() throws IOException {
      APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
       int statusCode = apiResponse.status();
       System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiResponse.ok(), true);
        String statusResText = apiResponse.statusText();
        System.out.println(statusResText);

        System.out.println("----print api response with plain text----");
        System.out.println(apiResponse.text());

        System.out.println("----print api json response----");
        ObjectMapper objectMapper = new ObjectMapper();
       JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
       String jsonPrettyRespose = jsonResponse.toPrettyString();
       System.out.println(jsonPrettyRespose);

        System.out.println("----print api url----");
        System.out.println(apiResponse.url());

        System.out.println("----print response headers----");
       Map<String, String> headersMap = apiResponse.headers();
        System.out.println(headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        Assert.assertEquals(headersMap.get("x-download-options"), "noopen");

    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }

}
