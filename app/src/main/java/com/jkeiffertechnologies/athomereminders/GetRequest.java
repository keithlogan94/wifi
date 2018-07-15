package com.jkeiffertechnologies.athomereminders;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kbecker on 7/14/2018.
 */

public class GetRequest {

    OkHttpClient client = new OkHttpClient();
    ServerWebAddress serverWebAddress = null;

    public GetRequest(ServerWebAddress webAddress) {
        serverWebAddress = webAddress;
    }

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String start() throws IOException {
        return get(serverWebAddress.getUrl());
    }
}
