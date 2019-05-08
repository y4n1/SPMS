/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjLoggingService;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.management.relation.Role;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Yan's
 */

@Named
@Stateless
public class Login implements Serializable{
    
    private String username;
    private String password;
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());

    @EJB
    ProjLoggingService pls;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

    public String login() {
        
       // insert Log
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        pls.insertLog(username, formattedDate, "Login", "Sign in");
       
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        try {
            request.login(this.username, this.password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed:" + e));
            return "error";
        }
        
        System.out.println(request.getRequestURI());
        if (request.isUserInRole("Admin")) {
            return "/faces/admin/index.xhtml";  
        } else if (request.isUserInRole("Supervisor")) {    
            return "/faces/supervisor/supervisorPage.xhtml";
        } else {
            return "/faces/student/studentPage.xhtml";
        }
        
    }

    public void logout() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            context.getExternalContext().redirect("login.xhtml");
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }
    
}
