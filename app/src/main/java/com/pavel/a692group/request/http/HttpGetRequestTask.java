package com.pavel.a692group.request.http;

import android.os.AsyncTask;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pavel on 17.11.2017.
 */

public class HttpGetRequestTask extends AsyncTask<Void, Void, String> {
    private String reqUrl;
    private OkHttpClient client;
    private final Request request;
    private String responseBody;

    private TextView mJsonStr;

    public HttpGetRequestTask(String url, TextView JsonStr){
        reqUrl = url;
        client = new OkHttpClient();

        mJsonStr = JsonStr;

        request = new Request.Builder()
                .url(reqUrl)
                .build();
    }

    @Override
    protected String doInBackground(Void...params) {
        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
        } catch (Exception e){
            responseBody = "404";
            e.printStackTrace();
        }
        return responseBody;
    }

    @Override
    protected void onPostExecute(String res)
    {
        super.onPostExecute(res);
        if(mJsonStr != null) mJsonStr.setText(res);
    }
}