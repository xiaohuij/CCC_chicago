/**
 * Copyright 2014 University of Melbourne
 * 
 * Cloud and Cluster Computing
 * Team6
 * Tweets Harvesting Application 
 * Chicago
 **/

package com.team6.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import com.google.gson.Gson;
import com.team6.dal.Auth;
import com.team6.twitterHarvester.FilterStream;*/
import com.team6.twitterHarvester.Tweet;
import com.team6.twitterHarvester.RunningCheck;

/**
 * Servlet implementation class Start
 */
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Start() {
        // TODO Auto-generated constructor stub
    }
    
    //read oAuth json file
/*    private Auth readAuth(){
    	Gson gson = new Gson();
    	InputStreamReader in = new InputStreamReader(
    		  //getClass().getResourceAsStream("auth.json"));
    		Thread.currentThread().getContextClassLoader().getResourceAsStream("auth.json"));
    	Auth auth = gson.fromJson(in, Auth.class);
		return auth;
    }*/

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");  
			response.setCharacterEncoding("UTF-8"); 
			PrintWriter out = response.getWriter();
			
			String par = request.getParameter("to");
			if(null != par){
				RunningCheck chk = new RunningCheck();
				if(par.equals("check")){
					if(chk.isRunning()){   //Application has started running
						out.print("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\"><title>Tweets Harvesting Applicaton - Team6</title><meta name=\"description\" content=\"Tweets Harvesting Application\"><meta name=\"author\" content=\"Team6\"></head><body><h3>Application is running. one instance one time!</h3></html>");
					}else{
						out.print("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\"><title>Tweets Harvesting Applicaton - Team6</title><meta name=\"description\" content=\"Tweets Harvesting Application\"><meta name=\"author\" content=\"Team6\"><style type=\"text/css\">#frm {font-size:25px;margin:30px 10px;}#btnStart {font-size:20px;height:35px;width:100px;}</style><script type=\"text/javascript\" src=\"/tweet/js/jquery-1.11.0.min.js\"></script><script type=\"text/javascript\">$(document).ready(function() {$('#btnStart').click(function(event) {$(\"#btnStart\").val(\"RUNNING\").attr(\"disabled\", true);$.get('http://127.0.0.1:8080/tweet/start?to=tweet',{},function(data) {$(\"#btnStart\").val(\"START\").removeAttr(\"disabled\");});});});</script></head><body><form id=\"frm\"><label>Tweets Harvesting:</label><input type=\"button\" id=\"btnStart\" value=\"START\" /></form></body></html>");
					}
				}else if(par.equals("tweet")){
					chk.setStatus(1);
					/*Auth auth = readAuth();
					FilterStream.oauth(
							auth.getConsumer(), auth.getCsecret(), auth.getToken(), auth.getTsecret());*/
					Tweet.exe();
					chk.setStatus(0);
				}	
			}else{
				out.print("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\"><title>Tweets Harvesting Applicaton - Team6</title><meta name=\"description\" content=\"Tweets Harvesting Application\"><meta name=\"author\" content=\"Team6\"></head><body><h3>Servlet parameter error!</h3></html>");
			}
			out.flush();
			out.close();
		} catch (InterruptedException e) {		    
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
