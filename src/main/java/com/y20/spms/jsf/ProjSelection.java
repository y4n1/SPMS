/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.jsf;

import com.y20.spms.ejb.ProjSelectionService;
import com.y20.spms.entity.Project;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Yan's
 */

@Named("ProjSelection")
@RequestScoped
public class ProjSelection implements Serializable{
    
    private String projtitle;
    private String projdecr;
    private String requiredskill;
    private String projspv;
    private String projtopic;
    private List<Project> spvlist = new ArrayList<>();
    private List<Project> prjtitlelist = new ArrayList<>();
    
    
    @EJB
    ProjSelectionService pss;
    
    public ProjSelection(String projtitle, String projdecr, String requiredskill) {
        this.projtitle = projtitle;
        this.projdecr = projdecr;
        this.requiredskill = requiredskill;
    }

    public String getProjtitle() {
        return projtitle;
    }

    public void setProjtitle(String projtitle) {
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

    public String getProjspv() {
        return projspv;
    }

    public void setProjspv(String projspv) {
        this.projspv = projspv;
    }

    
    public String getProjtopic() {
        return projtopic;
    }

    public void setProjtopic(String projtopic) {
        this.projtopic = projtopic;
    }

    public List<Project> getSpvlist() {
        return spvlist;
    }

    public void setSpvlist(List<Project> spvlist) {
        this.spvlist = spvlist;
    }

    public List<Project> getPrjtitlelist() {
        return prjtitlelist;
    }

    public void setPrjtitlelist(List<Project> prjtitlelist) {
        this.prjtitlelist = prjtitlelist;
    }

    public ProjSelectionService getPss() {
        return pss;
    }

    public void setPss(ProjSelectionService pss) {
        this.pss = pss;
    }
    
    public List<Project> fillSpvList() {
        spvlist = pss.findAllSupervisor();  
       // int x = 0;
        
        List list = new ArrayList();
        
        for( Project p:spvlist ){
            list.add(p.getSupervisor());
      }
               
        return list;
    }
    
    public List<Project> fillTitleList() {
        prjtitlelist = pss.findAllTitle();  
       // int x = 0;
        
        List titlelist = new ArrayList();
        
        for( Project p:prjtitlelist ){
            titlelist.add(p.getTitle());
      }
               
        return titlelist;
    }
    
    public void fillbyspv() {
        spvlist = pss.findTitlebyspv(projspv);
        
        List projList = new ArrayList();
        
        for( Project p:spvlist ){
           // titlelist.add(p.getTitle());
      }
               
    }
    
}
