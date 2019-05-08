/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.ProjectTopic;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */

@Stateless
@TransactionAttribute(NOT_SUPPORTED)
public class ProjectCancellationService {
    
    @PersistenceContext
    EntityManager em;
     
    private static final Logger LOGGER = Logger.getLogger(ProjectCancellationService.class.getName());
    
    public ProjectCancellationService() {
        
    }
    
    //Update Project to cancelled
    @TransactionAttribute(REQUIRED)
    public void updateProject(Long id) {
      
      Project proj1;
      
      // Update
      proj1 = em.find(Project.class, id);
      
      proj1.setProjectStatus(Project.ProjectStatus.CANCELLED);
          
      em.persist(proj1);
      em.flush();
      LOGGER.log(Level.INFO, "Project {0} is cancelled", id);
    }
       
           
    // Get project description
    public Project findprojdecr(Long projID) {
        String query = "select p from Project p where p.id = :projID "; 
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("projID", projID);
        return q.getSingleResult();
    }
    
    
    //Get all title
    public List<Project> findAllTitle() {
        Enum status;
        status = Project.ProjectStatus.RFC;
        String query = "select p from Project p where p.projectStatus = :status";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("status", status);
        return q.getResultList();               
    }
    
}
