package com.github.drawers.retrofidds.summoner;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * Created by rawsond on 13/05/17.
 */
@Value.Immutable
@Gson.TypeAdapters
public interface SummonerDTO {

    int profileIconId();
    String name();
    long summonerLevel();
    long revisionDate();
    long id();
    long accountId();
}
