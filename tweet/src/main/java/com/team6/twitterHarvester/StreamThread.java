/**
 * Copyright 2014 University of Melbourne
 * 
 * Cloud and Cluster Computing
 * Team6
 * Tweets Harvesting Application 
 * Chicago
 **/

package com.team6.twitterHarvester;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.endpoint.Location;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.lightcouch.CouchDbClient;

import com.team6.twitterHarvester.DbFactory;

public class StreamThread extends Thread {
  private static boolean sw = true;
  private String consumerKey;
  private String consumerSecret;
  private String token;
  private String secret;
  
  public StreamThread(String consumerKey, String consumerSecret, String token, String secret){
	  this.consumerKey = consumerKey;
	  this.consumerSecret = consumerSecret;
	  this.token = token;
	  this.secret = secret;
  }
  
  //couchDB connection driver - lightcouch
  private static CouchDbClient dbClient;
	
  private void harvest() throws InterruptedException {	  
	BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

    //add trackTerms, languages, location to refine filter
    //endpoint.trackTerms(Lists.newArrayList("chicago bulls"));
    endpoint.languages(Lists.newArrayList("en"));
    endpoint.locations(Lists.newArrayList(
            new Location(new Location.Coordinate(-87.96,41.644), new Location.Coordinate(-87.40,42.04))));
    
    Authentication auth = new OAuth1(this.consumerKey, this.consumerSecret, this.token, this.secret);

    // Create a new BasicClient. By default gzip is enabled.
    Client client = new ClientBuilder()
      .hosts(Constants.STREAM_HOST)
      .endpoint(endpoint)
      .authentication(auth)
      .processor(new StringDelimitedProcessor(queue))
      .build();

    // Establish a connection
    client.connect();

    // Establish a database connection, return client as well
    dbClient = DbFactory.getClient("tweet_chicago_1");
    
    // Do whatever needs to be done with messages
    for (int msgRead = 0; msgRead < 10; msgRead++) {
      String tweet = queue.take();

      JsonParser parser = new JsonParser();
      JsonObject jsonobj = (JsonObject)parser.parse(tweet);
      
      String _id = jsonobj.getAsJsonPrimitive("id_str").getAsString();
      if(!dbClient.contains(_id)){
          System.out.println(tweet);
          jsonobj.addProperty("_id", _id);
          dbClient.save(jsonobj);
      }else{
    	  System.out.println("tweet _id="+_id+" exist");
      }
      
    }

    dbClient.shutdown();
    
    client.stop();
  }
  
  //Thread method
  public void run(){
	  while(sw){
		  try {
			harvest();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
  }
  
  public static void setOn(){
	  if(false == sw){
		  sw = true;
	  }
  }
  
  public static void setOff(){
	  if(true == sw){
		  sw = false;
	  }
  }
  
  public static boolean getStatus(){
	  return sw;
  }

}
