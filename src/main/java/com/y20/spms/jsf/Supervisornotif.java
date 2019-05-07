/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.RetrieveID;
import com.y20.spms.ejb.SupervisorNotifService;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Supervisor;
import java.io.Serializable;
import java.util.List;
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
@Named("Supervisornotif")
@RequestScoped
public class Supervisornotif implements Serializable{
    
    Integer total;
    Long spvid;
    private Supervisor sv = new Supervisor();
    
    @EJB
    SupervisorNotifService sns;
    
    @EJB
    RetrieveID ri;
    
    public Supervisornotif() {
        
    }
   
    // getter & setter
    public Integer getTotal() {    
        return total;
    }
    
    public void setTotal(Integer total) {   
        this.total = total;
    }

    public SupervisorNotifService getSns() {
        return sns;
    }

    public void setSns(SupervisorNotifService sns) {
        this.sns = sns;
    }

    public RetrieveID getRi() {
        return ri;
    }

    public void setRi(RetrieveID ri) {
        this.ri = ri;
    }

    public Long getSpvid() {
        return spvid;
    }

    public void setSpvid(Long spvid) {
        this.spvid = spvid;
    }

    public Supervisor getSv() {
        return sv;
    }

    public void setSv(Supervisor sv) {
        this.sv = sv;
    }
    
    // End getter & setter
    
    //Retrieve login ID
    public void Getloginid() {
        String std;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        std = request.getRemoteUser();
        sv = ri.getspvId(std);
        spvid = sv.getId();
        
    }   
    
    // display notif
    @PostConstruct
    public void Getnotif() {
        
        Getloginid();
        List<Project> prjtitlelist = sns.getproposebyspv(spvid);
        this.setTotal(prjtitlelist.size());
        
    }   
    
}
