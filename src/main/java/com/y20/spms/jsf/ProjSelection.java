/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjSelectionService;
import com.y20.spms.ejb.ProjectService;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yan's
 */

@Named("ProjSelection")
@RequestScoped
public class ProjSelection implements Serializable{
    
    private Long projtitle;
    private String projdecr;
    private String requiredskill;
    private Long projspv;
    private Long projstd;
    private Project pt = new Project();
    private Student st = new Student();
    //private List[] ttlList;
    
    
    @EJB
    ProjSelectionService pss;
    
    @EJB
    ProjectService prjSrv;
    
    public ProjSelection() {

    }

    public Long getProjtitle() {
        return projtitle;
    }

    public void setProjtitle(Long projtitle) {
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

//    public void setPrjtitlelist(List<Project> prjtitlelist) {
//        this.prjtitlelist = prjtitlelist;
//    }

    public ProjSelectionService getPss() {
        return pss;
    }

    public void setPss(ProjSelectionService pss) {
        this.pss = pss;
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

    
    
//    public List<Project> fillSpvList() {
//        spvlist = pss.findAllSupervisor();  
//       // int x = 0;
//        
//        List list = new ArrayList();
//        
//        for( Project p:spvlist ){
//            list.add(p.getSupervisor());
//      }
//               
//        return list;
//    }
    
    public static class projTitle{
	public Long ID;
	public String Title;
		
	public projTitle(Long ID, String Title){
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
    
    public projTitle[] fillTitleList() {
        
        List<Project> prjtitlelist = pss.findTitlebyspv(projspv);
        int i = 0;
        Long ID;
        String Title;
        projTitle[] titleList;
        
        titleList = new projTitle[prjtitlelist.size()];
                
        while (i < prjtitlelist.size()) {
            
            ID = prjtitlelist.get(i).getId();
            Title = prjtitlelist.get(i).getTitle();
            titleList[i] = new projTitle(ID,Title);
            
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
        
        List<Project> prjspvlist = pss.findAllSupervisor("AVAILABLE");
        int x = 0;
        String firstname;
        String lastname;
        String fullname;
        Long ID;
        projsupervisor[] SupervisorList;
        
        SupervisorList = new projsupervisor[prjspvlist.size()];
        System.out.print(prjspvlist.size());
        
        while (x < prjspvlist.size()) {
            
            firstname = prjspvlist.get(x).getSupervisor().getFname();
            lastname = prjspvlist.get(x).getSupervisor().getLname();
            fullname = firstname + " " + lastname;
            ID = prjspvlist.get(x).getSupervisor().getId();
            //ID = prjspvlist.get(x).getId();
            //fullname = prjspvlist.get(x).getDescription();
            SupervisorList[x] = new projsupervisor(ID,fullname);
            
            x = x+1;
        } 
 
        return SupervisorList;
    }
    
    // Fill up project details base on selected title
    public void Getprojectinfo() {
        
        pt = pss.findDetail(projtitle);
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
        
        pss.updateProject(projtitle, projspv, projstd);
        return "studentPage";
    }
    
    public void onspvchange(){
        
        fillsupervisorList();
        
    }
    
}
