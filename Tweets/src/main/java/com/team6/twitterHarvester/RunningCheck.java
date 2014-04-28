package com.team6.twitterHarvester;

import org.lightcouch.CouchDbClient;

import com.team6.dal.Base;
import com.team6.twitterHarvester.DbFactory;

public class RunningCheck {
	private CouchDbClient dbClient;
	private Base unique;
	private void syncObj(){
		unique = dbClient.find(Base.class, "tweet_auth_unique_mark");
	}
	
	public RunningCheck(){
		this.dbClient = DbFactory.getClient("tweet_auth");
		unique = new Base();
		syncObj();
	}
	protected void finalize(){
        dbClient.shutdown();
    }
	
	public boolean isRunning() throws InterruptedException{
	    if(0!=unique.getStatus()){  //Application is running
	    	return true;
	    }
		return false;
	}
	public void setStatus(int mark) throws InterruptedException{
		syncObj();
		unique.setStatus(mark);
		dbClient.update(unique);
	}
	public int getStatus() throws InterruptedException{
		return unique.getStatus();
	}
	public Base getObj() throws InterruptedException{
		return unique;
	}
	public String getObjStr() throws InterruptedException{
		return "_id:"+unique.getId()+"  _rev:"+unique.getRev()+"  status:"+unique.getStatus();
	}
}
