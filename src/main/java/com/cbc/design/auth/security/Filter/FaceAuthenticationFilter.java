package com.cbc.design.auth.security.Filter;

import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FaceAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Setter
    private boolean postOnly = true;

    private String faceParameter = "face";


    public FaceAuthenticationFilter() {
        super(new AntPathRequestMatcher("/face/login", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException, ServletException {
        if(this.postOnly&&!req.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
        }else{
            String face = this.obtainFace(req);
            if(face == null){
                face = "";
            }
            face = face.trim();
            FaceAuthenticationToken authRequest = new FaceAuthenticationToken(face);
            this.setDetails(req,authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, FaceAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainFace(HttpServletRequest request) {
        return request.getParameter(this.faceParameter);
    }

    public void setFaceParameter(String faceParameter) {
        Assert.hasText(faceParameter, "face parameter must not be empty or null");
        this.faceParameter = faceParameter;
    }

    public final String getFaceParameter() {
        return this.faceParameter;
    }
}
