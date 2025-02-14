/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.ejb.UserService;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Yan's
 */

//@ManagedBean(name = "Registration")
@Named("Registration")
@RequestScoped

public class Registration implements Serializable{
   
    private String username;
    private String password;
    private String confirmpassword;
    private String fname;
    private String lname;
    private String email;
    private String course_name;
    private String department;
    private String phone_number;
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());

    @EJB
    UserService usrSrv;
    
    @EJB
    ProjLoggingService pls;

    private static final Logger LOGGER = Logger.getLogger(Registration.class.getName());
    
    public Registration() {

    }

    public UserService getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserService usrSrv) {
        this.usrSrv = usrSrv;
    }

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

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    //call the injected EJB 
    public String registerStu() {
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        usrSrv.registerStudent(username, password, fname, lname, email, course_name);
        LOGGER.log(Level.INFO, "Student {0} is added", username);
        
        // log
        pls.insertLog("admin", formattedDate, "Registration", "Register new Student");
        return "index";
    }
    
    public String registerSpv() {
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        usrSrv.registerSpv(username, password, fname, lname, email, department, phone_number);
        LOGGER.log(Level.INFO, "Supervisor {0} is added", username);
        
        // log
        pls.insertLog("admin", formattedDate, "Registration", "Register new Supervisor");
        return "index";
    }
    
    
    
}
