package com.qa.api.tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class APIDisposeTest {

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
    public void disposeResponseTest(){

        //Request-1:
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiResponse.ok(), true);
        String statusResText = apiResponse.statusText();
        System.out.println(statusResText);

        System.out.println("----print api response with plain text----");
        System.out.println(apiResponse.text());

        apiResponse.dispose();//will dispose only response body but status code, url, status text will remain same
        System.out.println("----print api response after dispose with plain text----");

        try {
            System.out.println(apiResponse.text());
        }
        catch(PlaywrightException e ){
            System.out.println("api response body is disposed");
        }


        int statusCode1 = apiResponse.status();
        System.out.println("response status code after dispose: " + statusCode1);

        String statusResText1 = apiResponse.statusText();
        System.out.println(statusResText1);

        System.out.println("response url:" + apiResponse.url());

        //Request -2 :
        APIResponse apiResponse1 = requestContext.get("https://reqres.in/api/users/2");
        System.out.println("get response body for 2nd request:");
        System.out.println("status code:" + apiResponse1.status());
        System.out.println("repose body:" + apiResponse1.text());


        //request context dispose:
        requestContext.dispose();
        //System.out.println("response2 body:" + apiResponse.text());
        System.out.println("response2 body:" + apiResponse1.text());


    }


    @AfterTest
    public void tearDown(){
        playwright.close();
    }



}
