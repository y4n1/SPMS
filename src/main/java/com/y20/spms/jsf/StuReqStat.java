/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.RetrieveID;
import com.y20.spms.ejb.StuReqStatService;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import java.io.Serializable;
import java.util.List;
import static java.util.Objects.isNull;
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

@Named("StuReqStat")
@RequestScoped
public class StuReqStat implements Serializable{
    
    private Long studentid;
    private Project pt = new Project();
    private Student st = new Student();
    
    @EJB
    StuReqStatService srss;
    
    @EJB
    RetrieveID ri;
    
    private static final Logger LOGGER = Logger.getLogger(StuReqStat.class.getName());
    
    public StuReqStat() {
        
    }
    
    // Getter & Setter

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
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

    public StuReqStatService getSrss() {
        return srss;
    }

    public void setSrss(StuReqStatService srss) {
        this.srss = srss;
    }

    public RetrieveID getRi() {
        return ri;
    }

    public void setRi(RetrieveID ri) {
        this.ri = ri;
    }
    
    // End getter & setter
    
    //Retrieve login ID
    @PostConstruct 
    public void Getloginid() {
        String std;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        st = ri.getstudentID(std);
        this.setStudentid(st.getId());
        LOGGER.log(Level.INFO, "Student {0} is accessing status report", std);
    }  
    
    
    // Get the projectlist
    // Retrieve Project
     public static class projTitle{
	public String Spvname;
	public String Title;
        public String Status;
        public String prjDescription;
        public String prjComment;
		
	public projTitle(String SpvName, String Title, String Status, String Description, String Comment){
		this.Spvname = SpvName;
		this.Title = Title;
                this.Status = Status;
                this.prjDescription = Description;
                this.prjComment = Comment;  
	}

        public String getSpvname() {
            return Spvname;
        }
        

        public String getTitle() {
            return Title;
        }

        public String getStatus() {
            return Status;
        }

        public String getPrjDescription() {
            return prjDescription;
        }

        public String getPrjComment() {
            return prjComment;
        }
			
        // end
    }
    
    // fill up project title list
    public projTitle[] fillProjectList() {
        projTitle[] projlist;
       
	String Title;
        String SpvName;
        String Status;
        String prjDescription;
        String prjComment;
        
        // assign student id
        Getloginid();
       // System.out.println(studentid);
        
        //
        
        int i = 0;
        int ttlrec;
        
        List<Project> prjtitlestudent = srss.getprojectstatus(studentid);
        projlist = new projTitle[prjtitlestudent.size()];
        ttlrec = prjtitlestudent.size();
        
        while (i < ttlrec) {
            
            SpvName = prjtitlestudent.get(i).getSupervisor().getFname() + " " + prjtitlestudent.get(i).getSupervisor().getLname();
            Title = prjtitlestudent.get(i).getTitle();
            Status = String.valueOf(prjtitlestudent.get(i).getProjectStatus());
            prjDescription = prjtitlestudent.get(i).getDescription();
            prjComment = prjtitlestudent.get(i).getReason();
            projlist[i] = new projTitle(SpvName, Title, Status, prjDescription, prjDescription); 
            
            i = i+1;
            
        }   
        return projlist;
    }
    
    public String projectStatus(){
        
        fillProjectList();
        return "StuRequestStatus";
        
    }
    
    
}
