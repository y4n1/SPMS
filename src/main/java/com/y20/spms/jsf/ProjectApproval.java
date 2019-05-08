/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjApprovalService;
import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.ejb.RetrieveID;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yan's
 */

@Named("ProjectApproval")
@RequestScoped
public class ProjectApproval implements Serializable{
    
    private String studentname;
    private Long projID;
    private Long studentid;
    private String comment;
    private String status;
    private String Description;
    private Long spvid;
    private String std;
    private Project pt = new Project();
    private Student st = new Student();
    private Supervisor sv = new Supervisor();
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    
    @EJB
    ProjApprovalService pas;
    
    @EJB
    RetrieveID ri;
    
    @EJB
    ProjLoggingService pls;
    
    private static final Logger LOGGER = Logger.getLogger(ProjectApproval.class.getName());
    
    public ProjectApproval() {
        
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public Long getProjID() {
        return projID;
    }

    public void setProjID(Long projID) {
        this.projID = projID;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Supervisor getSv() {
        return sv;
    }

    public void setSv(Supervisor sv) {
        this.sv = sv;
    }

    public ProjApprovalService getPas() {
        return pas;
    }

    public void setPas(ProjApprovalService pas) {
        this.pas = pas;
    }

    public RetrieveID getRi() {
        return ri;
    }

    public void setRi(RetrieveID ri) {
        this.ri = ri;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Long getSpvid() {
        return spvid;
    }

    public void setSpvid(Long spvid) {
        this.spvid = spvid;
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
    
    
    
    
    
    
    // project title
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
        Getloginid();
        projTitle[] titleList;
        List<Project> prjtitlelist = pas.findTitlebySpv(spvid);
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

    //Retrieve login ID
    public void Getloginid() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        sv = ri.getspvId(std);
        spvid = sv.getId();
        
    }   
    
    // get detail project    
    public void Getprojectdetail() {

        pt = pas.findprojdecr(projID);
        
        this.setStudentname(pt.getStudent().getFname() + " " + pt.getStudent().getLname());
        this.setStudentid(pt.getStudent().getId());
        this.setDescription(pt.getDescription());
        this.setComment(comment);
    }
   
    // Update project
    public String updateProj() {
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        Getloginid();
        pas.updateProject(projID, status, comment);
        LOGGER.info("Data updated");
        
        // log
        pls.insertLog(std, formattedDate, "ProjApproval", "Update Project");
        return "supervisorPage";
    }
    
    
    //onchange event when selecting spv
    public void ontitlechange(ValueChangeEvent e){
        
        String newvalue;       
        newvalue = e.getNewValue().toString();
        projID = Long.parseLong(newvalue);
        System.out.println(newvalue);
    }

    public void searchdetail (){
        Getprojectdetail();
    }
    
}
