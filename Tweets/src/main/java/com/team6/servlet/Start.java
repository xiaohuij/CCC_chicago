package com.team6.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.team6.twitterHarvester.Auth;
import com.team6.twitterHarvester.FilterStream;
import com.team6.twitterHarvester.Global;

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
    private Auth readAuth(){
    	Gson gson = new Gson();
    	InputStreamReader in = new InputStreamReader(
    		  //getClass().getResourceAsStream("auth.json"));
    		Thread.currentThread().getContextClassLoader().getResourceAsStream("auth.json"));
    	Auth auth = gson.fromJson(in, Auth.class);
		return auth;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/plain");  
			response.setCharacterEncoding("UTF-8"); 
			PrintWriter out = response.getWriter(); 
			
			if(!Global.unique){
			    //Open unique mark
			    Global.unique = true;
			    
				Auth auth = readAuth();
				FilterStream.oauth(
						auth.getConsumer(), auth.getCsecret(), auth.getToken(), auth.getTsecret());
				
			    //close unique mark
			    Global.unique = false;
			    
				out.print("Done!");
			}else{
				out.print("One instance has been activated!");
			}
			out.flush();
			out.close();
		} catch (InterruptedException e) {
		    Global.unique = false;
		    
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
