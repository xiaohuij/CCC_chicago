package com.team6.twitterHarvester;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

public class DbFactory {
	
	public DbFactory(){
	}
	
	public static CouchDbClient getClient(String dbName){
	    CouchDbProperties properties = new CouchDbProperties()
	    .setDbName(dbName)
	    .setCreateDbIfNotExist(true)
	    .setProtocol("http")
	    .setHost("127.0.0.1")
	    .setPort(5984)
	    .setMaxConnections(100)
	    .setConnectionTimeout(0);

	    return new CouchDbClient(properties);
	}
}
