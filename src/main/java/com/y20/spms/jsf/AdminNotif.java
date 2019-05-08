/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.AdminNotifService;
import com.y20.spms.entity.Project;
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
@Named("AdminNotif")
@RequestScoped
public class AdminNotif implements Serializable{
    
    Integer total;
    private Project prj = new Project();
    
    @EJB
    AdminNotifService ans;
    
    public AdminNotif() {
        
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Project getPrj() {
        return prj;
    }

    public void setPrj(Project prj) {
        this.prj = prj;
    }

    public AdminNotifService getAns() {
        return ans;
    }

    public void setAns(AdminNotifService ans) {
        this.ans = ans;
    }

        
    // end getter & setter
    
    // display notif
    @PostConstruct
    public void Getnotif() {
        
        List<Project> prjtitlelist = ans.getcancellation();
        this.setTotal(prjtitlelist.size());
        
    }   
    
}
