package com.assignment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 
 * @author admin
 *
 */
public interface ActionExecute {
    public String processRequest(HttpServletRequest request, HttpServletResponse response);
}
