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
    private Long projtopic;
    private Supervisor supervisor = new Supervisor();
    private ProjectTopic projectTopic = new ProjectTopic();
    //private List<Supervisor> spvlist = new ArrayList<>();
    //public ptopic[] topicList;
    //private List<ProjectTopic> prjtopiclist = new ArrayList<>();
    
   
    
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

//    public List<Supervisor> getSpvlist() {
//        return spvlist;
//    }
//
//    public void setSpvlist(List<Supervisor> spvlist) {
//        this.spvlist = spvlist;
//    }

//    public List<ProjectTopic> getPrjtopiclist() {
//        return prjtopiclist;
//    }
//
//    public void setPrjtopiclist(List<ProjectTopic> prjtopiclist) {
//        this.prjtopiclist = prjtopiclist;
//    }

    public Long getProjspv() {
        return projspv;
    }

    public void setProjspv(Long projspv) {
        this.projspv = projspv;
    }

    public Long getProjtopic() {
        return projtopic;
    }

    public void setProjtopic(Long projtopic) {
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
        prjSrv.registerProject(projtitle, projdecr, requiredskill, projspv, projtopic);
        return "index";
    }
      
    public static class pSpv{
	public Long ID;
	public String Name;
		
	public pSpv(Long ID, String Name){
		this.ID = ID;
		this.Name = Name;
	}
		
	public Long getID(){
		return ID;
	}
		
	public String getName(){
		return Name;
	}
		
    }
    
        
    public pSpv[] fillSpvList() {
        
        List<Supervisor> spvlist = prjSrv.findSupervisor();  
        int x = 0;
        String firstname;
        String lastname;
        String fullname;
        Long ID;
        pSpv[] SpvList;
        
        SpvList = new pSpv[spvlist.size()];
        
        while (x < spvlist.size()) {
            
            firstname = spvlist.get(x).getFname();
            lastname = spvlist.get(x).getLname();
            fullname = firstname + " " + lastname;
            ID = spvlist.get(x).getId();
            SpvList[x] = new pSpv(ID,fullname);
            
            x = x+1;
        } 
        
//        for( Supervisor e:spvlist ){
//            list.add(e.getId());
//      }
               
        return SpvList;
    }
    
    public static class ptopic{
	public Long ID;
	public String Title;
		
	public ptopic(Long ID, String Title){
		this.ID = ID;
		this.Title = Title;
	}
		
	public Long getID(){
		return ID;
	}
		
	public String getTitle(){
		return Title;
	}
		
    }
    
       
    public ptopic[] fillTopicList() {
        
        List<ProjectTopic> prjtopiclist = prjSrv.findTopic();
        int i = 0;
        Long ID;
        String Title;
        ptopic[] topicList;
        
        topicList = new ptopic[prjtopiclist.size()];
                
        while (i < prjtopiclist.size()) {
            
            ID = prjtopiclist.get(i).getId();
            Title = prjtopiclist.get(i).getTopicTitle();
            topicList[i] = new ptopic(ID,Title);
            
            i = i+1;
        }    
      
        //for( ProjectTopic p:prjtopiclist ){
        //    list_topic.add(p.getId());
       // }
        
        return topicList;
    }
    
}
