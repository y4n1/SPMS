/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.ProjectTopic;
import com.y20.spms.entity.Supervisor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */

@Stateless
public class ProjectService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjectService() {
    }
    
    public void registerProject(String title, String description, String requiredSkills, Long spv,  Long topic) {
        //long x = 4;
        Project proj;
        ProjectTopic pt = em.getReference(ProjectTopic.class, topic);
        
        Supervisor supervisor = em.getReference(Supervisor.class, spv);
        
        
        
        proj = new Project();
        proj.setTitle(title);
        proj.setDescription(description);
        proj.setRequiredSkills(requiredSkills);
        proj.setProjectStatus(Project.ProjectStatus.AVAILABLE);
        //Set<ProjectTopic> projectTopics = new HashSet<>();
       // projectTopics.add(topic);
        proj.addTopic(pt);
        //Set<Supervisor> supervisors = new HashSet<>();
        proj.setSupervisor(supervisor);
        em.persist(proj);
        
                
       
        
        
      //  em.detach(supervisor);
        
               
        
        
    }

   // public void registerProject(String projtitle, String projdecr, String requiredskill, String projspv, String projtopic) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }
    
    public List<Supervisor> findSupervisor() {
        String query = "select spv from Supervisor spv";
        TypedQuery<Supervisor> q = em.createQuery(query, Supervisor.class);
        return q.getResultList();               
    }
    
    public List<ProjectTopic> findTopic() {
        String query = "select pt from ProjectTopic pt";
        TypedQuery<ProjectTopic> q = em.createQuery(query, ProjectTopic.class);
        return q.getResultList();               
    }
        
}
