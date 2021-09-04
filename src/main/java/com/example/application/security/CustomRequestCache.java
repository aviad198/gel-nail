package com.example.application.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class CustomRequestCache extends HttpSessionRequestCache {

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!AuthenticatedUser.isFrameworkInternalRequest(request)) {
            //Saves unauthenticated requests, so that, once the user is logged in, you can redirect them to the page they were trying to access.
            super.saveRequest(request, response);
        }
    }
}