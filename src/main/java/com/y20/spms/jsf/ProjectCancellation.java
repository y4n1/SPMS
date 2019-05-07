/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjSelectionService;
import com.y20.spms.ejb.ProjectCancellationService;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author Yan's
 */

@Named("ProjectCancellation")
@RequestScoped
public class ProjectCancellation {
    
    private String studentname;
    private Long projID;
    private Long studentid;
    private String comment;
    private Project pt = new Project();
    private Student st = new Student();
    
    @EJB
    ProjectCancellationService pcs;
       
    private static final Logger LOGGER = Logger.getLogger(ProjectCancellation.class.getName());
    
    public ProjectCancellation() {
        
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public ProjectCancellationService getPcs() {
        return pcs;
    }

    public void setPcs(ProjectCancellationService pcs) {
        this.pcs = pcs;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }
    
    
    // Get project title   
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
        //System.out.println(projstd);
        //Getloginid();
        projTitle[] titleList;
        List<Project> prjtitlelist = pcs.findAllTitle();
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
        
    // get detail project    
    public void Getprojectdetail() {
        Long titleid;
        titleid = getProjID();
        pt = pcs.findprojdecr(projID);
        this.setStudentname(pt.getStudent().getFname() + " " + pt.getStudent().getLname());
        this.setComment(pt.getReason());
        this.setStudentid(pt.getStudent().getId());

    }
       
    //onchange event when selecting spv
    public void ontitlechange(ValueChangeEvent e){
        
        String newvalue;       
        newvalue = e.getNewValue().toString();
        projID = Long.parseLong(newvalue);
        System.out.println(newvalue);
    }
    
    public void submitbutton (){
        Getprojectdetail();
    }
    
    public String updateProj() {
        
        //Getloginid();
        System.out.println(projID);
        pcs.updateProject(projID);
        LOGGER.info("Data updated");
        //pcs.insertProject(projID, studentid);
        //LOGGER.info("Data inserted");
        return "studentPage";
    }
    
}
