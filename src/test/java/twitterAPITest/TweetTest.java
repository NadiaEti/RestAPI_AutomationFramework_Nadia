package twitterAPITest;

import Utils.LearnRandomNumber;
import base.RestBase;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import twitterAPI.Tweet;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static twitterAPI.Tweet.GET_USER_TWEET_ENDPOINT;

public class TweetTest extends RestBase {
    private Tweet tweet;

    @BeforeClass
    public void setUpTweetAPI() {
        this.tweet = new Tweet();
    }

    @Test
    public void testGetUserTimeLineTweet() {
        Tweet tweet = new Tweet();
        ValidatableResponse response = tweet.getUserTimeLineTweet();
        System.out.println(response.statusCode(200));
        // System.out.println(response.extract().body().asPrettyString());
        String expectedValue = "hello'";
        String expectedName = "nadia eti";
        long expectedId = 1615824562507186179l;
        String actualText = response.extract().body().path("[0].text");
        long actualId = response.extract().body().path("[0].id");
        // long actualId= response.extract().body().path("x[0].entities.hashtags");
        String actualName = response.extract().body().path("[0].user.name");
        Assert.assertEquals(actualText, expectedValue, "Text value does not match");
        Assert.assertEquals(actualId, expectedId, "ID value does not match");
        System.out.println(actualName);
        Assert.assertEquals(actualName, expectedName, "Name value does not match");


    }

    @Test
    public void testGetUserTimeLineTweetNew() {
        Tweet tweet = new Tweet();
        ValidatableResponse response = tweet.getUserTimeLineTweetNew();
        response.statusCode(200);
        // System.out.println(response.statusCode(200));
        System.out.println(response.extract().body().asPrettyString());  //For this line all the pretty will be printed.
        String expectedValue = "hello'";
        String expectedName = "nadia eti";
        long expectedId = 1615824562507186179l;
        String actualText = response.extract().body().path("[0].text");
        long actualId = response.extract().body().path("[0].id");
        // long actualId= response.extract().body().path("x[0].entities.hashtags");
        String actualName = response.extract().body().path("[0].user.name");
        Assert.assertEquals(actualText, expectedValue, "Text value does not match");
        Assert.assertEquals(actualId, expectedId, "ID value does not match");
        System.out.println(actualName);
        Assert.assertEquals(actualName, expectedName, "Name value does not match");


    }


    @Test
    public void verifyCreateTweet() {
        String tweetMessage = "Welcome back Ismat"+ LearnRandomNumber.randomNumberGenerate();
        ValidatableResponse response = this.tweet.createTweet(tweetMessage);
        response.statusCode(200);
        response.contentType("application/json");
      //  response.log().all();         // If we use this then it will generate all the response with Header response
      System.out.println(response.extract().body().asPrettyString());
        // Verify tweet value
        String actualTweet=response.extract().body().path("text");
        String actualScreenName=response.extract().body().path("user.screen_name");
        System.out.println(actualTweet);
        Assert.assertEquals(actualTweet,tweetMessage,"Tweet does not match");
        Assert.assertEquals(actualScreenName,"ShahibMh","Screen name does not match");



    }


    @Test
    public void verifyUserCanNotCreateSameTweetTwiceInARow() {
        String tweetMessage = "Welcome back Ismat";
        ValidatableResponse response = this.tweet.createTweet(tweetMessage);
        response.statusCode(403);
        response.contentType("application/json");
        //  response.log().all();
        System.out.println(response.extract().body().asPrettyString());
        // Verify tweet value
       String actualMessage=response.extract().body().path("errors[0].message");
      Assert.assertEquals(actualMessage,"Status is a duplicate.","message does not match");
    }


    @Test
    public void verifyDeleteTweet() {
        ValidatableResponse response = this.tweet.deleteTweet(1559727301549490176L);
      //  response.statusCode(200);
        //  response.log().all();
        System.out.println(response.extract().body().asPrettyString());

    }

    @Test
    public void verifyResponseTime() {
        long actualResponse = this.tweet.responseTimeCheck(GET_USER_TWEET_ENDPOINT);
     //   Assert.assertEquals(actualResponse,actualResponse<700,"Response time does not match");
        Assert.assertTrue(actualResponse<700,"Response time exit the default time does not match");



    }

    @Test
    public void verifyHeaderValue1() {
        this.tweet.headerValue1(GET_USER_TWEET_ENDPOINT);

    }

 @Test
    public void verifyHeaderValue() {
     Headers response= this.tweet.headerValue(GET_USER_TWEET_ENDPOINT);
     String actualHeaderValue=response.getValue("content-type");
     System.out.println(response.getValue("content-type"));
     Assert.assertEquals(actualHeaderValue,"application/json;charset=utf-8","Header value does not match");
 }


    @Test
    public void testPropertyFromResponse(){
        // User sent a tweet
        String tweet = "We are learning Rest API Automation and Hashem is the team Lead" + UUID.randomUUID().toString();
        ValidatableResponse response = this.tweet.createTweet(tweet);
     //   System.out.println(response.extract().body().asPrettyString());
      System.out.println(response.extract().body().asPrettyString().contains("id"));
        // Verify that the tweet is successful
        response.statusCode(200);

        // this.tweetAPIClient.checkProperty(tweetAPIClient.CREATE_TWEET_ENDPOINT,"text");

      Response response1 = given().auth().oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret).when().get(this.baseUrl+GET_USER_TWEET_ENDPOINT);
        // Response response2=  response;
     JsonPath pathEvaluator= response1.jsonPath();
     String property= pathEvaluator.get("[0].text");
    System.out.println("Property value : "+property);
      //   System.out.println(response1.extract().body().asPrettyString());
         System.out.println(response1.body().asPrettyString());

    }



}
