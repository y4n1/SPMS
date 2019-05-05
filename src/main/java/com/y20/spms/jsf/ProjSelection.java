/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjectService;
import com.y20.spms.ejb.ProjSelectionService;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yan's
 */

@Named("ProjSelection")
@RequestScoped
public class ProjSelection {
    
    private Long projID;
    private String projdecr;
    private String requiredskill;
    private Long projspv;
    private Long projstd;
    private Project pt = new Project();
    private Student st = new Student();
    //private HtmlSelectOneMenu selectedPage = new HtmlSelectOneMenu();
    

    @EJB
    ProjSelectionService pss;
    
    @EJB
    ProjectService prjSrv;
    
    public ProjSelection() {

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

    public Long getProjspv() {
        return projspv;
    }

    public void setProjspv(Long projspv) {
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
    
    public projTitle[] fillTitleList() {
        Long spv;
        spv = getProjspv();
        System.out.println(spv);
        List<Project> prjtitlelist = pss.findTitlebyspv(spv);
        int i = 0;
        Long ID;
        String Title;
        projTitle[] titleList;
        
        titleList = new projTitle[prjtitlelist.size()];
                
        while (i < prjtitlelist.size()) {
            
            ID = prjtitlelist.get(i).getId();
            Title = prjtitlelist.get(i).getTitle();
            titleList[i] = new projTitle(ID,Title+ID);
          //  System.out.println(ID);
            
            i = i+1;
        }    
        return titleList;
    }
    
    
    public static class projsupervisor{
	public Long ID;
	public String Name;
		
	public projsupervisor(Long ID, String Name){
		this.ID = ID;
		this.Name = Name;
	}

        public Long getID() {
            return ID;
        }

        public String getName() {
            return Name;
        }
		
	
	}
		
          
    public projsupervisor[] fillsupervisorList() {
        
        List<Supervisor> prjspvlist = pss.findAllSupervisor();
        int x = 0;
        String firstname;
        String lastname;
        String fullname;
        Long ID;
        projsupervisor[] SupervisorList; 

        SupervisorList = new projsupervisor[prjspvlist.size()];
        
        while (x < prjspvlist.size()) {
                firstname = prjspvlist.get(x).getFname();
                lastname = prjspvlist.get(x).getLname();
                fullname = firstname + " " + lastname;
                ID = prjspvlist.get(x).getId();
                SupervisorList[x] = new projsupervisor(ID,fullname);   
            x = x+1;
        } 
 
        return SupervisorList;
    }
    
    // Fill up project details base on selected title
    public void Getprojectinfo() {
        Long titleid;
        titleid = getProjID();
        pt = pss.findDetail(titleid);
        projdecr = pt.getDescription();
        requiredskill = pt.getRequiredSkills();
        
    }
    
    public void Getloginid() {
        String std;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        st = prjSrv.getstuId(std);
        projstd = st.getId();
        
    }   
    
    // Update Project
    public String updateProj() {
        
        pss.updateProject(projID, projspv, projstd);
        return "studentPage";
    }
    
    
    public void onspvchange(ValueChangeEvent e){
        
        String newvalue;       
        newvalue = e.getNewValue().toString();
        projspv = Long.parseLong(newvalue);
        System.out.println(newvalue);
    }
    
    public void ontitlechange(ValueChangeEvent event){
        String selecttitle;
               
        selecttitle = event.getNewValue().toString();
        projID = Long.parseLong(selecttitle);
        this.setProjID(projID);
        System.out.println(projID);
        Getprojectinfo();
        
    }
    
    
}
