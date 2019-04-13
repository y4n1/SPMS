/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjectService;
import com.y20.spms.entity.ProjectTopic;
import com.y20.spms.entity.Supervisor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Yan's
 */

@Named("ProjectRegistration")
@RequestScoped

public class ProjectRegistration implements Serializable {
    
    private String projtitle;
    private String projdecr;
    private String requiredskill;
    private Long projspv;
    private String projtopic;
    private Supervisor supervisor = new Supervisor();
    private ProjectTopic projectTopic = new ProjectTopic();
    private List<Supervisor> spvlist = new ArrayList<>();
    private List<ProjectTopic> prjtopiclist = new ArrayList<>();
   
    
    @EJB
    ProjectService prjSrv;
    
    public ProjectRegistration() {

    }

    public String getProjtitle() {
        return projtitle;
    }

    public void setProjtitle(String projtitle) {
        this.projtitle = projtitle;
    }

    public String getProjdecr() {
        return projdecr;
    }

    public void setProjdecr(String projdecr) {
        this.projdecr = projdecr;
    }

    public String getRequiredskill() {
        return requiredskill;
    }

    public void setRequiredskill(String requiredskill) {
        this.requiredskill = requiredskill;
    }

    public ProjectService getPrjSrv() {
        return prjSrv;
    }

    public void setPrjSrv(ProjectService prjSrv) {
        this.prjSrv = prjSrv;
    }

    public List<Supervisor> getSpvlist() {
        return spvlist;
    }

    public void setSpvlist(List<Supervisor> spvlist) {
        this.spvlist = spvlist;
    }

    public List<ProjectTopic> getPrjtopiclist() {
        return prjtopiclist;
    }

    public void setPrjtopiclist(List<ProjectTopic> prjtopiclist) {
        this.prjtopiclist = prjtopiclist;
    }

    public Long getProjspv() {
        return projspv;
    }

    public void setProjspv(Long projspv) {
        this.projspv = projspv;
    }

   

    public String getProjtopic() {
        return projtopic;
    }

    public void setProjtopic(String projtopic) {
        this.projtopic = projtopic;
    }

    

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor projspv) {
        this.supervisor = projspv;
    }

    public ProjectTopic getProjectTopic() {
        return projectTopic;
    }

    public void setProjectTopic(ProjectTopic projectTopic) {
        this.projectTopic = projectTopic;
    }
    
    
    //call the injected EJB 
    public String registerProj() {
        prjSrv.registerProject(projtitle, projdecr, requiredskill, projspv, projectTopic);
        return "index";
    }
    
    public List<Supervisor> fillSpvList() {
        spvlist = prjSrv.findSupervisor();  
        int x = 0;
        String firstname;
        String lastname;
        String fullname;
        
        List list = new ArrayList();
        
        for( Supervisor e:spvlist ){
            list.add(e.getId());
      }
               
        return list;
    }
    
    public List<ProjectTopic> fillTopicList() {
        prjtopiclist = prjSrv.findTopic();
        int i = 0;
        
        List list_topic = new ArrayList();
                
      /*  while (i < prjtopiclist.size()) {
            
            list_topic.add(prjtopiclist.get(i).getTopicTitle());
            
            i = i+1;
        }  */  
      
        for( ProjectTopic p:prjtopiclist ){
            list_topic.add(p.getTopicTitle());
        }
        
        return list_topic;
    }
    
}
