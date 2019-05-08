/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.ejb.TopicService;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yan's
 */

@Named("TopicRegistration")
@RequestScoped
public class TopicRegistration implements Serializable{
    
    private String project_title;
    private String topic_description;
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    
    @EJB
    TopicService prjSrv;
    
    @EJB
    ProjLoggingService pls;
    
    private static final Logger LOGGER = Logger.getLogger(TopicRegistration.class.getName());
    
    public TopicRegistration() {

    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getTopic_description() {
        return topic_description;
    }

    public void setTopic_description(String topic_description) {
        this.topic_description = topic_description;
    }

    public TopicService getPrjSrv() {
        return prjSrv;
    }

    public void setPrjSrv(TopicService prjSrv) {
        this.prjSrv = prjSrv;
    }
    
    //call the injected EJB 
    public String registerProjTopic() {
        
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        prjSrv.registerTopic(project_title, topic_description);
        LOGGER.log(Level.INFO, "New Topic{0} is added by Admin", project_title);
        
        // log
        pls.insertLog("admin", formattedDate, "TopicRegistration", "Create New Topic");
        
        return "index";
    }
    
    public String registerProjTopicSpv() {
        
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        prjSrv.registerTopic(project_title, topic_description);
        LOGGER.log(Level.INFO, "New Topic{0} is added by Supervisor", project_title);
        
        // log
        pls.insertLog("Supervisor", formattedDate, "TopicRegistration", "Create New Topic");
        return "supervisorPage";
    }
    
}
