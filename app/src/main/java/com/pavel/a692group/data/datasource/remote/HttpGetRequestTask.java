package com.pavel.a692group.data.datasource.remote;

import android.os.AsyncTask;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by p.mazhnik on 17.11.2017.
 * to 692group
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