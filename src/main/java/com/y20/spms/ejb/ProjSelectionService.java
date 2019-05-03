/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.ProjectTopic;
import com.y20.spms.entity.Supervisor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */
@Stateless
public class ProjSelectionService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjSelectionService() {
        
    }
    
    public void updateProject(String ttl, Long spv, String stu) {
          
      Query query = em.createQuery(
      "UPDATE Project p SET student = stu, project_status = PROPOSED WHERE project_supervisor = spv and title = ttl");
      int updateProj = query.executeUpdate();
        
    }
    
    public List<Project> findAllSupervisor() {
        String query = "select p from Project p where project_status = AVAILABLE ";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        return q.getResultList();               
    }
    
    public List<Project> findAllTitle() {
        String query = "select p from Project p where project_status = AVAILABLE";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        return q.getResultList();               
    }
    
    public List<Project> findTitlebyspv(String spv) {
        String query = "select p from Project p where p.project_supervisor = spv";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        return q.getResultList();               
    }
    
    public List<Project> findspvbytitle(String title) {
        String query = "select p from Project p where p.title = title";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        return q.getResultList();                
    }
    
}
