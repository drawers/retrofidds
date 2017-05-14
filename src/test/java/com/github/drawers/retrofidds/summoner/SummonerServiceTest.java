package com.github.drawers.retrofidds.summoner;

import com.github.drawers.retrofidds.retrofit.APIKeyInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by rawsond on 14/05/17.
 */
public class SummonerServiceTest {

    //fakes
    String fakeApiKey;

    //mocks
    MockWebServer mockWebServer;

    //system under test
    SummonerService summonerService;

    @Before
    public void setUp() throws Exception {
        fakeApiKey = "abcdef";

        mockWebServer = new MockWebServer();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor riotAPIInterceptor = new APIKeyInterceptor(fakeApiKey);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(riotAPIInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new GsonAdaptersSummonerDTO())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(mockWebServer.url("")).build();

        summonerService = retrofit.create(SummonerService.class);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testSummonerByNameRequest() throws Exception {
        //arrange
        MockResponse mockResponse = createValidMockSummonerDTOResponse();
        mockWebServer.enqueue(mockResponse);

        //act
        summonerService.byName("SimpleSam").execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        //assert
        assertEquals("/lol/summoner/v3/summoners/by-name/SimpleSam", recordedRequest.getPath());
        assertEquals(fakeApiKey, recordedRequest.getHeader("X-Riot-Token"));
    }

    @Test
    public void testSummonerByNameResponse() throws Exception {
        //arrange
        MockResponse mockResponse = createValidMockSummonerDTOResponse();
        mockWebServer.enqueue(mockResponse);

        //act
        SummonerDTO summonerDTO = summonerService.byName("SimpleSam").execute().body();

        //assert
        assertEquals(1627, summonerDTO.profileIconId());
        assertEquals("Simple Sam", summonerDTO.name());
        assertEquals(30, summonerDTO.summonerLevel());
        assertEquals(201511753, summonerDTO.accountId());
        assertEquals(4383087, summonerDTO.id());
        assertEquals(1494708726000L, summonerDTO.revisionDate());
    }

    private MockResponse createValidMockSummonerDTOResponse() {
        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody("{\n" +
                "    \"profileIconId\": 1627,\n" +
                "    \"name\": \"Simple Sam\",\n" +
                "    \"summonerLevel\": 30,\n" +
                "    \"accountId\": 201511753,\n" +
                "    \"id\": 4383087,\n" +
                "    \"revisionDate\": 1494708726000\n" +
                "}");
        mockResponse.setResponseCode(200);
        return mockResponse;
    }
}