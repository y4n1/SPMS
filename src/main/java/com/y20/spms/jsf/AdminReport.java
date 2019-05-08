/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.AdminReportService;
import com.y20.spms.ejb.ProjectCancellationService;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.io.Serializable;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author Yan's
 */
@Named("AdminReport")
@RequestScoped
public class AdminReport implements Serializable{
    
    private Long studentid;
    private Long spvid;
    private Project pt = new Project();
    private Student st = new Student();
    private Supervisor sv = new Supervisor();
    
    @EJB
    AdminReportService ars;
    
    private static final Logger LOGGER = Logger.getLogger(AdminReport.class.getName());
    // Getter & Setter

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public Long getSpvid() {
        return spvid;
    }

    public void setSpvid(Long spvid) {
        this.spvid = spvid;
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

    public AdminReportService getArs() {
        return ars;
    }

    public void setArs(AdminReportService ars) {
        this.ars = ars;
    }
    
    // end getter & setter
    
    // Retrieve Project by spv
     public static class projspv{
	//public String Stuname;
        public String Spvname;
	public String Title;
        public String Status;
        public String prjDescription;
        public String prjComment;
		
	public projspv(String SpvName, String Title, String Status, String Description, String Comment){
		//this.Stuname = StudentName;
                this.Spvname = SpvName;
		this.Title = Title;
                this.Status = Status;
                this.prjDescription = Description;
                this.prjComment = Comment;  
	}

        
//	public String getStuname() {
//            return Stuname;
//        }

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

        public String getSpvname() {
            return Spvname;
        }
    }
    
    // fill up project title list
    public projspv[] fillTitleList() {
        projspv[] projlist;
       
   //     String Stuname;
        String Spvname;
	String Title;
        String Status;
        String prjDescription;
        String prjComment;
        
        
        int i = 0;
        int ttlrec;
        
        if (Long.valueOf(0).equals(spvid)){

            
            List<Project> prjtitleall = ars.getallspv();
            projlist = new projspv[prjtitleall.size()];
            ttlrec = prjtitleall.size();
            
            while (i < ttlrec) {
                
//                if (isNull(prjtitleall.get(i).getStudent().getId())){
//                    Stuname = "";
//                }else{
//                    Stuname = prjtitleall.get(i).getStudent().getFname() + " " + prjtitleall.get(i).getStudent().getLname();
//                }
                Spvname = prjtitleall.get(i).getSupervisor().getFname() + " " + prjtitleall.get(i).getSupervisor().getLname();
                if (isNull(prjtitleall.get(i).getId())){
                    Title = "";
                    Status = "";
                    prjDescription = "";
                    prjComment = "";
                } else {
                    Title = prjtitleall.get(i).getTitle();
                    Status = String.valueOf(prjtitleall.get(i).getProjectStatus());
                    prjDescription = prjtitleall.get(i).getDescription();
                    prjComment = prjtitleall.get(i).getReason();
                }    
                projlist[i] = new projspv(Spvname, Title, Status, prjDescription, prjDescription); 
            
            i = i+1;
        }    
            
        }else {
            List<Project> prjtitlespv = ars.getprojectbyspv(spvid);
            projlist = new projspv[prjtitlespv.size()];
            ttlrec = prjtitlespv.size();
            
            while (i < ttlrec) {
            
//                if (isNull(prjtitlespv.get(i).getStudent().getId())){
//                    Stuname = "";
//                }else{
//                    Stuname = prjtitlespv.get(i).getStudent().getFname() + " " + prjtitlespv.get(i).getStudent().getLname();
//                }
                Spvname = prjtitlespv.get(i).getSupervisor().getFname() + " " + prjtitlespv.get(i).getSupervisor().getLname();
                Title = prjtitlespv.get(i).getTitle();
                Status = String.valueOf(prjtitlespv.get(i).getProjectStatus());
                prjDescription = prjtitlespv.get(i).getDescription();
                prjComment = prjtitlespv.get(i).getReason();
                projlist[i] = new projspv(Spvname, Title, Status, prjDescription, prjDescription); 
            
            i = i+1;
            }
        }   
        return projlist;
    }
    
    // by Student
     public static class projstu{
	public String Stuname;
        public String Spvname;
	public String Title;
        public String Status;
        public String prjDescription;
        public String prjComment;
		
	public projstu(String StudentName, String SpvName, String Title, String Status, String Description, String Comment){
		this.Stuname = StudentName;
                this.Spvname = SpvName;
		this.Title = Title;
                this.Status = Status;
                this.prjDescription = Description;
                this.prjComment = Comment;  
	}

        
	public String getStuname() {
            return Stuname;
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

        public String getSpvname() {
            return Spvname;
        }
    }
    
    // fill up project title list
    public projstu[] fillstudentList() {
        projstu[] stulist;
       
        String Stuname;
        String Spvname;
	String Title;
        String Status;
        String prjDescription;
        String prjComment;
        
        
        int i = 0;
        int ttlrec;
        
        if (Long.valueOf(0).equals(studentid)){

            
            List<Project> prjstudentall = ars.getallstudent();
            stulist = new projstu[prjstudentall.size()];
            ttlrec = prjstudentall.size();
            
            while (i < ttlrec) {
                
                if (isNull(prjstudentall.get(i).getStudent().getId())){
                    Stuname = "";
                }else{
                    Stuname = prjstudentall.get(i).getStudent().getFname() + " " + prjstudentall.get(i).getStudent().getLname();
                }
                Spvname = prjstudentall.get(i).getSupervisor().getFname() + " " + prjstudentall.get(i).getSupervisor().getLname();
                Title = prjstudentall.get(i).getTitle();
                Status = String.valueOf(prjstudentall.get(i).getProjectStatus());
                prjDescription = prjstudentall.get(i).getDescription();
                prjComment = prjstudentall.get(i).getReason();
                stulist[i] = new projstu(Stuname, Spvname, Title, Status, prjDescription, prjDescription); 
            
            i = i+1;
        }    
            
        }else {
            List<Project> prjstudent = ars.getprojectbystudent(studentid);
            stulist = new projstu[prjstudent.size()];
            ttlrec = prjstudent.size();
            
            while (i < ttlrec) {
            
                Stuname = prjstudent.get(i).getStudent().getFname() + " " + prjstudent.get(i).getStudent().getLname();
                Spvname = prjstudent.get(i).getSupervisor().getFname() + " " + prjstudent.get(i).getSupervisor().getLname();
                Title = prjstudent.get(i).getTitle();
                Status = String.valueOf(prjstudent.get(i).getProjectStatus());
                prjDescription = prjstudent.get(i).getDescription();
                prjComment = prjstudent.get(i).getReason();
                stulist[i] = new projstu(Stuname, Spvname, Title, Status, prjDescription, prjDescription); 
            
            i = i+1;
            }
        }   
        return stulist;
    }
    
    
   
    //spv
    public String submitbuttonspv(){
        
        LOGGER.info("User Admin Generate Supervisor Report");
        fillTitleList();
        return "ASpvReport";
        
    }
    
    //student
    public String submitbuttonstu(){
        
        LOGGER.info("User Admin Generate Student Report");
        fillstudentList();
        return "AStuReport";
        
    }
    
    // Fill up Supervisor List
    public static class Spvlist{
	public Long ID;
	public String Name;
		
	public Spvlist(Long ID, String Name){
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
    
    // fill up project title list
    public Spvlist[] fillspvList() {
        
        Spvlist[] spvList;
        List<Supervisor> spvall = ars.getspvall();
        
        int j = 0;
        Long ID;
        String Name;
        
        spvList = new Spvlist[spvall.size()];
                       
        while (j < spvall.size()) {
            
            ID = spvall.get(j).getId();
            Name = spvall.get(j).getFname() + " " + spvall.get(j).getLname();
            spvList[j] = new Spvlist(ID, Name);           
            j = j+1;

        }    
        return spvList;
    }
    
    // Fill Up Student list
    // Fill up Supervisor List
    public static class Stulist{
	public Long ID;
	public String Name;
		
	public Stulist(Long ID, String Name){
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
    
    // fill up project title list
    public Stulist[] fillstuList() {
        
        Stulist[] stuList;
        List<Student> stuall = ars.getstuall();
        
        int j = 0;
        Long ID;
        String Name;
        
        stuList = new Stulist[stuall.size()];
                       
        while (j < stuall.size()) {
            
            ID = stuall.get(j).getId();
            Name = stuall.get(j).getFname() + " " + stuall.get(j).getLname();
            stuList[j] = new Stulist(ID, Name);           
            j = j+1;

        }    
        return stuList;
    }
    
    
}

