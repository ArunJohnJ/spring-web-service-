package com.webservices.application.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservices.application.SpringApplicationContext;
import com.webservices.application.model.ui.request.UserLoginRequest;
import com.webservices.application.service.UserService;
import com.webservices.application.shared.dto.UserDto;

import io.jsonwebtoken.Jwts;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    
    private String contentType;
 
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
        	
//        	contentType = req.getHeader("Accept");
        	
            UserLoginRequest creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginRequest.class); //spring framework will try to match the credentials provided in the post requests with the data in the DB
            
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String userName = ((User) auth.getPrincipal()).getUsername();  
        
        String token = Jwts.builder()
                .setSubject(userName)// userName is the email
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret() )
                .compact();
        UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");//This is because the AuthenticationFilter class is just a regular class and was not created as a @Bean or a @Service or a @Component. I can use @Autowired only inside of those kind of objects. If Authentication Filter class was created as a @Component for example, it would live in the Application Context just like a User Service class. Then I could use @Autowired to inject UserService into Authentication Filter.  I cannot use @Autowired in regular classes which were not created as a @Service or a @Component for example...  
       //the bean first letter will be smallcase 
        UserDto userDto = userService.getUser(userName);
        
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader("UserID", userDto.getUserId());

    }  

}
