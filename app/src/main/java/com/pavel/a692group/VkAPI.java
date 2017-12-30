package com.pavel.a692group;

import android.content.Context;

import com.pavel.a692group.request.http.RequestUrlCreator;

import java.util.ArrayList;

/**
 * Created by p.mazhnik on 29.12.2017.
 * to 692group
 */

class VkAPI {
    private static final String StrMessages = "messages";
    public static class Messages{
        private static final String StrGetDialogs = ".getDialogs";
        public static class getDialogs{
            private Context context;
            private int offset;
            private int count;

            private ArrayList<String> parameters;

            public getDialogs(Context context){
                parameters = new ArrayList<>();
                this.context = context;
            }

            public static final String StrOffset = "offset=";
            public getDialogs offset(int offset){
                this.offset = offset;
                parameters.add(StrOffset + this.offset);
                return this;
            }

            public static final String StrCount = "count=";
            public getDialogs count(int count){
                this.count = count;
                parameters.add(StrCount + this.count);
                return this;
            }

            public String build(){
                RequestUrlCreator URL = new RequestUrlCreator(context,StrMessages + StrGetDialogs, parameters);
                return URL.getRequestUrl();
            }
        }

        private static final String StrSend = ".send";
        public static class send{
            private Context context;
            int user_id;
            ArrayList<Integer> user_ids;
            String message;

            private ArrayList<String> parameters;

            public send(Context context){
                parameters = new ArrayList<>();
                this.context = context;
            }

            public static final String StrUser_id = "user_id=";
            public send user_id(int user_id){
                this.user_id = user_id;
                parameters.add(StrUser_id + this.user_id);
                return this;
            }

            public static final String StrMessage = "message=";
            public send message(String message){
                this.message = message.replace("\n", "%0A");
                parameters.add(StrMessage + this.message);
                return this;
            }

            public static final String StrUser_ids = "user_ids=";
            public send user_ids(ArrayList<Integer> user_ids){
                this.user_ids = user_ids;
                StringBuilder str = new StringBuilder();
                for(int i = 0; i < user_ids.size() - 1; ++i){
                    if(user_ids.get(i) != 0) str.append(user_ids.get(i)).append(",");
                }
                str.append(user_ids.get(user_ids.size() - 1));
                parameters.add(StrUser_ids + str);
                return this;
            }

            public String build(){
                RequestUrlCreator URL = new RequestUrlCreator(context,StrMessages + StrSend, parameters);
                return URL.getRequestUrl();
            }
        }

        private static final String StrMarkAsRead = ".markAsRead";
    }
}
