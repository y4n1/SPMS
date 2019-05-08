/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.ejb.RetrieveID;
import com.y20.spms.ejb.SpvReportService;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
@Named("SpvReport")
@RequestScoped
public class SpvReport implements Serializable{
    
    private Long studentid;
    private Long spvid;
    private String std;
    private Project pt = new Project();
    private Student st = new Student();
    private Supervisor sv = new Supervisor();
    Date date = new Date();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
            
    @EJB
    SpvReportService srs;
    
    @EJB
    RetrieveID ri;
    
    @EJB
    ProjLoggingService pls;
            
    private static final Logger LOGGER = Logger.getLogger(SpvReport.class.getName());
    
    public SpvReport() {
        
    }
    
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

    public SpvReportService getSrs() {
        return srs;
    }

    public void setSrs(SpvReportService srs) {
        this.srs = srs;
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

   
    
    
    
    // end getter & setter
    
    //Retrieve login ID
    @PostConstruct 
    public void Getloginid() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        sv = ri.getspvId(std);
        this.setSpvid(sv.getId());
        LOGGER.log(Level.INFO, "{0} is extracting supervisor report", std);
    }  
    
    // Retrieve Project
     public static class projTitle{
	public String stuname;
	public String Title;
        public String Status;
        public String prjDescription;
        public String prjComment;
		
	public projTitle(String StudentName, String Title, String Status, String Description, String Comment){
		this.stuname = StudentName;
		this.Title = Title;
                this.Status = Status;
                this.prjDescription = Description;
                this.prjComment = Comment;  
	}

        
	
        public String getStuname() {
            return stuname;
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
    public projTitle[] fillTitleList() {
        projTitle[] projlist;
       
        String stuname;
	String Title;
        String Status;
        String prjDescription;
        String prjComment;
        
        // assign spv id
        Getloginid();
        
        //
        
        int i = 0;
        int ttlrec;
        
        if (Long.valueOf(0).equals(studentid)){
            
            List<Project> prjtitleall = srs.getproposeallstudent(spvid);
            projlist = new projTitle[prjtitleall.size()];
            ttlrec = prjtitleall.size();
            
            while (i < ttlrec) {
                
                if (prjtitleall.get(i).getStudent() == null){
                    System.out.println("check id");
                    
                    stuname = "";
                }else{
                    stuname = prjtitleall.get(i).getStudent().getFname() + " " + prjtitleall.get(i).getStudent().getLname();
                }
                
                Title = prjtitleall.get(i).getTitle();
                Status = String.valueOf(prjtitleall.get(i).getProjectStatus());
                prjDescription = prjtitleall.get(i).getDescription();
                prjComment = prjtitleall.get(i).getReason();
                projlist[i] = new projTitle(stuname, Title, Status, prjDescription, prjDescription); 
            
            i = i+1;
        }    
            
        }else {
            List<Project> prjtitlestudent = srs.getproposebystudent(spvid, studentid);
            projlist = new projTitle[prjtitlestudent.size()];
            ttlrec = prjtitlestudent.size();
            
            while (i < ttlrec) {
            
                stuname = prjtitlestudent.get(i).getStudent().getFname() + " " + prjtitlestudent.get(i).getStudent().getLname();
                Title = prjtitlestudent.get(i).getTitle();
                Status = String.valueOf(prjtitlestudent.get(i).getProjectStatus());
                prjDescription = prjtitlestudent.get(i).getDescription();
                prjComment = prjtitlestudent.get(i).getReason();
                projlist[i] = new projTitle(stuname, Title, Status, prjDescription, prjDescription); 
            
            i = i+1;
            }
        }   
        return projlist;
    }
   
    
    // Select Student for Front Page
    
    public static class projStudent{
	public Long ID;
	public String Name;
		
	public projStudent(Long ID, String Name){
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
    public projStudent[] fillStudentList() {
        
        projStudent[] studentList;
        List<Student> prjstudentlist = srs.findAllStudent(spvid);
        
        int j = 0;
        Long ID;
        String Name;
        
        studentList = new projStudent[prjstudentlist.size()];
                       
        while (j < prjstudentlist.size()) {
            
            ID = prjstudentlist.get(j).getId();
            Name = prjstudentlist.get(j).getFname() + " " + prjstudentlist.get(j).getLname();
            studentList[j] = new projStudent(ID, Name);           
            j = j+1;

        }    
        return studentList;
    }
    
    // Set selection value
    public void ontitlechange(ValueChangeEvent event){
        String selectstudent;

        selectstudent = event.getNewValue().toString();
        studentid = Long.parseLong(selectstudent);
        this.setStudentid(studentid);
        System.out.println("student" + studentid);
    }
    
    public String submitbutton(){
        
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(date);
        
        fillTitleList();
        
        // log
        pls.insertLog(std, formattedDate, "SpvReport", "Extract Report");
        
        return "supervisorReport";
        
    }
    
}
