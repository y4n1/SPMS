/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.ejb.ProjectService;
import com.y20.spms.ejb.RetrieveID;
import com.y20.spms.entity.ProjectTopic;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
    private Long student;
    private  String std;
    private Supervisor supervisor = new Supervisor();
    private ProjectTopic projectTopic = new ProjectTopic();
    private Student st = new Student();
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
   
   
    
    @EJB
    ProjectService prjSrv;
    
    @EJB
    RetrieveID ri;
    
    @EJB
    ProjLoggingService pls;
    
    private static final Logger LOGGER = Logger.getLogger(ProjectRegistration.class.getName());
    
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

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
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

    public Student getSt() {
        return st;
    }

    public void setSt(Student st) {
        this.st = st;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
    
    
    
    
       
    
    //call the injected EJB 
    //register project
    public String registerProj() {
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        prjSrv.registerProject(projtitle, projdecr, requiredskill, projspv, projtopic);
        
        //Log
        // log
        pls.insertLog(std, formattedDate, "ProjRegistration", "Register Project");
        
        LOGGER.log(Level.INFO, "Project{0} is added", projtitle);
        return "index";
    }
      
    //propose project
    public String proposeProj() {
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        int check;
        Getloginid();
        
        // log
        pls.insertLog(std, formattedDate, "ProjRegistration", "Propose Project");
        
        check = prjSrv.checkrecord(student);
        
        if (check == 0){
            prjSrv.proposeProject(projtitle, projdecr, requiredskill, projspv, projtopic, student);
            return "studentPage";
        } else {
            return "ProjError.xhtml";
        }    
    }
      
    //filling up spv list
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
 
        return SpvList;
    }
    
    // Get all title
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
    
    // Retrieve login ID
    public void Getloginid() {
       
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        st = ri.getstudentID(std);
        student = st.getId();
        
    }   
    
}
