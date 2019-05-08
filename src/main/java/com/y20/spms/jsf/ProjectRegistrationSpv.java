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
import com.y20.spms.entity.Supervisor;
import com.y20.spms.entity.SystemUser;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yan's
 */
@Named("ProjectRegistrationSpv")
@RequestScoped
public class ProjectRegistrationSpv implements Serializable{
    
    private String projtitle;
    private String projdecr;
    private String requiredskill;
    private Long projspv;
    private Long projtopic;
    private String std;
    private ProjectTopic projectTopic = new ProjectTopic();
    private Supervisor sv = new Supervisor();
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    
    
    @EJB
    ProjectService prjSrv;
    
    @EJB
    RetrieveID ri;
    
    @EJB
    ProjLoggingService pls;
    
    private static final Logger LOGGER = Logger.getLogger(ProjectRegistrationSpv.class.getName());
    
    public ProjectRegistrationSpv() {

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

    public ProjectTopic getProjectTopic() {
        return projectTopic;
    }

    public void setProjectTopic(ProjectTopic projectTopic) {
        this.projectTopic = projectTopic;
    }

    public Supervisor getSv() {
        return sv;
    }

    public void setSv(Supervisor sv) {
        this.sv = sv;
    }

    

    public ProjectService getPrjSrv() {
        return prjSrv;
    }

    public void setPrjSrv(ProjectService prjSrv) {
        this.prjSrv = prjSrv;
    }

    public RetrieveID getRi() {
        return ri;
    }

    public void setRi(RetrieveID ri) {
        this.ri = ri;
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
    
        
    
    
    //register project
    public String registerProj() {
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        Getloginid();
        prjSrv.registerProject(projtitle, projdecr, requiredskill, projspv, projtopic);
        LOGGER.log(Level.INFO, "Project {0} is added by {1}", new Object[]{projtitle, projspv});
        
        // log
        pls.insertLog(std, formattedDate, "ProjectRegistrationSpv", "Register New Project");
        
        return "supervisorPage.xhtml";
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
        return topicList;
    }
    
    //Retrieve login ID
    public void Getloginid() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        sv = ri.getspvId(std);
        projspv = sv.getId();
        
    }   
    
}
