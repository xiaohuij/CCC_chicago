package com.team6.twitterHarvester;

import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.team6.dal.Auth;
import com.team6.twitterHarvester.StreamThread;

public class Tweet {
	
    private static Auth readAuth(){
    	Gson gson = new Gson();
    	InputStreamReader in = new InputStreamReader(
    		//getClass().getResourceAsStream("auth.json"));
    		Thread.currentThread().getContextClassLoader().getResourceAsStream("auth.json"));
    	Auth auth = gson.fromJson(in, Auth.class);
		return auth;
    }
	
    public synchronized static void exe() throws InterruptedException {
    	Auth auth = readAuth();
        StreamThread tweet = new StreamThread(
        		auth.getConsumer(), auth.getCsecret(), auth.getToken(), auth.getTsecret());
    	tweet.start();
    }

    public static void main(String[] args) throws InterruptedException {
    	exe();
    } 
}
