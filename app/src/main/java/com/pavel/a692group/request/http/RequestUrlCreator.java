package com.pavel.a692group.request.http;

import android.content.Context;
import com.pavel.a692group.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by p.mazhnik on 29.12.2017.
 * to 692group
 */

public class RequestUrlCreator {
    private Context context;
    private String METHOD_NAME;
    private ArrayList<String> PARAMETERS;
    private final String ACCESS_TOKEN;
    private final String API_VK_version;
    private final String URL_BASE;

    private String finalUrl;

    public RequestUrlCreator(Context context, String methodName, String...params) {
        this.context = context;
        ACCESS_TOKEN = context.getString(R.string.access_token);
        API_VK_version = context.getString(R.string.api_vk_version);
        URL_BASE = context.getString(R.string.vk_url_base);
        this.PARAMETERS = new ArrayList<>();
        PARAMETERS.addAll(Arrays.asList(params));

        this.METHOD_NAME = methodName;

        finalUrl = makeRequestString();
    }

    public RequestUrlCreator(Context context, String methodName, ArrayList<String> parameters) {
        this.context = context;
        ACCESS_TOKEN = context.getString(R.string.access_token);
        API_VK_version = context.getString(R.string.api_vk_version);
        URL_BASE = context.getString(R.string.vk_url_base);
        this.PARAMETERS = new ArrayList<>(parameters);
        this.METHOD_NAME = methodName;

        finalUrl = makeRequestString();
    }

    private String makeRequestString(){
        String req = URL_BASE;
        req += METHOD_NAME;
        req += "?";
        if(PARAMETERS.size() > 0) {
            for (int i = 0; i < PARAMETERS.size(); ++i) {
                req += PARAMETERS.get(i);
                req += "&";
            }
        } else {
            req += "&";
        }
        req += "access_token=" + ACCESS_TOKEN;
        req += "&v=" + API_VK_version;
        //System.out.println(req);
        return req;
    }

    public String getRequestUrl(){
        return finalUrl;
    }
}
