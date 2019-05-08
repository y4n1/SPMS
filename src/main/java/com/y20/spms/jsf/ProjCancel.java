/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjCancelService;
import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.ejb.ProjectCancellationService;
import com.y20.spms.ejb.ProjectService;
import com.y20.spms.ejb.RetrieveID;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
@Named("ProjCancel")
@RequestScoped
public class ProjCancel implements Serializable{
    
    private Long projID;
    private Long projstd;
    private String comment;
    private Project pt = new Project();
    private Student st = new Student();
    private String std;
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    
    @EJB
    ProjCancelService pcs;
    
    @EJB
    ProjectService prjSrv;
    
    @EJB
    RetrieveID ri;
    
    @EJB
    ProjLoggingService pls;
    
    private static final Logger LOGGER = Logger.getLogger(ProjCancel.class.getName());
    
    public ProjCancel() {

    }

    public Long getProjID() {
        return projID;
    }

    public void setProjID(Long projID) {
        this.projID = projID;
    }

    public Project getPt() {
        return pt;
    }

    public void setPt(Project pt) {
        this.pt = pt;
    }

    public Student getSt() {
        return st;
    }

    public void setSt(Student st) {
        this.st = st;
    }

    public ProjectService getPrjSrv() {
        return prjSrv;
    }

    public void setPrjSrv(ProjectService prjSrv) {
        this.prjSrv = prjSrv;
    }
    
    public ProjCancelService getPcs() {
        return pcs;
    }

    public void setPcs(ProjCancelService pcs) {
        this.pcs = pcs;
    }

    public Long getProjstd() {
        return projstd;
    }

    public void setProjstd(Long projstd) {
        this.projstd = projstd;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
    
    
    
        
    public static class projTitle{
	public Long ID;
	public String Title;
		
	public projTitle(Long ID, String Title){
		this.ID = ID;
		this.Title = Title;
	}

        public Long getID() {
            return ID;
        }

        public String getTitle() {
            return Title;
        }
		
    }
    
    // fill up project title list
    public projTitle[] fillTitleList() {
        System.out.println(projstd);
        //Getloginid();
        projTitle[] titleList;
        List<Project> prjtitlelist = pcs.findAllTitle(projstd);
        int i = 0;
        Long ID;
        String Title;
        
        titleList = new projTitle[prjtitlelist.size()];
                
        while (i < prjtitlelist.size()) {
            
            ID = prjtitlelist.get(i).getId();
            Title = prjtitlelist.get(i).getTitle();
            titleList[i] = new projTitle(ID,Title);           
            i = i+1;
        }    
        return titleList;
    }
    
    // Retrieve login ID
    @PostConstruct
    public void Getloginid() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        System.out.println(std);
        st = ri.getstudentID(std);
        projstd = st.getId();
        
    }   
    
    // Update Project
    public String updateProj() {
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        Getloginid();
        LOGGER.log(Level.INFO, "Student {0}is requesting for cancellation of Project {1}", new Object[]{projstd, projID});
        pcs.updateProject(projID, projstd, comment);
        pls.insertLog(std, formattedDate, "ProjCancel", "Request Cancellation");
        return "studentPage";
    }
    
    
        
}
