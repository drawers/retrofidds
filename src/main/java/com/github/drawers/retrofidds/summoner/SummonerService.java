package com.github.drawers.retrofidds.summoner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rawsond on 13/05/17.
 */
public interface SummonerService {

    @GET("/lol/summoner/v3/summoners/by-account/{accountId}")
    Call<SummonerDTO> byAccount(@Path("accountId") long accountId);

    @GET("/lol/summoner/v3/summoners/by-name/{summonerName}")
    Call<SummonerDTO> byName(@Path("summonerName") String summonerName);

    @GET("/lol/summoner/v3/summoners/{summonerId}")
    Call<SummonerDTO> byId(@Path("summonerId") long summonerId);
}
