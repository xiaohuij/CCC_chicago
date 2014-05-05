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

public class StreamThread extends Thread {
	private volatile static boolean sw = false;
	private String consumerKey;
	private String consumerSecret;
	private String token;
	private String secret;

	public StreamThread(String consumerKey, String consumerSecret,
			String token, String secret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.token = token;
		this.secret = secret;
	}

	private void log(String log) {
		System.out.println(log);
	}

	// couchDB connection driver - lightcouch
	private CouchDbClient dbClient;
	private Client client;

	private void harvest() throws InterruptedException {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

		// add trackTerms, languages, location to refine filter
		// endpoint.trackTerms(Lists.newArrayList("melbourne", "mel au", "melb", "best city"));
		// endpoint.languages(Lists.newArrayList("en"));
		endpoint.locations(Lists.newArrayList(new Location(
				//new Location.Coordinate(-87.96, 41.644),
				//new Location.Coordinate(-87.774, 41.776))));
		        //new Location.Coordinate(-87.774, 41.776),
		        //new Location.Coordinate(-87.586, 41.908))));
		        new Location.Coordinate(-87.586, 41.908),
		        new Location.Coordinate(-87.40, 42.04))));
		
		        //new Location.Coordinate(144.408, -37.982),
                //new Location.Coordinate(144.919, -37.461))));
		        //new Location.Coordinate(144.919, -38.210),
                //new Location.Coordinate(145.574, -37.461))));

		Authentication auth = new OAuth1(this.consumerKey, this.consumerSecret,
				this.token, this.secret);

		// Create a new BasicClient. By default gzip is enabled.
		client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();
		log("<<<<<<<<<<1. Tweeter oauth connected>>>>>>>>>>>");

		// Establish a database connection, return client as well
		dbClient = DbFactory.getClient("tweet_chicago_3");
		log("<<<<<<<<<<2. couchDB connected!>>>>>>>>>>>>>>>>");

		// Tweets storage
		log("<<<<<<<<<<3. Start harvesting>>>>>>>>>>>>>>>>>>");
		while (!client.isDone()) {
			// for (int count = 0; count < 20; count++) {
			String tweet = queue.take();

			JsonParser parser = new JsonParser();
			JsonObject jsonobj = (JsonObject) parser.parse(tweet);

			String _id = jsonobj.getAsJsonPrimitive("id_str").getAsString();
			jsonobj.addProperty("_id", _id);
			dbClient.save(jsonobj);

		}
		// }
		log("<<<<<<<<<<4. end harvesting>>>>>>>>>>>>>>>>>>>>>");

		dbClient.shutdown();
		log("<<<<<<<<<<5. couchDB disconnected>>>>>>>>>>>>>>>");

		client.stop();
		log("<<<<<<<<<<6. Twitter oauth disconnected>>>>>>>>>");

	}

	// Thread method
	public void run() {
		try {
			while (sw) {
				harvest();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void setOn() {
		sw = true;
	}

	public static void setOff() {
		sw = false;
	}

	public static boolean getStatus() {
		return sw;
	}

}
