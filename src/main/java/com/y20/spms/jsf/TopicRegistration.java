/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjectService;
import com.y20.spms.ejb.UserService;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Yan's
 */

@Named("TopicRegistration")
@RequestScoped
public class TopicRegistration implements Serializable{
    
    private String project_title;
    private String topic_description;
    
    @EJB
    ProjectService prjSrv;
    
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

    public ProjectService getPrjSrv() {
        return prjSrv;
    }

    public void setPrjSrv(ProjectService prjSrv) {
        this.prjSrv = prjSrv;
    }
    
    //call the injected EJB 
    public String registerProjTopic() {
        prjSrv.registerTopic(project_title, topic_description);
        return "index";
    }
    
}
