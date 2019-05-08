/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.ejb.ProjSelectionService;
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
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

/**
 *
 * @author Yan's
 */

@Named("ProjSelection2")
//@RequestScoped
@ViewScoped
public class ProjSelection2 implements Serializable {
    
    private Long projID;
    private String projdecr;
    private String requiredskill;
    private String projspv;
    private Long projstd;
    private String std;
    private Project pt = new Project();
    private Student st = new Student();
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    

    @EJB
    ProjSelectionService pss;
    
    @EJB
    ProjectService prjSrv;
    
    @EJB
    RetrieveID ri;
    
    @EJB
    ProjLoggingService pls;
    
    private static final Logger LOGGER = Logger.getLogger(ProjSelection2.class.getName());
    
    public ProjSelection2() {

    }

    public Long getProjID() {
        return projID;
    }

    public void setProjID(Long projID) {
        this.projID = projID;
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

    public String getProjspv() {
        return projspv;
    }

    public void setProjspv(String projspv) {
        this.projspv = projspv;
    }

    public Long getProjstd() {
        return projstd;
    }

    public void setProjstd(Long projstd) {
        this.projstd = projstd;
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

    public ProjSelectionService getPss() {
        return pss;
    }

    public void setPss(ProjSelectionService pss) {
        this.pss = pss;
    }

    public ProjectService getPrjSrv() {
        return prjSrv;
    }

    public void setPrjSrv(ProjectService prjSrv) {
        this.prjSrv = prjSrv;
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
        
        projTitle[] titleList;
        String spv;
        spv = getProjspv();
        List<Project> prjtitlelist = pss.findAllTitle();
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
    
    // Fill up project details base on selected title
    public void Getprojectinfo() {
        Long titleid;
        titleid = getProjID();
        pt = pss.findDetail(titleid);
        this.setProjdecr(pt.getDescription());
        this.setRequiredskill(pt.getRequiredSkills());
        this.setProjspv(pt.getSupervisor().getFname() + " " + pt.getSupervisor().getLname());
        //System.out.println(projdecr);
    }
    
    // Retrieve login ID
    public void Getloginid() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        st = ri.getstudentID(std);
        projstd = st.getId();
        
    }   
    
    // Update Project
    public String updateProj() {
        int check;
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        Getloginid();
        
        LOGGER.log(Level.INFO, "User {0}Is applying for project {1}", new Object[]{projstd, projID});
        
        // log
        pls.insertLog(std, formattedDate, "ProjSelection2", "Request Project");
        
        check = pss.checkrecord(projstd);
        
        if (check == 0){
        //System.out.println(projstd);
            pss.updateProject(projID, projstd);
            return "studentPage";
        }
        else {
            return "ProjError.xhtml";
        }
    }
    
    public void ontitlechange(ValueChangeEvent event){
        String selecttitle;
               
        selecttitle = event.getNewValue().toString();
        projID = Long.parseLong(selecttitle);
        this.setProjID(projID);
        System.out.println("proj" + projID);
       // Getprojectinfo();
        
    }
    
    public void submitbutton(){
        Getprojectinfo();
    }
}
