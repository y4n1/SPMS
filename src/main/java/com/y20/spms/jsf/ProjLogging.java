/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjLoggingService;
import com.y20.spms.entity.Logging;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Yan's
 */
@Named("ProjLogging")
@RequestScoped
public class ProjLogging implements Serializable{
    
    private Logging log = new Logging();
    
    @EJB
    ProjLoggingService pls;
    
    public ProjLogging() {
        
    }
    
    public static class projLogging{
	public String username;
	public String dDate;
        public String form;
        public String activity;
		
	public projLogging(String username, String dDate, String form, String activity){
		this.username = username;
		this.dDate = dDate;
                this.form = form;
                this.activity = activity;
	}

        

        // end getter & setter

        public String getUsername() {
            return username;
        }

        public String getdDate() {
            return dDate;
        }

        public String getForm() {
            return form;
        }

        public String getActivity() {
            return activity;
        }
       
		
    }
    
    // fill up project title list
    public projLogging[] FillLogReport() {
        
        projLogging[] logList;
        List<Logging> prjloglist = pls.extractLog();
        
        int j = 0;
        String username;
	String dDate;
        String form;
        String activity;
        
        logList = new projLogging[prjloglist.size()];
                       
        while (j < prjloglist.size()) {
            
            username = prjloglist.get(j).getUsername();
            dDate = prjloglist.get(j).getDatetime();
            form = prjloglist.get(j).getForm();
            activity = prjloglist.get(j).getActivity();
            
            logList[j] = new projLogging(username, dDate, form, activity);           
            j = j+1;

        }    
        return logList;
    }
    
    public String extractReport(){
        
        FillLogReport();
        return "LogReport";
        
    }
    
}
