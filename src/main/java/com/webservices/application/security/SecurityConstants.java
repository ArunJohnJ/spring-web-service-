package com.webservices.application.security;

import com.webservices.application.SpringApplicationContext;

public class SecurityConstants {

	public final static long EXPIRATION_TIME = 864000000; //10 days
	public final static String TOKEN_PREFIX = "Bearer ";
	public final static String HEADER_STRING = "Authorization";
	public final static String SIGN_UP_URL = "/users";
//	public final static String TOKEN_SECRET = "sdfsf664fsd4f";
	
	public static String getTokenSecret()
    {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }
	
}
